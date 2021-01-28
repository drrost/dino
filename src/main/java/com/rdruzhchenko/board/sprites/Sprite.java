package com.rdruzhchenko.board.sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;

public class Sprite {

    // Variables
    //
    private boolean visible;
    private boolean dying;

    protected ArrayList<String> imageNames;
    protected ArrayList<Image> images;

    protected int x;
    protected int y;

    int dx;
    int dy;

    // Init
    //
    public Sprite() {
        visible = true;
        imageNames = new ArrayList<>();
        images = new ArrayList<>();
    }

    // Public
    //
    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void addImage(String name) {
        Image image = image(name);
        images.add(image);
    }

    public Image image(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        ImageIcon ii = new ImageIcon(url);
        return ii.getImage();
    }

    public void drawBorder(Graphics g, ImageObserver observer) {
        g.drawRect(getX(), getY(),
            getWidth(observer), getHeight(observer));
    }

    public int getWidth(ImageObserver observer) {
        Image image = getCurrentImage();
        return image.getWidth(observer);
    }

    public int getHeight(ImageObserver observer) {
        Image image = getCurrentImage();
        return image.getHeight(observer);
    }

    public boolean intercepts(Sprite other, ImageObserver observer) {
        Rectangle r1 = new Rectangle(
            getX(), getY(), getWidth(observer), getHeight(observer));
        Rectangle r2 = new Rectangle(
            other.getX(), other.getY(),
            other.getWidth(observer),
            other.getHeight(observer));

        return r1.intersects(r2);
    }

    // Protectes
    //
    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected Image getCurrentImage() {
        return getImage(0);
    }

    // Getters/setters
    //
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
}
