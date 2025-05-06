package ap.mni.controllers;

import ap.mni.CRUDApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ap.mni.controllers.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;  // Username input field
    
    @FXML
    private PasswordField passwordField;  // Password input field
    
    @FXML
    private Label errorLabel;  // Error message display
    
    @FXML
    private void handleLogin(ActionEvent event) {  // Process login attempt
        String username = usernameField.getText().trim();  // Get username input
        String password = passwordField.getText().trim();  // Get password input
        
        if (username.isEmpty() || password.isEmpty()) {  // Check empty fields
            errorLabel.setText("Please enter both username and password.");  // Show validation message
            return;  // Stop execution
        }
        
        if (isValidUser(username, password)) {  // Validate user credentials
            try {
                CRUDApp.showHomeView();  // Show main application
            } catch (IOException e) {  // Handle errors
                e.printStackTrace();  // Print error details
            }
        } else {
            errorLabel.setText("Invalid username or password!");  // Show authentication error
        }
    }
    
    private boolean isValidUser(String username, String password) {  // Verify user credentials
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";  // Prepare authentication query
        
        try (Connection conn = DBConnection.getConnection();  // Connect to database
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Create prepared statement
            
            stmt.setString(1, username);  // Set username parameter
            stmt.setString(2, password);  // Set password parameter
            
            ResultSet rs = stmt.executeQuery();  // Execute query
            return rs.next();  // Check results exist
            
        } catch (SQLException e) {  // Handle database errors
            e.printStackTrace();  // Print error details
            return false;  // Return authentication failure
        }
    }
}