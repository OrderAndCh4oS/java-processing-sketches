package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

import static utilities.Random.randomInt;

public class Missing extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Missing");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
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
            background(c1);
            float margin = 120;
            float w = _width - margin;
            float h = _height - margin;
            float step = 10;
            float size = 8;
            float backgroundStep = 1.5f;
            float strokeWidth = 0.75f;
            drawLineTexture(colour.black(), backgroundStep, strokeWidth, Direction.TOP);
            noStroke();
            fill(c2);
            float mX = (float) randomInt((int) margin / 10, (int) w / 10) * 10;
            float mY = (float) randomInt((int) margin / 10, (int) h / 10) * 10;
            for (float i = margin; i < w; i += step) {
                float x = i + (size / 2);
                for (float j = margin; j < h; j += step) {
                    float y = j + (size / 2);
                    if (mX == i && mY == j) continue;
                    println(mX, mY, i, j);
                    ellipse(x, y, size, size);
                }
            }
            save("missing", name);
        });
    }
}
