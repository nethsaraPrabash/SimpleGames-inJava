package game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static JFrame mframe;

    private static void createAndShowGUI() {
        mframe = new JFrame();

        mframe.setResizable(false);
        mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mframe.pack();
        mframe.setVisible(true);
        mframe.addWindowListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {}
            public void windowLostFocus(WindowEvent e) {}
        });
    }
}