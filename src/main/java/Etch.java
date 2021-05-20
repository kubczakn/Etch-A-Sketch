package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Etch
{
    public Etch()
        throws IOException
    {
        // Create frame and panel
        JFrame f = new JFrame("Etch-A-Sketch");
        f.add(new MainPanel());
        f.setLayout(null);
        f.setSize(800, 800);
        f.setVisible(true);

        // Set closing operation
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

// JPanel for image
class MainPanel extends JPanel {
    private final BufferedImage img;
    private final MovingAdapter ma = new MovingAdapter();
    private final CustomLabel label = new CustomLabel();
    private List<Point> points = new ArrayList<Point>();
    private int counter;

    private int x;
    private int y;

    public MainPanel()
        throws IOException
    {
        // Set layout
        setLayout(new GridBagLayout());

        // Set coordinates for painting
        counter = 0;

        // Set background color and size
        setBackground(Color.GRAY);
        setSize(800, 800);

        // Set image
        img = ImageIO.read(new File("src/main/resources/images/etch_a_sketch.jpg"));
        label.setIcon(new ImageIcon(img));
        label.setSize(label.getPreferredSize());
        label.addMouseListener(ma);
        label.addMouseMotionListener(ma);
        label.setFocusable(true);
        label.requestFocus();
        label.requestFocusInWindow();
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
        g2d.setColor(Color.CYAN);
        for (Point point : points) {
            int curr_x = point.getX();
            int curr_y = point.getY();
            g2d.drawLine(curr_x, curr_y, curr_x, curr_y);
        }
    }


    class CustomLabel extends JLabel {
        private KeyListener kl = new ArrowListener();
        private MoveListener ml = new MoveListener();
        private int x;
        private int y;
        private int loc_x;
        private int loc_y;


        private final List<Point> points = new ArrayList<>();
        public CustomLabel() {
            x = -1;
            y = -1;
            loc_x = -1;
            loc_y = -1;
            addKeyListener(kl);
            addComponentListener(ml);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawLine(g);
        }

        public void drawLine(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(Color.BLACK);
            for (Point point : points) {
                int curr_x = point.getX();
                int curr_y = point.getY();
                g2d.drawLine(curr_x, curr_y, curr_x, curr_y);
            }
        }

        private boolean checkXBounds() {
            return x <= 235 && x >= 20;
        }

        private boolean checkYBounds() {
            return y <= 178 && x >= 42;
        }

        // Listener for component movement
        private class MoveListener implements ComponentListener {
            @Override public void componentResized(ComponentEvent e)
            {

            }

            @Override public void componentMoved(ComponentEvent e)
            {
                // Maybe make paint more transparent with shakes
                ++counter;
                if (counter == 250) {
                    points.clear();
                    repaint();
                    counter = 0;
                }
            }

            @Override public void componentShown(ComponentEvent e)
            {

            }

            @Override public void componentHidden(ComponentEvent e)
            {

            }

        }
        // Listener for arrow keys
        private class ArrowListener implements KeyListener {
            @Override public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (x == -1) {
                    x = getX() - 100;
                    y = getY() - 120;
                }
                switch (key) {
                case KeyEvent.VK_LEFT -> {
                    x -= 1;
                    if (checkXBounds() && checkYBounds()) {
                        points.add(new Point(x, y));
                        repaint();
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    x += 1;
                    if (checkXBounds() && checkYBounds()) {
                        points.add(new Point(x, y));
                        repaint();
                    }
                }
                case KeyEvent.VK_UP -> {
                    y -= 1;
                    if (checkXBounds() && checkYBounds()) {
                        points.add(new Point(x, y));
                        repaint();
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    y += 1;
                    if (checkXBounds() && checkYBounds()) {
                        points.add(new Point(x, y));
                        repaint();
                    }
                }
                }
            }

            @Override public void keyReleased(KeyEvent e)
            {

            }
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

    // Point Class
    static class Point {
        private final int x;
        private final int y;

        public Point(int x_in, int y_in) {
            x = x_in;
            y = y_in;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}


