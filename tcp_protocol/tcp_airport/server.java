package tcp_airport;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Server {
    
    static final String host="localhost";
    static final Integer PORT=12345;
    
    public static void main(String[] args) {
        HashMap<String, List<String>> letovi=new HashMap<>();
        Path dir= Paths.get("/home/petar/Desktop/tests/aerodromi");
        
        try(DirectoryStream<Path> putanje= Files.newDirectoryStream(dir)){
            for(Path put: putanje){
                try(Scanner sc=new Scanner(new FileInputStream(put.toString()))){
                    String filename=put.getFileName().toString();
                    filename=filename.substring(0, filename.indexOf('.'));
                    String linija;
                    List<String> linije=new ArrayList<>();
                    
                    while(sc.hasNextLine()){
                        linija=sc.nextLine();
                        linije.add(linija);
                    }
                    
                    letovi.put(filename, linije);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(ServerSocket server=new ServerSocket(PORT)){
            System.out.println("Server is now active...");
            System.out.println("Server is now listening on port: " + PORT);
            
            while(true){
                Socket client=server.accept();
                
                System.out.println("-------------------------");
                System.out.println("Client has arrived!");
                
                serve(client, letovi);
                
                System.out.println("We have served the client!");
                System.out.println("-------------------------");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void serve(Socket client, HashMap<String, List<String>> letovi) {
        try(BufferedReader r=new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ){
            w.write(letovi.toString());
            w.newLine();
            w.flush();

            String grad=r.readLine();
            for(Map.Entry m: letovi.entrySet()){
                if(grad.equalsIgnoreCase((String) m.getKey())){
                    w.write(m.getValue().toString());
                    w.newLine();
                    w.flush();
                    return;
                }
            }
            
            String response="There are no flights for the entered city!";
            w.write(response);
            w.newLine();
            w.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
