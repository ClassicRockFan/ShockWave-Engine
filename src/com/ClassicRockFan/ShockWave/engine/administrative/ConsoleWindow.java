package com.ClassicRockFan.ShockWave.engine.administrative;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;


public class ConsoleWindow extends JFrame {

    private JTextArea mainField;
    private JTextField textField;

    public ConsoleWindow() {
        super();

        this.setSize(300, 300);

        getContentPane().setBackground(Color.BLACK);
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        mainField = new JTextArea();
        mainField.setBackground(Color.BLACK);

        DefaultCaret caret = (DefaultCaret)mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


        JScrollPane scrollPane = new JScrollPane(mainField);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 237, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, 284, SpringLayout.WEST, getContentPane());
        getContentPane().add(scrollPane);

        textField = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, textField, -20, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, textField, 0, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, textField, 213, SpringLayout.WEST, getContentPane());
        getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Submit");
        springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 0, SpringLayout.EAST, scrollPane);
        getContentPane().add(btnNewButton);
    }

    public void addConsoleText(String message) {
        mainField.append(message + "\n");
    }

    public void addConsoleText(String message, int numWhiteSpaces, String message2) {
        String whiteSpace = "";

        for (int i = 0; i < numWhiteSpaces - 1; i++) {
            whiteSpace += " ";
        }

        mainField.append(message + whiteSpace + message2 + "\n");
    }

    public JTextArea getMainField() {
        return mainField;
    }
}
