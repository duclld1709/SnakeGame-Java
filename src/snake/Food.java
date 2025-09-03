package snake;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private Point pos;

    public Food() {
        pos = new Point(0, 0);
    }

    public Point getPos() {
        return pos;
    }

    public void respawn(int gridW, int gridH, Snake snake) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        while (true) {
            Point candidate = new Point(rnd.nextInt(gridW), rnd.nextInt(gridH));
            if (snake.occupies(candidate)) {
                pos = candidate;
                return;
            }
        }
    }
}
