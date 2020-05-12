package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

public class Inferior extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Inferior");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            drawFibreTexture(colour.black(), 250000, 0.2f, 0.5f);
            float a = random(750, 900);
            float a2 = random(250, 300);
            PGraphics mask = createGraphics((int) _width, (int) _height);
            PGraphics source = createGraphics((int) _width, (int) _height);
            source.beginDraw();
            source.fill(colour.black());
            source.noStroke();
            drawTriangle(source, _width / 2, _height / 12 * 7, a);
            source.noFill();
            source.stroke(colour.black());
            source.strokeWeight(12);
            drawTriangle(source,_width / 10 * 6, _height / 12 * 10, a2);
            source.endDraw();
            mask.beginDraw();
            source.noStroke();
            mask.fill(0xff000000);
            mask.rect(0, 0, _width, _height);
            mask.fill(0xffffffff);
            drawTriangle(mask, _width / 2, _height / 12 * 7, a);
            mask.fill(0xff000000);
            mask.stroke(0xffffffff);
            mask.strokeWeight(12);
            drawTriangle(mask,_width / 10 * 6, _height / 12 * 10, a2);
            mask.endDraw();
            source.mask(mask);
            image(source, 0, 0);
            save("inferior", name);
        });
    }

    public void drawTriangle(float x, float y, float a) {
        pushMatrix();
        translate(x, y);
        beginShape();
        vertex(0, -(a / sqrt(3)));
        vertex(-(a / 2), (a / (2 * sqrt(3))));
        vertex((a / 2), (a / (2 * sqrt(3))));
        endShape(CLOSE);
        popMatrix();
    }

    public void drawTriangle(PGraphics source, float x, float y, float a) {
        source.pushMatrix();
        source.translate(x, y);
        source.beginShape();
        source.vertex(0, -(a / sqrt(3)));
        source.vertex(-(a / 2), (a / (2 * sqrt(3))));
        source.vertex((a / 2), (a / (2 * sqrt(3))));
        source.endShape(CLOSE);
        source.popMatrix();
    }
}
