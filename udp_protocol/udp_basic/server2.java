package udp_basic;

import jdk.jfr.Unsigned;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server2 {
    
    static final String host="localhost";
    static final Integer PORT=10101;
    static Integer num_of_packets=0;
    static final Integer BUFF_SIZE=512;

    public static void main(String[] args) {
        try(DatagramSocket server=new DatagramSocket(PORT);){
            System.out.println("Server is active now!");
            
            while(true) {
                byte[] buffer = new byte[BUFF_SIZE];
                DatagramPacket fromClient = new DatagramPacket(buffer, buffer.length);
                server.receive(fromClient);
                num_of_packets++;
                System.out.println(num_of_packets + ".  " + fromClient.getAddress());

                String message = new String(buffer, 0, fromClient.getLength(), StandardCharsets.UTF_8);
                Integer number = Integer.parseUnsignedInt(message);
                String binary = Integer.toBinaryString(number);

                buffer = binary.getBytes();
                System.out.println("---> " + binary);
                
                DatagramPacket forClient = new DatagramPacket(buffer, buffer.length, fromClient.getAddress(), fromClient.getPort());
                server.send(forClient);
                System.out.println("We have sent message to client!");
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
