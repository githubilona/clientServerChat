package models;

import java.io.IOException;
import java.net.*;

public class PeerServer implements Runnable{

    private final int PORT =5001;
    private InetAddress inetAddress;
    private DatagramSocket datagramSocket;
    private String message;

    public PeerServer() {
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
    @Override
    public void run(){
        System.out.println("Server thread started");
        try {
            while(true){
                // send();
                receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void send() throws IOException {
        byte [] buffer = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, PORT);
        datagramSocket.send(datagramPacket);
    }
    public void receive() throws IOException {
        // Receive msg from the client
        byte [] buffer2 = new byte[50];
        DatagramPacket datagramPacket = new DatagramPacket(buffer2, buffer2.length);
        System.out.println("Receving packet...");
        datagramSocket.receive(datagramPacket);  // blocks until message will be received
        System.out.println(" Server received packet : "+ new String(buffer2, 0, datagramPacket.getLength()) +" **********");
    }

//    public static void main(String [] args){
//        try {
//            DatagramSocket datagramSocket = new DatagramSocket(5001);
//
//            while(true){
//                byte [] buffer = new byte[50];
//                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//                datagramSocket.receive(packet);
//                System.out.println("Received message "+ new String(buffer, 0 , packet.getLength()));
//
//                // send replay to the client
//                String serverReplay="Thank you for your message: "+ new String(buffer, 0, packet.getLength()) + "Cheers, Server ";
//                byte [] buffer2 = serverReplay.getBytes();
//                InetAddress clientAddress = packet.getAddress(); // get the address from the packet to know where resend message
//                int clientPort = packet.getPort();
//                packet = new DatagramPacket(buffer2, buffer2.length, clientAddress, clientPort);
//                datagramSocket.send(packet);
//
//
//
//
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
