package tcp_basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(
            Socket client=new Socket(Server.host, Server.PORT);
            BufferedReader r=new BufferedReader(new InputStreamReader(client.getInputStream()));
        ){
            String fromServer;
            fromServer=r.readLine();
            System.out.println("Current time on the server side is: " + fromServer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
