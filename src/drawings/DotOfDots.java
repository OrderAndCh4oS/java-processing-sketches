package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;
import utilities.colour.Colours;

import java.util.ArrayList;
import java.util.Random;

import static utilities.Map.CUBIC;
import static utilities.Map.EASE_OUT;

public class DotOfDots extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DotOfDots");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.rand());
            drawTexture(colour.white(), 0.2f, 0.4f);
            drawGrainTexture(colour.white(), 1f, _height/2.3f, CUBIC, EASE_OUT);
            save("dot-of-dots", name);
        });
    }
}

