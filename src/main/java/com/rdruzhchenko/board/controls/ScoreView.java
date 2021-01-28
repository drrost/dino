package com.rdruzhchenko.board.controls;

import javax.swing.*;

public class ScoreView extends JLabel {

    public void setScoreSpeed(int score, float speed) {
        String text = String.format("Score: %d, Speed: %.1f", score, speed);
        setText(text);
    }
}
