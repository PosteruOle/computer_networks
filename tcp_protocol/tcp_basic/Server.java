package tcp_basic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Server {
    
    static final Integer PORT=54321;
    static final String host="localhost";
    
    public static void main(String[] args) {
        try(ServerSocket server=new ServerSocket(PORT)){
            
            System.out.println("Server is now active!");
            System.out.println("Server is listenting on port: " + PORT);
            int count=0;
            
            while(true){
               Socket client=server.accept();
               System.out.println("-----------------------");
               count++;
               System.out.println("The " + count + ". has arrived!");
               sleep(4000);
               Date date=new Date();
               System.out.println("We are sending the following information to the client:" + date.toString());
               serve(client);
               System.out.println("We have served the client!");
               System.out.println("-----------------------");
            }
            
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void serve(Socket client) {
        try(BufferedWriter w=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))){
            
            Date date=new Date();
            w.write(date.toString());
            w.newLine();
            w.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
