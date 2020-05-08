package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

public class Corn extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Corn");
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
            int c1 = colour.rand();
            int c2 = colour.rand();
            while (c2 == c1) c2 = colour.rand();
            background(c1);
            drawTexture(colour.black(), 0.9f, 0.1f);
            drawWaveTexture(colour, 0.25f, 10, 4, 1);
            blendMode(NORMAL);
            fill(c2);
            noStroke();
            int column = 0;
            for (int i = (int) (1024 * 0.1f); i < 1024 - (int) (1024 * 0.1f); i += 12) {
                column++;
                for (int j = (int) (1024 * 0.1f); j < 1024 - (int) (1024 * 0.1f); j += 14) {
                    pushMatrix();
                    if (column % 2 == 0) translate(0, -7);
                    ellipse(i, j, 12, 12);
                    popMatrix();
                }
            }
            save("corn", name);
        });
    }

    private void drawWaveTexture(Colours colour, float wave, float waveHeight, float stepY, float stepX) {
        stroke(colour.black());
        strokeWeight(0.75f);
        noFill();
        for (int y = -10; y < _height + 10; y += stepY) {
            float a = 0;
            beginShape();
            for (int x = 0; x < _width; x += stepX) {
                float dY = sin(a) * waveHeight;
                float newY = dY - dY / 2;
                curveVertex(x, y + newY);
                a += wave;
            }
            endShape();
        }
    }
}
