package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;

public class SnakePlane extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.SnakePlane");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            noFill();
            for (int i = 0; i < 18; i++) {
                float x1 = -20;
                float y1 = random(_height + 100) - 50;
                float x2 = _width + 10;
                float y2 = random(_height + 100) - 50;
                float x1h = x1 + random(225);
                float y1h = y1 + random(225);
                float x2h = x2 - random(225);
                float y2h = y2 - random(225);
                Point a = new Point(x1, y1);
                Point d = new Point(x2, y2);
                Point c = new Point(x1h, y1h);
                Point b = new Point(x2h, y2h);
                noFill();
                strokeWeight(1);
                strokeCap(PROJECT);
                for (float t = 0; t < 1; t += 0.01) {
                    Point p = cubicBezier(a, b, c, d, t);
                    stroke(colour.rand());
                    pushMatrix();
                    translate(p.x(), p.y());
                    rotateY(map(p.x(), 0, _width / 2, 0, PI));
                    float size = map(p.x(), 0, _width / 2, 0, 50);
                    rect(0, 0, size, size);
                    popMatrix();
                }

            }
            save("snake-plane", name);
        });
    }
}
