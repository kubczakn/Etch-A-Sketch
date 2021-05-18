package main.java;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Etch
{
    public Etch()
        throws IOException
    {
        // Create frame and canvas
        Frame f= new Frame("Etch-A-Sketch");
        f.add(new MainCanvas());
        f.setLayout(null);
        f.setSize(400, 400);
        f.setVisible(true);
    }
}

class MainCanvas extends Canvas {
    private final BufferedImage img;

    public MainCanvas()
        throws IOException
    {
        // Set background color and size
        setBackground(Color.GRAY);
        setSize(400, 400);

        // Set image
        img = ImageIO.read(new File("src/main/resources/images/etch_a_sketch.jpg"));
    }

    public void paint(Graphics g) {
        // Draw Etch-A-Sketch
        g.drawImage(img, 0, 0, null);
    }
}
