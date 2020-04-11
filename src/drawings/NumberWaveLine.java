package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;

public class NumberWaveLine extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.NumberWaveLine");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
//        String[] fontList = PFont.list();
//        printArray(fontList);
        _colours.getColours().forEach((name, colour) -> {
            PFont font = createFont("ModernTwoSxtnITCStd-Bold", 1200);
            textFont(font);
            String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            for (String n : numbers) {
                background(0xff000000);
                fill(0xffffffff);
                textAlign(CENTER);
                text(n, _width/2, 908);
                PImage img = get(0, 0, (int) _width, (int) _height);
                int step = 5;
                float lineWidth = random(12, 26);
                float padding = random(18, 22);
                float wave = 0.12f;
                int bg = colour.rand();
                int lineColour = colour.rand();
                background(colour.rand());
                strokeWeight(1);
                strokeCap(ROUND);
                for (int x = 0; x < _width; x += lineWidth - padding) {
                    stroke(colour.rand());
                    float a = 0;
                    int offsetY = (x % step);
                    for (int y = 0; y < _width; y += step) {
                        PImage section = img.get(x, y, step, step);
                        float shade = extractShadeFromImage(section.pixels);
                        float dX = sin(a) * 40;
                        float newX = dX - dX / 2;
                        if (shade < 0.5) {
                            lineColour = colour.rand();
                            while (lineColour == bg) {
                                lineColour = colour.rand();
                            }
                            stroke(lineColour);
                        } else {
                            stroke(colour.bg());
                        }
                        line(
                                x + newX + step/2f,
                                y + step/2f + offsetY,
                                x + newX + step/2f + lineWidth + padding,
                                y + step/2f + offsetY
                        );
                        a += wave;
                    }
                    lineWidth = random(24, 56);
                    padding = random(14, 22);
                }
                save("number-line-wave", name + "-" + n);
            }
        });
    }

    private void drawDepth(int colour, float density, float alpha) {
        blendMode(MULTIPLY);
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                if (random(1) > density) {
                    point(i, j);
                }
            }
        }
        blendMode(NORMAL);
    }
}