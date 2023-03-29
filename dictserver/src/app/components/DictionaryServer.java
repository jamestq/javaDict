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

    public void setConnection(Socket socket, Dictionary dictionary){
        this.socket = socket;
        this.clientID = String.format("Port-%s-%s",
                                    this.socket.getPort(),
                                    this.socket.getInetAddress().getHostName());
        this.dictionary = dictionary;
    }

    public void response(){
        try{
            System.out.printf("Serving %s%n", this.clientID);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            BufferedWriter serverOutput = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            String clientMessage = null;
            try{
                serverOutput.write("FROM SERVER: Connection established \n");
                serverOutput.flush();
                serverOutput.write("Enter command: \n");
                serverOutput.flush();
                while((clientMessage = userInput.readLine())!=null){
                    handleCommands(clientMessage, userInput, serverOutput);
                }
                System.out.printf("Client %s connection has been closed!%n", this.clientID);
                this.dictionary.saveDictionary();
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

    public void handleCommands(String command, BufferedReader userInput, BufferedWriter serverOutput) throws IOException{
        String response = "";
        switch(command){
            case "search": 
                serverOutput.write("Word to search: \n");
                serverOutput.flush();
                response = this.dictionary.searchWord(userInput.readLine());
                break;
            case "add":
                serverOutput.write("Word to add: \n");
                serverOutput.flush();
                String word = userInput.readLine();
                serverOutput.write("Meanings to add: \n");
                serverOutput.flush();
                String meanings = userInput.readLine();
                response = this.dictionary.addWord(word, meanings);
                break;
            case "remove":
                serverOutput.write("Word to remove \n");
                serverOutput.flush();
                response = this.dictionary.removeWord(userInput.readLine());
                break;
            case "update":
                serverOutput.write("Word to update: \n");
                serverOutput.flush();
                String wordUpdate = userInput.readLine();
                serverOutput.write("Meanings to update: \n");
                serverOutput.flush();
                String meaningsUpdate = userInput.readLine();
                response = this.dictionary.updateMeaning(wordUpdate, meaningsUpdate);
                break;
            default:
                response = "Invalid command! Try again!";
        }
        serverOutput.write(response + "\n");
        serverOutput.flush();
    }

}
