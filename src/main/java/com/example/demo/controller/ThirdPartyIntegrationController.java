package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/third-party")
public class ThirdPartyIntegrationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // GET: Get all users
    @GetMapping("/users")
    public List<Map<String, Object>> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.queryForList(sql);  // Executes the query and returns the result as a list of maps
    }

    // GET: Get user by ID
    @GetMapping("/users/{userId}")
    public Map<String, Object> getUserById(@PathVariable Long userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForMap(sql, userId);  // Fetch a single row as a map
    }

    // POST: Create a new user
    @PostMapping("/users")
    public String createUser(@RequestBody Map<String, Object> user) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        int result = jdbcTemplate.update(sql, user.get("username"), user.get("email"));
        return result > 0 ? "User created successfully" : "Failed to create user";
    }

    // PUT: Update user details
    @PutMapping("/users/{userId}")
    public String updateUser(@PathVariable Long userId, @RequestBody Map<String, Object> user) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE user_id = ?";
        int result = jdbcTemplate.update(sql, user.get("username"), user.get("email"), userId);
        return result > 0 ? "User updated successfully" : "Failed to update user";
    }

    // DELETE: Delete user by ID
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        int result = jdbcTemplate.update(sql, userId);
        return result > 0 ? "User deleted successfully" : "Failed to delete user";
    }

    // GET: Get all requests
    @GetMapping("/requests")
    public List<Map<String, Object>> getAllRequests() {
        String sql = "SELECT * FROM requests";
        return jdbcTemplate.queryForList(sql);  // Executes the query and returns the result as a list of maps
    }

    // GET: Get requests by user ID
    @GetMapping("/requests/user/{userId}")
    public List<Map<String, Object>> getRequestsByUserId(@PathVariable Long userId) {
        String sql = "SELECT * FROM requests WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, userId);  // Executes the query with the parameter userId
    }

    // POST: Create a new request
    @PostMapping("/requests")
    public String createRequest(@RequestBody Map<String, Object> request) {
        String sql = "INSERT INTO requests (user_id, endpoint, request_payload, headers) VALUES (?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, request.get("user_id"), request.get("endpoint"),
                request.get("request_payload"), request.get("headers"));
        return result > 0 ? "Request created successfully" : "Failed to create request";
    }

    // PUT: Update a request
    @PutMapping("/requests/{requestId}")
    public String updateRequest(@PathVariable Long requestId, @RequestBody Map<String, Object> request) {
        String sql = "UPDATE requests SET endpoint = ?, request_payload = ?, headers = ? WHERE request_id = ?";
        int result = jdbcTemplate.update(sql, request.get("endpoint"), request.get("request_payload"),
                request.get("headers"), requestId);
        return result > 0 ? "Request updated successfully" : "Failed to update request";
    }

    // DELETE: Delete request by ID
    @DeleteMapping("/requests/{requestId}")
    public String deleteRequest(@PathVariable Long requestId) {
        String sql = "DELETE FROM requests WHERE request_id = ?";
        int result = jdbcTemplate.update(sql, requestId);
        return result > 0 ? "Request deleted successfully" : "Failed to delete request";
    }

    // Similar methods for other entities (responses, API endpoints, etc.)

    // GET: Get all responses
    @GetMapping("/responses")
    public List<Map<String, Object>> getAllResponses() {
        String sql = "SELECT * FROM responses";
        return jdbcTemplate.queryForList(sql);  // Executes the query and returns the result as a list of maps
    }

    // POST: Create a new response
    @PostMapping("/responses")
    public String createResponse(@RequestBody Map<String, Object> response) {
        String sql = "INSERT INTO responses (request_id, response_payload, status_code) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, response.get("request_id"), response.get("response_payload"),
                response.get("status_code"));
        return result > 0 ? "Response created successfully" : "Failed to create response";
    }

    // PUT: Update a response
    @PutMapping("/responses/{responseId}")
    public String updateResponse(@PathVariable Long responseId, @RequestBody Map<String, Object> response) {
        String sql = "UPDATE responses SET response_payload = ?, status_code = ? WHERE response_id = ?";
        int result = jdbcTemplate.update(sql, response.get("response_payload"), response.get("status_code"), responseId);
        return result > 0 ? "Response updated successfully" : "Failed to update response";
    }

    // DELETE: Delete response by ID
    @DeleteMapping("/responses/{responseId}")
    public String deleteResponse(@PathVariable Long responseId) {
        String sql = "DELETE FROM responses WHERE response_id = ?";
        int result = jdbcTemplate.update(sql, responseId);
        return result > 0 ? "Response deleted successfully" : "Failed to delete response";
    }
}
