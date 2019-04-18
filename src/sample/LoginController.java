package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Stage stage;
    private Message loginResultMessage;

    public static LoginController con;

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
        Message loginMessage = new Message(newUser,MessageType.LOGIN);

//        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("loginFrame.fxml"));
//        Parent window1 = (Pane) fmxlLoader.load();
//        con = fmxlLoader.<LoginController>getController();

        ClientThread clientThread = new ClientThread(loginMessage, getInstance());
        clientThread.start();
//_____________________________________________________________________________________________
        // TODO
        try {
            Thread.sleep(5000);  // 1:19 18.04. - ppczekac az ClientThread wykona sie i w LoginControler polu Message ustawi wartosc
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
            //                Parent parent = FXMLLoader.load(getClass().getResource("userFrame.fxml"));
//                Scene scene = new Scene(parent);
//                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//                stage.setScene(scene);
//                stage.show();

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
            new NewFrame("sample/userFrame.fxml","title", 500,600);

        }



        //   new Server().getClientThreads().add(clientThread);
//        System.out.println("login action");
//        if(username.equals("123") && password.equals("123")){
//            new NewFrame("sample/sample.fxml","ClientThread frame", 500, 400);
//            // create new ClientThread
//        }

    }
//    public void loginButtonAction() throws IOException {
//        String hostname = hostnameTextfield.getText();
//        int port = Integer.parseInt(portTextfield.getText());
//        String username = usernameTextfield.getText();
//        String picture = selectedPicture.getText();
//
//        FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/ChatView.fxml"));
//        Parent window = (Pane) fmxlLoader.load();
//        con = fmxlLoader.<ChatController>getController();
//        Listener listener = new Listener(hostname, port, username, picture, con);
//        Thread x = new Thread(listener);
//        x.start();
//        this.scene = new Scene(window);
//    }
    @FXML
    public void registerAction(){
        new NewFrame("sample/registerFrame.fxml","Register", 500, 400);

    }

    public void updateFrame(){
        Platform.runLater(() ->{
//            Parent root = null;
//            try {
//                root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//                stage.setScene(new Scene(root));
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        });
    }

    public Message getLoginResultMessage() {
        return loginResultMessage;
    }

    public void setLoginResultMessage(Message loginResultMessage) {
        System.out.println("            setLoginResultMessage");
        this.loginResultMessage = loginResultMessage;
        System.out.println(loginResultMessage.getMessageType() + "//././././././././././././././././././././/./././");
    }

    //    public void showUserFrame(Message loginResultMessage) {
//
//        MessageType messageType = loginResultMessage.getMessageType();
//        List<User> users = new ArrayList<>(loginResultMessage.getUsersHashMap().values());
//        for (User user : users) {
//            System.out.println("..............." + user.getUsername());
//
//        }
//
//        if (messageType.equals(MessageType.LOGIN_SUCCESSFULL)) {
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/userFrame.fxml"));
//            //  Parent root = (Parent) loader.load();
//
//            UserController userController = loader.<UserController>getController();
////                Object load = loader.load();
//            // UserController userController = loader.getController();
//            userController.setUsers(users);
//
//        }
//    }

}
