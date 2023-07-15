package udp_basic;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try(
            Scanner sc=new Scanner(System.in);
            DatagramSocket client=new DatagramSocket();
        ){

            System.out.println("Client exists now!");
            
            while(true){
                System.out.println("Enter one number and you will get its binary representation:");

                String line=sc.nextLine();

                if(line.equalsIgnoreCase("KRAJ"))
                    break;

                byte[] message=new byte[512];
                message=line.getBytes();
                DatagramPacket forServer=new DatagramPacket(message, message.length, InetAddress.getByName(Server2.host), Server2.PORT);
                client.send(forServer);

                byte[] buffer=new byte[512];

                DatagramPacket fromServer=new DatagramPacket(buffer, buffer.length);
                client.receive(fromServer);

                System.out.print("We have recieved the folowing content: ");
                String finale=new String(buffer, 0, fromServer.getLength(), StandardCharsets.UTF_8);

                System.out.println(finale);
            }
            
            System.out.println("Clients is going to end his work!");
            
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
} 
