package world.ucode.sprites;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Dino extends Sprite {

    public enum State { STAND, LEFT, RIGHT, DEAD };

    private State state;

    public Dino() {
        initDino();
    }

    private void initDino() {
        addImage("dino_stand.png");
        addImage("dino_left_up.png");
        addImage("dino_right_up.png");
    }

    public void act(State state) {
        x += dx;
        y += dy;

        if (x <= 2) {
            x = 2;
        }

        setState(state);
    }

    public void setState(State state) {
        this.state = state;
    }

    public Image getCurrentImage() {
        return getImage(state.ordinal());
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
