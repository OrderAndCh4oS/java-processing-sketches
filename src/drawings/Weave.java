package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class Weave extends Sketch {
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
        WeaveF w1 = new WeaveF(_width / 2, _height / 2, 12.5f, true, true);
        WeaveF w2 = new WeaveF(_width / 2, _height / 2, 12.5f, false, false);
        WeaveF w3 = new WeaveF(_width / 2, _height / 2, 12.5f, true, false);
        WeaveF w4 = new WeaveF(_width / 2, _height / 2, 12.5f, false, true);
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            int c1 = colour.get(1);
            int c2 = colour.get(2);
            int c3 = colour.get(3);
            int c4 = colour.get(4);
            fill(colour.black());
            for (int i = 0; i < 20000; i++) {
                stroke(c1);
                w1.f();
                stroke(c2);
                w2.f();
                stroke(c3);
                w3.f();
                stroke(c4);
                w4.f();
            }
            save("weaveDual", name);
        });
    }

    public class WeaveF {
        float _x;
        float _y;
        float _radius;
        boolean _horizontal;
        boolean _vertical;

        WeaveF(float x, float y, float radius, boolean horizontal, boolean vertical) {
            _x = x;
            _y = y;
            _radius = radius;
            _horizontal = horizontal;
            _vertical = vertical;
        }

        public void f() {
            if (_x < _radius) {
                _horizontal = true;
            }
            if (_x > _width - _radius) {
                _horizontal = false;
            }
            if (_y < _radius) {
                _vertical = true;
            }
            if (_y > _height - _radius) {
                _vertical = false;
            }


            if (_horizontal) {
                _x += 2;
            } else {
                _x -= 2;
            }

            if (_vertical) {
                _y += 2;
            } else {
                _y -= 2;
            }

            _x += (randomGaussian() - 0.5) * 0.9;
            _y += (randomGaussian() - 0.5) * 0.5;

            ellipse(_x, _y, _radius * 2, _radius * 2);
        }
    }
}

