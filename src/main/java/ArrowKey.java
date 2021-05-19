package main.java;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArrowKey implements KeyListener
{
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
        case KeyEvent.VK_LEFT:
            System.out.println("Left!");
            break;
        case KeyEvent.VK_RIGHT:
            System.out.println("Right!");
            break;
        case KeyEvent.VK_UP:
            System.out.println("Up!");
            break;
        case KeyEvent.VK_DOWN:
            System.out.println("Down!");
            break;
        }
    }

    @Override
     public void keyTyped(KeyEvent e)
    {

    }

    @Override
     public void keyReleased(KeyEvent e)
    {

    }

}
