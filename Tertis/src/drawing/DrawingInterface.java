package drawing;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface DrawingInterface {
    public void draw(Graphics g);

    public void keyPressed(KeyEvent e);

    public void mouseMoved(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void repaint();

}
