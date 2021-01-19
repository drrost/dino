import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Dino dino;
    private final int DELAY = 10;
    private int count = 0;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        dino = new Dino();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        Image im = dino.getImage();
        g2d.drawImage(im, dino.getX(), dino.getY(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void step() {
        count++;
        Dino.State state = (count / 10) % 2 == 0 ? Dino.State.LEFT : Dino.State.RIGHT;
        dino.setState(state);

        dino.move();
        repaint(dino.getX() - 1, dino.getY() - 1,
            dino.getWidth() + 2, dino.getHeight() + 2);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            dino.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            dino.keyPressed(e);
        }
    }
}
