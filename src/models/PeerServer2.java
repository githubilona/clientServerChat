package models;

import controllers.ChatController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PeerServer2 implements Runnable{
    private int port;


    @Override
    public void run() {
        server();
    }
    public void server(){
        try {
            DatagramSocket socket = new DatagramSocket(port);

              while(true) {
            byte[] buffer = new byte[50];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Server receive ... ");
            socket.receive(packet);
            String response = new String(buffer, 0, packet.getLength());
            System.out.println("Server: Text received is: " + response);
            ChatController.getInstance().setPeerResponse(response);

            String returnString = "echo: " + new String(buffer, 0, packet.getLength());
            byte[] buffer2 = returnString.getBytes();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buffer2, buffer2.length, address, port);
            socket.send(packet);
              }

        } catch(SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }

    public void setPort(int port) {
        this.port = port;
    }
}
