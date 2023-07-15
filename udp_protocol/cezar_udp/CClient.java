package cezar_udp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CClient {
    public static void main(String[] args) {
        try(DatagramSocket client=new DatagramSocket();
            Scanner sc=new Scanner(System.in);
        ){
            System.out.println("Klijent se povezao na server...");
            String recenica;
            while(true){
                recenica=sc.nextLine();
                if(recenica.equalsIgnoreCase("KRAJ")){
                    break;
                }
                byte[] buffer1=new byte[1024];
                buffer1=recenica.getBytes();
                DatagramPacket forServer=new DatagramPacket(buffer1, buffer1.length, InetAddress.getByName(CServer.host), CServer.PORT);
                client.send(forServer);



                byte[] buff2=new byte[1024];
                DatagramPacket fromServer=new DatagramPacket(buff2, buff2.length);
                client.receive(fromServer);

                String cezar=new String(fromServer.getData(), 0, fromServer.getLength(), StandardCharsets.UTF_8);
                System.out.println(cezar);


            }

            System.out.println("Klijent se diskonektuje...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
