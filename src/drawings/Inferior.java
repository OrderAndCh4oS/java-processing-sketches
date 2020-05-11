package drawings;

import processing.core.PApplet;
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
            fill(colour.black());
            noStroke();
            drawTriangle(_width / 2, _height / 12 * 7, random(750, 900));
            fill(colour.white());
            stroke(colour.black());
            strokeWeight(12);
            drawTriangle(_width / 10 * 6, _height / 12 * 10, random(250, 300));
            save("inferior", name);
        });
    }

    public void drawTriangle(float x, float y, float a) {
        pushMatrix();
        translate(x, y);
        float aX = 0;
        float aY = -(a / sqrt(3));
        float bX = -(a / 2);
        float bY = (a / (2 * sqrt(3)));
        float cX = (a / 2);
        float cY = (a / (2 * sqrt(3)));
        beginShape();
        vertex(aX, aY);
        vertex(bX, bY);
        vertex(cX, cY);
        endShape(CLOSE);
        popMatrix();
    }
}
