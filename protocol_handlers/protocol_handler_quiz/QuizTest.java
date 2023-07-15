package protocol_handler_quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class QuizTest {
    public static void main(String[] args) throws IOException {
        URL url=new URL(null, "quiz://localhost:1337?oblast=Biologija", new Handler());
        URLConnection conn= url.openConnection();
        
        try(BufferedReader r=new BufferedReader(new InputStreamReader(conn.getInputStream()))){
            String linija;
            while((linija=r.readLine())!=null){
                System.out.println(linija);
            }

        }
    }
}
