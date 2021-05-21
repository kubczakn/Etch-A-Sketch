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
        f.setLayout(null);
        f.add(new MainPanel(), BorderLayout.CENTER);
        f.setSize(800, 800);
        f.setVisible(true);

        // Turn fullscreen on or off
        f.setResizable(false);

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

//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawLine(g);
//    }
//
//    public void drawLine(Graphics g) {
//        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.CYAN);
//        for (Point point : points) {
//            double curr_x = point.getX();
//            double curr_y = point.getY();
//            g2d.drawLine(curr_x, curr_y, curr_x, curr_y);
//        }
//    }


    class CustomLabel extends JLabel {
        private int x;
        private int y;

        private boolean up;
        private boolean down;
        private boolean left;
        private boolean right;


        private final List<Point> points = new ArrayList<>();
        public CustomLabel() {
            x = -1;
            y = -1;
            KeyListener kl = new ArrowListener();
            addKeyListener(kl);
            MoveListener ml = new MoveListener();
            addComponentListener(ml);

            up = false;
            down = false;
            right = false;
            left = false;

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawLine(g);
        }

        public void drawLine(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1.5F));
            for (Point point : points) {
                int curr_x = (int)point.getX();
                int curr_y = (int)point.getY();
                g2d.drawLine(curr_x, curr_y, curr_x, curr_y);
            }
        }

        private boolean checkXBounds() {
            return x <= 235 && x >= 35;
        }

        private boolean checkYBounds() {
            return y <= 178 && y >= 42;
        }

        private void updatePaint() {
            if (up) {
                y -= 1;
            }
            if (down) {
               y += 1;
            }
            if (left) {
               x -= 1;
            }
            if (right) {
               x += 1;
            }
            if (checkXBounds() && checkYBounds()) {
                points.add(new Point(x, y));
                repaint();
            }
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
                    left = true;
                }
                case KeyEvent.VK_RIGHT -> {
                    right = true;
                }
                case KeyEvent.VK_UP -> {
                    up = true;
                }
                case KeyEvent.VK_DOWN -> {
                    down = true;
                }
                }
                updatePaint();
            }

            @Override public void keyReleased(KeyEvent e)
            {
                int key = e.getKeyCode();
                switch (key) {
                case KeyEvent.VK_LEFT -> {
                    left = false;
                }
                case KeyEvent.VK_RIGHT -> {
                    right = false;
                }
                case KeyEvent.VK_UP -> {
                    up = false;
                }
                case KeyEvent.VK_DOWN -> {
                    down = false;
                }
                }
            }
        }
    }

    // Logic for moving image
    class MovingAdapter extends MouseAdapter
    {
        private boolean drag = false;

        Point location;
        MouseEvent pressed;

        public void mousePressed(MouseEvent e) {
            pressed = e;
            if (e.getSource() == label) {
                drag = true;
            }
        }

        public void mouseReleased(MouseEvent e) {
            drag = false;
        }

        public void mouseDragged(MouseEvent e) {
            if (drag) {
                location = label.getLocation(location);
                int x = location.x - pressed.getX() + e.getX();
                int y = location.y - pressed.getY() + e.getY();
                label.setLocation(x, y);
            }
        }
    }


}


