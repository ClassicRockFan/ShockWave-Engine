package com.ClassicRockFan.ShockWave.engine.administrative;

/**
 * Created by Tyler on 3/19/2015.
 */


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConsoleWindow extends JFrame {

    private JTextArea mainField;
    private JTextField input;
    private JButton button;

    public ConsoleWindow() {
        super("Console Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.black);
        setForeground(Color.black);

        JPanel panel = new JPanel();

        mainField = new JTextArea();
        mainField.setLineWrap(true);
        mainField.setAutoscrolls(true);
        mainField.setForeground(Color.WHITE);
        mainField.setBackground(Color.BLACK);
        DefaultCaret caret = (DefaultCaret) mainField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);


        input = new JTextField();
        input.setBounds(0, 250, 200, 50);

        button = new JButton("Submit");
        button.setBounds(200, 250, 100, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = input.getText().toLowerCase().trim();


            }
        });

        panel.add(mainField);
        panel.add(input);
        panel.add(button);

        JScrollPane pane = new JScrollPane(panel);
        pane.setBounds(0, 0, 300, 300);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(pane, BorderLayout.CENTER);

        pack();

        setSize(300, 300);
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
