package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class assd extends JFrame {
    public assd() {
        int width = 800;
        int height = 600;
        BufferedImage background = null;
        try {
            background = ImageIO.read(new File("./res/textures/LoadScreen.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setSize(800, 600);
        setVisible(true);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);

        getContentPane().add(panel);
        //backGroundImage.setBounds(0, 0, width, height);
        //panel.add(backGroundImage);
//
        JButton startButton = new JButton("Start Game");
        startButton.setSize(300, 50);
        startButton.setLocation(800 - 310, height / 2 - 50);


        JButton quitButton = new JButton("Quit");
        quitButton.setSize(300, 50);
        quitButton.setLocation(width - 310, height / 2 + 100);


        SpringLayout sl_panel = new SpringLayout();
        sl_panel.putConstraint(SpringLayout.SOUTH, startButton, -113, SpringLayout.NORTH, quitButton);
        sl_panel.putConstraint(SpringLayout.WEST, quitButton, 0, SpringLayout.WEST, startButton);
        sl_panel.putConstraint(SpringLayout.SOUTH, quitButton, -38, SpringLayout.SOUTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, quitButton, -10, SpringLayout.EAST, panel);
        sl_panel.putConstraint(SpringLayout.WEST, startButton, 490, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.EAST, startButton, -10, SpringLayout.EAST, panel);
        panel.setLayout(sl_panel);

        panel.add(startButton);
        panel.add(quitButton);

        JButton btnNewButton = new JButton("New button");
        sl_panel.putConstraint(SpringLayout.NORTH, btnNewButton, 6, SpringLayout.SOUTH, startButton);
        sl_panel.putConstraint(SpringLayout.WEST, btnNewButton, 0, SpringLayout.WEST, startButton);
        sl_panel.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, panel);
        panel.add(btnNewButton);

    }
}
