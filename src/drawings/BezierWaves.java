package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;
import static utilities.Random.randomInt;

public class BezierWaves extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BezierWaves");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
                noiseSeed(randomInt(0, 999999));
                background(colour.black());
                drawCurves(colour.rand());
                save("bezier-waves", name);
        });
    }

    public void drawCurves(int colour) {
        float x1 = random(0, 250) - 125;
        float y1 = -120;
        float x2 = random(0, 250) - 125;
        float y2 = _height + 120;
        float x1h = x1 + random(0, 400) - 150;
        float x2h = x1 + random(0, 400) - 150;
        float y1h = y1 + random(150, 400);
        float y2h = y2 - random(150, 400);
        int margin = 100;
        for (int x = margin; x < _width - margin; x += _width / (_width / 5f)) {
            drawCubicBezier(colour, x + x1, y1, x + x2, y2, x + x1h, x + x2h, y1h, y2h);
        }
    }

    public void drawCubicBezier(int colour, float x1, float y1, float x2, float y2, float x1h, float x2h, float y1h, float y2h) {
        Point a = new Point(x1, y1);
        Point d = new Point(x2, y2);
        Point c = new Point(x1h, y1h);
        Point b = new Point(x2h, y2h);
        noFill();
        stroke(colour);
        strokeWeight(2);
        strokeCap(PROJECT);
        beginShape();
        for (float t = 0; t <= 1; t += 0.01) {
            Point p = cubicBezier(a, b, c, d, t);
            float n = noise(p.x() * 0.01f, p.y() * 0.01f);
            float offset = (n - 0.5f) * 150;
            curveVertex(p.x() + offset, p.y());
        }
        endShape();
    }


}
