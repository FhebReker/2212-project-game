package sprites;

import javax.swing.JLabel;

/**
 * Sprite class
 * 
 * @author Malik Alghneimin
 */

public class Sprite {
    private JLabel image;
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
}