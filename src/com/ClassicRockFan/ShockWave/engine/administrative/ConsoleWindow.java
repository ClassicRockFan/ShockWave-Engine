package com.ClassicRockFan.ShockWave.engine.administrative;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConsoleWindow extends JFrame {

    private JTextArea mainField;
    private JTextField textField;
    private CoreEngine engine;
    private JScrollPane scrollPane;

    public ConsoleWindow(CoreEngine engine) {
        super();

        this.engine = engine;
        this.setSize(300, 300);

        getContentPane().setBackground(Color.BLACK);
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        mainField = new JTextArea();
        mainField.setBackground(Color.BLACK);
        mainField.setForeground(Color.WHITE);

        DefaultCaret caret = (DefaultCaret) mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JButton btnNewButton = new JButton("Submit");
        springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, 0, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -80, SpringLayout.WEST, getContentPane());
        getContentPane().add(btnNewButton);
        setupSubmitButon(btnNewButton);

        textField = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, textField, -25, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, textField, 0, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, textField, 210, SpringLayout.WEST, getContentPane());
        getContentPane().add(textField);
        textField.setColumns(10);


        scrollPane = new JScrollPane(mainField);
        springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 0, SpringLayout.EAST, scrollPane);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 237, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, 284, SpringLayout.WEST, getContentPane());
        scrollPane.setAutoscrolls(true);
        getContentPane().add(scrollPane);
    }

    private void setupSubmitButon(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().trim().toLowerCase();
                Logging.printLog("Console Text Entered: " + text);

                if (text.equals("terminate"))
                    System.exit(2);
//                else if (text.equals("pause"))
//                    pauseCaret();
//                else if (text.equals("resume"))
//                    resumeCaret();

                textField.setText("");
            }
        });

    }

    private void pauseCaret(){
        DefaultCaret caret = (DefaultCaret) mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
        mainField.setAutoscrolls(false);
        scrollPane.setAutoscrolls(false);
    }

    private void resumeCaret(){
        DefaultCaret caret = (DefaultCaret) mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        mainField.setAutoscrolls(true);
        scrollPane.setAutoscrolls(true);
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
