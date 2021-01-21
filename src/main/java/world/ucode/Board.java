package world.ucode;

import world.ucode.sprites.Commons;
import world.ucode.sprites.Dino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {

    private Dimension d;
    private Dino dino;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "world.ucode.Game Over";

    private Timer timer;
    private int count;

    public Board() {

        initBoard();
        gameInit();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Commons.DELAY, new GameCycle());
        timer.start();

        gameInit();
    }

    private void gameInit() {
        dino = new Dino();
        count = 0;
    }

    private void drawDino(Graphics g) {
        if (dino.isVisible()) {
            g.drawImage(dino.getImage(), dino.getX(), dino.getY(), this);
        }

        if (dino.isDying()) {
            dino.die();
            inGame = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame) {
            drawDino(g);
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Commons.BOARD_WIDTH / 2 - 30, Commons.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Commons.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
            Commons.BOARD_WIDTH / 2);
    }

    private void update() {
        if (false) {
            inGame = false;
            timer.stop();
            message = "world.ucode.Game won!";
        }

        count++;

        Dino.State state = (count / 10) % 2 == 0 ? Dino.State.LEFT : Dino.State.RIGHT;
        dino.act(state);
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            dino.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            dino.keyPressed(e);

            int x = dino.getX();
            int y = dino.getY();

            int key = e.getKeyCode();
        }
    }
}