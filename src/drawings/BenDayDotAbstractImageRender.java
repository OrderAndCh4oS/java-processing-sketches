package drawings;

import enums.CmykChannel;
import processing.core.PApplet;
import processing.core.PImage;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colour;
import utilities.colour.Colours;
import utilities.colour.Hsl;

import static enums.CmykChannel.*;
import static utilities.Bezier.cubicBezier;
import static utilities.Image.extractColorFromImage;
import static utilities.Image.extractShadeFromImage;

public class BenDayDotAbstractImageRender extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BenDayDotAbstractImageRender");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2400, 2400, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            blendMode(BLEND);
            background(colour.white());
            drawFibreTexture(colour.black(), 250000, 0.2f, 0.6f);
            drawAbstract(colour);
            PImage img = get(0, 0, (int) _width, (int) _height);
            img.filter(BLUR, 24);
            save("ben-day-dot-abstract-image-render", name);
            background(colour.white());
            noStroke();
            Colours c1 = _colours.get("sunset-beach");
            blendMode(MULTIPLY);
            bendayGrid(img, c1.get(0), 90 * (TAU / 360), y);
            bendayGrid(img, c1.get(1), 105 * (TAU / 360), m);
            bendayGrid(img, c1.get(2), 75 * (TAU / 360), c);
            bendayGrid(img, c1.black(), 15 * (TAU / 360), k);
            save("ben-day-dot-abstract-image-render", name);
        });
    }

    public void drawAbstract(Colours colour) {
        noFill();
        for (int i = 0; i < 70; i++) {
            float x1 = -20;
            float y1 = random(_height + 200) - 100;
            float x2 = _width + 10;
            float y2 = random(_height + 200) - 100;
            float x1h = x1 + random(40, 100);
            float y1h = y1 + random(40, 100);
            float x2h = x2 - random(40, 100);
            float y2h = y2 - random(40, 100);
            Point a = new Point(x1, y1);
            Point d = new Point(x2, y2);
            Point c = new Point(x1h, y1h);
            Point b = new Point(x2h, y2h);
            noStroke();
            int c1 = colour.rand();
            Hsl hsl = new Hsl(c1);
            int min = hsl.h() - 50;
            int max = hsl.h() + 50;
            for (float t = 0; t < 1; t += 0.0005) {
                Point p = cubicBezier(a, b, c, d, t);
                hsl.setH((int) map(t, 0, 1, min, max));
                fill(hsl.getRgba());
                pushMatrix();
                translate(p.x(), p.y());
                float size = 120;
                rect(0, 0, size, size);
                popMatrix();
            }
        }
    }

    public void bendayGrid(PImage img, int colour, float angle, CmykChannel cmykChannel) {
        int columnCount = 66;
        float margin = 80;
        float gutter = 0;
        float size = ((_width - margin * 2) / columnCount) - (gutter / columnCount) * (columnCount - 1);
        for (float y = margin; y <= _height - margin - size; y += size + gutter) {
            for (float x = margin; x <= _width - margin - size; x += size + gutter) {
                float scale = random(1);
                PImage section = img.get((int) x, (int) y, 3, 3);
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
                        scale = (1 - k) / 4;
                        break;
                }
                pushMatrix();
                translate(x + size / 2, y + size / 2);
                rotate(angle);
                drawBenDayTexture(-size / 2, -size / 2, size, size, colour, 12, scale);
                popMatrix();
            }
        }
    }
}
