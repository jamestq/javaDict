package app.components;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;

public class DictionaryServer{
    
    private Socket socket;
    private int clientNumber;

    public void setSocket(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public void response(){
        try{
            System.out.println("Client connection number " + this.clientNumber + " accepted");
            System.out.println("Remote port " + this.socket.getPort());
            System.out.println("Remote hostname " + this.socket.getInetAddress().getHostName());
            System.out.println("Local port " + this.socket.getLocalPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String clientMessage = null;
            try{
                while((clientMessage = in.readLine())!=null){
                    System.out.println("Message from client "+this.clientNumber+": "+clientMessage);
                    out.write("Server ACK " + clientMessage + "\n");
                    out.flush();
                    System.out.println("Response sent");
                }
                System.out.println("Server closed the client connection!!! - received null");
            }catch(SocketException e){
                System.out.println("closed...");
            }
            this.socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
