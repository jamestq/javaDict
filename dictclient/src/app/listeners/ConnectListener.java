package app.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextField;

public class ConnectListener implements ActionListener{

    

    private JTextField hostField;
    private JTextField portField;

    public ConnectListener(JTextField hostField, JTextField portField){
        this.hostField = hostField;
        this.portField = portField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }

    
}
