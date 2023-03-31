package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import app.components.Dictionary;
import app.components.DictionaryThread;
import app.helper.Utility;

public class Connection extends DictionaryThread implements Runnable{

    public Connection(Socket socket, Dictionary dictionary){
        super(socket, dictionary);
    }

    public void run(){
        String clientID = runService();
        System.out.printf("Thread has stopped for client %s%n", clientID);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket listeningSocket = null;
        Dictionary newDict = setUpDictionary(args);
        try{
            listeningSocket = setUpSocket(args);
            while(true){
                acceptClient(listeningSocket, newDict);
            }
        }catch(SocketException se){
            Utility.callErrorStop(se);
        }finally{
            if(listeningSocket!=null){
                try{
                    listeningSocket.close();
                }catch(SocketException se){
                    Utility.callErrorStop(se);
                }catch(IOException io){
                    Utility.callErrorStop(io);
                }
            }
            newDict.saveDictionary();
        }
    }

    private static void acceptClient(ServerSocket listeningSocket, Dictionary dictionary) throws IOException{
        System.out.println("Server listening on port "+ listeningSocket.getLocalPort() + " for a connection");
        Socket clientSocket = listeningSocket.accept();
        Connection connection = new Connection(clientSocket, dictionary);
        new Thread(connection).start();
    }

    private static Dictionary setUpDictionary(String[] args){
        //Setup connection to a dictionary based initial input
        try{
            Dictionary newDict = new Dictionary();
            String fileName = "";
            fileName = args[1];
            newDict.loadDictionary(fileName);
            return newDict;
        }catch(IndexOutOfBoundsException ie){
            Utility.callErrorStop(ie);
        }
        return null;
    }

    private static ServerSocket setUpSocket(String[] args) throws IOException{
        try{
            int port = Integer.parseInt(args[0]);
            return new ServerSocket(port);
        }catch(NumberFormatException nbe){
            Utility.callErrorStop(nbe);
        }catch(IndexOutOfBoundsException ie){
            Utility.callErrorStop(ie);
        }
        return null;
    }
}
