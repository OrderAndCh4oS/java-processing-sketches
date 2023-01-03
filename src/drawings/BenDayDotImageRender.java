package drawings;

import enums.CmykChannel;
import processing.core.PApplet;
import processing.core.PImage;
import sketch.Sketch;

import static enums.CmykChannel.*;
import static utilities.Image.extractColorFromImage;
import static utilities.Image.extractShadeFromImage;

public class BenDayDotImageRender extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BenDayDotImageRender");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2955 / 3, 3694 / 3, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            noStroke();
            PImage img = loadImage("/Users/seancooper/IdeaProjects/java-processing-sketches/src/images/me.jpg");
//            img.filter(BLUR, 30);
            blendMode(BLEND);
            background(colour.white());
            blendMode(MULTIPLY);
            bendayGrid(img, colour.get(0), 90 * (TAU / 360), y);
            bendayGrid(img, colour.get(1), 105 * (TAU / 360), m);
            bendayGrid(img, colour.get(2), 75 * (TAU / 360), c);
            bendayGrid(img, colour.get(3), 15 * (TAU / 360), k);
            save("ben-day-dot-image-render", name);
        });
    }

    public void bendayGrid(PImage img, int colour, float angle, CmykChannel cmykChannel) {
        int columnCount = 106;
        float margin = 80;
        float gutter = 0;
        float size = ((_width - margin * 2) / columnCount) - (gutter / columnCount) * (columnCount - 1);
        for (float y = margin; y <= _height - margin - size; y += size + gutter) {
            for (float x = margin; x <= _width - margin - size; x += size + gutter) {
                float scale = random(1);
                PImage section = img.get((int) (x * 3f), (int) (y * 3f), 10, 10);
                int c = extractColorFromImage(section.pixels);
                float k = extractShadeFromImage(section.pixels);
                int r = c >> 16 & 0xFF;
                int g = c >> 8 & 0xFF;
                int b = c & 0xFF;
                switch (cmykChannel) {
                    case c:
                        scale = 1 - (b / 255f);
                        break;
                    case m:
                        scale = 1 - (r / 255f);
                        break;
                    case y:
                        scale = 1 - (g / 255f);
                        break;
                    case k:
                        scale = (1 - k) / 3;
                        break;
                }
                pushMatrix();
                translate(x + size / 2, y + size / 2);
                rotate(angle);
                drawBenDayTexture(-size / 2, -size / 2, size, size, colour, 6, scale, 0.2f);
                popMatrix();
            }
        }
    }
}

