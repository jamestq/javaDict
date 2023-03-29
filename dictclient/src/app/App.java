package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {

    private String address;
    private int port;

    private Socket serverSocket;
    private BufferedReader serverInput;
    private BufferedWriter clientOutput;

    public App(String address, int port){
        this.address = address;
        this.port = port;
    }

    public void setUp() throws UnknownHostException, IOException{
        this.serverSocket = new Socket(this.address, this.port);
        this.serverInput = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
        this.clientOutput = new BufferedWriter(new OutputStreamWriter(this.serverSocket.getOutputStream()));
    }

    public BufferedReader getServerInput(){
        return this.serverInput;
    }

    public BufferedWriter getClientOutput(){
        return this.clientOutput;
    }

    public void closeSocket() throws IOException{
        this.serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try{
            App newClient = new App(args[0], Integer.parseInt(args[1]));
            newClient.setUp();
            
            BufferedReader serverResponse = newClient.getServerInput();
            String received = serverResponse.readLine();
            System.out.println(received);
            received = serverResponse.readLine();
            System.out.println(received);

            BufferedWriter clientOutput = newClient.getClientOutput();
            Scanner userInput = new Scanner(System.in);
            String inputStr = null;

            while(!(inputStr = userInput.nextLine()).equalsIgnoreCase("exit")){
                clientOutput.write(inputStr + "\n");
                clientOutput.flush();
                received = serverResponse.readLine();
                System.out.println(received);
            }
            userInput.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(socket!=null){
                try{
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
