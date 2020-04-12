package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;

public class Number extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Number");
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
            String[] numbers = {"1", "2", "3", "4", "5"};
            for (String n : numbers) {
                background(0xff000000);
                fill(0xffffffff);
                textAlign(CENTER);
                text(n, _width/2, 908);
                PImage img = get(0, 0, (int) _width, (int) _height);
                int dotSize = 18;
                background(colour.rand());
                drawDepth(colour.bg(), 0.75f, 0.25f);
                strokeWeight(1);
                strokeCap(ROUND);
                for (int x = 0; x < _width; x += dotSize) {
                    for (int y = 0; y < _width; y += dotSize) {
                        PImage section = img.get(x, y, dotSize, dotSize);
                        float shade = extractShadeFromImage(section.pixels);
                        if (shade > 0.5) {
                            for (int i = 0; i < random(4, 8); i ++) {
                                stroke(colour.rand());
                                line(
                                        x + dotSize/2f,
                                        y + dotSize/2f,
                                        x + random(dotSize, dotSize * 12) - dotSize * 6,
                                        y + random(dotSize, dotSize * 12) - dotSize * 6
                                );
                            }
                        }
                    }
                }
                save("number", name + "-" + n);
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
