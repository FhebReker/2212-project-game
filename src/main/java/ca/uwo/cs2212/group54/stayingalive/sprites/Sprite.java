package ca.uwo.cs2212.group54.stayingalive.sprites;

import java.awt.Image;

/**
 * Sprite class
 * 
 * @author Malik Alghneimin
 */

public class Sprite {
    private final Image image;
    private int x;
    private int y;

    public Sprite(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
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
            //this.image.setLocation(this.x, this.y);
        }
    }

    public void setY(int y) {
        this.y = y;
        if (this.image != null) {
            //this.image.setLocation(this.x, this.y);
        }
    }
}