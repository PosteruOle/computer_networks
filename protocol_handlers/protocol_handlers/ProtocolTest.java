package protocol_handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ProtocolTest {
    public static void main(String[] args) throws IOException {
        URL url=new URL(null, "daytime://localhost:8765", new Handler());
        URLConnection conn=url.openConnection();
        
        try(BufferedReader r=new BufferedReader(new InputStreamReader(conn.getInputStream()))){
            String line;
            
            while((line=r.readLine())!=null){
                System.out.println(line);
            }
        }
    }
}
