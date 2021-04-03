package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;
import static utilities.Random.randomInt;

public class BezierCircleV1 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BezierCircleV1");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            int c1 = colour.rand();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            noiseSeed(randomInt(0, 999999));
            blendMode(NORMAL);
            background(c1);
            drawTexture(colour.white(), 0.2f, 0.4f);
            drawCurves(c2);
            blendMode(DIFFERENCE);
            noStroke();
            fill(colour.white());
            ellipse(_width / 2, _height / 2, _width / 1.5f, _height / 1.5f);
            save("bezier-circle-v2", name);
        });
    }

    public void drawCurves(int colour) {
        float x1 = random(0, 250) - 125;
        float y1 = -120;
        float x2 = random(0, 250) - 125;
        float y2 = _height + 120;
        float x1h = x1 + random(0, 800) - 400;
        float x2h = x1 - random(0, 800) - 400;
        float y1h = y1 + random(400, 800);
        float y2h = y2 - random(400, 800);
        float margin = -_width / 3f;
        for (float x = margin; x < _width - margin; x += _width / (_width / 16f)) {
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
        strokeWeight(6);
        strokeCap(PROJECT);
        beginShape();
        for (float t = 0; t <= 1; t += 0.01) {
            Point p = cubicBezier(a, b, c, d, t);
//            float n = noise(p.x() * 0.01f, p.y() * 0.01f);
//            float offset = (n - 0.5f) * 150;
            float offset = 0;
            curveVertex(p.x() + offset, p.y());
        }
        endShape();
    }


}
