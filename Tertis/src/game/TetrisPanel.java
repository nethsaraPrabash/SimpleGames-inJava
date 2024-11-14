package game;

import drawing.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisPanel extends JPanel {

    public DrawingInterface current = null;

    public DrawingMenu menu;
    public DrawingTetris tetrisd;

    public TetrisPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        KeyListener kl = new KeyListener() {

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if(current != null)
                    current.keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
            }

        };
        MouseMotionListener ml = new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {

            }

            public void mouseMoved(MouseEvent e) {
                current.mouseMoved(e);
            }
        };
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                current.mousePressed(e);
            }
        };
        this.addKeyListener(kl);
        this.addMouseMotionListener(ml);
        this.addMouseListener(ma);
        this.setFocusable(true);
        menu = new DrawingMenu(this);
        tetrisd = new DrawingTetris(this);
        current = menu;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    @Override
    public void paintComponent(Graphics g) {
        //System.out.println("repainting");
        current.draw(g);
    }

    public void setDrawingInterface(DrawingInterface dif) {
        current = dif;
    }

    public void openTetris() {
        current = tetrisd;
        tetrisd.paused = false;
        repaint();
    }

    public void openMenu() {
        current = menu;
        repaint();
    }

    public void restartTetris() {
        current = tetrisd;
        if(tetrisd.started)
            tetrisd.stopTetris();
        tetrisd.start();
        tetrisd.paused = false;
        repaint();
    }

    public void pause() {
        if(current == tetrisd)
            tetrisd.paused = true;
    }

}
