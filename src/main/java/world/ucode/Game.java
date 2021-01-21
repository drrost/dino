package world.ucode;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public Game() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setTitle("T-Rex");
        setSize(800, 250);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void run() {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}
