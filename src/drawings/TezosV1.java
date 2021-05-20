package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import sketch.Sketch;

import static utilities.Image.extractShadeFromImage;
import static utilities.Map.CUBIC;
import static utilities.Map.EASE_OUT;

public class TezosV1 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.TezosV1");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048);
    }

    @Override
    public void sketch() {
        String[] fontList = PFont.list();
        printArray(fontList);
        for(int iter = 0; iter < 5; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                PFont font = createFont("Charis SIL", 2000);
                textFont(font);
                String[] numbers = {"ꜩ"};
                for (String n : numbers) {
                    blendMode(BLEND);
                    background(0xff000000);
                    fill(0xffffffff);
                    textAlign(CENTER);
                    text(n, _width / 2, 1400);
                    PImage img = get(0, 0, (int) _width, (int) _height);
                    int dotSize = 18;
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
                    drawTextureToSource(source, colour.black(), 0.9f, 0.1f);
                    source.strokeWeight(3);
                    source.strokeCap(ROUND);
                    for (int x = 0; x < _width; x += dotSize) {
                        for (int y = 0; y < _width; y += dotSize) {
                            PImage section = img.get(x, y, dotSize, dotSize);
                            float shade = extractShadeFromImage(section.pixels);
                            if (shade > 0.5) {
                                for (int i = 0; i < random(4, 8); i++) {
                                    source.stroke(colour.white());
                                    source.line(
                                            x + dotSize / 2f,
                                            y + dotSize / 2f,
                                            x + random(dotSize, dotSize * 12) - dotSize * 6,
                                            y + random(dotSize, dotSize * 12) - dotSize * 6
                                    );
                                }
                            } else {
                                for (int i = 0; i < random(4, 8); i++) {
                                    source.stroke(colour.rand());
                                    source.line(
                                            x + dotSize / 2f,
                                            y + dotSize / 2f,
                                            x + random(dotSize, dotSize * 12) - dotSize * 6,
                                            y + random(dotSize, dotSize * 12) - dotSize * 6
                                    );
                                }
                            }
                        }
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
                    save("tezos", name + "-" + n);
                }
            });
        }
    }
}
