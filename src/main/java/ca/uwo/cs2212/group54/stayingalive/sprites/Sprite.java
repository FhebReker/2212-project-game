package ca.uwo.cs2212.group54.stayingalive.sprites;

import javax.swing.JLabel;

/**
 * Sprite class
 * 
 * @author Malik Alghneimin
 */

public class Sprite {
    private final JLabel image;
    private int x;
    private int y;

    public Sprite(JLabel image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public JLabel getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        if (this.image != null) {
            this.image.setLocation(this.x, this.y);
        }
    }

    public void setY(int y) {
        this.y = y;
        if (this.image != null) {
            this.image.setLocation(this.x, this.y);
        }
    }
}