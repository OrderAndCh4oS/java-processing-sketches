package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class AlienTwo extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.AlienTwo");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours("red-blue").forEach((name, colour) -> {
            background(colour.white());
            for (int i = 0; i < random(20000, 30000); i++) {
                stroke(colour.black(), 255 * 0.45f);
                point(random(_width), random(_height));
            }
            fill(colour.white());
            for (int i = 0; i < 50; i++) {
                Wave w = new Wave(_width, random(_height), PI, 4, 50f, random(20, 48), random(0.0002f, 0.001f));
                fill(colour.randWithWhite());
                stroke(colour.black());
                for (int j = 0; j < random(180, 260); j++) {
                    w.update();
                    w.draw();
                }
            }
            save("alien", name);
        });
    }

    public class Wave {
        float _x;
        float _y;
        float _a;
        float _s;
        float _startY;
        float _v = PI / 60;
        float _waveHeight;
        float _modV;
        float _radius;

        Wave(float x, float y, float a, float speed, float radius, float waveHeight, float modV) {
            _x = x;
            _y = y;
            _a = a;
            _s = speed;
            _startY = y;
            _radius = radius;
            _waveHeight = waveHeight;
            _modV = modV;
        }

        public void update() {
            _a += _v;
            float dY = sin(_a) * _waveHeight;
            _x = _x - _s;
            _y = _startY + dY;
            _radius *= 0.985;
            _v += _modV;
            _s += 0.0075;
        }

        public void draw() {
            ellipse(_x, _y, _radius * 2, _radius * 2);
        }
    }
}

