package app.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

public class GUIFunction {
    
    private BufferedReader serverResponse;
    private BufferedWriter clientInput;
    private Socket serverSocket;

    public void connect(JPanel connectionPanel, JPanel connectionStatus){
        JTextField statusField = (JTextField) connectionStatus.getComponent(0);
        try{
            int port = Integer.parseInt(((JTextField) connectionPanel.getComponent(3)).getText());
            String address = ((JTextField) connectionPanel.getComponent(1)).getText();
            try{
                this.serverSocket = new Socket(address, port);
                this.serverResponse = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                this.clientInput = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
                String received = this.serverResponse.readLine();
                statusField.setText(received);
            }catch(UnknownHostException error){
                statusField.setText("Host not found!");
                error.printStackTrace();
            }catch(IOException io){
                statusField.setText("Something went wrong. Refer to the console for further details");
                io.printStackTrace();
            }
        }catch(NumberFormatException nfe){
            statusField.setText("Enter a number in the port field");
            nfe.printStackTrace();
        }catch(NullPointerException npe){
            statusField.setText("One of the fields (hostname, port) are empty, please retry");
            npe.printStackTrace();
        }
    }

    public void disconnect(JPanel connectionStatus){
        JTextField statusField = (JTextField) connectionStatus.getComponent(0);
        if(this.serverSocket!=null){
            try{
                this.serverSocket.close();
                statusField.setText("Disconnected");
            }catch(IOException e){
                statusField.setText("Something went wrong. Refer to the console for further details");
                e.printStackTrace();
            }
        }
    }

    public void search(JPanel searchWordPanel, JPanel wordSearchResult){
        JTextField searchField = (JTextField) searchWordPanel.getComponent(0);
        JTextArea resultField = (JTextArea) wordSearchResult.getComponent(1);
        try{
            String searchString = searchField.getText();
            try{
                this.clientInput.write("search:"+searchString+"\n");
                this.clientInput.flush();
                String receivedString = this.serverResponse.readLine();
                resultField.setText(receivedString);
            }catch(IOException e){
                resultField.setText(e.getMessage());
                e.printStackTrace();
            }
        }catch(NullPointerException e){
            resultField.setText("empty search field. Please try again");
        }
    }

    public void add(JPanel addWordPanel, JPanel wordAddResult){
        JTextField wordField = (JTextField) addWordPanel.getComponent(1);
        JTextField meaningsField = (JTextField) addWordPanel.getComponent(3);
        JTextArea resultField = (JTextArea) wordAddResult.getComponent(1);
        try{
            String addString = wordField.getText() + ":" + meaningsField.getText();
            try{
                this.clientInput.write("add:"+addString+"\n");
                this.clientInput.flush();
                String receivedString = this.serverResponse.readLine();
                resultField.setText(receivedString);
            }catch(IOException e){
                resultField.setText(e.getMessage());
                e.printStackTrace();
            }
        }catch(NullPointerException e){
            resultField.setText("empty word/meanings field. Please try again");
        }
    }

    public void update(JPanel updateWordPanel, JPanel wordUpdateResult){
        JTextField wordField = (JTextField) updateWordPanel.getComponent(1);
        JTextField meaningsField = (JTextField) updateWordPanel.getComponent(3);
        JTextArea resultField = (JTextArea) wordUpdateResult.getComponent(1);
        try{
            String addString = wordField.getText() + ":" + meaningsField.getText();
            try{
                this.clientInput.write("update:"+addString+"\n");
                this.clientInput.flush();
                String receivedString = this.serverResponse.readLine();
                resultField.setText(receivedString);
            }catch(IOException e){
                resultField.setText(e.getMessage());
                e.printStackTrace();
            }
        }catch(NullPointerException e){
            resultField.setText("empty word/meanings field. Please try again");
        }
    }

    public void delete(JPanel deleteWordPanel, JPanel wordDeleteResult){
        JTextField searchField = (JTextField) deleteWordPanel.getComponent(0);
        JTextArea resultField = (JTextArea) wordDeleteResult.getComponent(1);
        try{
            String searchString = searchField.getText();
            try{
                this.clientInput.write("remove:"+searchString+"\n");
                this.clientInput.flush();
                String receivedString = this.serverResponse.readLine();
                resultField.setText(receivedString);
            }catch(IOException e){
                resultField.setText(e.getMessage());
                e.printStackTrace();
            }
        }catch(NullPointerException e){
            resultField.setText("empty delete field. Please try again");
        }
    }

}
