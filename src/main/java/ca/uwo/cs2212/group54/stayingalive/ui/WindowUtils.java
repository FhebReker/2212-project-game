package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ca.uwo.cs2212.group54.stayingalive.accounts.Parental;

/**
 * WindowUtils.java - only for window utlities.
 * Current function only includes saving the data to JSON by calling Parental.saveAccountData()
 *  when the window is closed abruptly.
 */
public class WindowUtils {

    public static void addSaveOnClose(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Parental.saveAccountData();
                frame.dispose(); 
            }
        });
    }
}