package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Hsl;

import static utilities.Bezier.cubicBezier;

public class Saturation extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Saturation");
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
            noFill();
            for (int i = 0; i < 60; i++) {
                float x1 = -20;
                float y1 = random(_height + 200) - 10;
                float x2 = _width + 10;
                float y2 = random(_height + 200) - 10;
                float x1h = x1 + random(40,100);
                float y1h = y1 + random(40,100);
                float x2h = x2 - random(40,100);
                float y2h = y2 - random(40,100);
                Point a = new Point(x1, y1);
                Point d = new Point(x2, y2);
                Point c = new Point(x1h, y1h);
                Point b = new Point(x2h, y2h);
                noStroke();
                Hsl c1 = new Hsl(colour.rand());
                for (float t = 0; t < 1; t += 0.001) {
                    Point p = cubicBezier(a, b, c, d, t);
                    c1.setS((int) map(t, 0, 1, 0, 100));
                    fill(c1.getRgba());
                    pushMatrix();
                    translate(p.x(), p.y());
                    float size = 36;
                    rect(0, 0, size, size);
                    popMatrix();
                }

            }
            save("saturation", name);
        });
    }
}
