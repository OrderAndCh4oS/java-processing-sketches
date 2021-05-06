package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;

public class Forest extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.SnakePlane");
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
            background(colour.rand());

            // drawTexture(colour.black(), 0.4f, 0.3f);



            save("forest", name);
        });
    }
}
