package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;

public class BezierCurvesTwo extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BezierCurvesTwo");
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
            for (int i = 0; i < 18; i++) {
                float x1 = -10;
                float y1 = random(_height);
                float x2 = _width + 10;
                float y2 = random(_height);
                float x1h = x1 + random( 250);
                float y1h = y1 + random( 250);
                float x2h = x2 - random( 250);
                float y2h = y2 - random( 250);
                Point a = new Point(x1, y1);
                Point d = new Point(x2, y2);
                Point c = new Point(x1h, y1h);
                Point b = new Point(x2h, y2h);
                noFill();
                stroke(colour.rand());
                float step = random(2, 10);
                strokeWeight(step);
                strokeCap(PROJECT);
                beginShape();
                for (float t = 0; t < 1; t += 0.01) {
                    Point p = cubicBezier(a, b, c, d, t);
                    vertex(p.x(), p.y());
                }
                endShape();
            }
            save("essential", name);
        });
    }
}
