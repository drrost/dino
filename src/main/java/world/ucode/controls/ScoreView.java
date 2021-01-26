package world.ucode.controls;

import javax.swing.*;

public class ScoreView extends JLabel {

    public void setScore(int score) {
        setText("Score: " + score);
    }
}
