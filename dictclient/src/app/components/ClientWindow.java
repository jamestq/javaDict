package app.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class ClientWindow  extends JFrame{

    private static int DEFAULT_JFRAME_SIZE = 800;

    JPanel parentPanel;

    public ClientWindow(){
        super("Dictionary DS Client");
    }

    public void initiate(){
        
        setSize(DEFAULT_JFRAME_SIZE,DEFAULT_JFRAME_SIZE);
        HashMap<String, JPanel> panelMap = new HashMap<>();
        JPanel parentPanel = createPanel(panelMap);
        Controller controller = new Controller(panelMap);
        controller.setUpFunction();
        add(parentPanel);
        setVisible(true);

        //Add listener to shutdown the program when the window is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });


    }

    private JPanel createPanel(HashMap<String,JPanel> panelMap){
        JPanel parentPanel = new JPanel(new GridLayout(1,2));
        JPanel controlPanel = createControlPanel(panelMap);
        JPanel resultPanel = createResultPanel(panelMap);
        parentPanel.add(controlPanel);
        parentPanel.add(resultPanel);
        return parentPanel;
    }

    private JPanel createControlPanel(HashMap<String,JPanel> panelMap){
        JPanel controlPanel = new JPanel(new GridLayout(5,2));
        
        JPanel connectionPanel = ViewCreator.createTwoOnePanel("hostname", "port", "Connect to Server");
        JPanel searchWordPanel = ViewCreator.createOneOnePanel("", true, "Search for meaning");
        JPanel addWordPanel = ViewCreator.createTwoOnePanel("word", "meanings", "Add word to Dictionary");
        JPanel updateWordPanel = ViewCreator.createTwoOnePanel("word", "meanings", "Update word");
        JPanel deleteWordPanel = ViewCreator.createOneOnePanel("", true, "Delete word");

        panelMap.put("connectionPanel", connectionPanel);
        panelMap.put("searchWordPanel", searchWordPanel);
        panelMap.put("addWordPanel", addWordPanel);
        panelMap.put("updateWordPanel", updateWordPanel);
        panelMap.put("deleteWordPanel", deleteWordPanel);

        controlPanel.add(connectionPanel);
        controlPanel.add(searchWordPanel);
        controlPanel.add(addWordPanel);
        controlPanel.add(updateWordPanel);
        controlPanel.add(deleteWordPanel);

        return controlPanel;
    }

    private JPanel createResultPanel(HashMap<String, JPanel> panelMap){
        JPanel resultPanel = new JPanel(new GridLayout(5,2));

        JPanel connectionStatus = ViewCreator.createOneOnePanel("Disconnected", false, "Disconnect");
        JPanel wordSearchResult = ViewCreator.createOnePanel("Search Result", false);
        JPanel wordAddResult = ViewCreator.createOnePanel("Add Result", false);
        JPanel wordUpdateResult = ViewCreator.createOnePanel("Update Result", false);
        JPanel wordDeleteResult = ViewCreator.createOnePanel("Delete Result", false);

        panelMap.put("connectionStatus", connectionStatus);
        panelMap.put("wordSearchResult", wordSearchResult);
        panelMap.put("wordAddResult", wordAddResult);
        panelMap.put("wordUpdateResult", wordUpdateResult);
        panelMap.put("wordDeleteResult", wordDeleteResult);
        
        resultPanel.add(connectionStatus);
        resultPanel.add(wordSearchResult);
        resultPanel.add(wordAddResult);
        resultPanel.add(wordUpdateResult);
        resultPanel.add(wordDeleteResult);

        return resultPanel;
    }
}
