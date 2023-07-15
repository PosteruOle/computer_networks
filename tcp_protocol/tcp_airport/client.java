package tcp_airport;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(
            Socket client=new Socket(Server.host, Server.PORT);
            BufferedReader r=new BufferedReader( new InputStreamReader(client.getInputStream()));
            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Scanner sc=new Scanner(System.in);
        ){
            System.out.println("Client is now connected to the server!");
            String line;
            line=r.readLine();
            System.out.println(line);

            System.out.println("Enter the name of the city to see its outgoing information: ");
            String city=sc.nextLine();

            w.write(city);
            w.newLine();
            w.flush();

            while((line=r.readLine())!=null){
                System.out.println(line);
            }
            
            System.out.println("Client is going to disconnect from the server!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
