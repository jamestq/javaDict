package app.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JButton;

public class Controller {

    public void setUpFunction(HashMap<String,JPanel> panels){
        GUIFunction guiFunction = new GUIFunction();
        setUpConnectListener(panels, guiFunction);
        setUpDisconnectListener(panels, guiFunction);
        setUpSearchListener(panels, guiFunction);
        setUpAddListener(panels, guiFunction);
        setUpUpdateListener(panels, guiFunction);
        setUpDeleteListener(panels, guiFunction);
    }

    private void setUpConnectListener(HashMap<String, JPanel> panels, GUIFunction guiFunction){
        JPanel connectionPanel = panels.get("connectionPanel");
        JPanel connectionStatus = panels.get("connectionStatus");
        JButton connectButton = (JButton) connectionPanel.getComponent(4);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFunction.connect(connectionPanel, connectionStatus);
            }
        });
    }

    private void setUpDisconnectListener(HashMap<String, JPanel> panels, GUIFunction guiFunction){
        JPanel connectionStatus = panels.get("connectionStatus");
        JButton disconnectButton = (JButton) connectionStatus.getComponent(1);
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                guiFunction.disconnect(connectionStatus);
            }
        });
    }

    private void setUpSearchListener(HashMap<String, JPanel> panels, GUIFunction guiFunction){
        JPanel searchWordPanel = panels.get("searchWordPanel");
        JPanel wordSearchResult = panels.get("wordSearchResult");
        JButton searchButton = (JButton) searchWordPanel.getComponent(1);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFunction.search(searchWordPanel, wordSearchResult);
            }
        });
    }

    private void setUpAddListener(HashMap<String, JPanel> panels, GUIFunction guiFunction){
        JPanel addWordPanel = panels.get("addWordPanel");
        JPanel wordAddResult = panels.get("wordAddResult");
        JButton addButton = (JButton) addWordPanel.getComponent(4);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFunction.add(addWordPanel, wordAddResult);                
            }  
        });
    }

    private void setUpUpdateListener(HashMap<String, JPanel> panels, GUIFunction guiFunction){
        JPanel updateWordPanel = panels.get("updateWordPanel");
        JPanel wordUpdateResult = panels.get("wordUpdateResult");
        JButton updateButton = (JButton) updateWordPanel.getComponent(4);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFunction.update(updateWordPanel, wordUpdateResult);                
            }  
        });
    }

    private void setUpDeleteListener(HashMap<String, JPanel> panels, GUIFunction guiFunction){
        JPanel deleteWordPanel = panels.get("deleteWordPanel");
        JPanel wordDeleteResult = panels.get("wordDeleteResult");
        JButton deleteButton = (JButton) deleteWordPanel.getComponent(1);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFunction.delete(deleteWordPanel, wordDeleteResult);
            }
        }); 
    } 
} 
