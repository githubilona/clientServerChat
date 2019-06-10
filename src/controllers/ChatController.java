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

import java.io.*;
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
    private List<ChatMessage> chatMessages2= new ArrayList<>();
    @FXML
    private  VBox chatBox = new VBox(5);
    private List<Label> messages = new ArrayList<>();
    private List<HBox> messagesHbox = new ArrayList<>();
    private int index = 0;
    private VBox chat;
    private int scrollWidth=600;
    private int scrollHeight=300;

    private static ChatController instance;
    public ChatController(){
        instance=this;

    }
    public void initialize(){
        peerClient2 = new PeerClient2();
       // scrollPane.setFitToHeight(true);
     //   scrollPane.setPrefSize(600, 400);
     //   scrollPane.setContent(chatBox);

        scrollPane.setPrefSize(scrollWidth, scrollHeight);

        chat = new VBox();

        chat.setSpacing(7);
        scrollPane.setContent(chat);

        HBox hbox = new HBox();
        Label label = new Label("");
        label.setMinWidth(scrollWidth-20);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        hbox.getChildren().add(label);
        hbox.setVisible(false);



        //chat.getChildren().add(hbox);
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

   /*     messagesHbox.add(addMessage("hello"));
        messagesHbox.add(addResponseMessage("response hndjsj,vbwjhf"));
        messagesHbox.add(addMessage("qwerty asdfghj zxcvbn"));
        // scroll down after adding a message
       // chat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

        for(HBox hbox2 : messagesHbox){
            chat.getChildren().add(hbox2);
        }
*/
    }
    public HBox addMessage(String message)
    {
        HBox hbox = new HBox();
        Label label = new Label(message);
        label.setId("label");
        label.setWrapText(true);
        label.setMaxWidth(scrollWidth-20);
        messages.add(label);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        hbox.getChildren().add(label);

      //  messagesHbox.add(hbox);
        return hbox;
    }
    public HBox addResponseMessage(String message)
    {
        HBox hbox = new HBox();
        Label label = new Label(message);
        label.setId("labelResponse");
        label.setWrapText(true);
        label.setMaxWidth(scrollWidth-20);
        messages.add(label);
        hbox.setAlignment(Pos.BASELINE_LEFT);
       //  hbox.setAlignment(Pos.BASELINE_LEFT);
        hbox.getChildren().add(label);

      //  messagesHbox.add(hbox);
        return hbox;
    }
    @FXML
    public void sendButtonAction(){
        System.out.println("send msg button " + sender + " port : " + sender.getPort());
        String message=  sender.getUsername() + ":\t" + sendMessageTextArea.getText()+ "\n";

        int receiverPort=this.receiver.getPort();
        peerClient2.client(message, receiverPort);
        ChatMessage chatMessage=new ChatMessage(message, sender, receiver);
        chatMessages.add(chatMessage);


        Platform.runLater(() -> {
            HBox hBox = addMessage(message);
            chat.getChildren().add(hBox);
            messagesHbox.add(hBox);
            // scroll down after adding a message
            chat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

        });



       /* Label label =new Label(message);
        label.setId("label");
        label.setWrapText(true);
        label.setMaxWidth(500);
        messages.add(label);*/

    /*    HBox hBox=new HBox();
        hBox.getChildren().add(messages.get(index));
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        chatBox.getChildren().add(hBox);
        chatBox.setSpacing(10); */

       // messages.get(index).setAlignment(Pos.CENTER_RIGHT);


       // messages.get(index).setWrapText(true);

       // Platform.runLater(() ->{
       // chatBox.getChildren().add(messages.get(index));
//        System.out.println(messages.get(index).getText() + "label ettx");
            //chatBox.getChildren().add(label);
           // chatBox.getChildren().add(label2);
            //chatBox.getChildren().add(label3);
      //  });
        index++;

       //     for(ChatMessage m:chatMessages){
        //        System.out.println(" .,.,.,.,.,.,.,.,.,.   "+ m.getMessage() + "  " + m.getDate() + "  "+ m.getReceiver() + m.getSender());
          //  }

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
      /* Label labelResponse = new Label();
        labelResponse.setId("labelResponse");
        labelResponse.setText(this.peerResponse + "     response ");
        labelResponse.setAlignment(Pos.CENTER_RIGHT);
        labelResponse.setWrapText(true);*/
      Platform.runLater(() ->{
          HBox response = addResponseMessage(this.peerResponse);
            chat.getChildren().add(response);
            messagesHbox.add(response);
            // scroll down after adding a message
            chat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
      });

    }
    public  void saveMessage(){
        String fileName= "C:\\Users\\Adrian\\IdeaProjects\\Projekt TS\\javaFXtest\\src\\controllers\\conversation.txt";
        try(FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (ChatMessage chatMessage : chatMessages) {
                oos.writeObject(chatMessage);
                oos.flush();
            }
            readMeassage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readMeassage() throws FileNotFoundException {
        String fileName= "C:\\Users\\Adrian\\IdeaProjects\\Projekt TS\\javaFXtest\\src\\controllers\\conversation.txt";
        FileInputStream fis = new FileInputStream(fileName);
       ArrayList<ChatMessage> objectsList = new ArrayList<>();
        boolean cont = true;
        try{
            ObjectInputStream input = new ObjectInputStream(fis);
            while(cont){
                ChatMessage obj = (ChatMessage)input.readObject();
                if(obj != null)
                    chatMessages.add(obj);
                else
                    cont = false;
            }
            System.out.println("retrived objects rom file : ");
            for(ChatMessage chatMessage :chatMessages){
                System.out.println("@@@@@" + chatMessage.getMessage());
            }
        }catch(Exception e){
            //System.out.println(e.printStackTrace());
        }


        for(ChatMessage chatMessage : chatMessages){
            if(chatMessage.getSender().getPort() == this.sender.getPort()){
                chat.getChildren().add(addMessage(chatMessage.getMessage()));
            }else{
                chat.getChildren().add(addResponseMessage(chatMessage.getMessage()));
            }
        }









/*
        ChatMessage chatMessage;
        try(ObjectInputStream ios = new ObjectInputStream(new FileInputStream(fileName))) {
            while ((chatMessage = (ChatMessage) ios.readObject()) != null) {
                System.out.println("ADD" + chatMessage.getMessage() + " " + chatMessage.getSender() + " "+ chatMessage.getReceiver());
                chatMessages2.add(chatMessage);
            }
            System.out.println("EXIT FROM LOOP ");
            for (ChatMessage chatMessage2 : chatMessages2) {
                System.out.println("chatMessahe 2 !!!!!!!!!!!");
                System.out.println("@@@@" +chatMessage2.getMessage() + " ");
            }
        } catch (EOFException e) {

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
      */
    }
}
