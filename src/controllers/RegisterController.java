package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import models.*;


public class RegisterController {
    @FXML private Button registerButton;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label errorLabel;

    /**
     * Creates new user and
     * Closes the registerFrame once the register button is pressed
     */
    @FXML
    public void registerAction() {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        System.out.println(username);
        System.out.println(password);

        User user = new User(username, password, Status.OFFLINE);
        Message registerMessage = new Message(user, MessageType.REGISTER);

        System.out.println("new ClientThread(registerMessage)");
        new ClientThread(registerMessage, this).start();

    }

    public void updateFrame(MessageType registrationResult){
        if(registrationResult.equals(MessageType.USER_REGISTERED)){
            System.out.println("New user was registered!");

            Platform.runLater(() ->{
               usernameTextField.setEditable(false);
               passwordTextField.setEditable(false);
               errorLabel.setText("New user was registered! ");
               errorLabel.setTextFill(Color.FORESTGREEN);

            });
        }else if(registrationResult.equals(MessageType.USER_NOT_REGISTERED_DUPLICATE)){
            System.out.println("User not registered, check if entered data is correct");
            Platform.runLater(() ->{
                errorLabel.setText("User already exists! ");
                errorLabel.setVisible(true);
            });

        }else if(registrationResult.equals(MessageType.USER_NOT_REGISTERED_WRONG_FORMAT)){
            Platform.runLater(() ->{
                errorLabel.setVisible(true);
                errorLabel.setText("Username and password should have more than 3 characters which are letters or digits ");
            });

        }
    }
}
