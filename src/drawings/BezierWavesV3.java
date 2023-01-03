package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;

import static utilities.Bezier.cubicBezier;
import static utilities.Random.randomInt;

public class BezierWavesV3 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BezierWavesV3");
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
            noiseSeed(randomInt(0, 999999));
            blendMode(NORMAL);
            background(colour.white());
            drawTexture(colour.white(), 0.4f, 1.5f);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            drawCurves(colour);
            blendMode(MULTIPLY);
            noStroke();
            save("bezier-waves-v3", name);
        });
    }

    public void drawCurves(Colours colour) {
        float x1 = random(120, 250);
        float x2 = random(120, 250);
        float x1h = x1 + random(0, 400) - 200;
        float x2h = x1 - random(0, 400) - 200;
        float margin = 160;
        for (float x = margin; x < _width - margin * 2.5f; x += _width / (_width / 16f)) {
            float y1 = random(120, 240);
            float y2 = _height - random(120, 240);
            float y1h = y1 + random(_height / 3, _height / 3 * 2);
            float y2h = y2 - random(_height / 3, _height / 3 * 2);
            drawCubicBezier(colour.rand(), x + x1, y1, x + x2, y2, x + x1h, x + x2h, y1h, y2h);
        }
    }

    public void drawCubicBezier(int colour, float x1, float y1, float x2, float y2, float x1h, float x2h, float y1h, float y2h) {
        Point a = new Point(x1, y1);
        Point d = new Point(x2, y2);
        Point c = new Point(x1h, y1h);
        Point b = new Point(x2h, y2h);
        noFill();
        stroke(colour);
        strokeWeight(0.75f);
        beginShape();
        for (float t = 0; t <= 1; t += 0.01) {
            Point p = cubicBezier(a, b, c, d, t);
            curveVertex(p.x(), p.y());
        }
        endShape();
    }


}
