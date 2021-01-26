package world.ucode.hall;

import world.ucode.Constants;
import world.ucode.Main;
import world.ucode.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Hall extends JPanel {

    private List<Integer> scores;
    private Dimension dimension;

    public Hall() {
        fetchScores();
        initHall();
    }

    private void fetchScores() {
        scores = new ArrayList<>();
        scores.add(234234);
        scores.add(33344);
        scores.add(33);
        scores.add(4445);
    }

    private void initHall() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setBackground(Color.gray);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(Color.green);

        drawScores(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawScores(Graphics g) {

        g.setColor(Color.darkGray);

        var font = new Font("Helvetica", Font.BOLD, 24);
        var fontMetrics = this.getFontMetrics(font);

        g.setFont(font);
        var title = "Hall of Fame";
        var x = (Constants.BOARD_WIDTH - fontMetrics.stringWidth(title)) / 2;
        var y = fontMetrics.getHeight() + 10;
        g.drawString(title, x, y);

        y += fontMetrics.getHeight() + 10;
        for (int i = 0; i < scores.size(); i++) {
            int score = scores.get(i);
            y += drawScore(score, g, y, i + 1);
        }
    }

    private int drawScore(int score, Graphics graphics, int y, int i) {
        var font = new Font("Helvetica", Font.BOLD, 14);
        graphics.setFont(font);
        var message = i + ": " + score;
        var fontMetrics = getFontMetrics(font);
        var x = (Constants.BOARD_WIDTH - 100) / 2;
        graphics.drawString(message, x, y);

        return fontMetrics.getHeight() + 5;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_ESCAPE) {
                setFocusable(false);
                setVisible(false);
                Main.shared().showMainMenu();
            }
        }
    }
}
