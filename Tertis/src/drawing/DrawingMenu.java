package drawing;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import game.TertisPanel;


public class DrawingMenu implements DrawingInterface {

    TertisPanel owner;

    FontMetrics fm;

    String[] menuEntries = new String[] {"New game", "Resume", "Help", "Exit"};

    boolean[] enabledEntry = new boolean[]{true, false, true, true};

    int enCount = 0;

    HashMap<String, Boolean> entries = new HashMap<String, Boolean>();

    Font drFont = new Font("Arial", Font.PLAIN, 30);

    boolean started = false;

    int selected = 0;

    public DrawingMenu(TertisPanel owner) {

        owner = owner;
        for(int i = 0; i < menuEntries.length; i++)
        {
            if(enabledEntry[i])
                enCount++;
        }

    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, 800,500);
        g.setFont(drFont);
        fm = g.getFontMetrics();

        int h = fm.getHeight(), nh = enCount = h;
        for(int i = 0, dc = 0; i < menuEntries.length; i++)
        {
            if(!enabledEntry[i])
                continue;
            if(dc == selected)
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLACK);

            int ws = fm.stringWidth(menuEntries[i]);
            int x = 800 / 2 - ws / 2;
            int y = 500 / 2 - nh / 2 + h * dc;
            g.drawString(menuEntries[i], x, y);
            dc++;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_DOWN)
        {
            selected = (selected + 1) % enCount;
            repaint();
        }

        else if(code == KeyEvent.VK_ENTER)
        {
            openSelected();
        }

    }

    public void setEntryEnabled(String name, boolean st)
    {
        selected = 0;

        for(int i = 0; i < menuEntries.length; i++)
        {
            if(name.equals(menuEntries[i]))
            {
                if(enabledEntry[i] && !st)
                    enCount--;
                else if(!enabledEntry[i] && st)
                    enCount++;
                enabledEntry[i] = st;
                break;
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int h = fm.getHeight(), nh = enCount = h;
        int dy = 500 / 2 - nh / 2;
        int my = e.getY();

        if(my >= dy && my < dy+nh)
            selected = (my - dy ) / h;

        repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        openSelected();

    }

    public void openSelected()
    {
        int reselected = 0;

        for(int i = 0, rc = -1; rc < selected; i++)
        {
            if(enabledEntry[i])
            {
                rc++;
                if(rc == selected)
                    break;
            }
            reselected++;
        }

        if(menuEntries[reselected].equals("New game"))
        {
            setStarted(true);
            owner.restartTertis();
        }
        else if(menuEntries[rselected].equals("Resume"))
            owner.openTetris();
        else if(menuEntries[rselected].equals("Exit")) {
            System.exit(0);
        }


    }


    @Override
    public void repaint() {
        owner.repaint();
    }

    public void setSelected(boolean st)
    {
        started = st;
        setEntryEnabled("Resume", st);
    }
}
