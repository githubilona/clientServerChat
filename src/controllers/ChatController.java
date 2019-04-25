package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    @FXML private TextArea chatTextArea;
    @FXML private TextArea sendMessageTextArea;
    @FXML private Button sendButton;
    private PeerClient peerClient;
    private PeerServer2 peerServer2;
    private PeerClient2 peerClient2;
    private Thread peerClientThread;
    private Thread peerServerThread;
    private User sender;
    private User receiver;
    private String peerResponse;
    private List<ChatMessage> chatMessages= new ArrayList<>();

    private static ChatController instance;

    public ChatController(){
        instance=this;

    }
    public void initialize(){
        peerClient2 = new PeerClient2();
//        peerClient = new PeerClient();
//        peerServer = new PeerServer();
//        peerClientThread= new Thread(peerClient);
//        peerServerThread = new Thread(peerServer);
//        peerClientThread.start();
//        peerServerThread.start();
//         peerServer2 =new PeerServer2();
//        peerServer2.setPort(1001);
//         peerServer2.server();
//        Thread peerServerThread2 = new Thread(peerServer2);
//        peerServerThread2.start();

    }

    @FXML
    public void sendButtonAction(){
        System.out.println("send msg button " + sender + " port : " + sender.getPort());
      //  System.out.println("Sender      ---   " + sender.getUsername()  +   "port  " + sender.getPort());
        String message= chatTextArea.getText() +" " + sender.getUsername() + ":\t" + sendMessageTextArea.getText()+ "\n";
       // Platform.runLater(() ->{
            chatTextArea.setText(message);
            int receiverPort=this.receiver.getPort();
            peerClient2.client(message, receiverPort);
            chatMessages.add(new ChatMessage(message, sender, receiver));
            for(ChatMessage m:chatMessages){
                System.out.println(" .,.,.,.,.,.,.,.,.,.   "+ m.getMessage() + "  " + m.getDate() + "  "+ m.getReceiver() + m.getSender());
            }

      //  });



//        try {
//            peerClientThread.sleep(1000);
//            peerClient.setMessage(message);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


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

    public void setPeerResponse(String peerResponse) {
        this.peerResponse = peerResponse;
        chatTextArea.setText(peerResponse);
    }
}
