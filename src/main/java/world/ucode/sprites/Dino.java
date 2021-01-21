package world.ucode.sprites;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Dino extends Sprite {

    public enum State {NONE, STAND, LEFT, RIGHT, DEAD};

    private Image image_left_up;
    private Image image_right_up;

    private State state;

    public Dino() {
        initDino();
    }

    private void initDino() {
        image_left_up = image("dino_left_up.png");
        image_right_up = image("dino_right_up.png");
        setImage(image_left_up);

        setX(0);
        setY(150);


    }

    public void act(State state) {
        x += dx;
        y += dy;

        if (x <= 2) {
            x = 2;
        }

        updateState(state);
    }

    private void updateState(State state) {
        this.state = state;
        switch (state) {
            case LEFT:
                setImage(image_left_up);
                break;
            case RIGHT:
                setImage(image_right_up);
                break;
            default:
                setImage(image_left_up);
        }
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
