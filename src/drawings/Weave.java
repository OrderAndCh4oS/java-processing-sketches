package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class Weave extends Sketch {
    float _x = 240;
    float _y = 240;
    float _radius = 12.5f;
    boolean horizontal = true;
    boolean vertical = true;

    public static void main(String... args) {
        PApplet.main("drawings.Weave");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.get_colours().forEach((name, colour) -> {
            for (int image = 0; image < 5; image++) {
                background(colour.bg());
                fill(colour.bg());
                for (int i = 0; i < 30000; i++) {
                    stroke(colour.rand());
                    f();
                }
            }
            save(name);
        });
    }

    private void f() {
        if (_x < _radius) {
            horizontal = true;
        }
        if (_x > _width - _radius) {
            horizontal = false;
        }
        if (_y < _radius) {
            vertical = true;
        }
        if (_y > _height - _radius) {
            vertical = false;
        }


        if (horizontal) {
            _x += 2;
        } else {
            _x -= 2;
        }

        if (vertical) {
            _y += 2;
        } else {
            _y -= 2;
        }

        _x += (randomGaussian() - 0.5) * 0.9;
        _y += (randomGaussian() - 0.5) * 0.5;

        ellipse(_x, _y, _radius * 2, _radius * 2);
    }
}
