package models;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

/*
 *  4.04.19 21:47 HashMap has to be static, otherwise useras are not saved to HashMAp
 */

/**
 *  Class where servers starts its work
 */
public class Server {
    private static final int PORT=5000;
    private static HashMap<String, User> usersHashMap=new HashMap<>();
    /**
     * Stores all connected client's ObjectOutputStreams
     */
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();
    private static int userPort =1000;  // TODO port numbers are incremented from 1000, at port number 5000 main server is listening, change that.
        // TODO So if userPort variable will be incremented to 5000 there will be error, solve that

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                Socket socket = serverSocket.accept();
                 new Thread(new ServerThread(socket)).start();
//                models.ServerThread serverThread =new models.ServerThread(socket);
//                serverThread.start();


                System.out.println(" server thread started ");
                for(String username : usersHashMap.keySet()){
                    System.out.println(username);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, User> getUsersHashMap() {
        return usersHashMap;
    }

    public void setUsersHashMap(HashMap<String, User> usersHashMap) {
        this.usersHashMap = usersHashMap;
    }

    public  HashSet<ObjectOutputStream> getWriters() {
        return writers;
    }

    public  int getPort() {
        return userPort;
    }

    public  void setPort(int port) {
        Server.userPort = port;
    }
}
