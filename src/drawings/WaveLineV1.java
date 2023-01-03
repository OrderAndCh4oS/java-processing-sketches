package drawings;

import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

import static utilities.Map.CUBIC;
import static utilities.Map.EASE_IN;
import static utilities.Random.randomInt;

public class WaveLineV1 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.WaveLineV1");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            int step = 6;
            float lineWidth = random(40, 180);
            float padding = random(15, 21);
            float wave = random(0.12f, 0.18f);
            int c1 = colour.randAll();
            int c2 = colour.randAll();
            while (c2 == c1) {
                c2 = colour.randAll();
            }
            background(c1);
            drawBenDayTexture(c2, random(8, 12), random(0.66f, 0.85f));
            drawTexture(colour.black(), 0.18f, Direction.TOP, CUBIC, EASE_IN);
            noStroke();
            strokeCap(ROUND);
            for (int x = 240; x < _width - 270; x += lineWidth - padding) {
                stroke(colour.randAll());
                strokeWeight(random(2, step - 1));
                float a = 0;
                int offsetY = (x % step);
                int yStart = randomInt(180, 360);
                int yEnd = (int) (_height - randomInt(180, 360));
                for (int y = 0; y < _height; y += step) {
                    float dX = sin(a) * 40;
                    float newX = dX - dX / 2;
                    a += wave;
                    if(y < yStart || y > yEnd) continue;
                    line(
                        x + newX + step / 2f,
                        y + step / 2f + offsetY,
                        x + newX + step / 2f + lineWidth + padding,
                        y + step / 2f + offsetY
                    );

                }
                lineWidth = random(24, 56);
                padding = random(14, 22);
            }
            save("wave-line-v1", name);
        });
    }
}
