package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

public class DeadSpace extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DeadSpace");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            float wave = 0.25f;
            float stepY = 1024 / 12f;
            float stepX = 1024 / 500f;
            float diameter = 980;
            int c1 = colour.rand();
            int c2 = colour.rand();
            while (c2 == c1) {
                c2 = colour.rand();
            }
            background(colour.rand());
            drawTexture(colour.black(), 0.1f, 0.1f);
            stroke(0xffffffff);
            strokeWeight(1);
            noFill();
            for (int y = -10; y < _height; y += stepY) {
                float a = 0;
                beginShape();
                for (int x = 0; x < _width; x += stepX) {
                    float newY = 0;
                    if (distanceToMidpoint(x, y) < diameter / 2) {
                        float dY = sin(a) * 80;
                        newY = dY - dY / 2;
                    }
                    curveVertex(x, y + newY);
                    a += wave;
                }
                endShape();
            }
            PGraphics source = createGraphics((int) _width, (int) _height);
            PGraphics mask = createGraphics((int) _width, (int) _height);
            source.beginDraw();
            source.noStroke();
            source.fill(c2);
            source.rect(0, 0, _width, _height);
            drawTextureToSource(source, colour.black(), 0.9f, 0.1f);
            source.strokeWeight(1);
            source.strokeCap(ROUND);
            source.stroke(colour.black());
            source.noFill();
            for (int y = -10; y < _height; y += stepY) {
                float a = 0;
                source.beginShape();
                for (int x = 0; x < _width; x += stepX) {
                    float newY = 0;
                    if (distanceToMidpoint(x, y) < diameter / 2) {
                        float dY = sin(a) * 40;
                        newY = dY - dY / 2;
                    }
                    source.curveVertex(x, y + newY);
                    a += wave;
                }
                source.endShape();
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
            save("deadspace", name);
        });
    }
}
