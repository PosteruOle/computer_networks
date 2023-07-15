package tcp_echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final Integer PORT=12121;
    static final String host="localhost";
    
    public static void main(String[] args) {
        try(ServerSocket server=new ServerSocket(PORT);){
            System.out.println("Server je sada aktivan");
            int count=0;
            
            while(true){
                Socket client=server.accept();
                count++;
                System.out.println("-------------------------------------------");
                System.out.println("Pristigao je " + count + ". po redu klijent.");

                serve(client);
                
                System.out.println("Opsluzili smo klijenta");
                System.out.println("-------------------------------------------");
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void serve(Socket client) {
        try(BufferedReader r=new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
        ){
            String fromClient=r.readLine();

            w.write(fromClient);
            w.newLine();
            w.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
