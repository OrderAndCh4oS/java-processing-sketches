package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import sketch.Sketch;
import utilities.Vector;
import utilities.colour.Colours;

import static utilities.Random.randomInt;

public class Woods extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Woods");
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
        _colours.get_colours().forEach((name, colour) -> {
            float minScale;
            float maxScale;
            float count;
            float minY;
            float maxY;
            blendMode(NORMAL);
            noFill();
            background(colour.rand());
            drawDepth(colour.bg(), 0.4f, 0.7f);
            minScale = 2;
            maxScale = 5;
            count = random(_width / 2);
            minY = _height * 0.60f;
            maxY = _height * 0.66f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawDepth(colour.bg(), 0.6f, 0.5f);
            minScale = 6;
            maxScale = 10;
            count = random(_width / minScale / 3, _width / maxScale / 3);
            minY = _height * 0.68f;
            maxY = _height * 0.78f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawDepth(colour.bg(), 0.6f, 0.4f);
            minScale = 11;
            maxScale = 20;
            count = random(_width / minScale / 4, _width / maxScale / 4);
            minY = _height * 0.80f;
            maxY = _height * 0.90f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            drawDepth(colour.bg(), 0.7f, 0.2f);
            minScale = 22;
            maxScale = 60;
            count = random(_width / minScale / 6, _width / maxScale / 6);
            minY = _height * 0.90f;
            maxY = _height * 1f;
            drawLayer(colour, count, minScale, maxScale, minY, maxY);
            fill(colour.bg());
            text("森", 24, 908);
            save("woods", name);
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
            drawRays(randomInt(1,3));
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

    private void drawDepth(int colour, float density, float alpha) {
        blendMode(MULTIPLY);
        stroke(colour, 255 * alpha);
        strokeCap(ROUND);
        strokeWeight(1);
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                if (random(1) > density) {
                    point(i, j);
                }
            }
        }
    }
}
