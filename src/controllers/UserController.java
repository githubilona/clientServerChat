package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Message;
import models.Status;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserController {

    @FXML private ListView<User> listView;
    @FXML private Label usernameLabel;
    @FXML private ImageView imageView;
    private List<User> list;
    private List <User> users;
    private ObservableList<User> observableList;

    @FXML private ListView userList;

    private static UserController instance; // =new UserController();

    public UserController() {
        instance = this;
    }

    public static UserController getInstance() {
        return instance;
    }

// initalize vs constructor- constructor -> recognizing @FXML annotations -> initalize()
// so initalizing via constructor will not recognize @FXML annotations
    public void initialize(){
//       qaz    qaz

        Message message = LoginController.getInstance().getLoginResultMessage();
        List<User> users = message.getUsers();
        observableList = FXCollections.observableArrayList(users);
        listView.setItems(observableList);


        // listView holds User objects, so toString() is called to display User in each tableView field
        // by default toString() returns set of digits and characters which describe object, so overriding toString in user class,
        // to return String username instead of User object will solve that problem
       // observableList.addListener();
//        Platform.runLater(() -> {
//        List<User> users = new ArrayList<>(LoginController.getInstance().getUpdateUserListMessage().getUsersHashMap().values());
//            observableList =FXCollections.observableArrayList (users);
//        listView.setItems(observableList);
//        this.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        });

    }
    @FXML
    public void handleClickListView(){
        User user = listView.getSelectionModel().getSelectedItem();
        System.out.println(user.getUsername());
        usernameLabel.setText(user.getUsername());
        imageView.setImage(new Image(user.getPhoto()));

    }

//    public void setUserList1(Message msg) {
//      //  logger.info("setUserList() method Enter");
//       // Platform.runLater(() -> {
//            System.out.println(                     "????? "+msg.getMessageType() +" "+ msg.getUser());
//            for(User u: msg.getUsersHashMap().values()){
//                System.out.println("///////"+ u.getUsername());
//
//            }
//            List<User> users=new ArrayList<User>(msg.getUsersHashMap().values());
//            this.observableList =FXCollections.observableArrayList (new ArrayList<User>(msg.getUsersHashMap().values()));
//            listView.setItems(observableList);
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            // listView.setCellFactory(new CellRenderer());
//            // setOnlineLabel(String.valueOf(msg.getUserlist().size()));
//       // });
//       // logger.info("setUserList() method Exit");
//    }
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
//
//    public ObservableList<User> getObservableList() {
//        return observableList;
//    }
//
//    public void setObservableList(HashMap<String, User> hashMap) {
//        // Platform.runLater(() -> {
//        this.observableList = FXCollections.observableArrayList(hashMap.values());
//        listView.setItems(observableList);
//        // });
//    }
//    //    public UserController(List<User> users) {
////        Platform.runLater(() ->{
////            new NewFrame("models/userView.fxml","Chat App", 800, 500);
////            setList(users);
////
////        });
////        }
////
////    @FXML
////    public void setList(List<User> users){
////        for(User user : users){
////            System.out.println("User list:------------");
////            System.out.println(user.getUsername());
////            listView.getItems().add(user.getUsername());
////        }
////          //  listView.getItems().addAll(users.);
////            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
////
////    }


    public void setUserList(Message msg) {

//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Platform.runLater(() -> {
            ObservableList<User> users = FXCollections.observableList(msg.getUsers());
            listView.setItems(users);
           // userList.setCellFactory(new CellRenderer());

        });

    }
}
