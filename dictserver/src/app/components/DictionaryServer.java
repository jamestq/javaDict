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
                serverOutput.write("Connection established \n");
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

    public void handleCommands(String clientMessage, BufferedReader userInput, BufferedWriter serverOutput) throws IOException{
        String[] tokens = getMessageTokens(clientMessage);
        String response = "";
        switch(tokens[0]){
            case "search": 
                response = this.dictionary.searchWord(tokens[1]);
                break;
            case "add":
                response = this.dictionary.addWord(tokens[1], tokens[2]);
                break;
            case "remove":
                response = this.dictionary.removeWord(tokens[1]);
                break;
            case "update":
                response = this.dictionary.updateMeaning(tokens[1], tokens[2]);
                break;
            default:
                response = "Invalid command! Try again!";
        }
        serverOutput.write(response + "\n");
        serverOutput.flush();
    }

    private String[] getMessageTokens(String clientMessage){
        String[] tokens = new String[3];
        int tokenInd=0;
        int afterSplitterInd = 0;
        for(int i=0; i<clientMessage.length(); i++){
            if(clientMessage.charAt(i)==':'){
                tokens[tokenInd] = clientMessage.substring(afterSplitterInd, i);
                afterSplitterInd = i+1;
                tokenInd++;
            }
        }
        tokens[tokenInd] = clientMessage.substring(afterSplitterInd);
        return tokens;
    }

}
