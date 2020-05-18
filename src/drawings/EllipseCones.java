package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Ellipse.getEllipsePoint;

public class EllipseCones extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.EllipseCones");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            stroke(colour.rand());
            float x1 = random(450, _width - 450);
            float y1 = random(450, _height - 450);
            float x2 = random(450,_width - 450);
            float y2 = random(450, _height - 450);
            float x3 = random(450,_width - 450);
            float y3 = random(450, _height - 450);
            for (float a = 0; a < TAU; a += (TAU / 360f)) {
                Point p1 = getEllipsePoint(x1, y1, 450, 450, a);
                Point p2 = getEllipsePoint(x2, y2, 250, 250, a + (TAU / 360f) * 3);
                Point p3 = getEllipsePoint(x3, y3, 100, 100, a + -((TAU / 360f) * 3));
                line(p1.x(), p1.y(), p2.x(), p2.y());
                line(p2.x(), p2.y(), p3.x(), p3.y());
            }
            save("ellipse-cones", name);
        });
    }
}
