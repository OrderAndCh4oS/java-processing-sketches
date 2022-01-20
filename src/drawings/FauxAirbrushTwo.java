package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;

import static utilities.Map.*;

public class FauxAirbrushTwo extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.FauxAirbrushTwo");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048);
    }

    @Override
    public void sketch() {
        for(int iter = 0; iter < 10; iter++) {
            _colours.getColours("constructivist").forEach((name, colour) -> {
                background(colour.black());
                for (int i = 0; i < 12; i++) {
                    for (int j = 0; j < 12; j++) {
                        square(_width / 12 * i, _height / 12 * j, _width / 12, _height / 12, Direction.rand(), CUBIC, EASE_IN, colour.rand());
                    }
                }
                save("faux-airbrush-two-v2", name);
            });
        }
    }
}
