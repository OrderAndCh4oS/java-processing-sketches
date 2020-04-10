package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class Salamander extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Salamander");
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
            background(colour.bg());
            float left = -100;
            for (int j = 0; j < 30; j++) {
                float strokeWidth = 25 + random(30);
                ThickWave wave = new ThickWave(
                        new Point(left, 0),
                        PI / 110,
                        2,
                        strokeWidth
                );
                for (int i = 0; i < 512; i++) {
                    wave.update();
                }
                PGraphics mask = wave.getMask();
                PGraphics source = createGraphics((int) _width, (int) _height);
                source.beginDraw();
                source.noStroke();
                int stripeColour = j % 2 == 0 ? colour.rand() : colour.bg();
                source.fill(stripeColour);
                source.rect(0, 0, _width, _height);
                int fillColour = colour.rand();
                if (fillColour == stripeColour) {
                    fillColour = colour.bg();
                }
                source.fill(fillColour);
                wave.drawDots(source);
                source.endDraw();
                source.mask(mask);
                image(source, 0, 0);
                left = left + strokeWidth;
            }
            save("salamander", name);
        });
    }

    class ThickWave {
        float _startX;
        ArrayList<Point> _points = new ArrayList<>();
        Point _p;
        float _a;
        float _v;
        float _s;
        float _thickness;
        float _waveHeight = 40;

        ThickWave(Point point, float value, float speed, float thickness) {
            _startX = point.x();
            _p = point;
            _v = value;
            _s = speed;
            _a = PI;
            _thickness = thickness;
            _points.add(new Point(_p.x(), _p.y()));
        }

        void update() {
            _a += _v;
            float dX = sin(_a) * _waveHeight;
            _p.setX(_startX + dX - dX / 2);
            _p.setY(_p.y() + _s);
            _points.add(new Point(_p.x(), _p.y()));
        }

        void drawDots(PGraphics source) {
            for (int i = 0; i < _points.size(); i += (int) _thickness / 2) {
                source.ellipse(_points.get(i).x() + (_thickness / 2), _points.get(i).y(), _thickness / 2, _thickness / 2);
            }
        }

        PGraphics getMask() {
            PGraphics mask = createGraphics((int) _width, (int) _height);
            mask.beginDraw();
            mask.noStroke();
            mask.fill(0xff000000);
            mask.rect(0, 0, _width, _height);
            mask.fill(0xffffffff);
            mask.beginShape();
            for (Point p : _points) {
                mask.vertex(p.x(), p.y());
            }
            for (int i = _points.size() - 1; i >= 0; i--) {
                mask.vertex(_points.get(i).x() + _thickness, _points.get(i).y());
            }
            mask.endShape(CLOSE);
            mask.endDraw();
            return mask;
        }
    }
}
