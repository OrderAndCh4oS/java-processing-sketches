package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

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
            background(colour.black());
            fill(colour.black());
            for (int i = 0; i < 15; i++) {
                drawLayer(colour, scale);
                drawTexture(colour.black(), 0.4f, 0.3f);
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
}

