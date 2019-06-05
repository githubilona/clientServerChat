package models;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class PeerClient2 {
  //  private int receiverPort;

    public void client(String message, int receiverPort){
        try {
            InetAddress address = InetAddress.getLocalHost();  // getByName()
            DatagramSocket datagramSocket = new DatagramSocket();


            String echoString;

            //   do {qq

            echoString = message;

            byte[] buffer = echoString.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, receiverPort);
            datagramSocket.send(packet);
            System.out.println("Client packet sent !!!!!!!!!!!!!!!!!1");
//            byte[] buffer2 = new byte[50];
//            packet = new DatagramPacket(buffer2, buffer2.length);
//            datagramSocket.receive(packet);
//            String recivedMessage =new String(buffer2, 0, packet.getLength());
//            System.out.println("Client: Text received is: " + recivedMessage );
           // return recivedMessage;

            // } while(!echoString.equals("exit"));

        } catch(SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch(IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
     //   return null;
    }
}
