package cezar_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class CServer {
    static final String host="localhost";
    static final Integer PORT=12345;
    
    public static void main(String[] args) {
        try(DatagramSocket server=new DatagramSocket(PORT)){
            System.out.println("Lokalni UDP server je sada aktivan...");
            System.out.println("Server osluskuje na portu " + PORT);
            
            Random random=new Random();
            Integer kljuc= random.nextInt(25) + 1;
            System.out.println("Kljuc za ovu sesiju je: " + kljuc);
            int count=1;
            
            while(true){
               byte[] buff1=new byte[1024];
                DatagramPacket fromClient=new DatagramPacket(buff1, buff1.length);
                server.receive(fromClient);
                System.out.println("Pristigao nam je " + count + ". po redu paket od klijenta sa adrese " + fromClient.getAddress());
                count++;

                String poruka=new String(fromClient.getData(), 0, fromClient.getLength(), StandardCharsets.UTF_8);
                System.out.println("Klijent nam je poslao:" + poruka);
                
                char[] pomereno=pomeriZaKljuc(poruka, kljuc);
                String salji=pomereno.toString();
                buff1=salji.getBytes(StandardCharsets.UTF_8);
                DatagramPacket forClient=new DatagramPacket(buff1, buff1.length, fromClient.getAddress(), fromClient.getPort());
                server.send(forClient);
                System.out.println("Poslali smo sifrovanu poruku klijentu!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static char[] pomeriZaKljuc(String poruka, Integer kljuc) {
        char[] tmp=poruka.toCharArray();
        System.out.println("P1--> " + tmp.toString());
        int n=tmp.length;
        
        for(int i=0;i<n;i++){
            tmp[i]+=kljuc;
        }
        
        System.out.println("P2--> " + tmp.toString());
        return tmp;
    }
}
