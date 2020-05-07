package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;

public class NumberWaveLineTwo extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.NumberWaveLineTwo");
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
            PFont font = createFont("ModernTwoSxtnITCStd-Bold", 1000);
            textFont(font);
            String[] numbers = {"1", "2", "3", "4", "5"};
            for (String n : numbers) {
                background(0xff000000);
                fill(0xffffffff);
                textAlign(CENTER);
                text(n, _width / 2, 848);
                PImage img = get(0, 0, (int) _width, (int) _height);
                int step = 6;
                float lineWidth = random(20, 30);
                float padding = random(15, 20);
                float wave = 0.12f;
                int c1 = colour.rand();
                int c2 = colour.rand();
                while (c2 == c1) {
                    c2 = colour.rand();
                }
                background(c1);
                drawDepth(colour.black(), 0.8f, 0.2f);
                PGraphics source = createGraphics((int) _width, (int) _height);
                PGraphics mask = createGraphics((int) _width, (int) _height);
                source.beginDraw();
                source.noStroke();
                source.fill(c2);
                source.rect(0, 0, _width, _height);
                drawDepthToSource(source, colour.black(), 0.9f, 0.1f);
                source.strokeWeight(1);
                source.strokeCap(ROUND);
                source.stroke(colour.black());
                for (int x = -30; x < _width; x += lineWidth - padding) {
                    float a = 0;
                    int offsetY = (x % step);
                    for (int y = -10; y < _height; y += step) {
                        PImage section = img.get(x, y, step, step);
                        float shade = extractShadeFromImage(section.pixels);
                        float dX = sin(a) * 40;
                        float newX = dX - dX / 2;
                        if (shade > 0.5) {
                            source.strokeWeight(2);
                        } else {
                            source.strokeWeight(1);
                        }
                        source.line(
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
                source.endDraw();
                mask.beginDraw();
                mask.noStroke();
                mask.fill(0xff000000);
                mask.rect(0, 0, _width, _height);
                mask.translate(_width / 2, _height / 2);
                mask.fill(0xffffffff);
                mask.ellipse(0, 0, 980, 980);
                mask.endDraw();
                source.mask(mask);
                image(source, 0, 0);
                save("number-line-wave-two", name + "-" + n);
            }
        });
    }
}
