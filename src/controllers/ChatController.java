package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

    @FXML private TextArea chatTextArea;
   // @FXML private TextArea chatBox;
    @FXML private TextArea sendMessageTextArea;
    @FXML private Button sendButton;
    //@FXML private chatBox chatBox;
    @FXML private ScrollPane scrollPane;
    private PeerClient peerClient;
    private PeerServer2 peerServer2;
    private PeerClient2 peerClient2;
    private Thread peerClientThread;
    private Thread peerServerThread;
    private User sender;
    private User receiver;
    private String peerResponse;
    private List<ChatMessage> chatMessages= new ArrayList<>();
    @FXML
    private  VBox chatBox = new VBox(5);
    private List<Label> messages = new ArrayList<>();
    private int index = 0;


    private static ChatController instance;
    public ChatController(){
        instance=this;

    }
    public void initialize(){
        peerClient2 = new PeerClient2();
      //  scrollPane.setVvalue(1.0);
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
        String message=  sender.getUsername() + ":\t" + sendMessageTextArea.getText()+ "\n";
        System.out.println(message);
       // Platform.runLater(() ->{
            // chatBox.setText(message);
            int receiverPort=this.receiver.getPort();
            peerClient2.client(message, receiverPort);
            chatMessages.add(new ChatMessage(message, sender, receiver));



        Label label =new Label(message);
        label.setId("label");
        messages.add(label);
        //chatBox.getChildren().add(label);

        System.out.println("index  " + index);
  /*      if(index%2==0){

            messages.get(index).setAlignment(Pos.CENTER_LEFT);
            System.out.println("1");

        }else{

            messages.get(index).setAlignment(Pos.CENTER_RIGHT);
            System.out.println("2");

        }

*/
        HBox hBox=new HBox();
        hBox.getChildren().add(messages.get(index));
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        chatBox.getChildren().add(hBox);
        chatBox.setSpacing(10);

       // messages.get(index).setAlignment(Pos.CENTER_RIGHT);


        messages.get(index).setWrapText(true);
       // Platform.runLater(() ->{
       // chatBox.getChildren().add(messages.get(index));
        System.out.println(messages.get(index).getText() + "label ettx");
            //chatBox.getChildren().add(label);
           // chatBox.getChildren().add(label2);
            //chatBox.getChildren().add(label3);
      //  });
        index++;

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
        System.out.println(peerResponse + " peer REsponse" + this.peerResponse);
        Label labelResponse = new Label();
        labelResponse.setId("labelResponse");
        labelResponse.setText(this.peerResponse + "     response ");
        labelResponse.setAlignment(Pos.CENTER_RIGHT);
        labelResponse.setWrapText(true);
        Platform.runLater(() ->{
            chatBox.getChildren().add(labelResponse);
        });

    }
}
