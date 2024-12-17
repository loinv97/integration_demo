package com.example.demo.controller;

import com.example.demo.dto.BatchRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    // Sample GET method to say hello
    @GetMapping("/hello")
    public String sayHello() {
        logger.info("Received GET request for /hello");
        return "Hello, World!";
    }

    // Sample POST method to greet a user
    @PostMapping("/greet")
    public String greet(@RequestBody String name) {
        logger.info("Received POST request for /greet with name: {}", name);
        return "Hello, " + name + "!";
    }

    // Handle GET requests for a single item
    @GetMapping("/{id}")
    public ResponseEntity<String> handleGet(@PathVariable("id") Long id) {
        logger.info("Received GET request for ID: {}", id);
        return ResponseEntity.ok("GET request processed for ID: " + id);
    }

    // Handle POST requests to create an item
    @PostMapping
    public ResponseEntity<String> handlePost(@RequestBody Map<String, Object> payload) {
        logger.info("Received POST request with payload: {}", payload);
        return ResponseEntity.ok("POST request processed with payload: " + payload);
    }

    // Handle PUT requests to update an item
    @PutMapping("/{id}")
    public ResponseEntity<String> handlePut(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        logger.info("Received PUT request for ID: {} with payload: {}", id, payload);
        return ResponseEntity.ok("PUT request processed for ID: " + id + " with payload: " + payload);
    }

    // Handle PATCH requests for partial update of an item
    @PatchMapping("/{id}")
    public ResponseEntity<String> handlePatch(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        logger.info("Received PATCH request for ID: {} with payload: {}", id, payload);
        return ResponseEntity.ok("PATCH request processed for ID: " + id + " with payload: " + payload);
    }

    // Handle DELETE requests for an item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleDelete(@PathVariable("id") Long id) {
        logger.info("Received DELETE request for ID: {}", id);
        return ResponseEntity.ok("DELETE request processed for ID: " + id);
    }

    // Handle OPTIONS requests (to fetch allowed methods)
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions(@PathVariable("id") Long id) {
        logger.info("Received OPTIONS request for ID: {}", id);
        return ResponseEntity.ok().build();
    }

    // Handle GET requests for all items (e.g., list of records)
    @GetMapping
    public ResponseEntity<String> handleGetAll() {
        logger.info("Received GET request for all items");
        return ResponseEntity.ok("GET request processed for all items");
    }

    // Handle batch operations (POST)
    @PostMapping("/batch")
    public ResponseEntity<String> handleBatchOperations(@RequestBody BatchRequestDTO batchRequest) {
        logger.info("Received batch request: {}", batchRequest);

        StringBuilder response = new StringBuilder();

        // Process Create operations
        if (batchRequest.getCreate() != null) {
            batchRequest.getCreate().forEach(item -> {
                logger.info("Processing CREATE operation for: {}", item);
                response.append("Created: ").append(item).append("\n");
            });
        }

        // Process Update operations
        if (batchRequest.getUpdate() != null) {
            batchRequest.getUpdate().forEach(item -> {
                logger.info("Processing UPDATE operation for: {}", item);
                response.append("Updated: ").append(item).append("\n");
            });
        }

        // Process Delete operations
        if (batchRequest.getDelete() != null) {
            batchRequest.getDelete().forEach(id -> {
                logger.info("Processing DELETE operation for ID: {}", id);
                response.append("Deleted ID: ").append(id).append("\n");
            });
        }

        // Process Patch operations
        if (batchRequest.getPatch() != null) {
            batchRequest.getPatch().forEach(item -> {
                logger.info("Processing PATCH operation for: {}", item);
                response.append("Patched: ").append(item).append("\n");
            });
        }

        logger.info("Batch request processed successfully.");
        return ResponseEntity.ok(response.toString());
    }
}
