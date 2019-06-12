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

    @FXML private TextArea sendMessageTextArea;
    @FXML private ScrollPane scrollPane;
    private PeerClient2 peerClient2;
    private User sender;
    private User receiver;
    private String peerResponse;
    private List<ChatMessage> chatMessages= new ArrayList<>();
    private List<Label> messages = new ArrayList<>();
    private List<HBox> messagesHbox = new ArrayList<>();
    private VBox chat;
    private int scrollWidth=600;
    private int scrollHeight=300;
    private String fileName;

    private static ChatController instance;
    public ChatController(){
        instance=this;

    }

    /**
     * Starts PeerClient
     */
    public void initialize(){
        peerClient2 = new PeerClient2();
       // scrollPane.setPrefSize(scrollWidth, scrollHeight);

        chat = new VBox();

        chat.setSpacing(7);
        scrollPane.setContent(chat);

        // Invisible label to have chat bubbles aligned to edges of the scroll pane
        HBox hbox = new HBox();
        Label label = new Label("");
        label.setMinWidth(scrollWidth-20);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        hbox.getChildren().add(label);
        hbox.setVisible(false);
        chat.getChildren().add(hbox);
    }

    /**
     * Creates a hbox for sender message
     * @param message message that will be placed on a chat label
     * @return HBox containing a label with sender message
     */
    private HBox addMessage(String message){
        HBox hbox = new HBox();
        Label label = new Label(message);
        label.setId("label");
        label.setWrapText(true);
        label.setMaxWidth(scrollWidth-20);
        messages.add(label);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        hbox.getChildren().add(label);
        return hbox;
    }

    /**
     * Creates a hbox for response message
     * @param message message that will be placed on a chat label
     * @return HBox containing a label with response message
     */
    private HBox addResponseMessage(String message) {
        HBox hbox = new HBox();
        Label label = new Label(message);
        label.setId("labelResponse");
        label.setWrapText(true);
        label.setMaxWidth(scrollWidth-20);
        messages.add(label);
        hbox.setAlignment(Pos.BASELINE_LEFT);
       //  hbox.setAlignment(Pos.BASELINE_LEFT);
        hbox.getChildren().add(label);
        return hbox;
    }

    /**
     * Send message to the peer
     *
     */
    @FXML
    public void sendButtonAction(){
        System.out.println("send msg button " + sender + " port : " + sender.getPort());
        String message=  sender.getUsername() + ":\t" + sendMessageTextArea.getText()+ "\n";

        int receiverPort=this.receiver.getPort();
        peerClient2.client(message, receiverPort);
        ChatMessage chatMessage=new ChatMessage(message, sender, receiver, ChatMessageType.MY_MESSAGE);
        chatMessages.add(chatMessage);


        Platform.runLater(() -> {
            HBox hBox = addMessage(message);
            chat.getChildren().add(hBox);
            messagesHbox.add(hBox);
            // scroll down after adding a message
            chat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

        });
        saveMessage();
        sendMessageTextArea.clear();


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

    /**
     * Displays peer response on the chat panel.
     * This method is used by this PeerServer to set/display message from another peer
     * @param peerResponse message form another peer
     */
    public void setPeerResponse(String peerResponse) {
        this.peerResponse = peerResponse;
        System.out.println(peerResponse + " peer REsponse" + this.peerResponse);
      ChatMessage message = new ChatMessage(peerResponse, sender, receiver, ChatMessageType.RESPONSE);
      chatMessages.add(message);
      Platform.runLater(() ->{
          HBox response = addResponseMessage(this.peerResponse);
            chat.getChildren().add(response);
            messagesHbox.add(response);
            // scroll down after adding a message
            chat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
      });

    }

    /**
     * Save all conversation to a file
     */
    public  void saveMessage(){
        try(FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (ChatMessage chatMessage : chatMessages) {
                oos.writeObject(chatMessage);
                oos.flush();
            }
           // readMeassage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loading all messages and displaying them on the chat panel
     * @throws FileNotFoundException
     */
    public void readMeassage() throws FileNotFoundException {
        File file = new File(fileName);
        try {
            file.createNewFile(); // if file already exists will do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fis = new FileInputStream(file);
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
            if(chatMessage.getType().equals(ChatMessageType.MY_MESSAGE)){
                chat.getChildren().add(addMessage(chatMessage.getMessage()));
            }else{
                chat.getChildren().add(addResponseMessage(chatMessage.getMessage()));
            }
        }
        chat.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
