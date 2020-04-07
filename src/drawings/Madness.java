package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

import static utilities.Random.randomInt;

public class Madness extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Madness");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            blendMode(NORMAL);
            background(colour.bg());
            for (int i = 0; i < random(20000, 30000); i++) {
                point(random(_width), random(_height));
            }
            fill(colour.bg());
            for (int i = 0; i < 10; i++) {
                int count = randomInt(5, 10);
                drawLayer(colour, count);
                drawDepth(colour.bg(), 0.4f, 0.3f);
            }
            save("madness", name);
        });
    }

    private void drawLayer(Colours colour, int count) {
        for (int i = 0; i < count; i++) {
            pushMatrix();
            translate(_width / 2, _height / 2);
            rotate(random(TWO_PI));
            float waveHeight = random(5, 24);
            float modV = random(0.0002f, 0.001f);
            float x = random(_width);
            noStroke();
            Tendril w = new Tendril(0, 0, PI, 2.25f, 45f, waveHeight, modV);
            fill(colour.rand());
            for (int j = 0; j < random(180, 260); j++) {
                w.update();
                w.draw();
            }
            popMatrix();
        }
    }

    public class Tendril {
        float _x;
        float _y;
        float _a;
        float _s;
        float _startX;
        float _v = PI / 60;
        float _waveHeight;
        float _modV;
        float _radius;

        Tendril(float x, float y, float a, float speed, float radius, float waveHeight, float modV) {
            _x = x;
            _y = y;
            _a = a;
            _s = speed;
            _startX = x;
            _radius = radius;
            _waveHeight = waveHeight;
            _modV = modV;
        }

        public void update() {
            _a += _v;
            float dX = sin(_a) * _waveHeight;
            _y = _y - _s;
            _x = _startX + dX;
            _radius *= 0.975;
            _v += _modV;
            _s += 0.015;
        }

        public void draw() {
            ellipse(_x, _y, _radius * 2, _radius * 2);
        }
    }

    private void drawDepth(int colour, float density, float alpha) {
        blendMode(MULTIPLY);
        stroke(colour, 255 * alpha);
        strokeCap(ROUND);
        strokeWeight(1);
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                if (random(1) > density) {
                    point(i, j);
                }
            }
        }
        blendMode(NORMAL);
    }
}

