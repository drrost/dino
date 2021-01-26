package world.ucode.board.sprites;

import java.awt.*;

public class Character extends Sprite {

    public enum State { STAND, LEFT, RIGHT, DEAD };

    private State state;

    public Character() {
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
        setState(state);
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public Image getCurrentImage() {
        return getImage(state.ordinal());
    }

}
