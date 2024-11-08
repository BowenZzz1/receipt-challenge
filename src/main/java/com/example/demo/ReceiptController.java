package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final Map<String, Integer> receiptPoints = new HashMap<>();

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        String id = UUID.randomUUID().toString();
        int points = calculatePoints(receipt);
        receiptPoints.put(id, points);

        Map<String, String> response = new HashMap<>();
        response.put("id", id);
        return response;
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        Map<String, Integer> response = new HashMap<>();
        response.put("points", receiptPoints.getOrDefault(id, 0));
        return response;
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: Points for alphanumeric characters in retailer name
        points += receipt.getRetailer().replaceAll("[^A-Za-z0-9]", "").length();

        // Rule 2: 50 points if total is a round dollar
        if (receipt.getTotal() == Math.floor(receipt.getTotal())) {
            points += 50;
        }

        // Rule 3: 25 points if total is a multiple of 0.25
        if (receipt.getTotal() % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: Points based on item description length
        for (Receipt.Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                points += (int) Math.ceil(item.getPrice() * 0.2);
            }
        }

        // Rule 6: 6 points if the purchase day is odd
        int day = LocalDate.parse(receipt.getPurchaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfMonth();
        if (day % 2 == 1) {
            points += 6;
        }

        // Rule 7: 10 points if time is between 2:00pm and 4:00pm
        LocalTime time = LocalTime.parse(receipt.getPurchaseTime(), DateTimeFormatter.ofPattern("HH:mm"));
        if (!time.isBefore(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}
