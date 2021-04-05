package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;

public class SaturationV2 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.SaturationV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 25; iter++) {
            _colours.getColours("candyfloss").forEach((name, colour) -> {
                background(colour.black());
                noFill();
                int whiteBlack;
                for (int i = 0; i < 80; i++) {
                    whiteBlack = i % 2 + 1;
                    float x1 = random(-64, -96);
                    float y1 = random(_height + 100) - 50;
                    float x2 = _width + 20;
                    float y2 = random(_height + 100) - 50;
                    float x1h = x1 + random(80, 200);
                    float y1h = y1 + random(80, 200);
                    float x2h = x2 - random(80, 200);
                    float y2h = y2 - random(80, 200);
                    Point a = new Point(x1, y1);
                    Point d = new Point(x2, y2);
                    Point c = new Point(x1h, y1h);
                    Point b = new Point(x2h, y2h);
                    noStroke();
                    for (float t = 0; t < 1; t += 0.00005) {
                        Point p = cubicBezier(a, b, c, d, t);
                        fill(floor(t * 100) % whiteBlack == 0 ? colour.black() : colour.white());
                        pushMatrix();
                        translate(p.x(), p.y());
                        float size = 64;
                        rect(0, 0, size, size);
                        popMatrix();
                    }

                }
                save("saturation-v2", name);
            });
        }
    }
}
