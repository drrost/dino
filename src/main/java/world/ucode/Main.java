package world.ucode;

import world.ucode.board.Board;
import world.ucode.menu.Menu;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        initUI();
    }

    private void initUI() {
        showMainMenu();

        setTitle("T-Rex");
        setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            instance = new Main();
            instance.setVisible(true);
        });
    }

    public void startNewGame() {
        showPanel(new Board());
    }

    public void showMainMenu() {
        showPanel(new Menu());
    }

    private static Main instance;

    public static Main shared() {
        return instance;
    }

    private void showPanel(JPanel panel) {
        EventQueue.invokeLater(() -> {
            getContentPane().removeAll();
            getContentPane().add(panel);
            invalidate();
            validate();
            panel.requestFocusInWindow();
            panel.setFocusable(true);
            panel.setVisible(true);
        });
    }
}