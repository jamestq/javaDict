/**
 * Name: Uy Thinh Quang
 * Surname: Quang
 * StudentID: 1025981
 */

package app.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class GUIFunction {
    
    private BufferedReader serverResponse;
    private BufferedWriter clientInput;
    private Socket serverSocket;
    private boolean isConnected = false;
    private JTextArea statusField;

    public void checkSocketClosed(HashMap<String,JPanel> panels){
        if(isConnected || this.serverSocket!=null){
            processComms("", (JTextArea) panels.get("connectionStatus").getComponent(0));
        }
        System.out.println("");
    }

    public void connect(JPanel connectionPanel, JPanel connectionStatus){
        statusField = (JTextArea) connectionStatus.getComponent(0);
        statusField.setText("Connecting...");
        if(this.isConnected){
            statusField.setText("Client is already connected. Disconnect first!");
            return;  
        } 
        try{
            int port = Integer.parseInt(((JTextField) connectionPanel.getComponent(3)).getText());
            String address = ((JTextField) connectionPanel.getComponent(1)).getText();
            try{
                this.serverSocket = new Socket(address, port);
                this.serverResponse = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                this.clientInput = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
                String received = this.serverResponse.readLine();
                statusField.setText(received);
                this.isConnected = true;
            }catch(UnknownHostException error){
                statusField.setText(reportError("Host not found!", error));
            }catch(IOException io){
                statusField.setText(reportError("Something went wrong. Please try again.", io));
            }
        }catch(NumberFormatException nfe){
            statusField.setText(reportError("Enter a number in the port field!", nfe));
        }catch(NullPointerException npe){
            statusField.setText(reportError("One of the fields (hostname, port) are empty, please retry", npe));
        }
    }

    public void disconnect(JPanel connectionStatus){
        statusField = (JTextArea) connectionStatus.getComponent(0);
        if(this.serverSocket!=null){
            try{
                this.serverSocket.close();
                statusField.setText("Disconnected");
            }catch(IOException e){
                statusField.setText(reportError("Something went wrong.", e));
            }
        }
        this.isConnected = false;
    }

    public void search(JPanel searchWordPanel, JPanel wordSearchResult){
        JTextArea searchField = (JTextArea) searchWordPanel.getComponent(0);
        JTextArea resultField = (JTextArea) wordSearchResult.getComponent(1);
        try{
            String searchString = searchField.getText();
            searchField.setText("");
            String message = "search:"+searchString+"\n";
            processComms(message, resultField);
        }catch(NullPointerException e){
            resultField.setText(reportError("Empty search field. Please try again", e));
        }
    }

    public void add(JPanel addWordPanel, JPanel wordAddResult){
        JTextField wordField = (JTextField) addWordPanel.getComponent(1);
        JTextArea meaningsField = (JTextArea) addWordPanel.getComponent(3);
        JTextArea resultField = (JTextArea) wordAddResult.getComponent(1);
        try{
            String addString = wordField.getText() + ":" + meaningsField.getText();
            wordField.setText(""); meaningsField.setText("");
            String message = "add:"+addString+"\n";
            processComms(message, resultField);
        }catch(NullPointerException e){
            resultField.setText(reportError("Empty word/meanings field. Please try again.", e));
        }
    }

    public void update(JPanel updateWordPanel, JPanel wordUpdateResult){
        JTextField wordField = (JTextField) updateWordPanel.getComponent(1);
        JTextArea meaningsField = (JTextArea) updateWordPanel.getComponent(3);
        JTextArea resultField = (JTextArea) wordUpdateResult.getComponent(1);
        try{
            String addString = wordField.getText() + ":" + meaningsField.getText();
            wordField.setText(""); meaningsField.setText("");
            String message = "update:"+addString+"\n";
            processComms(message, resultField);
        }catch(NullPointerException e){
            resultField.setText(reportError("Empty word/meanings field. Please try again.", e));
        }
    }

    public void delete(JPanel deleteWordPanel, JPanel wordDeleteResult){
        JTextArea deleteField = (JTextArea) deleteWordPanel.getComponent(0);
        JTextArea resultField = (JTextArea) wordDeleteResult.getComponent(1);
        try{
            String searchString = deleteField.getText();
            deleteField.setText("");
            String message = "remove:"+searchString+"\n";
            processComms(message, resultField);
        }catch(NullPointerException e){
            resultField.setText(reportError("Empty delete field. Please try again.", e));
        }
    }

    private void processComms(String message, JTextArea resultField){
        try{
            sendMessage(message);
            setResponse(resultField);
        }catch(IOException e){
            statusField.setText(reportError("Something went wrong while processing the messages.", e));
        }
    }

    private void sendMessage(String message) throws IOException{
        System.out.println(message);
        this.clientInput.write(message);
        this.clientInput.flush();
    }

    private void setResponse(JTextArea textArea) throws IOException{
        String outputString = "";
        String receiveString = "";
        do{ 
            receiveString = this.serverResponse.readLine();
            if(receiveString.equalsIgnoreCase("timeout")){
                this.serverSocket.close();
                this.isConnected = false;
                statusField.setText("The connection has timed out. Please reconnect");
                return;
            }
            outputString += receiveString + "\n";
        }while(this.serverResponse.ready());
        textArea.setText("");
        textArea.setText(outputString);
    }

    private String reportError(String customeMessage, Exception e){
        String errrorMessage = customeMessage + "\n" + "Details: " + e.getMessage();
        return errrorMessage;
    }
}
