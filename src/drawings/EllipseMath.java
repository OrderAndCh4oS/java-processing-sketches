package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Map.*;

public class EllipseMath extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.EllipseMath");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.bg());
            drawDepth(colour.rand(), 0.9f, 0.1f);
            drawDepth(colour.rand(), 0.8f, 0.2f, 512f);
            noFill();
            strokeWeight(3);
            strokeCap(ROUND);
            for (int i = 0; i < random(25, 45); i++) {
                pushMatrix();
                translate(_width / 2, _height / 2);
                rotate(random(TAU));
                float x = map(random(_width), 0, _width, 128, _width/2 - 128);
                float y = map(random(_height), 0, _height, 128, _height/2 - 128);
                float a = random(35, 65);
                float b = a - random(10, 25);
                for (float t = TAU / 60; t <= TAU + TAU / 60; t += TAU / 60) {
                    Point p = ellipsePoint(x, y, a, b, t);
                    stroke(colour.rand());
                    line(x, y, p.x(), p.y());
                }
                popMatrix();
            }
            save("ellipse-math", name);
        });
    }

    Point ellipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }

    private void drawDepth(int colour, float density, float alpha) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (random(1) > density) {
                    point(x, y);
                }
            }
        }
    }

    private void drawDepth(int colour, float density, float alpha, float radius) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) > map2(distance, 0, radius, 0, density, QUADRATIC, EASE_IN)) {
                    point(x, y);
                }
            }
        }
    }

    private float getDistance(Point p, Point q) {
        float dX = p.x() - q.x();
        float dY = p.y() - q.y();

        return sqrt((dX * dX) + (dY * dY));
    }
}

