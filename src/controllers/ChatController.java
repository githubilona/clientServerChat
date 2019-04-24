package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.PeerClient;
import models.PeerServer;
import models.User;

public class ChatController {

    @FXML private TextArea chatTextArea;
    @FXML private TextArea sendMessageTextArea;
    @FXML private Button sendButton;
    private PeerClient peerClient;
    private PeerServer peerServer;
    private Thread peerClientThread;
    private Thread peerServerThread;
    private User sender;
    private User receiver;

    private static ChatController instance;

    public ChatController(){
        instance=this;
    }
    public void initialize(){
        peerClient = new PeerClient();
        peerServer = new PeerServer();
        peerClientThread= new Thread(peerClient);
        peerServerThread = new Thread(peerServer);
        peerClientThread.start();
        peerServerThread.start();
    }

    @FXML
    public void sendButtonAction(){
        String message= chatTextArea.getText() +"\n" + sender.getUsername() + ":\t" + sendMessageTextArea.getText()+ "\n";
        chatTextArea.setText(message);

        try {
            peerClientThread.sleep(1000);
            peerClient.setMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // message should be displayed on the sender screen,
        //  send to the chosen user and displayed on his screen
        // to do that I have to create for each user client and server.
        // the message above should be passed to the client, which will send it to the another's user server where
        // the message will be handled and directed to client where the message will be displayed

    }
    public static ChatController getInstance(){
        return instance;
    }


    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
