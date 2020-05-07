package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

public class Waves extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Waves");
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
            background(colour.black());
            for (int j = 0; j < 108; j++) {
                int c1 = colour.rand();
                int c2 = colour.rand();
                int c3 = colour.rand();
                float startX = (j * 10);
                Point point = new Point(startX, -150);
                Point pointTwo = new Point(startX, -150);
                Point pointThree = new Point(startX, -150);
                Wave wave = new Wave(startX, point, PI / (random(80) + 20), 2, 3 + random(3));
                Wave waveTwo = new Wave(startX, pointTwo, PI / (random(80) + 20), 2, 3 + random(4));
                Wave waveThree = new Wave(startX, pointThree, PI / (random(80) + 20), 2, 3 + random(5));
                for (int i = 0; i < 2000; i++) {
                    stroke(c1);
                    wave.update();
                    wave.draw();
                }
                for (int i = 0; i < 2000; i++) {
                    stroke(c2);
                    waveTwo.update();
                    waveTwo.draw();
                }
                for (int i = 0; i < 2000; i++) {
                    stroke(c3);
                    waveThree.update();
                    waveThree.draw();
                }
            }
            save("waves", name);
        });
    }

    class Wave {
        float _startX;
        Point _lastP;
        Point _p;
        float _a;
        float _v;
        float _s;
        float _strokeWidth;
        float _waveHeight = 40;

        Wave(float startX, Point point, float value, float speed, float strokeWidth) {
            _startX = startX;
            _p = point;
            _v = value;
            _s = speed;
            _a = PI;
            _strokeWidth = strokeWidth;
            _lastP = new Point(_p.x(), _p.y());
        }

        void update() {
            _lastP.setX(_p.x());
            _lastP.setY(_p.y());
            _a += _v;
            float dX = sin(_a) * _waveHeight;
            _p.setX(_startX + dX - dX / 2);
            _p.setY(_p.y() + _s);
        }

        void draw() {
            strokeWeight(_strokeWidth);
            strokeCap(ROUND);
            line(_lastP.x(), _lastP.y(), _p.x(), _p.y());
        }
    }
}
