package protocol_handlers;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class DayTimeURLConnection extends URLConnection {
    static final Integer DEFAULT_PORT=8765;
    private Socket connection=null;

    public DayTimeURLConnection(URL u) {
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
            int port=url.getPort();
            
            if(port<1 || port>65536){
                port=DEFAULT_PORT;
            }

            this.connection=new Socket(DTServer.host, DTServer.PORT);
            
            OutputStream out=this.connection.getOutputStream();
            BufferedWriter w=new BufferedWriter(new OutputStreamWriter(out));
            
            String pitanje="Kako se ja zovem i prezimavam?! Ko sam ja u stvari?!";
            w.write(pitanje);
            w.newLine();
            w.flush();

            this.connected=true;
        }
    }
}
