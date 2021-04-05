package drawings;

import processing.core.PApplet;
import sketch.Sketch;

import static utilities.Random.randomInt;

public class Mixer extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Mixer");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600);
    }

    @Override
    public void sketch() {
        _colours.getColours("warm-bw", "candy").forEach((name, colour) -> {
            int step = randomInt(9, 24);
            float lineWidth = random(320, 640);
            float padding = random(60, 90);
            float wave = 0.28f;
            int c1 = colour.rand();
            int c2 = colour.rand();
            while (c2 == c1) {
                c2 = colour.rand();
            }
            background(colour.black());
            strokeCap(SQUARE);
            for (int x = -30; x < _width; x += lineWidth - padding) {
                float a = 0;
                int offsetY = (x % step);
                strokeWeight(random(2, step));
                stroke(colour.randWithWhite());
                for (int y = -30; y < _height; y += step + 3) {
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

            save("mixer-v2", name);
        });
    }
}
