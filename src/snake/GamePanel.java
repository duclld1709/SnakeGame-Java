package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener{
    public static final int CELL = 20;
    public static final int GRID_W = 30;
    public static final int GRID_H = 24;
    public static final int HUD_H = 60;
    public static final int WIDTH = GRID_W * CELL;
    public static final int HEIGHT = GRID_H * CELL + HUD_H;

    private Snake snake;
    private Food food;
    private int score = 0;
    private boolean running = false;

    private final Timer timer;
    private Direction pendingDir = null;
    private final InputTracker inputTracker;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(18, 18, 18));
        setFocusable(true);

        inputTracker = new InputTracker(this);
        addKeyListener(inputTracker);

        timer = new Timer(100, this); 

        initGame();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    private void initGame() {
        snake = new Snake(GRID_W / 2, GRID_H / 2);
        food = new Food();
        food.respawn(GRID_W, GRID_H, snake);
        score = 0;
        running = true;
        pendingDir = null;
        timer.start();
    }

    public void restart() {
        initGame();
        repaint();
    }

    public void queueDirection(Direction d) {
        if (!snake.getDirection().isOpposite(d)) {
            pendingDir = d;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) return;

        if (pendingDir != null) {
            snake.setDirection((pendingDir));
            pendingDir = null;
        }
        snake.move();

        // Wall collision
        Point head = snake.getHead();
        if (head.x < 0 || head.y < 0 || head.x >= GRID_W || head.y >= GRID_H) {
            gameOver();
            return;
        }

        // Self collision
        if (snake.hitSelf()) {
            gameOver();
            return;
        }

        // Eat 
        if (head.equals(food.getPos())) {
            snake.grow();
            score += 10;
            food.respawn(GRID_W, GRID_H, snake);
        }

        repaint();
    }

    private void gameOver() {
        running = false;
        timer.stop();
        repaint();
    }

    @Override   
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // HUD background
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, WIDTH, HUD_H);

        // HUD text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Consolas", Font.BOLD, 18));
        String dirStr = running ? snake.getDirection().name() : "STOP";
        g2.drawString("Score: " + score, 12, 48);
        g2.drawString("Direction: " + dirStr, 160, 26);
        g2.drawString("Last key: " + inputTracker.getLastKey(), 360, 26);
        g2.setFont(new Font("Consolas", Font.PLAIN, 14));
        g2.drawString("Recent inputs: ", 12, 48);
        int xLog = 160, yLog = 48;
        for (InputTracker.Entry e : inputTracker.snapshot()) {
            g2.drawString(e.timeStr + " - " + e.key, xLog, yLog);
            xLog += 160;
            if (xLog > WIDTH - 160) {xLog = 130; yLog += 18;}
        }

        // Playfield
        g2.translate(0, HUD_H);

        // Optional grid
        g2.setColor(new Color(40, 40, 40));
        for (int x = 0;  x <= GRID_W; x++) g2.drawLine(x * CELL, 0, x * CELL, GRID_H * CELL);
        for (int y = 0; y <= GRID_H; y++) g2.drawLine(0, y * CELL, GRID_W * CELL, y * CELL);

        // Food
        g2.setColor(new Color(220, 70, 70));
        drawCell(g2, food.getPos().x, food.getPos().y);

        // Snake
        int i = 0;
        for (Point p : snake.getBody()) {
            if (i++ == 0) {
                g2.setColor(new Color(90, 200, 250));
            } else {
                g2.setColor(new Color(80, 180, 120));
            }
            drawCell(g2, p.x, p.y);
        }

        // Game over overplay
        if (!running) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, GRID_W * CELL, GRID_H * CELL);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Consolas", Font.BOLD, 28));
            drawCentered(g2, "GAME OVER - Score: " + score, GRID_W * CELL, GRID_H * CELL, -20);
            g2.setFont(new Font("Consolas", Font.PLAIN, 18));
            drawCentered(g2, "Press R to Restart, Esc to Exit", GRID_W * CELL, GRID_H * CELL, 20);
        }

        g2.dispose();
    }

    private void drawCell(Graphics2D g2, int gx, int gy) {
        g2.fillRoundRect(gx * CELL + 2, gy * CELL + 2, CELL - 4, CELL - 4, 6, 6);
    }

    private void drawCentered(Graphics2D g2, String text, int w, int h, int dy) {
        FontMetrics fm = g2.getFontMetrics();
        int x = (w - fm.stringWidth(text)) / 2;
        int y = (h - fm.getHeight()) / 2 + fm.getAscent() + dy;
        g2.drawString(text, x, y);
    }
    
}
