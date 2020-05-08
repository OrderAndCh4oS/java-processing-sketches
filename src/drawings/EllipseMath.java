package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

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
            background(colour.black());
            drawTexture(colour.rand(), 0.9f, 0.1f);
            drawTexture(colour.rand(), 0.8f, 0.2f, 512f);
            noFill();
            strokeWeight(3);
            strokeCap(ROUND);
            for (int i = 0; i < random(25, 45); i++) {
                pushMatrix();
                translate(_width / 2, _height / 2);
                rotate(random(TAU));
                float x = map(random(_width), 0, _width, 128, _width / 2 - 128);
                float y = map(random(_height), 0, _height, 128, _height / 2 - 128);
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
}

