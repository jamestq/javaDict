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
    private String clientID;
    private Dictionary dictionary;

    public void setSocket(Socket socket, Dictionary dictionary){
        this.socket = socket;
        this.clientID = String.format("Port-%s-%s ",
                                    this.socket.getPort(),
                                    this.socket.getInetAddress().getHostName());
        this.dictionary = dictionary;
    }

    public void response(){
        try{
            System.out.printf("Server %s%n", this.clientID);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            BufferedWriter serverOutput = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String clientMessage = null;
            try{
                while((clientMessage = userInput.readLine())!=null){
                    handleCommands(clientMessage, userInput, serverOutput);
                    serverOutput.flush();
                    System.out.println("Response sent");
                    if(clientMessage.equalsIgnoreCase("exit")) break;
                }
                System.out.printf("Client %s connection has been closed!", this.clientID);
            }catch(SocketException e){
                System.out.println("Unexpected Socket Error");
                e.printStackTrace();
            }
            this.socket.close();
        }catch(IOException e){
            System.out.println("Unexpected IO Error while processing");
            e.printStackTrace();
        }
    }

    public void handleCommands(String command, BufferedReader input, BufferedWriter output) throws IOException{
        String response = "";
        switch(command){
            case "exit":
                response = "Exiting server%n";
                break;
            case "search": 
                System.out.print("Word to search: ");
                response = this.dictionary.searchWord(input.readLine());
                break;
            case "add":
                System.out.print("Word to add: ");
                String word = input.readLine();
                System.out.print("Meanings: ");
                String meanings = input.readLine();
                response = this.dictionary.addWord(word, meanings);
                break;
            case "remove":
                System.out.print("Word to remove: ");
                response = this.dictionary.removeWord(input.readLine());
                break;
            case "update":
                System.out.print("Word to update: ");
                String wordUpdate = input.readLine();
                System.out.print("Meanings: ");
                String meaningsUpdate = input.readLine();
                response = this.dictionary.updateMeaning(wordUpdate, meaningsUpdate);
            default:
                response = "Invalid command! Try again!%n";
        }
        output.write(String.format(response));
    }

}
