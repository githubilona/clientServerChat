package models;

import controllers.LoginController;
import controllers.RegisterController;
import controllers.UserController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread {
    private User user;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Message messageToServer;
    private RegisterController registerController;
    private LoginController loginController;
    private UserController userController;
    private Stage stage;


    public ClientThread(String username, String password){
        this.user=new User(username,password,Status.ONLINE);
        System.out.println(username +"------"+ password);

    }
    public ClientThread(String username, String password, Message messageToServer){
        this.user=new User(username,password,Status.ONLINE);
        System.out.println(username +"------"+ password);
        this.messageToServer = messageToServer;

    }
    public ClientThread(Message messageToServer){

        this.messageToServer=messageToServer;
    }

    public ClientThread(Message messageToServer, Stage stage){
        this.messageToServer=messageToServer;
        this.stage=stage;
    }
    // TODO find solution how to replace all these constructors with one generic
    public ClientThread(Message messageToServer, RegisterController registerController){
        this.messageToServer=messageToServer;
        this.registerController=registerController;
    }
    public ClientThread(Message messageToServer, LoginController loginController){
        this.messageToServer=messageToServer;
        this.loginController=loginController;
    }

    /**
     *
     */
    @Override
    public void run() {
        try{
            socket = new Socket("localhost",5000);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            this.objectInputStream=new ObjectInputStream(socket.getInputStream());

            System.out.println("connected+++++++++++++++");

            while(socket.isConnected()){
                    System.out.println("----------------------");
                    writeToServer();
                    readFromServer();

            }

        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try{

//            Scanner scanner=new Scanner(System.in);
//            String messageToServer;
//            String serverRespone;
//
//            this.connect();
//            System.out.println("connected");
//            while(socket.isConnected()) { // read servers response
//                Message message=(Message) objectInputStream.readObject(); // read message from the
//
//                System.out.println("Serveres meessage "+ message.getUser().getUsername());
//                if ( message!= null) {
//                    System.out.println("Serveres meessage "+ message.getUser().getUsername());
//                }
//            }
//
//            do {
//                System.out.println("Enter a message ");
//                messageToServer=scanner.nextLine();
//                output.println(messageToServer);
//
//
////            }while(!messageToServer.equals("exit"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Sends User object to the server
     * @throws IOException
     */
    public void connect() throws IOException {

        Message message = new Message(this.user);
        this.objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        System.out.println("conect method");
    }
    public void writeToServer(){
        if(messageToServer!=null){
            try{
                switch (messageToServer.getMessageType()){
                    case REGISTER:
                        this.objectOutputStream.writeObject(messageToServer);
                        System.out.println(" writeToServer() " + messageToServer.getUser().getUsername());
                        break;
                    case LOGIN:
                        this.objectOutputStream.writeObject(messageToServer);
                        System.out.println("             this.objectOutputStream.writeObject(messageToServer);");
//                        messageToServer.setMessageType(MessageType.UPDATE_USER_LIST);
//                        this.objectOutputStream.writeObject(messageToServer);
//                        this.objectOutputStream.flush();
                        break;
//                    case UPDATE_USER_LIST:
//                        System.out.println("                 case UPDATE_USER_LIST: in CLIENT THREAD");
//                        this.objectOutputStream.writeObject(messageToServer);
//                        break;
                }
                objectOutputStream.flush();
                messageToServer=null;            // 11.04- to prevent infinite while loop, after sending msg to server set messageToServer to null
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void readFromServer() {

        try {
            Message messageFromServer = (Message) objectInputStream.readObject();
            if (messageFromServer != null) {
                System.out.println("readFromServer");
                System.out.println(messageFromServer.getMessageType() + "@@@@@@@@@");
                switch (messageFromServer.getMessageType()) {
                    case USER_REGISTERED:
//                    FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("models/registerView.fxml"));
//                    fmxlLoader.setLocation(getClass().getResource("models/registerView.fxml"));
//                    registerController = fmxlLoader.<RegisterController>getController();
//                        Dialog<ButtonType> dialog = new Dialog<>();
//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(getClass().getResource("/models/registerView.fxml"));
//                        dialog.getDialogPane().setContent(loader.load());
////                    Parent root = loader.load(getClass().getResource("/models/registerView.fxml"));
//                    RegisterController registerController = loader.getController();
//                        registerController=messageToServer.getRegisterController();
                    registerController.updateFrame(MessageType.USER_REGISTERED);
                        System.out.println(" registerController=messageToServer.getRegisterController();");
                        System.out.println("USER_REGISTERED");
                        break;
                    case USER_NOT_REGISTERED_WRONG_FORMAT:
                        //new RegisterController().updateFrame(MessageType.USER_NOT_REGISTERED);
                        registerController.updateFrame(MessageType.USER_NOT_REGISTERED_WRONG_FORMAT);
                        System.out.println("USER_NOT_REGISTERED_WRONG_FORMAT");
                        break;
                    case USER_NOT_REGISTERED_DUPLICATE:
                        registerController.updateFrame(MessageType.USER_NOT_REGISTERED_DUPLICATE);
                        System.out.println("USER_NOT_REGISTERED_DUPLICATE");
                        break;
                    case LOGIN_SUCCESSFULL:
//                        Platform.runLater(() ->{
//                            Parent root = null;
//                            try {
//                                root = FXMLLoader.load(getClass().getResource("userView.fxml"));
//                                stage.setScene(new Scene(root));
//                                stage.show();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                        });
                        // message from server contains list of users
                       // loginController.showUserFrame(messageFromServer);

                       //loginController.setLoginResultMessage(messageFromServer);
                        // UserController.getInstance().setUserList(messageFromServer);
                        LoginController.getInstance().setLoginResultMessage(messageFromServer);
                        break;
                    case LOGIN_FAILED:
                        LoginController.getInstance().setLoginResultMessage(messageFromServer);
                        break;
                    case UPDATE_USER_LIST:
                        System.out.println("5555555555555555555555555555555555555555555555");
                        // TODO fix Thread.sleep
                        // I'm using Thread.sleep here, becouse before updating ListView in UserCOntroller
                        // user Frame has to be initialized. So to prevent NullPointerException
                        // when frame components (listView etc) are not set yet, program has to wait until user scene
                        // will be initialized. Then program access user scene components
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        UserController.getInstance().setUserList(messageFromServer);
                        System.out.println("                        client  case UPDATE_USER_LIST:");
                        break;
//                    case UPDATE_USER_LIST:
////                        try {
////                            Thread.sleep(5000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////                        UserController.getInstance().setUserList(messageFromServer);
////                        System.out.println("case update user list ");
////                        // Platform run later is necessay heare, becouse othrwise all active clients won't get the messagethe the new
////                        // client has joinde
////
////                        Platform.runLater(() ->{
////                        System.out.println(messageFromServer.getUser() + " "+ messageFromServer.getMessageType());
////                        LoginController.getInstance().setUpdateUserListMessage(messageFromServer);
////                      // UserController.getInstance().setUserList(messageFromServer);
//////                        UserController.getInstance().setObservableList(messageFromServer.getUsersHashMap());
////                        });
//                       break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setMessageToServer(Message messageToServer) {
        this.messageToServer = messageToServer;
    }
}
