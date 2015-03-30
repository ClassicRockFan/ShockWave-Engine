package com.ClassicRockFan.ShockWave.engine.administrative;

/**
 * Created by Tyler on 3/19/2015.
 */


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;


@SuppressWarnings("serial")
public class ConsoleWindow extends JFrame {

    private JTextArea mainField;

    public ConsoleWindow() {
        super("Console Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.black);
        setForeground(Color.black);
        mainField = new JTextArea();
        mainField.setLineWrap(true);
        mainField.setAutoscrolls(true);
        mainField.setForeground(Color.WHITE);
        mainField.setBackground(Color.BLACK);
        DefaultCaret caret = (DefaultCaret)mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        JScrollPane pane = new JScrollPane(mainField);
        pane.setBounds(0, 0, 300, 300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane, BorderLayout.CENTER);
        pack();

        setSize(300, 300);
        //setVisible(true);
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
