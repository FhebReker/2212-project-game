package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ca.uwo.cs2212.group54.stayingalive.accounts.AccountManagement;
import ca.uwo.cs2212.group54.stayingalive.accounts.Parental;

/**
 * WindowUtils.java - only for window utlities.
 * Current function only includes saving the data to JSON by calling Parental.saveAccountData()
 *  when the window is closed abruptly.
 * 
 * @author Osman
 */
public class WindowUtils {

    /**
     * This method saves everything when the window is closed abruptly.
     * IMPORTANT: Don't add this method on all screens. Keep it only for the screens where 
     * the player is already logged in.
     * @param frame
     */
    public static void addSaveOnClose(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                activateSaveSequence();
                frame.dispose(); 
            }
        });
    }

    /**
     * Updates the playtime for the player that's logged in currnetly
     *  before calling the saveAccountData() from parental class. 
     */
    protected static void activateSaveSequence() {
        NavigationControl.endTimer();
        AccountManagement.getCurrentAccount().getProgress().updatePlaytime(
            NavigationControl.getStartTime(), NavigationControl.getEndTime());
        Parental.saveAccountData();
    }
}