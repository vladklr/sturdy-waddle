package com.example.fahrradverwaltung;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserManager {

    private static final String USER_FILE = "users.json";
    private static final String EMPLOYEE_FILE = "employees.json";

    public UserManager() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.write(new JSONArray().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean registerUser(String username, String password, String firstName, String lastName, String email, String balance) {
        try {
            // Read existing users
            JSONArray users = new JSONArray(new String(Files.readAllBytes(Paths.get(USER_FILE))));
            JSONArray employees = new JSONArray(new String(Files.readAllBytes(Paths.get(EMPLOYEE_FILE))));

            // Check if username already exists
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(username)) {
                    return false;  // Username is already taken
                }
            }

            for (int i = 0; i < employees.length(); i++) {
                JSONObject employee = employees.getJSONObject(i);
                System.out.println(employee.getString("username"));
                if (employee.getString("username").equals(username)) {
                    return false;  // Username is already taken
                }
            }

            // If username is not taken, add new user
            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("first_name", firstName);
            newUser.put("last_name", lastName);
            newUser.put("email", email);
            newUser.put("balance", balance);
            users.put(newUser);

            // Write updated user list
            try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
                writer.write(users.toString());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JSONObject loginUser(String username, String password) {
        try {
            // Read existing users
            JSONArray users = new JSONArray(new String(Files.readAllBytes(Paths.get(USER_FILE))));

            // Check if user exists and password matches
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(username) && user.getString("password").equals(password)) {
                    return user;
                }
            }

            return null;  // User not found or password does not match
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean loginEmployee(String username, String password) {
        try {
            // Read existing users
            JSONArray employees = new JSONArray(new String(Files.readAllBytes(Paths.get(EMPLOYEE_FILE))));

            // Check if user exists and password matches
            for (int i = 0; i < employees.length(); i++) {
                JSONObject employee = employees.getJSONObject(i);
                if (employee.getString("username").equals(username) && employee.getString("password").equals(password)) {
                    return true;
                }
            }

            return false;  // User not found or password does not match
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String username) {
        try {
            JSONArray users = new JSONArray(new String(Files.readAllBytes(Paths.get(USER_FILE))));
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveUserData(Client userToSave) {
        try {
            // Read existing users
            JSONArray users = new JSONArray(new String(Files.readAllBytes(Paths.get(USER_FILE))));

            // Find and update the user
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(userToSave.getUsername())) {
                    // Replace the user data
                    user.put("username", userToSave.getUsername());
                    user.put("password", userToSave.getPassword());
                    user.put("first_name", userToSave.getFirst_name());
                    user.put("last_name", userToSave.getLast_name());
                    user.put("email", userToSave.getEmail());
                    user.put("balance", userToSave.getBalance());

                    break;
                }
            }

            // Write back to the file
            Files.write(Paths.get(USER_FILE), users.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
