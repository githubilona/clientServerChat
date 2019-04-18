package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;


public class UserController {
    private List<User> list;
    @FXML private ListView<User> listView;
    @FXML private TextArea textArea;
    private List <User> users;

// initalize vs constructor- constructor -> recognizing @FXML annotations -> initalize()
// so initalizing via constructor will not recognize @FXML annotations
    public void initialize(){
        User user1 = new User("User1", "pass1", Status.OFFLINE);
        User user2 = new User("User2", "pass2", Status.OFFLINE);

        list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        List<User> users = new ArrayList<>(LoginController.getInstance().getLoginResultMessage().getUsersHashMap().values());
        listView.getItems().setAll(users);
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }
    @FXML
    public void handleClickListView(){
        User user = listView.getSelectionModel().getSelectedItem();
        System.out.println(user.getUsername());
        textArea.setText(user.getUsername());


    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    //    public UserController(List<User> users) {
//        Platform.runLater(() ->{
//            new NewFrame("sample/userFrame.fxml","Chat App", 800, 500);
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
