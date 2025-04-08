package ap.mni.controllers;

import ap.mni.CRUDApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private final Map<String, String> users = new HashMap<>() {{
        put("Christina", "C123");
        put("Charbel", "C321");
        put("Michael", "M123");
<<<<<<< HEAD
        put("Jimmy", "J1234");
=======
        put("Jimmy", "J123");
       
>>>>>>> df9a2aa0ab9fc8cb5885d046818e29f87fb03ca6
    }};

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            try {
                CRUDApp.showHomeView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid username or password!");
        }
    }
}
