package protocol_handler_quiz;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class QuizURLConnection extends URLConnection {
    public static Integer DEFAULT_PORT=1337;
    private Socket connection=null;
    
    public QuizURLConnection(URL u) {
        super(u);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if(!this.connected)
            connect();
        return this.connection.getInputStream();
    }

    @Override
    public void connect() throws IOException {
        if(!this.connected){
            int port=this.url.getPort();
            
            if(port<1 || port>65535)
                port=DEFAULT_PORT;

            this.connection=new Socket(QServer.host, DEFAULT_PORT);
            
            if(this.connection==null){
                System.out.println("Zasto?!");
            }

            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream()));
            
            String oblast=url.getQuery();
            String[] podeli=oblast.split("=");
            
            oblast=podeli[1];
            w.write(oblast);
            w.newLine();
            w.flush();

            connected=true;
        }
    }
}
