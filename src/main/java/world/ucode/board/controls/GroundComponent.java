package world.ucode.board.controls;

import world.ucode.board.sprites.Ground;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class GroundComponent {

    private Ground ground_1;
    private Ground ground_2;

    public void initGround(int y) {
        // Ground 1
        ground_1 = new Ground();
        ground_1.setX(0);
        ground_1.setY(y);

        // Ground 2
        ground_2 = new Ground();
        ImageIcon ii = new ImageIcon(ground_1.getImage(0));
        ground_2.setX(ii.getIconWidth());
        ground_2.setY(ground_1.getY());
    }

    public void drawGround(Graphics g, ImageObserver observer) {
        Image image = ground_1.getImage(0);
        g.drawImage(image, ground_1.getX(), ground_1.getY(), observer);

        image = ground_2.getImage(0);
        g.drawImage(image, ground_2.getX(), ground_2.getY(), observer);
    }

    public void shift(int increment) {
        ground_1.setX(ground_1.getX() - increment);
        ground_2.setX(ground_2.getX() - increment);

        if (ground_2.getX() <= 0) {
            ImageIcon ii = new ImageIcon(ground_2.getImage(0));
            ground_1.setX(ground_2.getX() + ii.getIconWidth());
            Ground temp = ground_1;
            ground_1 = ground_2;
            ground_2 = temp;
        }
    }
}
