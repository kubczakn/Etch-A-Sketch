package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LinePanel extends JPanel
{
    private int x;
    private int y;
    private int x2;
    private int y2;

    public LinePanel() {
        x = getWidth() / 2;
        y = getHeight() / 2;
        x2 = x;
        y2 = y;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
//        g2d.drawLine()
    }



}
