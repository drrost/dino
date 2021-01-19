import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

public class Dino {

    public enum State {
        NONE, STAND, LEFT, RIGHT
    }

    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private int w;
    private int h;

    private Image image_left_up;
    private Image image_right_up;

    private State state = State.STAND;

    public Dino() {
        loadImage();
    }

    private void loadImage() {
        image_left_up = image("dino_left_up.png");
        image_right_up = image("dino_right_up.png");
    }

    private Image image(String name) {
        URL url = this.getClass().getClassLoader().getResource(name);
        ImageIcon ii = new ImageIcon(url);

        Image im = ii.getImage();
        w = im.getWidth(null);
        h = im.getHeight(null);

        return im;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public Image getImage() {
        if (state == State.LEFT)
            return image_left_up;
        else
            return image_right_up;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
