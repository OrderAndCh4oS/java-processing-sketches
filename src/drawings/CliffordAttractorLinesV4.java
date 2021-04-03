package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

public class CliffordAttractorLinesV4 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractorLinesV4");
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
            blendMode(NORMAL);
            background(colour.white());
            drawTexture(colour.black(), 250000, 0.1f, 0.5f);
            noFill();
            blendMode(MULTIPLY);
            ArrayList<AttractorPoint> attractorPointsLeft = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsRight = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsTop = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsBottom = new ArrayList<>();
            float strokeWidth = 5;
            float gap = 5;
            strokeWeight(strokeWidth);
            strokeCap(ROUND);
            int c1 = colour.rand();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            int c3 = colour.rand();
            int c4 = colour.rand();
            if (colour.getAll().size() > 2) {
                do {
                    c3 = colour.rand();
                } while (c1 == c3 || c2 == c3);
            }
            if (colour.getAll().size() > 3) {
                do {
                    c4 = colour.rand();
                } while (c1 == c4 || c2 == c4 || c3 == c4);
            }
            for (float x = -strokeWidth; x <= _width + strokeWidth; x += strokeWidth + gap) {
                attractorPointsTop.add(new AttractorPoint(x, -strokeWidth));
                attractorPointsBottom.add(new AttractorPoint(_height - x, _height + strokeWidth));
            }
            for (float y = -strokeWidth; y <= _height + strokeWidth; y += strokeWidth + gap) {
                attractorPointsLeft.add(new AttractorPoint(-strokeWidth, y));
                attractorPointsRight.add(new AttractorPoint(_width + strokeWidth, _width - y));
            }
            ArrayList<Line> lines = new ArrayList<>();
            Point lastPoint = new Point();
            stroke(c1, 175f);
            drawAttractorPaths(attractorPointsLeft, lines, lastPoint);
            stroke(c2, 175f);
            drawAttractorPaths(attractorPointsTop, lines, lastPoint);
            stroke(c3, 175f);
            drawAttractorPaths(attractorPointsBottom, lines, lastPoint);
            stroke(c4, 175f);
            drawAttractorPaths(attractorPointsRight, lines, lastPoint);
            save("clifford-attractor-lines-v4", name);
        });
    }

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        for (AttractorPoint attractorPoint : attractorPoints) {
            beginShape();
            for (int i = 0; i < 5000; i++) {
                Point p = attractorPoint.getPoint();
                if (!lastPoint.isNull()) {
                    Line line = new Line(lastPoint, p);
                    for (Line otherLine : lines) {
                        line.isIntersect(otherLine);
                        lastPoint.setIsNull(true);
                        break;
                    }
                    lines.add(line);
                }
                vertex(p.x(), p.y());
                lastPoint.setX(p.x());
                lastPoint.setY(p.y());
            }
            endShape();
        }
    }

    class AttractorPoint implements Cloneable {
        private float _x;
        private float _y;
        private float _vx;
        private float _vy;

        public AttractorPoint(float x, float y) {
            _x = x;
            _y = y;
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

        public AttractorPoint clone() throws CloneNotSupportedException {
            return (AttractorPoint) super.clone();
        }

        Point getPoint() {
            float value = getValue();
            _vx += cos(value) * 0.66; // 0.5
            _vy += sin(value) * 0.66; // 0.5
            _x += _vx;
            _y += _vy;
            _vx *= 0.85;
            _vy *= 0.85;
            return new Point(_x, _y);
        }

        float getValue() {
            // clifford attractor
            // http://paulbourke.net/fractals/clifford/

            float scale = 0.0035f;
            float dx = (_x - width / 2f) * scale;
            float dy = (_y - height / 2f) * scale;

            float x1 = sin(a * dy) + c * cos(a * dx);
            float y1 = sin(b * dx) + d * cos(b * dy);

            return atan2(y1 - dy, x1 - dx);
        }
    }
}
