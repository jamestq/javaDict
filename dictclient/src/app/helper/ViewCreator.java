package app.helper;

import javax.swing.*;
import java.awt.*;

public class ViewCreator {

    private static int W_WIDTH = 100;
    private static int W_WIDTH_BUTTON = 200;
    private static int W_HEIGHT = 20;
    private static int W_HEIGHT_TEXT_AREA = 100;
    private static Dimension DEFAULT_DIMENSION_TEXT = new Dimension(W_WIDTH, W_HEIGHT);
    private static Dimension DEFAULT_DIMENSION_TEXT_AREA = new Dimension(W_WIDTH, W_HEIGHT_TEXT_AREA);
    private static Dimension DEFAULT_DIMENSION_BUTTON = new Dimension(W_WIDTH_BUTTON, W_HEIGHT);

    public static JPanel createTwoOnePanel(String fieldDes1, String fieldDes2, String buttonDes){
        JPanel parentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // add the first label and text field
        JLabel label1 = new JLabel(fieldDes1);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        parentPanel.add(label1, constraints);

        JTextField textField1 = createTextField("", true);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        parentPanel.add(textField1, constraints);

        // add the second label and text field
        JLabel label2 = new JLabel(fieldDes2);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        parentPanel.add(label2, constraints);

        JTextField textField2 = createTextField("", true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        parentPanel.add(textField2, constraints);

        // add the button
        JButton button = createButton(buttonDes);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        parentPanel.add(button, constraints);
        return parentPanel;
    }

    public static JPanel createOnePanel(String fieldDes, boolean editable){
        JPanel parentPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(fieldDes);
        JTextArea textArea = createTextArea("", editable);
        parentPanel.add(label, BorderLayout.NORTH);
        parentPanel.add(textArea, BorderLayout.CENTER);
        return parentPanel;
    }

    public static JPanel createOneOnePanel(String fieldDes1, boolean editable, String buttonDes){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JTextField textField = createTextField(fieldDes1, editable);
        JButton button = createButton(buttonDes);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(textField, c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 5, 5, 5);
        panel.add(button, c);
        return panel;
    }

    public static JTextField createTextField(String text, boolean editable){
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(DEFAULT_DIMENSION_TEXT);
        textField.setEditable(editable);
        return textField;
    }

    public static JTextArea createTextArea(String text, boolean editable){
        JTextArea textArea = new JTextArea(text);
        textArea.setPreferredSize(DEFAULT_DIMENSION_TEXT_AREA);
        textArea.setEditable(editable);
        return textArea;
    }

    public static JButton createButton(String name){
        JButton button = new JButton(name);
        button.setPreferredSize(DEFAULT_DIMENSION_BUTTON);
        return button;
    }
    
}
