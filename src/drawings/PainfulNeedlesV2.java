package drawings;

import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

import static utilities.Map.CUBIC;
import static utilities.Map.EASE_IN;

public class PainfulNeedlesV2 extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.PainfulNeedlesV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P3D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            for (int i = 0; i < 64; i++) {
                for (int j = 0; j < 64; j++) {
                    square(_width / 64 * i, _height / 64 * j, _width / 64, _height / 64, Direction.rand(), CUBIC, EASE_IN, colour.rand());
                }
            }
            PGraphics darken = createGraphics((int) _width, (int) _height);
            darken.beginDraw();
            drawTextureToSource(darken, colour.black(), 0.88f, 0.8f);
            darken.endDraw();
            image(darken, 0, 0);
            PGraphics darkenLayer = createGraphics((int) _width, (int) _height);
            darkenLayer.beginDraw();
            drawTextureToSource(darkenLayer, colour.black(), 0.66f, 0.0075f);
            darkenLayer.endDraw();
            float section = TAU / 64;
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
            for (float angle = 0; angle < TAU * 8; angle += section + 0.045) {
                magnitude = magnitude * 0.999f;
                mask.pushMatrix();
                mask.translate(_width / 2, _height / 2);
                mask.rotate(angle);
                source.pushMatrix();
                source.translate(_width / 2, _height / 2);
                source.rotate(angle);
                float length = map(random(1), 0, 1, (_height / 2 - 200) * magnitude, (_height / 2 - 120) * magnitude);
                float a = random(12, 36);
                float b = random(6, 24);
                float x1 = 0;
                float x2 = x1 - a;
                float x3 = x1 + b;
                float y1 = random(80, 180);
                float y2 = y1 + length + b;
                float y3 = y1 + length + a;
                source.fill(colour.rand());
                source.triangle(x1, y1, x2, y2, x3, y3);
                mask.triangle(x1, y1, x2, y2, x3, y3);
                source.image(darkenLayer, 0, 0);
                source.popMatrix();
                mask.popMatrix();
            }
            source.endDraw();
            mask.endDraw();
            source.mask(mask);
            image(source, 0, 0);
            save("painful-needles-v2", name);
        });
    }
}

