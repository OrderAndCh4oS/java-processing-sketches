package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class Autosuggestion extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Autosuggestion");
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
            background(colour.white());
            float angle = 0;
            int count = 0;
            while (angle <= TAU) {
                float sectionAngle = PI * random(0.05f, 0.085f);
                ThickAngleWave wave = new ThickAngleWave(new Point(0, 0), PI / 110, 2, sectionAngle);
                for (int x = 0; x < 512; x++) {
                    wave.update();
                }
                PGraphics mask = wave.getMask(angle);
                PGraphics source = createGraphics((int) _width, (int) _height);
                source.beginDraw();
                source.noStroke();
                int stripeColour = count % 2 == 0 ? colour.white() : colour.rand();
                source.fill(stripeColour);
                source.rect(0, 0, _width, _height);
                source.endDraw();
                source.mask(mask);
                image(source, 0, 0);
                int fillColour = colour.rand();
                if (fillColour == stripeColour) {
                    fillColour = colour.white();
                }
                fill(fillColour);
                noStroke();
                wave.drawDots(angle);
                angle += sectionAngle;
                count++;
            }
            save("autosuggestion", name);

        });
    }

    class ThickAngleWave {
        float _startX;
        ArrayList<Point> _points = new ArrayList<>();
        Point _p;
        float _a;
        float _v;
        float _s;
        float _sectionAngle;
        float _waveHeight = 40;

        ThickAngleWave(Point point, float value, float speed, float sectionAngle) {
            _startX = point.x();
            _p = point;
            _v = value;
            _s = speed;
            _a = 0;
            _sectionAngle = sectionAngle;
            _points.add(new Point(_p.x(), _p.y()));
        }

        void update() {
            _a += _v;
            float dX = sin(_a) * _waveHeight;
            _p.setX(_startX + dX - dX / 2);
            _p.setY(_p.y() + _s);
            _points.add(new Point(_p.x(), _p.y()));
        }

        void drawDots(float angle) {
            pushMatrix();
            translate(_width / 2, _height / 2);
            int i = 10;
            while (i < _points.size()) {
                Point p = _points.get(i);
                float distance = sqrt(p.x() * p.x() + p.y() * p.y());
                float scale = map(distance, 0, 512, 0, 30);
                i += scale * 1.25;
                Point v = rotateXY(p, angle + _sectionAngle / 2);
                ellipse(v.x(), v.y(), scale, scale);
            }
            popMatrix();
        }

        PGraphics getMask(float angle) {
            PGraphics mask = createGraphics((int) _width, (int) _height);
            mask.beginDraw();
            mask.noStroke();
            mask.fill(0xff000000);
            mask.rect(0, 0, _width, _height);
            mask.fill(0xffffffff);
            mask.translate(_width / 2, _height / 2);
            mask.beginShape();
            for (Point p : _points) {
                Point v = rotateXY(p, angle);
                mask.vertex(v.x(), v.y());
            }
            for (int i = _points.size() - 1; i >= 0; i--) {
                float dA = angle + _sectionAngle;
                Point v = rotateXY(_points.get(i), dA);
                mask.vertex(v.x(), v.y());
            }
//            for (float a = _sectionAngle; a > _sectionAngle / 3; a -= _sectionAngle / 3) {
//                Point v = rotateXY(_points.get(_points.size() - 1), (angle + _sectionAngle) - a);
//                mask.curveVertex(v.x(), v.y());
//            }
            mask.curveVertex(_points.get(_points.size() - 1).x(), _points.get(_points.size() - 1).y());
            mask.endShape(CLOSE);
            mask.endDraw();
            return mask;
        }

        /**
         * Rotation Maths
         * http://danceswithcode.net/engineeringnotes/rotations_in_2d/rotations_in_2d.html
         */
        private Point rotateXY(Point p, float dA) {
            float x = p.x();
            float y = p.y();
            float cos = (float) Math.cos(dA);
            float sin = (float) Math.sin(dA);
            float dX = x * cos - y * sin;
            float dY = x * sin + y * cos;

            return new Point(dX, dY);
        }
    }
}
