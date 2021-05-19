package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Etch
{
    public Etch()
        throws IOException
    {
        // Create frame and panel
        JFrame f= new JFrame("Etch-A-Sketch");
        f.add(new MainPanel());
        f.setLayout(null);
        f.setSize(800, 800);
        f.setVisible(true);
    }
}

// JPanel for image
class MainPanel extends JPanel {
    private final BufferedImage img;
    private final MovingAdapter ma = new MovingAdapter();
    private final KeyListener kl = new ArrowKey();
    private final JLabel label = new JLabel();

    public MainPanel()
        throws IOException
    {
        // Set focusable to be true
        this.setFocusable(true);
        this.requestFocusInWindow();

        // Set background color and size
        setBackground(Color.GRAY);
        setSize(800, 800);

        // Add Key Listener to JFrame
        addKeyListener(kl);

        // Set image
        img = ImageIO.read(new File("src/main/resources/images/etch_a_sketch.jpg"));
//        int x = (this.getWidth() - img.getWidth()) / 2;
//        int y = (this.getHeight() - img.getHeight()) / 2;
        label.setIcon(new ImageIcon(img));
        label.setSize(label.getPreferredSize());
        label.addMouseListener(ma);
        label.addMouseMotionListener(ma);
        add(label);

    }

    // Logic for moving image
    class MovingAdapter extends MouseAdapter
    {
        private int x;
        private int y;
        private boolean drag = false;

        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if (e.getSource() == label) {
                drag = true;
            }
        }

        public void mouseReleased(MouseEvent e) {
            drag = false;
        }

        public void mouseDragged(MouseEvent e) {
            if (drag) {
                label.setLocation(e.getXOnScreen() - x,
                    e.getYOnScreen() - y);
            }
        }
    }
}

// TODO: Maybe create new object for BufferedImage for better mouse movements


