package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;

import static utilities.Bezier.quadraticBezier;

public class Spin extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Spin");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.bg());
            drawDepth(colour.rand(), 0.9f, 0.1f);
            drawDepth(colour.rand(), 0.8f, 0.2f, 512f);
            noFill();
            strokeWeight(1);
            strokeCap(ROUND);
            pushMatrix();
            translate(_width / 2, _height / 2);
            for (int i = 0; i < random(4, 9); i++) {
                stroke(colour.rand());
                drawRotations();
            }
            popMatrix();
            save("spin", name);
        });
    }

    private void drawRotations() {
        rotate(random(TAU));
        float x = 0;
        float y = 0;
        float r = random(35, 65);
        float r1 = r + random(50, 200);
        float r2 = r1 + random(100, 300);
        float step = TAU / 60;
        float mag = random(-3, 3);
        float mag1 = random(4, 12);
        for (float t = step; t <= TAU + step; t += step) {
            Point p = ellipsePoint(x, y, r, r, t);
            Point p1 = ellipsePoint(x, y, r1, r1, t + (step * mag));
            Point p2 = ellipsePoint(x, y, r2, r2, t + (step * mag1));
            beginShape();
            for(float t2 = 0; t2 < 1; t2 += 0.01) {
                Point p3 = quadraticBezier(p, p1, p2, t2);
                curveVertex(p3.x(), p3.y());
            }
            endShape();
        }
    }

    Point ellipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }
}

