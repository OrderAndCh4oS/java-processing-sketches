package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

import static utilities.Random.randomInt;

public class Ellipse extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Ellipse");
    }
    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            print('.');
            float scale = 2048;
            blendMode(NORMAL);
            background(colour.bg());
            fill(colour.bg());
            for (int i = 0; i < 15; i++) {
                drawLayer(colour, scale);
                drawDepth(colour.bg(), 0.4f, 0.3f);
                scale *= 0.90f;
            }
            save("ellipse", name);
        });
    }

    private void drawLayer(Colours colour, float scale) {
        fill(colour.rand());
        noStroke();
        pushMatrix();
        translate(_width / 2, _height / 2);
        ellipse(0, 0, scale, scale);
        popMatrix();

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
        blendMode(NORMAL);
    }
}

