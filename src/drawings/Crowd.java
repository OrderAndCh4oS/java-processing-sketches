package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;

public class Crowd extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Crowd");
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
            noStroke();
            PFont notoSC = createFont("NotoSerifSC-Bold", 1000);
            textFont(notoSC);
            text("众", 24, 908);
            PImage img = get(0, 0, (int) _width, (int) _height);
            int dotSize = 14;
            int dotPadding = 1;
            background(colour.black());
            for (int x = 0; x < _width; x += dotSize) {
                for (int y = 0; y < _width; y += dotSize) {
                    PImage section = img.get(x, y, dotSize, dotSize);
                    float shade = extractShadeFromImage(section.pixels);
                    if (shade > 0.5) {
                        fill(colour.rand());
                        ellipse(x + dotPadding, y + dotPadding, dotSize - dotPadding * 2, dotSize - dotPadding * 2);
                    } else {
                        fill(0xffffffff);
                        ellipse(x + dotPadding, y + dotPadding, dotSize - dotPadding * 2, dotSize - dotPadding * 2);
                    }
                }
            }
            save("crowd", name);
        });
    }
}
