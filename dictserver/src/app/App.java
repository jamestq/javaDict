package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import app.components.Dictionary;
import app.components.DictionaryServer;

public class App extends DictionaryServer implements Runnable{

    public void run(){
        response();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket listeningSocket = null;
        Socket clientSocket = null;
        Dictionary newDict = new Dictionary();
        newDict.loadDictionary(args[1]);
        try{
            int port = Integer.parseInt(args[0]);
            listeningSocket = new ServerSocket(port);
            while(true){
                System.out.println("Server listening on port "+ listeningSocket.getLocalPort() + " for a connection");
                clientSocket = listeningSocket.accept();
                App server = new App();
                server.setSocket(clientSocket, newDict);
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
            newDict.saveDictionary();
        }
    }
}
