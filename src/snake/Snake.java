package snake;

import java.awt.*;
import java.util.Deque;

import java.util.ArrayDeque;

public class Snake {
    private final Deque<Point> body = new ArrayDeque<>();
    private Direction direction = Direction.RIGHT;
    private boolean growNext = false;

    public Snake(int startX, int startY) {
        body.addFirst(new Point(startX, startY));
        body.addLast(new Point(startX - 1, startY));
        body.addLast(new Point(startX - 2, startY));
    }

    public void setDirection(Direction newDir) {
        if (!direction.isOpposite(newDir)) {
            direction = newDir;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public Point getHead() {
        return body.peekFirst();
    }

    public Deque<Point> getBody() {
        return body;
    }

    public void grow() {
        growNext = true;
    }

    public void move() {
        Point head = getHead();
        Point newHead = new Point(head.x + direction.dx, head.y + direction.dy);
        body.addFirst(newHead);
        if (growNext == false) {
            body.removeLast();
        } else {
            growNext = false;
        }
    }

    public boolean hitSelf() {
        // dau khong duoc cham vao than -> lay dau, lap qua cac diem than, neu trung return true
        Point head = getHead();
        Deque<Point> body = getBody();
        int i = 0;
        for (Point p : body) {
            if (i++ == 0) {
                continue;
            }
            if (head.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean occupies(Point p) {
        Deque<Point> body = getBody();
        for (Point b : body) {
            if (b.equals(p)) {
                return false;
            }
        }
        return true;
    }
}
