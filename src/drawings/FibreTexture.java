package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class FibreTexture extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.FibreTexture");
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
            save("fibre-texture", name);
        });
    }
}
