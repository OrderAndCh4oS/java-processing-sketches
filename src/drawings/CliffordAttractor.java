package drawings;

import processing.core.PApplet;
import sketch.Sketch;

import java.util.ArrayList;

public class CliffordAttractor extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractor");
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
            a = random(1) * 6 - 3;
            b = random(1) * 6 - 3;
            c = random(1) * 4 - 2;
            d = random(1) * 4 - 2;
            background(colour.white());
            noStroke();
            ArrayList<Point> points = new ArrayList<>();
            ArrayList<Point> pointsTwo = new ArrayList<>();
            int c1 = colour.rand();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            for (int i = 0; i < 100; i++) {
                points.add(new Point(0, (_height / 100f) * i, c1));
                pointsTwo.add(new Point(_width, (_height / 100f) * i, c2));
            }
            for (int i = 0; i < 450; i++) {
                for (int j = 0; j < points.size(); j++) {
                    points.get(j).draw();
                    pointsTwo.get(j).draw();
                }
            }
            save("clifford-attractor", name);
        });
    }

    class Point implements Cloneable {
        private float _x;
        private float _y;
        private float _vx;
        private float _vy;
        private float _scale = 1;
        private boolean _isNull = false;
        private int _colour;

        public Point() {
            _isNull = true;
        }

        public Point(float x, float y, int colour) {
            _x = x;
            _y = y;
            _colour = colour;
            _isNull = false;
        }

        public void setX(float x) {
            _x = x;
        }

        public void setY(float y) {
            _y = y;
        }

        public float x() {
            return _x;
        }

        public float y() {
            return _y;
        }

        public boolean isFinite() {
            return !Float.isInfinite(_x) && !Float.isInfinite(_y);
        }

        public boolean isNull() {
            return _isNull;
        }

        public Point clone() throws CloneNotSupportedException {
            return (Point) super.clone();
        }

        void draw() {
            fill(_colour);
            float value = getValue();
            _vx += Math.cos(value) * 0.8;
            _vy += Math.sin(value) * 0.8;
            _x += _vx;
            _y += _vy;
            ellipse(_x, _y, 8 * _scale, 8 * _scale);
            _vx *= 0.97;
            _vy *= 0.97;
            _scale *= 0.98;
        }

        float getValue() {
            // clifford attractor
            // http://paulbourke.net/fractals/clifford/

            // scale down x and y
            float scale = 0.005f;
            float dx = (_x - width / 2f) * scale;
            float dy = (_y - height / 2f) * scale;

            // attractor gives new x, y for old one.
            float x1 = sin(a * dy) + c * cos(a * dx);
            float y1 = sin(b * dx) + d * cos(b * dy);

            // find angle from old to new. that's the value.
            return atan2(y1 - dy, x1 - dx);
        }
    }
}
