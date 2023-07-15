package protocol_handlers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DTServer {
    static final Integer PORT=8765;
    static final String host="localhost";
    
    public static void main(String[] args) {
        try(ServerSocket server=new ServerSocket(PORT)){
            System.out.println("Server je sada aktivan...");
            System.out.println("Server osluskuje na portu " + PORT);
            
            int count=1;
            
            while(true){
                System.out.println("-----------------------------");
                Socket client=server.accept();
                System.out.println("Primeljen je " + count +  ". klijent. Opsluzujemo ga...");
                
                serve(client);
                
                System.out.println("Opsluzili smo klijenta...");
                System.out.println("-----------------------------");
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void serve(Socket client) {
        try(
            BufferedReader r=new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ){
            String pitanje=r.readLine();
            System.out.println("Klijent je postavio sledece pitanje: " + pitanje);
            String odgovor="Petar Tesic";
            
            w.write(odgovor);
            w.newLine();
            w.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
