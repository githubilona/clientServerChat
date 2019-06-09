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


public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;

    private  Message loginResultMessage;
    private static LoginController instance; //= new LoginController();

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    /**
     * Invoked when login button was pressed
     * @param event login button was pressed
     * @throws IOException
     */
    @FXML
    public void loginAction(ActionEvent event) throws IOException {
        String username=usernameTextField.getText().trim();
        String password=passwordTextField.getText().trim();
        System.out.println(username);
        System.out.println(password);

        User newUser = new User(username, password , Status.ONLINE);
        Message loginMessage = new Message(newUser, MessageType.LOGIN);
// TODO delete getInstance( from the constructor, it's no loger needed since LoginController in ClientThread is accesed "via singleton"
        ClientThread clientThread = new ClientThread(loginMessage, getInstance());
        clientThread.start();
//_____________________________________________________________________________________________
        // TODO execute the code below when the value in loginResultMessage will be set by the ClientThread
        // ClientThread have to work continuously to handle server messages, so it cant't be suspended i.e by join()- which
        // waits until one thread finish execution

        try {
            Thread.sleep(5000);  // 1:19 18.04. - ppczekac az ClientThread wykona sie i w LoginControler polu Message ustawi wartosc
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(loginResultMessage!=null){

            System.out.println(loginResultMessage + "   "+ loginResultMessage.getUser() );
            System.out.println("                loginResultMessage!=null");
        }

       if(loginResultMessage.getMessageType().equals(MessageType.LOGIN_SUCCESSFULL)){
            Parent parent = FXMLLoader.load(getClass().getResource("../views/userView.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

         //  clientThread.setMessageToServer(new Message(loginResultMessage.getSender(), MessageType.UPDATE_USER_LIST));
       }else {
           //TODO display information in login frame, that username or password is incorrect
           System.out.println("username or password is incorrect");
       }
    }

    /**
     * Invoked when a user wants to create a new account. Register frame is shown.
     */
    @FXML
    public void registerAction(){
        new NewFrame("views/registerView.fxml","Register", 500, 400);
    }

    /**
     * Sets loginResultMessage value (LOGIN_SUCCESSFUL or LOGIN_FAILED) to the this class field
     * @param loginResultMessage Message containing information with LOGIN_SUCCESSFUL-password and username were correct
     *                           or LOGIN_FAILED-user with entered username and password wasn't found in server's user list.
     */
    public void setLoginResultMessage(Message loginResultMessage) {
        this.loginResultMessage= loginResultMessage;
    }

    public Message getLoginResultMessage() {
        return loginResultMessage;
    }
}
