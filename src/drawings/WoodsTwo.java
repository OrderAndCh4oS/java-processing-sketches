package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import sketch.Sketch;
import utilities.Vector;
import utilities.colour.Colours;

import static utilities.Random.randomInt;

public class WoodsTwo extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.WoodsTwo");
    }

    @Override
    public void settings() {
        _save = true;
        _width = 1024;
        _height = 1024;
        super.settings(1024, 1024);
        smooth(8);
    }

    @Override
    public void sketch() {
        PFont notoSC = createFont("NotoSerifSC-Bold", 1000);
        textFont(notoSC);
        _colours.getColours().forEach((name, colour) -> {
            float minScale;
            float maxScale;
            float count;
            float minY;
            float maxY;
            blendMode(NORMAL);
            noFill();
            background(colour.rand());
            drawTexture(colour.black(), 0.6f, 0.6f);
            minScale = 2;
            maxScale = 5;
            count = random(_width / 2);
            minY = _height * 0.60f;
            maxY = _height * 0.66f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawTexture(colour.black(), 0.5f, 0.5f);
            drawRays(randomInt(10, 100));
            minScale = 6;
            maxScale = 10;
            count = random(_width / minScale / 3, _width / maxScale / 3);
            minY = _height * 0.68f;
            maxY = _height * 0.78f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawTexture(colour.black(), 0.4f, 0.4f);
            drawRays(randomInt(10, 100));
            minScale = 11;
            maxScale = 20;
            count = random(_width / minScale / 4, _width / maxScale / 4);
            minY = _height * 0.80f;
            maxY = _height * 0.90f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawTexture(colour.black(), 0.3f, 0.3f);
            drawRays(randomInt(10, 100));
            minScale = 22;
            maxScale = 60;
            count = random(_width / minScale / 6, _width / maxScale / 6);
            minY = _height * 0.90f;
            maxY = _height * 1f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawTexture(colour.black(), 0.2f, 0.1f);
            fill(0xffffffff);
            blendMode(DIFFERENCE);
            text("森", 24, 908);
            save("woods-two", name);
        });
    }

    private void drawLayer(Colours colour, float count, float minScale, float maxScale, float minY, float maxY) {
        blendMode(NORMAL);
        for (int i = 0; i < count; i++) {
            float scale = random(minScale, maxScale);
            float y = random(minY, maxY);
            float x = random(_width);
            stroke(colour.rand());
            strokeCap(SQUARE);
            strokeWeight(scale);
            line(x, 0, x, y);
        }
    }

    private void drawRays(int count) {
        Vector ray = new Vector(0, 0);
        for (int i = 0; i < count; i++) {
            blendMode(SOFT_LIGHT);
            stroke(0xffffffff, 255 * random(0.2f, 0.5f));
            strokeCap(SQUARE);
            strokeWeight(random(1, 8));
            ray.setAngle(random(0, PI * 0.5f));
            ray.setLength(_width * 1.5f);
            line(-100, random(-5, -40), ray.x(), ray.y());
        }
    }
}
