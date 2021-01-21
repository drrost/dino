package world.ucode;

import world.ucode.sprites.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {

    private Dimension d;
    private Character character;
    private GroundComponent ground;

    private boolean inGame = true;
    private String message = "Game Over";
    private boolean gameStarted = false;

    private Timer timer;
    private int count;
    float gameSpeed = Constants.GAME_SPEED_INITIAL;

    public Board() {
        initBoard();
        initGame();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Constants.DELAY, new GameCycle());
        timer.start();
    }

    private void initGame() {
        // Character
        character = new Character();
        character.setX(30);
        character.setY(100);
        character.setState(Character.State.STAND);

        // Ground
        ground = new GroundComponent();
        int height = character.getCurrentImage().getHeight(this);
        int y = character.getY() + height - 30;
        ground.initGround(y);

        count = 0;
    }

    private void drawCharacter(Graphics g) {
        if (character.isVisible()) {
            Image image = character.getCurrentImage();
            g.drawImage(image, character.getX(), character.getY(), this);
        }

        if (character.isDying()) {
            character.die();
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
            ground.drawGround(g, this);
            drawCharacter(g);
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
        g.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Constants.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
            Constants.BOARD_WIDTH / 2);
    }

    private void update() {
        if (false) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        if (!gameStarted) {
            character.act(Character.State.STAND);
            return;
        }

        count++;

        int increment = (int)(Constants.CHARACTER_SPEED_FACTOR * gameSpeed);
        ground.shift(increment);

        Character.State state = (count / 10) % 2 == 0 ? Character.State.LEFT : Character.State.RIGHT;
        character.act(state);
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
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_ENTER) {
                gameStarted = true;
                return;
            }

            character.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            character.keyPressed(e);

            int x = character.getX();
            int y = character.getY();

            int key = e.getKeyCode();
        }
    }
}
