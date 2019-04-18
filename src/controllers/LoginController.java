package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Stage stage;
    private Message loginResultMessage;

    private static LoginController instance= new LoginController();

    public LoginController() {
        //instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    @FXML
    public void loginAction(ActionEvent event) throws IOException {
        stage = (Stage) loginButton.getScene().getWindow();
        String username=usernameTextField.getText().trim();
        String password=passwordTextField.getText().trim();
        System.out.println(username);
        System.out.println(password);

        User newUser = new User(username, password , Status.ONLINE);
        Message loginMessage = new Message(newUser, MessageType.LOGIN);

//        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("loginFrame.fxml"));
//        Parent window1 = (Pane) fmxlLoader.load();
//        con = fmxlLoader.<LoginController>getController();

        ClientThread clientThread = new ClientThread(loginMessage, getInstance());
        clientThread.start();
//_____________________________________________________________________________________________
        // TODO
        try {
            Thread.sleep(4000);  // 1:19 18.04. - ppczekac az ClientThread wykona sie i w LoginControler polu Message ustawi wartosc
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(getInstance().loginResultMessage!=null){

            System.out.println(loginResultMessage);
            System.out.println("dchnjhvb buer felhrfc  eruf berlhb eurnppadfve  wfqerih eivn");
        }
        System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn n n n   n n ");
        MessageType messageType = getInstance().getLoginResultMessage().getMessageType();
        System.out.println("                    "+messageType);
        if(messageType.equals(MessageType.LOGIN_SUCCESSFULL)){

            List<User> users = new ArrayList<>(getInstance().loginResultMessage.getUsersHashMap().values());
            for (User user : users) {
                System.out.println("..............." + user.getUsername());
            }

            Parent parent = FXMLLoader.load(getClass().getResource("../views/userFrame.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("userFrame.fxml"));
//                Parent parent1 = loader.load();
//
//                Scene scene1 = new Scene(parent1);
//
//                //access the controller and call a method
//                UserController userController = loader.getController();
//                userController.setUsers(users);
//
//                //This line gets the Stage information
//                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//                window.setScene(scene1);
//                window.show();
           // new NewFrame("models/userFrame.fxml","title", 500,600);

        }



    }

    @FXML
    public void registerAction(){
        new NewFrame("views/registerFrame.fxml","Register", 500, 400);

    }

    public Message getLoginResultMessage() {
        return loginResultMessage;
    }

    public void setLoginResultMessage(Message loginResultMessage) {
        System.out.println("            setLoginResultMessage");
        this.loginResultMessage = loginResultMessage;
        System.out.println(loginResultMessage.getMessageType() + "//././././././././././././././././././././/./././");
    }

}
