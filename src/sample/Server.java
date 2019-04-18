package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  4.04.19 21:47 HashMap has to be static, otherwise useras are not saved to HashMAp
 */
public class Server {
    private List<ClientThread> clientThreads = new ArrayList<>();
    private static HashMap<String, User> usersHashMap=new HashMap<>();
    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(5000)){
            while(true){
                Socket socket = serverSocket.accept();
                 new Thread(new ServerThread(socket)).start();
//                ServerThread serverThread =new ServerThread(socket);
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

    public List<ClientThread> getClientThreads() {
        return clientThreads;
    }

    public void setClientThreads(List<ClientThread> clientThreads) {
        this.clientThreads = clientThreads;
    }

    public HashMap<String, User> getUsersHashMap() {
        return usersHashMap;
    }

    public void setUsersHashMap(HashMap<String, User> usersHashMap) {
        this.usersHashMap = usersHashMap;
    }
}
