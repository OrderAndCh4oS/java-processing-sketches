package drawings;

import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;

import static utilities.Map.CUBIC;
import static utilities.Map.EASE_IN;

public class PainfulNeedles extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.PainfulNeedles");
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
            drawTexture(colour.rand(), 0.18f, Direction.TOP, CUBIC, EASE_IN);
            float section = TAU / 52;
            PGraphics source = createGraphics((int) _width, (int) _height);
            PGraphics mask = createGraphics((int) _width, (int) _height);
            mask.beginDraw();
            source.beginDraw();
            source.noStroke();
            mask.fill(0xff000000);
            mask.noStroke();
            mask.rect(0, 0, _width, _height);
            mask.fill(0xffffffff);
            float magnitude = 1;
            for (float angle = 0; angle < TAU * 2.5; angle += section) {
                magnitude = magnitude * 0.99f;
                mask.pushMatrix();
                mask.translate(_width / 2, _height / 2);
                mask.rotate(angle);
                source.pushMatrix();
                source.translate(_width / 2, _height / 2);
                source.rotate(angle);
                float length = map(random(1), 0, 1, (_height / 2 - 200) * magnitude, (_height / 2 - 120) * magnitude);
                float a = random(5, 60);
                float b = random(5, 30);
                float x1 = 0;
                float x2 = x1 - a;
                float x3 = x1 + b;
                float y1 = random(40, 90);
                float y2 = y1 + length + b;
                float y3 = y1 + length + a;
                source.fill(colour.rand());
                source.triangle(x1, y1, x2, y2, x3, y3);
                mask.triangle(x1, y1, x2, y2, x3, y3);
                drawTextureToSource(source, colour.black(), 0.3f, 0.03f);
                source.popMatrix();
                mask.popMatrix();

            }
            source.endDraw();
            mask.endDraw();
            source.mask(mask);
            image(source, 0, 0);
            save("painful-needles", name);
        });
    }

    Point ellipsePoint(float x, float y, float a, float b, float t) {
        return new Point(x + a * cos(t), y + b * sin(t));
    }
}

