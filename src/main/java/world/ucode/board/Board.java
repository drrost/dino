package world.ucode.board;

import world.ucode.Constants;
import world.ucode.Main;
import world.ucode.board.controls.GroundComponent;
import world.ucode.board.controls.ScoreView;
import world.ucode.board.game.ObstacleFactory;
import world.ucode.board.sprites.Cactus;
import world.ucode.board.sprites.Character;
import world.ucode.board.sprites.Cloud;
import world.ucode.board.sprites.Obstacle;
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

    private ArrayList<Cloud> clouds;
    int countToNextCloud;

    int counts_to_increment = 0;

    public Board() {
        initBoard();
        startNewGame();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Constants.DELAY, new GameCycle());
        timer.start();

        scoreView = new ScoreView();
        scoreView.setSize(100, 100);
        add(scoreView);
    }

    private void startNewGame() {
        inGame = true;
        gameStarted = false;
        score = 0;
        scoreView.setScoreSpeed(score, gameSpeed);
        gameSpeed = Constants.GAME_SPEED_INITIAL;
        counts_to_increment = 0;

        // Character
        if (character == null)
            character = new Character();
        character.setState(Character.State.STAND);

        Image image = character.getCurrentImage();
        int height = image.getHeight(this);

        character.setX(Constants.CHARACTER_X_POSITION);
        int y = Constants.BOARD_HEIGHT - height - Constants.CHARACTER_Y_OFFSET;
        character.setY(y);
        baseline = y + height;

        // Ground
        if (ground == null) {
            ground = new GroundComponent();
            y = character.getY() + height - 30;
            ground.initGround(y);
        }

        count = 0;
        isJumping = false;

        // Obstacles
        cactuses = new ArrayList<>();
        countToNextCactus = Constants.COUNT_TO_NEXT_INITIAL;

        // Clouds
        clouds = new ArrayList<>();
        countToNextCloud = Constants.COUNT_TO_NEXT_CLOUD_INITIAL;
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

    private void drawCactuses(Graphics g) {
        for (Obstacle cactus : cactuses) {
            var image = cactus.getImage(0);
            g.drawImage(image, cactus.getX(), cactus.getY(), this);
        }
    }

    private void drawClouds(Graphics g) {
        for (Cloud cloud : clouds) {
            var image = cloud.getImage(0);
            g.drawImage(image, cloud.getX(), cloud.getY(), this);
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

        if (!inGame)
            gameOver(g);

        ground.drawGround(g, this);
        drawCactuses(g);
        drawClouds(g);
        drawCharacter(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(new Color(32, 32, 32));
        g.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        var small = new Font("Helvetica", Font.BOLD, 24);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.darkGray);

        g.setFont(small);
        var x = (Constants.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2;
        var y = Constants.BOARD_HEIGHT / 2;
        g.drawString(message, x, y);

        var smaller = new Font("Helvetica", Font.BOLD, 14);
        g.setFont(smaller);
        var submessage = "(press ENTER to restart)";
        fontMetrics = getFontMetrics(smaller);
        x = (Constants.BOARD_WIDTH - fontMetrics.stringWidth(submessage)) / 2;
        y = Constants.BOARD_HEIGHT / 2 + fontMetrics.getHeight() + 5;
        g.drawString(submessage, x, y);
    }

    private void update() {

        int step = 10;
        Character.State state = (count / step) % 2 == 0 ? Character.State.LEFT : Character.State.RIGHT;

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
        //
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

        // Clouds
        countToNextCloud--;
        if (countToNextCloud <= 0) {
            addCloud();
            countToNextCloud = Utils.getRandomNumber(
                Constants.COUNT_TO_NEXT_CLOUD_MIN, Constants.COUNT_TO_NEXT_CLOUD_MAX);
        }
        for (Cloud cloud : clouds) {
            cloud.setX(cloud.getX() - (int) (increment * 0.5));
        }
        clouds.stream().filter(o -> o.getX() + o.getWidth(this) > 0);

        // Collisions
        //
        for (Obstacle cactus : cactuses) {
            if (character.intercepts(cactus, this)) {
                gameStarted = false;
                inGame = false;
                Utils.playSound("game_over.wav");
            }
        }

        // Scores
        //
        var oldScore = score;
        score = count / step;

        // Complexity
        //
        float increment_value = Constants.GAME_SPEED_INCREMENT;
        int increment_gap = Constants.GAME_SPEED_GAP;

        int total_increment_steps = increment_gap * step;
        if (score % 100 == 0 && gameSpeed < Constants.GAME_SPEED_MAX && score != oldScore) {
            counts_to_increment = total_increment_steps;
            Utils.playSound("speed_up.wav");
        }

        if (counts_to_increment > 0) {
            float increment_step = increment_value / total_increment_steps;
            gameSpeed += increment_step;
            counts_to_increment--;
        }

        scoreView.setScoreSpeed(score, gameSpeed);
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

    private void addCloud() {
        Cloud cloud = new Cloud();
        clouds.add(cloud);

        Image image = cloud.getImage(0);
        cloud.setX(Constants.BOARD_WIDTH);
        int height = image.getHeight(this);
        image.getWidth(this);
        var y_offset = Utils.getRandomNumber(Constants.CLOUD_Y_MIN, Constants.CLOUD_Y_MAX);
        cloud.setY(baseline - height - y_offset);
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
                if (inGame == false) {
                    startNewGame();
                    return;
                }
                if (gameStarted == false)
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
                if (inGame == false) {
                    return;
                }
                if (isJumping)
                    return;
                if (!gameStarted)
                    gameStarted = true;
                else
                    jump();
                return;
            }

            if (key == KeyEvent.VK_ESCAPE) {
                setFocusable(false);
                setVisible(false);
                timer.stop();
                Main.shared().showMainMenu();
            }
        }
    }

    private void jump() {
        isJumping = true;
        jump_x = jump_x0;
         Utils.playSound("jump.wav");
    }
}
