package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Message;
import models.NewFrame;
import models.PeerServer2;
import models.User;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Singleton class
 * Handles actions invoked on userView
 * Displays the list of currently logged in users, enabling another user to select a person that he/she wants to chat with
 *
 */
public class UserController {

    @FXML
    private ListView<User> listView;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private Button selectButton;
    private ObservableList<User> observableList;
    private Message messageFromServer;
    private static UserController instance; // =new UserController();

    public UserController() {
        instance = this;
    }

    public static UserController getInstance() {
        return instance;
    }

    // initalize vs constructor- constructor -> recognizing @FXML annotations -> initalize()
    // so initalizing via constructor will not recognize @FXML annotations

    /**
     * Initializes a chat frame by setting a user list
     * Configuring this logged user to act as a server in peer to peer model by assigning a port
     * Then the peer server starts working
     */
    @FXML
    public void initialize() {
        messageFromServer = LoginController.getInstance().getLoginResultMessage();
        List<User> users = messageFromServer.getUsers();
        observableList = FXCollections.observableArrayList(users);
        listView.setItems(observableList);

        User sender = messageFromServer.getUser();
        PeerServer2 peerServer2 = new PeerServer2();
        System.out.println("sender port " + sender.getPort());
        peerServer2.setPort(sender.getPort());
        Thread peerServerThread2 = new Thread(peerServer2);
        peerServerThread2.start();
        // listView holds User objects, so toString() is called to display User in each tableView field
        // by default toString() returns set of digits and characters which describe object, so overriding toString in user class,
        // to return String username instead of User object will solve that problem
    }

    /**
     * Display information(image, name) about selected user form the list
     */
    @FXML
    public void handleClickListView() {
        User user = listView.getSelectionModel().getSelectedItem();
        usernameLabel.setText(user.getUsername());
        imageView.setImage(new Image(user.getPhoto()));
        System.out.println("Selected user : " + user.getUsername() + " port : " + user.getPort());
    }

    /**
     * Open a chat frame to be able to chat with selected user
     * @param event select button was pressed
     */
    @FXML
    public void selectButtonAction(ActionEvent event) {
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        User sender = messageFromServer.getUser();
        new NewFrame("views/chatView.fxml", selectedUser.getUsername(), 800, 600);
        ChatController.getInstance().setReceiver(selectedUser);
        ChatController.getInstance().setSender(sender);
        try {
            ChatController.getInstance().readMeassage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        PeerServer2 peerServer2 = new PeerServer2();
//        System.out.println("receiver port "+ selectedUser.getPort() + "sender port "+ sender.getPort());
//        peerServer2.setPort(sender.getPort());
//              Thread peerServerThread2 = new Thread(peerServer2);
//        peerServerThread2.start();
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/views/chatView.fxml"));
//            Scene scene = new Scene(parent);
//            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    public void setUserList(Message msg) {

//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Platform.runLater(() -> {
            ObservableList<User> users = FXCollections.observableList(msg.getUsers());
            listView.setItems(users);
        });

    }
}
