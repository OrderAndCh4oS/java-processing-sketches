package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;
import static utilities.Random.randomInt;

public class Mixer extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Mixer");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(0xff000000);
            fill(0xffffffff);
            int step = randomInt(2, 4);
            float lineWidth = random(20, 30);
            float padding = random(15, 20);
            float wave = 0.12f;
            int c1 = colour.rand();
            int c2 = colour.rand();
            while (c2 == c1) {
                c2 = colour.rand();
            }
            background(c1);
            drawDepth(colour.bg(), 0.4f, 0.6f);
            for (int x = -30; x < _width; x += lineWidth - padding) {
                float a = 0;
                int offsetY = (x % step);
                strokeWeight(random(1, step));
                stroke(colour.rand());
                for (int y = -10; y < _height; y += step) {
                    float dX = sin(a) * 40;
                    float newX = dX - dX / 2;
                    line(
                            x + newX + step / 2f,
                            y + step / 2f + offsetY,
                            x + newX + step / 2f + lineWidth + padding,
                            y + step / 2f + offsetY
                    );
                    a += wave;
                }
                lineWidth = random(24, 56);
                padding = random(14, 22);
            }

            save("mixer", name);
        });
    }
}
