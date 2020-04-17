package utilities;

import static processing.core.PApplet.sqrt;

public class Trigonometry {
    public static float getDistance(Point p, Point q) {
        float dX = p.x() - q.x();
        float dY = p.y() - q.y();

        return sqrt((dX * dX) + (dY * dY));
    }
}
