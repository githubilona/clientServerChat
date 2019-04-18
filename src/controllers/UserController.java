package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Status;
import models.User;

import java.util.ArrayList;
import java.util.List;


public class UserController {

    @FXML private ListView<User> listView;
    @FXML private Label usernameLabel;
    @FXML private ImageView imageView;
    private List<User> list;
    private List <User> users;

// initalize vs constructor- constructor -> recognizing @FXML annotations -> initalize()
// so initalizing via constructor will not recognize @FXML annotations
    public void initialize(){
        User user1 = new User("User1", "pass1", Status.OFFLINE);
        User user2 = new User("User2", "pass2", Status.OFFLINE);

        list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        // listView holds User objects, so toString() is called to display User in each tableView field
        // by default toString() returns set of digits and characters which describe object, so overriding toString in user class,
        // to return String username instead of User object will solve that problem
        List<User> users = new ArrayList<>(LoginController.getInstance().getLoginResultMessage().getUsersHashMap().values());
        listView.getItems().setAll(users);
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }
    @FXML
    public void handleClickListView(){
        User user = listView.getSelectionModel().getSelectedItem();
        System.out.println(user.getUsername());
        usernameLabel.setText(user.getUsername());
        imageView.setImage(new Image(user.getPhoto()));

    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    //    public UserController(List<User> users) {
//        Platform.runLater(() ->{
//            new NewFrame("models/userFrame.fxml","Chat App", 800, 500);
//            setList(users);
//
//        });
//        }
//
//    @FXML
//    public void setList(List<User> users){
//        for(User user : users){
//            System.out.println("User list:------------");
//            System.out.println(user.getUsername());
//            listView.getItems().add(user.getUsername());
//        }
//          //  listView.getItems().addAll(users.);
//            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//
//    }

}
