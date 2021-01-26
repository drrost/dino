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
        EventQueue.invokeLater(() -> {
            getContentPane().removeAll();
            var board = new Board();
            getContentPane().add(board);
            invalidate();
            validate();
            board.requestFocusInWindow();
            board.setFocusable(true);
            board.setVisible(true);
        });
    }

    public void showMainMenu() {
        EventQueue.invokeLater(() -> {
            getContentPane().removeAll();
            var menu = new Menu();
            getContentPane().add(menu);
            invalidate();
            validate();
            menu.requestFocusInWindow();
            menu.setFocusable(true);
            menu.setVisible(true);
        });
    }

    private static Main instance;

    public static Main shared() {
        return instance;
    }
}