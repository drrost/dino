package world.ucode;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame  {

    public Main() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setTitle("T-Rex");
        setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new Main();
            ex.setVisible(true);
        });
    }
}