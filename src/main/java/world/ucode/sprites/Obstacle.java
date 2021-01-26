package world.ucode.sprites;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Obstacle extends Sprite {

    public int getWidth(ImageObserver observer) {
        Image image = getImage(0);
        return image.getWidth(observer);
    }

    public int getHeight(ImageObserver observer) {
        Image image = getImage(0);
        return image.getHeight(observer);
    }
}
