package world.ucode;

import world.ucode.controls.ScoreView;
import world.ucode.game.ObstacleFactory;
import world.ucode.sprites.Cactus;
import world.ucode.sprites.Character;
import world.ucode.sprites.Obstacle;
import world.ucode.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board extends JPanel {

    // UI
    private ScoreView scoreView;

    // Values

    private int score = 0;

    private Dimension d;
    private int baseline = 0;
    private Character character;
    private GroundComponent ground;

    private boolean inGame = true;
    private String message = "Game Over";
    private boolean gameStarted = false;

    private Timer timer;
    private int count;
    float gameSpeed = Constants.GAME_SPEED_INITIAL;

    private boolean isJumping = false;
    private int jump_x0 = -Constants.JUMP_WINDOW_WIDTH;
    private int jump_xz = -jump_x0;
    private float jump_x;
    private float jump_dx = Constants.JUMP_STEP;

    private ArrayList<Obstacle> cactuses;
    int countToNextCactus;

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

        scoreView = new ScoreView();
        scoreView.setScore(score);
        scoreView.setSize(100, 100);
        this.add(scoreView);
    }

    private void initGame() {
        score = 0;

        // Character
        character = new Character();
        character.setState(Character.State.STAND);

        Image image = character.getCurrentImage();
        int height = image.getHeight(this);

        character.setX(Constants.CHARACTER_X_POSITION);
        int y = Constants.BOARD_HEIGHT - height - Constants.CHARACTER_Y_OFFSET;
        character.setY(y);
        baseline = y + height;

        // Ground
        ground = new GroundComponent();
        y = character.getY() + height - 30;
        ground.initGround(y);

        count = 0;
        isJumping = false;

        // Obstacles
        cactuses = new ArrayList<Obstacle>();
        countToNextCactus = Constants.COUNT_TO_NEXT_INITIAL;
    }

    private void drawCharacter(Graphics g) {
        if (character.isVisible()) {
            Image image = character.getCurrentImage();
            character.drawBorder(g, this);
            g.drawImage(image, character.getX(), character.getY(), this);
        }

        if (character.isDying()) {
            character.die();
            inGame = false;
        }
    }

    private void drawCactuses(Graphics g) {
        for (Obstacle cactus : cactuses) {
            Image image = cactus.getImage(0);
            g.drawImage(image, cactus.getX(), cactus.getY(), this);
            cactus.drawBorder(g, this);
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
            drawCactuses(g);
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

        Character.State state = (count / 10) % 2 == 0 ? Character.State.LEFT : Character.State.RIGHT;

        if (!gameStarted) {
            state = count > 0 ? state : Character.State.STAND;
            character.act(state);
            return;
        }

        count++;

        int increment = (int) (Constants.CHARACTER_SPEED_FACTOR * gameSpeed);
        ground.shift(increment);

        if (isJumping) {
            state = Character.State.STAND;
            int y = (int) (Constants.JUMP_WIDTH_FACTOR * jump_x * jump_x +
                Constants.JUMP_TOP_FACTOR);
            jump_x += jump_dx;
            character.setY(y);
            if (jump_x >= jump_xz)
                isJumping = false;
        }

        character.act(state);

        // Cactuses
        countToNextCactus--;
        if (countToNextCactus <= 0) {
            addCactus();
            countToNextCactus = Utils.getRandomNumber(
                Constants.COUNT_TO_NEXT_MIN, Constants.COUNT_TO_NEXT_MAX);
        }
        for (Obstacle cactus : cactuses) {
            cactus.setX(cactus.getX() - increment);
        }
        cactuses.stream().filter(o -> o.getX() + o.getWidth(this) > 0);

        score = count / 10;
        scoreView.setScore(score);
    }

    private void addCactus() {
        ObstacleFactory obstacleFactory = new ObstacleFactory();
        Cactus cactus = obstacleFactory.randomCactus();
        cactuses.add(cactus);

        Image image = cactus.getImage(0);
        cactus.setX(Constants.BOARD_WIDTH);
        int height = image.getHeight(this);
        image.getWidth(this);
        cactus.setY(baseline - height);
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

            if (key == KeyEvent.VK_P) {
                if (count == 0)
                    return;
                gameStarted = !gameStarted;
                return;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int x = character.getX();
            int y = character.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE ||
                key == KeyEvent.VK_UP) {
                if (isJumping)
                    return;
                if (!gameStarted)
                    gameStarted = true;
                else
                    jump();
                return;
            }
        }
    }

    private void jump() {
        isJumping = true;
        jump_x = jump_x0;
    }
}
