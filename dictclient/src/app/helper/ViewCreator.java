/**
 * Name: Uy Thinh Quang
 * Surname: Quang
 * StudentID: 1025981
 */

package app.helper;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Insets;

public class ViewCreator {

    private static int W_WIDTH = 100;
    private static int W_WIDTH_BUTTON = 200;
    private static int W_HEIGHT = 20;
    private static int W_HEIGHT_TEXT_AREA = 100;
    private static Dimension DEFAULT_DIMENSION_TEXT = new Dimension(W_WIDTH, W_HEIGHT);
    private static Dimension DEFAULT_DIMENSION_TEXT_AREA = new Dimension(W_WIDTH, W_HEIGHT_TEXT_AREA);
    private static Dimension DEFAULT_DIMENSION_BUTTON = new Dimension(W_WIDTH_BUTTON, W_HEIGHT);

    public JPanel createTwoOnePanel(String fieldDes1, String fieldDes2, String buttonDes, boolean setTextArea){
        
        JPanel parentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 2, 0);

        JLabel label1 = new JLabel(fieldDes1);
        setConstraints(constraints, 0, 0, false, 0);
        parentPanel.add(label1, constraints);
        JTextField textField1 = createTextField("", true);
        setConstraints(constraints, 1, 0, false, 0);
        parentPanel.add(textField1, constraints);

        JLabel label2 = new JLabel(fieldDes2);
        setConstraints(constraints, 0, 1, false, 0);
        parentPanel.add(label2, constraints);

        setConstraints(constraints, 1, 1, false, 0);
        if(setTextArea){
            JTextArea textArea2 = createTextArea("", true);
            parentPanel.add(textArea2, constraints);
        }else{
            JTextField textField2 = createTextField("", true);
            parentPanel.add(textField2, constraints);
        }

        // add the button
        JButton button = createButton(buttonDes);
        setConstraints(constraints, 0, 2, false, 0);
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        parentPanel.add(button, constraints);
        return parentPanel;
    }

    public JPanel createOneOnePanel(String fieldDes1, boolean editable, String buttonDes){
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JTextArea textField = createTextArea(fieldDes1, editable);
        setConstraints(constraints, 0, 0, true, GridBagConstraints.HORIZONTAL);
        panel.add(textField, constraints);

        JButton button = createButton(buttonDes);
        setConstraints(constraints, 0, 1, false, 0);
        panel.add(button, constraints);

        return panel;
    }

    public JPanel createOnePanel(String fieldDes, boolean editable){

        JPanel parentPanel = new JPanel(new BorderLayout());
        
        JLabel label = new JLabel(fieldDes);
        JTextArea textArea = createTextArea("", editable);
        
        parentPanel.add(label, BorderLayout.NORTH);
        parentPanel.add(textArea, BorderLayout.CENTER);
        parentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        
        return parentPanel;
    }

    private JTextField createTextField(String text, boolean editable){
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(DEFAULT_DIMENSION_TEXT);
        textField.setEditable(editable);
        return textField;
    }

    private JTextArea createTextArea(String text, boolean editable){
        JTextArea textArea = new JTextArea(text);
        textArea.setPreferredSize(DEFAULT_DIMENSION_TEXT_AREA);
        textArea.setEditable(editable);
        textArea.setLineWrap(true);
        return textArea;
    }

    private JButton createButton(String name){
        JButton button = new JButton(name);
        button.setPreferredSize(DEFAULT_DIMENSION_BUTTON);
        return button;
    }

    private void setConstraints(GridBagConstraints constraints, int x, int y, boolean isFill, int fill){
        constraints.gridx = x;
        constraints.gridy = y; 
        if(isFill) constraints.fill = fill;
    }
}
