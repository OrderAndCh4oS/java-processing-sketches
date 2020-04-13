package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Vector;
import utilities.colour.Colours;

import static utilities.Random.randomInt;

public class Painful extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Painful");
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
            background(colour.rand());
            drawDepth(colour.bg(), 0, 0.9f);
            fill(colour.bg());
            drawLayer(colour, randomInt(100, 125));
            drawDepth(colour.bg(), 0.4f, 0.2f);
            save("painful", name);
        });
    }

    private void drawLayer(Colours colour, int count) {
        for (int i = 0; i < count; i++) {
            pushMatrix();
            float waveHeight = random(6, 14);
            float modV = random(0.0005f, 0.001f);
            Direction direction = Direction.rand();
            Tendril w = new Tendril(0, 0, PI, 1.75f, 120, waveHeight, modV);
            float offset = 130;
            Vector v;
            switch (direction) {
                case TOP:
                    v = new Vector(random(_width + offset * 2) - offset, _height + offset);
                    break;
                case RIGHT:
                    v = new Vector(_width + offset, random(_height + offset * 2) - offset);
                    break;
                case BOTTOM:
                    v = new Vector(random(_width + offset * 2) - offset, -offset);
                    break;
                case LEFT:
                    v = new Vector(-offset, random(_height + offset * 2) - offset);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
            float angle = v.angleTo(new Vector(_midPoint));
            translate(v.x(), v.y());
            rotate(angle + PI * 0.5f);
            noStroke();
            fill(colour.rand());
            for (int j = 0; j < randomInt(185, 200); j++) {
                w.update();
                w.draw();
            }
            popMatrix();
        }
    }

    private void drawDepth(int colour, float density, float alpha) {
        blendMode(MULTIPLY);
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
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
}

