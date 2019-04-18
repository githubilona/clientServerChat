package models;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    private User user;
    private MessageType messageType;
    private HashMap<String, User> usersHashMap =new HashMap<>();
   // private RegisterController registerController;


    public Message() {

    }
    public Message(User user) {
        this.user = user;
    }

    public Message(User user, MessageType messageType ) {
        this.user=user;
        this.messageType=messageType;
    }
//    public Message(User user, MessageType messageType, RegisterController registerController ) {
//        this.user=user;
//        this.messageType=messageType;
//        this.registerController=registerController;
//    }
    public Message(MessageType messageType, HashMap<String, User> usersHashMap ){
        this.messageType=messageType;
        this.usersHashMap = usersHashMap;
    }

    public Message(User user, MessageType messageType, HashMap<String, User> usersHashMap) {
        this.user = user;
        this.messageType = messageType;
        this.usersHashMap = usersHashMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public HashMap<String, User> getUsersHashMap() {
        return usersHashMap;
    }

    public void setUsersHashMap(HashMap<String, User> usersHashMap) {
        this.usersHashMap = usersHashMap;
    }

//    public RegisterController getRegisterController() {
//        return registerController;
//    }
//
//    public void setRegisterController(RegisterController registerController) {
//        this.registerController = registerController;
//    }
}
