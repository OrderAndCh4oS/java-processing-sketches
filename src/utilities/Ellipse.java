package utilities;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

public class Ellipse {
    public static  Point getEllipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }
}

