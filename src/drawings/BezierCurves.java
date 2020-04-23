package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;

public class BezierCurves extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BezierCurves");
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
            background(colour.bg());
            for (int i = 0; i < 1000; i++) {
                Point a = new Point(random(_width), random(_height));
                Point b = new Point(random(_width), random(_height));
                Point c = new Point(random(_width), random(_height));
                Point d = new Point(random(_width), random(_height));
                noFill();
                stroke(colour.rand());
                beginShape();
                for (float t = 0; t < 1; t += 0.01) {
                    Point p = cubicBezier(a, b, c, d, t);
                    vertex(p.x(), p.y());
                }
                endShape();
            }
            save("bezier", name);
        });
    }
}
