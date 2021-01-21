package world.ucode.sprites;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;

public class Sprite {

    private boolean visible;
    private Image image;
    private boolean dying;

    protected int x;
    protected int y;

    int dx;
    int dy;

    public Sprite() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return dying;
    }

    public Image image(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        ImageIcon ii = new ImageIcon(url);
        return ii.getImage();
    }
}
