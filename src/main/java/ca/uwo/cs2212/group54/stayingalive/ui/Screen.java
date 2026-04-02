package ca.uwo.cs2212.group54.stayingalive.ui;

// package ui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

public interface Screen extends ActionListener{
    void showScreen();
    void moveToNextScreen(String screenToMoveTo);
    JFrame getFrame();
}