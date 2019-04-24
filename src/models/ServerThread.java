package models;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerThread  extends Server implements Runnable {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private List<ClientThread> clients = new ArrayList<>();
   // private HashMap<String, User> usersHashMap=super.getUsersHashMap();

    public ServerThread(Socket socket){
        this.socket=socket;
        System.out.println("Server thread constructor");


    }

    @Override
    public void run() {
        System.out.println("Server thread run()");

        try{

            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                this.objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
             //   objectOutputStream.flush();
                this.objectInputStream=new ObjectInputStream(socket.getInputStream());

                super.getWriters().add(objectOutputStream);
                System.out.println("                 new objectOutputStraem has been added to the list");
                while (socket.isConnected()){
                    readFromClient();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }finally {
            try{
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // TODO correct repeating try catch blocks

    public boolean checkDuplicateUsername(Message message) {
        if(!super.getUsersHashMap().containsKey(message.getUser().getUsername()) ){
            for(String username : super.getUsersHashMap().keySet()){
                System.out.println(username);
            }
            return false;
        }else{
            String exceptionMessage ="User "+message.getUser().getUsername() +" already exists";
            System.out.println(exceptionMessage);
                // TODO send why registration didn't succeed - wrong format or duplicate username
                // TODO if checkDuplicateUsername returns true- registration didn't succeed  becouse of duplicate
                // TODO writeToAll that message to the frame
            return true;
        }
    }
    /**
     *
     * @return true username and password have correct format, false username or password have incorrect format
     *  or both username and password have incorrect format
     */
    public boolean checkFormat(Message clientMessage){
        String username= clientMessage.getUser().getUsername();
        String password= clientMessage.getUser().getPassword();
        RegistrationValidator registrationValidator =new RegistrationValidator();
        return registrationValidator.validate(username) && registrationValidator.validate(password);
    }
    /**
     * Writes message to the client with information if user was successfully registered or not
     * @param messageType
     * @param usersHashMap
     */
    public void writeRegisteredMessage(MessageType messageType, HashMap<String, User> usersHashMap){
        try{
            Message message = new Message(messageType, super.getUsersHashMap());
            objectOutputStream.writeObject(message);
            super.getWriters().add(objectOutputStream);
           // objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // to writeToAll msg to all connected users
    private void writeToAll(Message msg){
        for (ObjectOutputStream writer : super.getWriters()) {
            try {
                msg.setUsersHashMap(super.getUsersHashMap());
                writer.writeObject(msg);
                writer.reset();
                //writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean login(Message clientMesage) {
        for(User user : super.getUsersHashMap().values()){
            if(user.equals(clientMesage.getUser())){
                System.out.println("Logged in!");
                return true;
                // TODO send message to client thata login was successful , show chat frame
            }
        }
        System.out.println("Wrong login or password");
        return false;
    }
    public void readFromClient(){
            // TODO cbheck if client message is not null
        Message clientMesage= readClientMessage();
        if(clientMesage!=null){
            System.out.println("Server " + clientMesage.getMessageType()+ " user "+ clientMesage.getUser().getUsername());
            switch(clientMesage.getMessageType()){
                case REGISTER:
                    boolean format=checkFormat(clientMesage);
                    boolean isDuplicated=checkDuplicateUsername(clientMesage);
                    if(!format){
                        sendToClient(new Message(clientMesage.getUser(), MessageType.USER_NOT_REGISTERED_WRONG_FORMAT));
                    }else if(isDuplicated){
                        sendToClient(new Message(clientMesage.getUser(), MessageType.USER_NOT_REGISTERED_DUPLICATE));
                    }else {
                        super.getUsersHashMap().put(clientMesage.getUser().getUsername(), clientMesage.getUser());
                        sendToClient(new Message(clientMesage.getUser(), MessageType.USER_REGISTERED));
                    }
                    break;
                case LOGIN:
                    boolean isLogged =login(clientMesage);
                    System.out.println("CASE LOGIN in SERVER");
                    if(isLogged){
                        // Send the list of logged in users
                        // convert hash map to list - don't sent passwords
                        sendToClient(new Message(clientMesage.getUser(), MessageType.LOGIN_SUCCESSFULL, super.getUsersHashMap()));
                       // System.out.println("                     case login ,  writeToAll update user list msg");
                        writeToAll(new Message(clientMesage.getUser(), MessageType.UPDATE_USER_LIST, super.getUsersHashMap()));
                    }else{
                        sendToClient(new Message(clientMesage.getUser(), MessageType.LOGIN_FAILED));
                    }
                    break;
//                case UPDATE_USER_LIST:
//                    System.out.println("            write to alll update user list in sERVER ");
//                    writeToAll(new Message(clientMesage.getUser(), MessageType.UPDATE_USER_LIST, super.getUsersHashMap()));
//                    break;
            }
        }

    }
    public void sendToClient(Message messageToClient){
        try {
            objectOutputStream.writeObject(messageToClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Message readClientMessage(){
        Message clientMesage=null;
        try {
            clientMesage= (Message)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clientMesage;
    }

    public void addClient(ClientThread client){
        this.clients.add(client);
    }

    public List<ClientThread> getClients() {
        return clients;
    }

    public void setClients(List<ClientThread> clients) {
        this.clients = clients;
    }
}
