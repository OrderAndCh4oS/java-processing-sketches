package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;
import static utilities.Bezier.quadraticBezier;

public class Donut extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Donut");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            drawTexture(colour.rand(), 0.1f, 0.1f);
            drawTexture(colour.rand(), 0.8f, 0.2f, 1024f);
            noFill();
            strokeWeight(2f);
            strokeCap(ROUND);
            stroke(colour.black());
            drawRotations();
            save("donut", name);
        });
    }

    private void drawRotations() {
        pushMatrix();
        translate(_width / 2, _height / 2);
        rotate(random(TAU));
        float x = 0;
        float y = 0;
        float r = random(70, 130);
        float r1 = r + random(400, 420);
        float r2 = r1 + random(400, 420);
        float step = TAU / 180;
        float mag = random(0, 60);
        float mag1 = random(120, 180);
        for (float t = step; t <= TAU + step; t += step) {
            Point p = ellipsePoint(x, y, r, r, t);
            Point p1 = ellipsePoint(x, y, r1, r1, t + (step * mag));
            Point p2 = ellipsePoint(x, y, r2, r2, t + (step * mag1));
            Point p3 = ellipsePoint(x, y, r2, r2, t + step);
            beginShape();
            for (float t2 = 0; t2 < 1; t2 += 0.25) {
                Point p4 = cubicBezier(p, p1, p2, p3, t2);
                vertex(p4.x(), p4.y());
            }
            endShape();
        }
        popMatrix();
    }

    Point ellipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }
}

