package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Main Etch Class
public class Etch
{
    public Etch()
        throws IOException
    {
        // Create frame and panel
        JFrame f = new JFrame("Etch-A-Sketch");
        f.setLayout(null);
        f.add(new MainPanel(), BorderLayout.CENTER);
        f.setSize(1100, 900);
        f.setVisible(true);

        // Turn fullscreen on or off
        f.setResizable(false);

        // Set closing operation
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}




