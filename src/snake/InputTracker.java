package snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;

public class InputTracker extends KeyAdapter {
    public static class Entry {
        public final String key;
        public final String timeStr;
        public Entry(String key, String timeStr) {
            this.key = key; this.timeStr = timeStr;
        }
    }

    private final Deque<Entry> recent = new ArrayDeque<>();
    private final int limit = 3;
    private volatile String lastKey = "-";

    private final GamePanel game;

    public InputTracker(GamePanel game) {
        this.game = game;
    }

    private void log(String keyName) {
        lastKey = keyName;
        String t = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        recent.addFirst(new Entry(keyName, t));
        while (recent.size() > limit) recent.removeLast();
    }

    public String getLastKey() {
        return lastKey;
    }

    public Entry[] snapshot() {
        return recent.toArray(new Entry[0]);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        Direction dir = null;
        switch (code) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                dir = Direction.UP;  log("UP/W"); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                dir = Direction.DOWN; log("DOWN/S"); break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                dir = Direction.LEFT; log("LEFT/A"); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                dir = Direction.RIGHT; log("RIGHT/D"); break;
            case KeyEvent.VK_R:
                log("R (restart)");
                game.restart();
                return;
            case KeyEvent.VK_ESCAPE:
                log("ESC (exit)");
                System.exit(0);
                return;
        }
        if (dir != null) {
            game.queueDirection(dir);
        }
    }
}
