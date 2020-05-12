package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;
import static utilities.Bezier.quadraticBezier;

public class DonutDot extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.DonutDot");
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
            fill(colour.black());
            drawRotations();
            save("donut-dot", name);
        });
    }

    private void drawRotations() {
        pushMatrix();
        translate(_width / 2, _height / 2);
        rotate(random(TAU));
        float x = 0;
        float y = 0;
        float r = random(70, 130);
        float r1 = r + random(350, 420);
        float r2 = r1 + random(400, 420);
        float step = TAU / 180;
        float mag = random(0, 60);
        float mag1 = random(120, 160);
        for (float t = step; t <= TAU + step; t += step) {
            Point p = ellipsePoint(x, y, r, r, t);
            Point p1 = ellipsePoint(x, y, r1, r1, t + (step * mag));
            Point p2 = ellipsePoint(x, y, r2, r2, t + (step * mag1));
            for (float t2 = 0; t2 < 1; t2 += 0.01) {
                Point p4 = quadraticBezier(p, p1, p2, t2);
                float scale = map(distanceTo(0, 0, p4.x(), p4.y()), 0, 1024, 0, 8);
                ellipse(p4.x(), p4.y(), scale, scale);
            }
        }
        popMatrix();
    }

    Point ellipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }
}

