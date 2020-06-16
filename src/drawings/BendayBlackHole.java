package drawings;

import processing.core.PApplet;
import sketch.Sketch;

import static utilities.Map.*;

public class BendayBlackHole extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BendayBlackHole");
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
            background(colour.rand());
            drawBenDayTexture(colour.black(), 40, 0.75f);
            drawGrainTexture(colour.black(), 1f, 512f, QUADRATIC, EASE_IN);
            save("benday-black-hole-illusion", name);
        });
    }
}
