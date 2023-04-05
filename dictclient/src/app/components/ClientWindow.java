/**
 * Name: Uy Thinh Quang
 * Surname: Quang
 * StudentID: 1025981
 */

package app.components;

import javax.swing.JFrame;
import javax.swing.JPanel;

import app.helper.ViewCreator;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashMap;

public class ClientWindow  extends JFrame{

    private static int DEFAULT_JFRAME_SIZE = 900;
    private ViewCreator creator;

    JPanel parentPanel;

    public ClientWindow(){
        super("Dictionary DS Client");
        this.creator = new ViewCreator();
    }

    public void initiate(){
        setSize(DEFAULT_JFRAME_SIZE,DEFAULT_JFRAME_SIZE);

        JPanel parentPanel = createPanels();
        add(parentPanel);
        //Add listener to shutdown the program when the window is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private JPanel createPanels(){

        HashMap<String, JPanel> panelMap = new HashMap<>();

        JPanel controlPanel = createControlPanel(panelMap);
        JPanel resultPanel = createResultPanel(panelMap);
        
        Controller controller = new Controller();
        controller.setUpFunction(panelMap);

        JPanel parentPanel = new JPanel(new GridLayout(1,2));
        parentPanel.add(controlPanel);
        parentPanel.add(resultPanel);
        return parentPanel;
    }

    private JPanel createControlPanel(HashMap<String,JPanel> panelMap){

        JPanel connectionPanel = creator.createTwoOnePanel("hostname", "port", "Connect to Server", false);
        JPanel searchWordPanel = creator.createOneOnePanel("", true, "Search for meaning");
        JPanel addWordPanel = creator.createTwoOnePanel("word", "meanings", "Add word to Dictionary", true);
        JPanel updateWordPanel = creator.createTwoOnePanel("word", "meanings", "Update word", true);
        JPanel deleteWordPanel = creator.createOneOnePanel("", true, "Delete word");

        panelMap.put("connectionPanel", connectionPanel);
        panelMap.put("searchWordPanel", searchWordPanel);
        panelMap.put("addWordPanel", addWordPanel);
        panelMap.put("updateWordPanel", updateWordPanel);
        panelMap.put("deleteWordPanel", deleteWordPanel);

        JPanel controlPanel = new JPanel(new GridLayout(5,2));
        controlPanel.add(connectionPanel);
        controlPanel.add(searchWordPanel);
        controlPanel.add(addWordPanel);
        controlPanel.add(updateWordPanel);
        controlPanel.add(deleteWordPanel);

        return controlPanel;
    }

    private JPanel createResultPanel(HashMap<String, JPanel> panelMap){

        JPanel connectionStatus = creator.createOneOnePanel("Disconnected", false, "Disconnect");
        JPanel wordSearchResult = creator.createOnePanel("Search Result", false);
        JPanel wordAddResult = creator.createOnePanel("Add Result", false);
        JPanel wordUpdateResult = creator.createOnePanel("Update Result", false);
        JPanel wordDeleteResult = creator.createOnePanel("Delete Result", false);

        panelMap.put("connectionStatus", connectionStatus);
        panelMap.put("wordSearchResult", wordSearchResult);
        panelMap.put("wordAddResult", wordAddResult);
        panelMap.put("wordUpdateResult", wordUpdateResult);
        panelMap.put("wordDeleteResult", wordDeleteResult);

        JPanel resultPanel = new JPanel(new GridLayout(5,2));
        resultPanel.add(connectionStatus);
        resultPanel.add(wordSearchResult);
        resultPanel.add(wordAddResult);
        resultPanel.add(wordUpdateResult);
        resultPanel.add(wordDeleteResult);

        return resultPanel;
    }
}
