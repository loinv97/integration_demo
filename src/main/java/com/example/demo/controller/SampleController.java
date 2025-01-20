package com.example.demo.controller;

import com.example.demo.Employee;
import com.example.demo.dto.BatchRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // New: Get all PR/PO records
    @GetMapping("/pr-po")
    public List<Map<String, Object>> getAllPRPORecords() {
        String sql = "SELECT * FROM PR_PO_TABLE";
        return jdbcTemplate.queryForList(sql);  // Executes the query and returns the result as a list of maps
    }

    // New: Get PR/PO record by ID
    @GetMapping("/pr-po/{id}")
    public Map<String, Object> getPRPORecordById(@PathVariable Long id) {
        String sql = "SELECT * FROM PR_PO_TABLE WHERE ID = ?";
        return jdbcTemplate.queryForMap(sql, id);  // Fetch a single row as a map
    }

    // New: Create a new PR/PO record
    @PostMapping("/pr-po")
    public String createPRPORecord(@RequestBody Map<String, Object> prpoRecord) {
        String sql = "INSERT INTO PR_PO_TABLE (Requester_Name, Requester_Department, Delivering_Factory, Receiving_Factory, " +
                "Document_Number, Receiving_Department, Date, No, Description, Quantity_Out, Quantity_In, Remarks, Image_Path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql,
                prpoRecord.get("Requester_Name"), prpoRecord.get("Requester_Department"),
                prpoRecord.get("Delivering_Factory"), prpoRecord.get("Receiving_Factory"),
                prpoRecord.get("Document_Number"), prpoRecord.get("Receiving_Department"),
                prpoRecord.get("Date"), prpoRecord.get("No"), prpoRecord.get("Description"),
                prpoRecord.get("Quantity_Out"), prpoRecord.get("Quantity_In"),
                prpoRecord.get("Remarks"), prpoRecord.get("Image_Path"));
        return result > 0 ? "PR/PO record created successfully" : "Failed to create PR/PO record";
    }

    // New: Update PR/PO record
    @PutMapping("/pr-po/{id}")
    public String updatePRPORecord(@PathVariable Long id, @RequestBody Map<String, Object> prpoRecord) {
        String sql = "UPDATE PR_PO_TABLE SET Requester_Name = ?, Requester_Department = ?, Delivering_Factory = ?, " +
                "Receiving_Factory = ?, Document_Number = ?, Receiving_Department = ?, Date = ?, No = ?, " +
                "Description = ?, Quantity_Out = ?, Quantity_In = ?, Remarks = ?, Image_Path = ? WHERE ID = ?";
        int result = jdbcTemplate.update(sql,
                prpoRecord.get("Requester_Name"), prpoRecord.get("Requester_Department"),
                prpoRecord.get("Delivering_Factory"), prpoRecord.get("Receiving_Factory"),
                prpoRecord.get("Document_Number"), prpoRecord.get("Receiving_Department"),
                prpoRecord.get("Date"), prpoRecord.get("No"), prpoRecord.get("Description"),
                prpoRecord.get("Quantity_Out"), prpoRecord.get("Quantity_In"),
                prpoRecord.get("Remarks"), prpoRecord.get("Image_Path"), id);
        return result > 0 ? "PR/PO record updated successfully" : "Failed to update PR/PO record";
    }

    // New: Delete PR/PO record by ID
    @DeleteMapping("/pr-po/{id}")
    public String deletePRPORecord(@PathVariable Long id) {
        String sql = "DELETE FROM PR_PO_TABLE WHERE ID = ?";
        int result = jdbcTemplate.update(sql, id);
        return result > 0 ? "PR/PO record deleted successfully" : "Failed to delete PR/PO record";
    }

    // CREATE: Insert a new user into the SAP HANA database
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String email) {
        String sql = "INSERT INTO USERS (NAME, EMAIL) VALUES (?, ?)";
//        jdbcTemplate.update(sql, name, email); // Execute the insert
        return ResponseEntity.ok("User created successfully");
    }

    // READ: Get all users from SAP HANA and return as JSON
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        String sql = "SELECT * FROM EMPLOYEES";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
        return ResponseEntity.ok(users); // Return the result as JSON
    }

    @PostMapping("/usersSAP")
    public ResponseEntity<String> createUserSAP1(@RequestBody Map<String, Object> payload) {
        // Extract values from the Map
        Integer empId =  (Integer) payload.get("EMP_ID");
        String empName = (String) payload.get("EMP_NAME");
        String department = (String) payload.get("DEPARTMENT");
        String joinDate = (String) payload.get("JOIN_DATE");

        // SQL query with explicit column names
        String sql = "INSERT INTO EMPLOYEES (EMP_ID, EMP_NAME, DEPARTMENT, JOIN_DATE) VALUES (?, ?, ?, ?)";

        // Execute the insert statement using parameterized query
        int rowsInserted = jdbcTemplate.update(sql, empId, empName, department, joinDate);

        // Return the appropriate response based on the number of rows affected
        if (rowsInserted > 0) {
            return ResponseEntity.ok("User created successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to create user");
        }
    }


    @GetMapping("/hello")
    public String sayHello() {
        logger.info("Received GET request for /hello");
        return "CPU SAPConnect";
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
