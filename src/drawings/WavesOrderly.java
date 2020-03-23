package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

public class WavesOrderly extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.WavesOrderly");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.get_colours().forEach((name, colour) -> {
            background(colour.bg());
            for (int j = 0; j < 110; j++) {
                int c1 = colour.rand();
                float x = (j * 10) - 20;
                Point point = new Point(x, -150);
                Wave wave = new Wave(x, point, PI / 40, 2, 3 + random(3));
                for (int i = 0; i < 1000; i++) {
                    stroke(c1);
                    wave.update();
                    wave.draw();
                }
            }
            save("wavesOrderly", name);
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
            _v += 0.0001;
        }

        void draw() {
            strokeWeight(_strokeWidth);
            strokeCap(ROUND);
            line(_lastP.x(), _lastP.y(), _p.x(), _p.y());
        }
    }
}
