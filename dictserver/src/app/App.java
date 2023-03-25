package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import app.components.DictionaryServer;

public class App extends DictionaryServer implements Runnable{

    public void run(){
        response();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket listeningSocket = null;
        Socket clientSocket = null;
        try{
            int port = Integer.parseInt(args[0]);
            listeningSocket = new ServerSocket(port);
            int i=0;
            while(true){
                System.out.println("Server listening on port "+ listeningSocket.getLocalPort() + " for a connection");
                clientSocket = listeningSocket.accept();
                i++;
                App server = new App();
                server.setSocket(clientSocket, i);
                new Thread(server).start();
            }
        }catch(SocketException ex){
            ex.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            if(listeningSocket!=null){
                try{
                    listeningSocket.close();
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }
}
