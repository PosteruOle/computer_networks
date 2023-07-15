package tcp_echo;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(
            Socket client=new Socket(Server.host, Server.PORT);
            BufferedReader r=new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Scanner sc=new Scanner(System.in);
        ) {
            System.out.println("------------------");
            System.out.println("Klijent se konektovao na server!");
            String message=sc.nextLine();

            w.write(message);
            w.newLine();
            w.flush();

            String response=r.readLine();
            System.out.println(response);
            System.out.println("Klijent se diskonektuje sa servera!");
            System.out.println("------------------");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
