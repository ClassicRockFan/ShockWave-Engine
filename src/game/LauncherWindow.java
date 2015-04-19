package game;


import com.ClassicRockFan.ShockWave.engine.administrative.StateManager;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Tyler on 3/12/2015.
 */
public class LauncherWindow extends JFrame {

    private static int width, height;
    private static String title;
    private BufferedImage background;
    private CoreEngine core;

    public LauncherWindow(int width, int height, String title, String launcherBackgroundPath, CoreEngine core_) {
        super(title);
        this.core = core_;
        LauncherWindow.width = width;
        LauncherWindow.height = height;
        LauncherWindow.title = title;
        try {
            this.background = ImageIO.read(new File("./res/textures/" + launcherBackgroundPath));
        } catch (Exception e) {
            System.err.println("Invalid Launcher Background file.");
            e.printStackTrace();
            System.exit(-1);
        }


        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GroupLayout(this));

        populateScreen(panel);
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateScreen(JPanel panel) {
        panel.setBounds(0, 0, width, height);

        add(panel);

        JLabel backGroundImage = new JLabel(new ImageIcon(background));
        //backGroundImage.setBounds(0, 0, width, height);
        //panel.add(backGroundImage);
//
        JButton startButton = new JButton("Start Game");
        startButton.setSize(300, 50);
        startButton.setLocation(width - 310, height / 2 - 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                core.setRunning(true);
                CoreEngine.getStateManager().setCurrentState(StateManager.STATE_RUNNING);
                dispose();
            }
        });


        JButton quitButton = new JButton("Quit");
        quitButton.setSize(300, 50);
        quitButton.setLocation(width - 310, height / 2 + 100);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        SpringLayout sl_panel = new SpringLayout();
        panel.setLayout(sl_panel);

        sl_panel.putConstraint(SpringLayout.SOUTH, startButton, -38, SpringLayout.NORTH, quitButton);
        sl_panel.putConstraint(SpringLayout.WEST, quitButton, 0, SpringLayout.WEST, startButton);
        sl_panel.putConstraint(SpringLayout.SOUTH, quitButton, 0, SpringLayout.SOUTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, quitButton, 0, SpringLayout.EAST, panel);
        sl_panel.putConstraint(SpringLayout.WEST, startButton, 490, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.EAST, startButton, 0, SpringLayout.EAST, panel);
        sl_panel.putConstraint(SpringLayout.NORTH, backGroundImage, 0, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, backGroundImage, 0, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.SOUTH, backGroundImage, height, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, backGroundImage, width, SpringLayout.WEST, panel);

        JButton settings = new JButton("Settings");
        sl_panel.putConstraint(SpringLayout.NORTH, settings, 6, SpringLayout.SOUTH, startButton);
        sl_panel.putConstraint(SpringLayout.WEST, settings, 0, SpringLayout.WEST, startButton);
        sl_panel.putConstraint(SpringLayout.EAST, settings, 0, SpringLayout.EAST, panel);

        panel.add(settings);

        panel.add(startButton);
        panel.add(quitButton);
        panel.add(backGroundImage);

    }

}
