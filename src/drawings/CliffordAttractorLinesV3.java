package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

public class CliffordAttractorLinesV3 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractorLinesV3");
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
            drawFibreTexture(colour.black(), 250000, 0.2f, 0.6f);
            noFill();
            blendMode(MULTIPLY);
            strokeWeight(4f);
            ArrayList<AttractorPoint> attractorPoints = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsTwo = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsThree = new ArrayList<>();
            int c1 = colour.rand();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            int c3 = colour.rand();
            if(colour.getAll().size() > 2) {
                do {
                    c3 = colour.rand();
                } while (c1 == c3 || c2 == c3);
            }
            for (int i = 0; i < 1000; i++) {
                attractorPoints.add(new AttractorPoint((random(_width + 100) / 2) - 100, random(_height + 100) - 50));
            }
            for (int i = 0; i < 500; i++) {
                attractorPointsTwo.add(new AttractorPoint(((_width / 2 + random(_width + 100) / 2)), random(_height + 100) / 2 - 100));
                attractorPointsThree.add(new AttractorPoint(((_width / 2 + random(_width + 100) / 2)), _height / 2 + random(_height + 100) / 2));
            }
            stroke(c1, 75);
            ArrayList<Line> lines = new ArrayList<>();
            Point lastPoint = new Point();
            drawAttractorPaths(attractorPoints, lines, lastPoint);
            stroke(c2, 75);
            drawAttractorPaths(attractorPointsTwo, lines, lastPoint);
            stroke(c3, 75);
            drawAttractorPaths(attractorPointsThree, lines, lastPoint);
            save("clifford-attractor-lines-v3", name);
        });
    }

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        for (AttractorPoint attractorPoint : attractorPoints) {
            beginShape();
            for (int i = 0; i < 5000; i++) {
                Point p = attractorPoint.getPoint();
                if(!lastPoint.isNull()) {
                    Line line = new Line(lastPoint, p);
                    for(Line otherLine : lines) {
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
            _vx *= 0.75;
            _vy *= 0.75;
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
