package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class ImprovisationV2 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.ImprovisationV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for(int iter = 0; iter < 10; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                background(colour.black());
                noFill();
                drawTexture(colour.rand(), 0.1f, 0.1f);
                drawTexture(colour.rand(), 0.9f, 0.2f, 1048);
                strokeCap(ROUND);
                int c1 = colour.randWithWhite();
                int c2 = colour.black();
                int c3 = colour.randWithWhite();
                while (c3 == c1) {
                    c3 = colour.randWithWhite();
                }
                stroke(c1);
                squiggle(Direction.LEFT);
                squiggle(Direction.RIGHT);
                stroke(c2);
                squiggle(Direction.TOP);
                stroke(c3);
                squiggle(Direction.BOTTOM);
                save("improvisation-v2", name);
            });
        }
    }

    private void squiggle(Direction side) {
        Harmonograph xA = new Harmonograph(random(0.05f, 1), random(320, 480), random(320, 770), random(0.0005f, 0.01f));
        Harmonograph xB = new Harmonograph(random(0.05f, 1), random(320, 480), random(320, 770), random(0.0005f, 0.01f));
        Harmonograph yA = new Harmonograph(random(0.05f, 1), random(320, 480), random(320, 770), random(0.0005f, 0.01f));
        Harmonograph yB = new Harmonograph(random(0.05f, 1), random(320, 480), random(320, 770), random(0.0005f, 0.01f));
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 150; i += 10) {
            float x = xA.f(i) + xB.f(i);
            float y = yA.f(i) + yB.f(i);
            points.add(new Point(x, y));
        }
        for (Point p : points) {
            for (int i = 16; i < _width - 16; i += 16) {
                float x = p.x() + _width / 2;
                float y = p.y() + _height / 2;
                switch (side) {
                    case TOP:
                        line(i, 16, x, y);
                        break;
                    case RIGHT:
                        line(16, i, x, y);
                        break;
                    case BOTTOM:
                        line(i, _width - 16, x, y);
                        break;
                    case LEFT:
                        line(_width - 16, i, x, y);
                        break;
                }
            }
        }
    }

    static class Harmonograph {
        private final float _frequency;
        private final float _phase;
        private final float _amplitude;
        private final float _damping;

        public Harmonograph(float _frequency, float _phase, float _amplitude, float _damping) {
            this._frequency = _frequency;
            this._phase = _phase;
            this._amplitude = _amplitude;
            this._damping = _damping;
        }

        float f(float t) {
            float angle = t * _frequency + _phase;
            float x1 = _amplitude * sin(angle);
            return x1 * pow(exp(1.0f), -_damping * t);
        }
    }
}
