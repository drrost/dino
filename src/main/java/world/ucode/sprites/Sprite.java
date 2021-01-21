package world.ucode.sprites;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class Sprite {

    private boolean visible;
    private boolean dying;

    protected ArrayList<String> imageNames;
    protected ArrayList<Image> images;

    protected int x;
    protected int y;

    int dx;
    int dy;

    public Sprite() {
        visible = true;
        imageNames = new ArrayList<>();
        images = new ArrayList<>();
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

    public void addImage(String name) {
        Image image = image(name);
        images.add(image);
    }

    public Image getImage(int idx) {
        return images.get(idx);
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
