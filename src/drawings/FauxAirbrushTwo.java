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
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 12; j++) {
                    square(_width / 12 * i, _height / 12 * j, _width / 12, _height / 12, Direction.rand(), QUARTIC, EASE_IN, colour.rand());
                }
            }
            save("faux-airbrush-two", name);
        });
    }

    void square(float x, float y, float width, float height, Direction direction, int type, int ease, int colour) {
        for (float i = x; i < x + width; i++) {
            for (float j = y; j < y + height; j++) {
                float iX = i - x;
                float jY = j - y;
                float density;
                switch (direction) {
                    case TOP:
                        density = map2(height - jY, 0, height, 0.05f, 0.9f, type, ease);
                        break;
                    case RIGHT:
                        density = map2(iX, 0, width, 0.05f, 0.9f, type, ease);
                        break;
                    case BOTTOM:
                        density = map2(jY, 0, height, 0.05f, 0.9f, type, ease);
                        break;
                    case LEFT:
                        density = map2(width - iX, 0, width, 0.05f, 0.9f, type, ease);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
                stroke(colour, random(0.8f, 1) * 255);
                if (random(1) > density) {
                    point(i, j);
                }
            }
        }
    }
}
