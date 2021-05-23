package main.java.com.kubczakn.etch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

// JPanel for image
public class MainPanel extends JPanel
{
    private final BufferedImage img;
    private final MovingAdapter ma = new MovingAdapter();
    private final CustomLabel label = new CustomLabel();
    private final JLabel instructions = new JLabel();
    private int counter;

    public MainPanel()
        throws IOException
    {
        // Set layout
        setLayout(new GridBagLayout());

        // Set coordinates for painting
        counter = 0;

        // Set background color and size
        setBackground(Color.LIGHT_GRAY);
        setSize(1100, 900);

        // Set image
        img = ImageIO.read(Objects.requireNonNull(
            getClass().getClassLoader().getResourceAsStream("images/etch_a_sketch.jpg")));
        label.setIcon(new ImageIcon(img));
        label.setSize(label.getPreferredSize());
        label.addMouseListener(ma);
        label.addMouseMotionListener(ma);
        label.setFocusable(true);
        label.requestFocus();
        label.requestFocusInWindow();
        // Add etch-a-sketch
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
    }

    class CustomLabel extends JLabel {
        private int x;
        private int y;
        private int alpha;
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

            // Add instructions
//            setText("<html>Use arrow keys to draw<br><br>Click and drag to erase<html>");
            alpha = 255;
            setText("Use arrow keys to draw, click and drag to erase");
            setFont(new Font("Calibri", Font.BOLD, 20));
            setForeground(new Color(0,0,0, alpha));
            setHorizontalTextPosition(JLabel.CENTER);

            // Set timer to fade away instructions
            TimerTask task = new TimerTask()
            {
                @Override public void run()
                {
//                    setFont(new Font("Calibri", Font.BOLD, 0));
                    while (alpha >= 0) {
                        setForeground(new Color(0, 0, 0, alpha));
                        alpha -= 15;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(alpha);
                }
            };
            Timer timer = new Timer("Timer");
            int delay = 5000;
            timer.schedule(task, delay);
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

        private int checkXBounds() {
            if (x > 549) {
                return 549;
            }
            else if (x < 84) {
                return 84;
            }
            return -1;
        }

        private int checkYBounds() {
            if (y > 425) {
                return 425;
            }
            else if (y < 89) {
                return 89;
            }
            return -1;
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
            int tmp_x = checkXBounds();
            int tmp_y = checkYBounds();
            if (tmp_x != -1) {
                x = tmp_x;
            }
            if (tmp_y != -1) {
                y = tmp_y;
            }
            points.add(new Point(x, y));
            repaint();
        }

        // Listener for component movement
        private class MoveListener implements ComponentListener
        {
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
                    x = getX() + 80;
                    y = getY() + 50;
                }
                //                System.out.printf("X: %d Y: %d\n", x, y);
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
