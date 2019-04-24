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
import models.User;

import java.util.List;


public class UserController {

    @FXML private ListView<User> listView;
    @FXML private Label usernameLabel;
    @FXML private ImageView imageView;
    @FXML private Button selectButton;
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
    @FXML
    public void initialize(){
        messageFromServer = LoginController.getInstance().getLoginResultMessage();
        List<User> users = messageFromServer.getUsers();
        observableList = FXCollections.observableArrayList(users);
        listView.setItems(observableList);

        // listView holds User objects, so toString() is called to display User in each tableView field
        // by default toString() returns set of digits and characters which describe object, so overriding toString in user class,
        // to return String username instead of User object will solve that problem
    }

    @FXML
    public void handleClickListView(){
        User user = listView.getSelectionModel().getSelectedItem();
        usernameLabel.setText(user.getUsername());
        imageView.setImage(new Image(user.getPhoto()));
        System.out.println("Selected user : "+user.getUsername());
    }
    @FXML
    public void selectButtonAction(ActionEvent event){
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        User sender = messageFromServer.getUser();
        new NewFrame("views/chatView.fxml", selectedUser.getUsername(), 800, 600);
        ChatController.getInstance().setReceiver(selectedUser);
        ChatController.getInstance().setSender(sender);
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
