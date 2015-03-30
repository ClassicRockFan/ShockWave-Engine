package com.ClassicRockFan.ShockWave.engine.rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

/**
 * Created by Tyler on 3/19/2015.
 */
public class Window extends JFrame {

    private String title;
    private int width, height;
    private double frameCap;

    public Window(String title, int width, int height, double frameCap) throws HeadlessException {
        super(title);

        this.width = width;
        this.height = height;
        this.frameCap = frameCap;

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ArrayList<String> values = new ArrayList<String>();
                ArrayList<String> titles = new ArrayList<String>();

                int newWidth = (int) getSize().getWidth();
                int newHeight = (int) getSize().getHeight();
                titles.add("backColor:r");
                titles.add("backColor:g");
                titles.add("backColor:b");
//                values.add(configMap.get("backColor:r"));
//                values.add(configMap.get("backColor:g"));
//                values.add(configMap.get("backColor:b"));
                values.add(Integer.toString(newHeight));
                titles.add("frameHeight");
                values.add(Integer.toString(newWidth));
                titles.add("frameWidth");
//                Config.saveProp(titles, values, Launcher.mainDir + "/Configs/windowConfigs.txt");
                System.out.println("The new size is (" + newHeight + ", " + newWidth + ")");
            }
        });
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public double getFrameCap() {
        return frameCap;
    }
}
