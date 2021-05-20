package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;

public class TezosWaveLine extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.TezosWaveLine");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048);
    }

    @Override
    public void sketch() {
//        String[] fontList = PFont.list();
//        printArray(fontList);
        _colours.getColours().forEach((name, colour) -> {
            PFont font = createFont("Charis SIL", 1800);
            textFont(font);
            background(0xff000000);
            fill(0xffffffff);
            textAlign(CENTER);
            text("ꜩ", _width / 2, 1350);
            PImage img = get(0, 0, (int) _width, (int) _height);
            int step = 9;
            float lineWidth = random(20, 30);
            float padding = random(15, 20);
            float wave = 0.12f;
            int c1 = colour.rand();
            int c2 = colour.rand();
            while (c2 == c1) {
                c2 = colour.rand();
            }
            background(c1);
            drawTexture(colour.black(), 0.2f, 0.1f);
            PGraphics source = createGraphics((int) _width, (int) _height);
            PGraphics mask = createGraphics((int) _width, (int) _height);
            source.beginDraw();
            source.noStroke();
            source.fill(colour.white());
            source.rect(0, 0, _width, _height);
            drawTextureToSource(source, colour.black(), 0.5f, 0.3f);
            source.strokeWeight(1);
            source.strokeCap(ROUND);
            for (int x = -30; x < _width; x += lineWidth - padding) {
                float a = 0;
                int offsetY = (x % step);
                int outerColour = colour.rand();
                int innerColour = colour.white();
                for (int y = -20; y < _height; y += step) {
                    PImage section = img.get(x, y, step, step);
                    float shade = extractShadeFromImage(section.pixels);
                    float dX = sin(a) * 60;
                    float newX = dX - dX / 2;
                    if (shade > 0.5) {
                        source.stroke(innerColour);
                        source.strokeWeight(5);
                    } else {
                        source.stroke(outerColour);
                        source.strokeWeight(3);
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
                padding = random(14, 26);
            }
            source.endDraw();
            mask.beginDraw();
            mask.noStroke();
            mask.fill(0xff000000);
            mask.rect(0, 0, _width, _height);
            mask.translate(_width / 2, _height / 2);
            mask.fill(0xffffffff);
            mask.ellipse(0, 0, 1960, 1960);
            mask.endDraw();
            source.mask(mask);
            image(source, 0, 0);
            save("tezos-line-wave", name);
        });
    }
}
