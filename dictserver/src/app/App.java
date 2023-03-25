package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class App {
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
                System.out.println("Client connection number " + i + " accepted");
                System.out.println("Remote port " + clientSocket.getPort());
                System.out.println("Remote hostname " + clientSocket.getInetAddress().getHostName());
                System.out.println("Local port " + clientSocket.getLocalPort());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String clientMessage = null;
                try{
                    while((clientMessage = in.readLine())!=null){
                        System.out.println("Message from client "+i+": "+clientMessage);
                        out.write("Server ACK " + clientMessage + "\n");
                        out.flush();
                        System.out.println("Response sent");
                    }
                    System.out.println("Server closed the client connection!!! - received null");
                }catch(SocketException e){
                    System.out.println("closed...");
                }
                clientSocket.close();
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
