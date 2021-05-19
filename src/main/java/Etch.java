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
//    private final KeyListener kl = new ArrowKey();
    private final KeyListener kl = new ArrowListener();
    private final JLabel label = new JLabel();

    private int x;
    private int y;
    private int x2;
    private int y2;

    public MainPanel()
        throws IOException
    {
        // Set layout
        setLayout(new GridBagLayout());

        // Set coordinates for painting
        x = getWidth() / 2;
        y = getHeight() / 2;
        x2 = x;
        y2 = y;

        // Set focusable to be true
        this.setFocusable(true);
        this.requestFocusInWindow();

        // Set background color and size
        setBackground(Color.GRAY);
        setSize(800, 800);

        // Add Key Listener to JPanel
        addKeyListener(kl);

        // Set image
        img = ImageIO.read(new File("src/main/resources/images/etch_a_sketch.jpg"));
        label.setIcon(new ImageIcon(img));
        label.setSize(label.getPreferredSize());
        label.addMouseListener(ma);
        label.addMouseMotionListener(ma);
        add(label);
        setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLine(g);
    }

    public void drawLine(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawLine(x, y, x2, y2);
        x = x2;
        y = y2;
    }

    // Listener for arrow keys
    private class ArrowListener implements KeyListener {
        @Override public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
            case KeyEvent.VK_LEFT:
                x2 -= 1;
                System.out.println("Left!");
                break;
            case KeyEvent.VK_RIGHT:
                x2 += 1;
                System.out.println("Right!");
                break;
            case KeyEvent.VK_UP:
                y2 += 1;
                System.out.println("Up!");
                break;
            case KeyEvent.VK_DOWN:
                y2 -= 1;
                System.out.println("Down!");
                break;
            }
        }

        @Override public void keyReleased(KeyEvent e)
        {

        }
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


