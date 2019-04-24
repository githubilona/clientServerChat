package models;

import java.io.IOException;
import java.net.*;

// TODO port to which data is send and ip address can be changed in administrator mode- if in the login frame
// TODO login ad password attached to admin account will be entered
// TODo or place settings icon on the frame, by clicking on it there will be ability to change port and ip address


// TODO send packet not only to localhost but also to other host
// TODO speciefied by the ip address

public class PeerClient implements Runnable {

    private final int PORT =5001;
    private InetAddress inetAddress;
    private DatagramSocket datagramSocket;
    private volatile String message;

    public PeerClient() {
        try {
            this.inetAddress = InetAddress.getLocalHost();
            this.datagramSocket = new DatagramSocket(PORT);
           // this.message=message;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

//    public PeerClient() {
//
//    }

    @Override
    public void run(){
        try {
            while(true){
                if(message!=null){
                    System.out.println("Message ! = null ");
                    send();
                    receive();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void send() throws IOException {
        byte [] buffer = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, PORT);
        datagramSocket.send(datagramPacket);
        System.out.println("Message to server has been sent");
        message=null;
    }
    public void receive() throws IOException {
        // Receive respone from the server
        byte [] buffer2 = new byte[50];
        DatagramPacket datagramPacket = new DatagramPacket(buffer2, buffer2.length);
        datagramSocket.receive(datagramPacket);  // blocks until message will be received
        System.out.println(" "+ new String(buffer2, 0, datagramPacket.getLength()));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
