package ca.uwo.cs2212.group54.stayingalive.ui;

/**
 * Interfacce for all screens.
 * 
 * <p>
 * Extends ActionListener for button presses, shows screen for visuals, moves to the next screen, and gets the class' JFrame.
 * @author Fardin Abbassi
 */
import java.awt.event.*;
import javax.swing.*;

public interface Screen extends ActionListener{
    void showScreen();
    void moveToNextScreen(String screenToMoveTo);
    JFrame getFrame();
}