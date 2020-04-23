package utilities;

public class Bezier {
    public static Point quadraticBezier(Point a, Point b, Point c, float t) {
        float x = (float) (Math.pow(1 - t, 2) * a.x() +
                (1 - t) * 2 * t * b.x() +
                t * t * c.x());
        float y = (float) (Math.pow(1 - t, 2) * a.y() +
                (1 - t) * 2 * t * b.y() +
                t * t * c.y());
        return new Point(x, y);
    }

    public static Point cubicBezier(Point a, Point b, Point c, Point d, float t) {
        float x = (float) (Math.pow(1 - t, 3) * a.x() +
                Math.pow(1 - t, 2) * 3 * t * b.x() +
                (1 - t) * 3 * t * t * c.x() +
                t * t * t * d.x());
        float y = (float) (Math.pow(1 - t, 3) * a.y() +
                Math.pow(1 - t, 2) * 3 * t * b.y() +
                (1 - t) * 3 * t * t * c.y() +
                t * t * t * d.y());
        return new Point(x, y);
    }
}
