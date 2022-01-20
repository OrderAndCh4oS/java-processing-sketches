package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Random.randomInt;

public class CliffordAttractorLinesV6 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractorLinesV6");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
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
            drawLineTextureWithGapsThick(colour.black(), 9, 6, Direction.TOP);
            fill(colour.white());
            stroke(colour.black());
            strokeCap(MITER);
            float margin = 200;
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            strokeWeight(12);
            noFill();
            blendMode(MULTIPLY);
            ArrayList<AttractorPoint> attractorPointsLeft = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsRight = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsTop = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsBottom = new ArrayList<>();
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
            float innerMargin = margin * 1.5f;
            for (int i = 0; i < 150; i++) {
                attractorPointsTop.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsBottom.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsLeft.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsRight.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
            }
            ArrayList<Line> lines = new ArrayList<>();
            Point lastPoint = new Point();
            stroke(c1);
            drawAttractorPaths(attractorPointsLeft, lines, lastPoint);
            stroke(c2);
            drawAttractorPaths(attractorPointsTop, lines, lastPoint);
            stroke(c3);
            drawAttractorPaths(attractorPointsBottom, lines, lastPoint);
            stroke(c4);
            drawAttractorPaths(attractorPointsRight, lines, lastPoint);
            save("clifford-attractor-lines-v6", name);
        });
    }

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        for (AttractorPoint attractorPoint : attractorPoints) {
            lastPoint.setIsNull(true);
            boolean isIntersect = false;
            beginShape();
            for (int i = 0; i < randomInt(500, 1500); i++) {
                Point p = attractorPoint.getPoint();
                if (!lastPoint.isNull()) {
                    Line line = null;
                    try {
                        line = new Line(lastPoint.clone(), p.clone());
                        for (Line otherLine : lines) {
                            if(otherLine.p() == line.p() || otherLine.p() == line.q()) break;
                            if(otherLine.q() == line.p() || otherLine.q() == line.q()) break;
                            isIntersect = line.isIntersect(otherLine);
                            lastPoint.setIsNull(true);
                            if (isIntersect) break;
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    if(isIntersect) break;
                    lines.add(line);
                }

                curveVertex(p.x(), p.y());
                lastPoint.setX(p.x());
                lastPoint.setY(p.y());
                lastPoint.setIsNull(false);
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
