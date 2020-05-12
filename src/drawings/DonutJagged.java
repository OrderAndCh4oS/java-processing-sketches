package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Bezier.cubicBezier;

public class DonutJagged extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.JaggedDonut");
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
            fill(colour.rand());
            noStroke();
            drawRotations();
            save("jagged-donut", name);
        });
    }

    private void drawRotations() {
        pushMatrix();
        translate(_width / 2, _height / 2);
        rotate(random(TAU));
        float x = 0;
        float y = 0;
        float r = random(70, 130);
        float r1 = r + random(200, 320);
        float r2 = r1 + random(400, 420);
        float step = TAU / 180;
        float mag = random(20, 40);
        float mag1 = random(80, 150);
        Point p, p1, p2, p3;
        for (float t = step; t <= TAU + step / 2; t += step) {
            p = ellipsePoint(x, y, r, r, t);
            p1 = ellipsePoint(x, y, r1, r1, t + (step * mag));
            p2 = ellipsePoint(x, y, r2, r2, t + (step * mag1));
            p3 = ellipsePoint(x, y, r2, r2, t + step);
            ArrayList<Point> points = new ArrayList<>();
            for (float t2 = 0; t2 < 1; t2 += 0.33) {
                points.add(cubicBezier(p, p1, p2, p3, t2));
            }
            t += step;
            p = ellipsePoint(x, y, r, r, t);
            p1 = ellipsePoint(x, y, r1, r1, t + (step * mag));
            p2 = ellipsePoint(x, y, r2, r2, t + (step * mag1));
            p3 = ellipsePoint(x, y, r2, r2, t + step);
            for (float t2 = 1; t2 > 0; t2 -= 0.33) {
                points.add(cubicBezier(p, p1, p2, p3, t2));
            }
            beginShape();
            for (Point point : points) {
                vertex(point.x(), point.y());
            }
            endShape(CLOSE);
        }
        popMatrix();
    }

    Point ellipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }
}

