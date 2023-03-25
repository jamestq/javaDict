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
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try{
            String address = args[0];
            int port = Integer.parseInt(args[1]);
            socket = new Socket(address, port);
            System.out.println("Connection established");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner userInput = new Scanner(System.in);
            String inputStr = null;
            while(!(inputStr = userInput.nextLine()).equalsIgnoreCase("exit")){
                out.write(inputStr + "\n");
                out.flush();
                System.out.println("Message sent");
                String received = in.readLine();
                System.out.println("Message received: " + received);
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

    public static void printMenu(){
        System.out.println("continue program?");
    }
}
