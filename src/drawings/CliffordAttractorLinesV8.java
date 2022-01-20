package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Random.randomInt;

public class CliffordAttractorLinesV8 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractorLinesV8");
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
            int bg = colour.rand();
            background(bg);
            ArrayList<Integer> gapColours = new ArrayList<>();
            gapColours.add(colour.white());
            gapColours.add(colour.black());
            drawLineTextureWithGapsAlternate(gapColours, 9, 6, Direction.TOP);
            fill(bg);
            stroke(colour.white());
            strokeCap(MITER);
            float margin = 200;
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            strokeWeight(12);
            noFill();
            ArrayList<AttractorPoint> attractorPointsLeft = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsRight = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsTop = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsBottom = new ArrayList<>();
            int c1 = colour.white();
            int c2 = colour.black();
            int c3 = colour.white();
            int c4 = colour.black();

            float innerMargin = margin * 1.5f;
            for (int i = 0; i < 150; i++) {
                attractorPointsTop.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsBottom.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsLeft.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsRight.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
            }
            ArrayList<Line> lines = new ArrayList<>();
            Point lastPoint = new Point();
            strokeCap(ROUND);
            stroke(c1);
            drawAttractorPaths(attractorPointsLeft, lines, lastPoint);
            strokeCap(ROUND);
            stroke(c2);
            drawAttractorPaths(attractorPointsTop, lines, lastPoint);
            strokeCap(ROUND);
            stroke(c3);
            drawAttractorPaths(attractorPointsBottom, lines, lastPoint);
            strokeCap(ROUND);
            stroke(c4);
            drawAttractorPaths(attractorPointsRight, lines, lastPoint);
            save("clifford-attractor-lines-v8", name);
        });
    }

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        for (int j = 0, attractorPointsSize = attractorPoints.size(); j < attractorPointsSize; j += 2) {
            AttractorPoint attractorPointOne = attractorPoints.get(j);
            AttractorPoint attractorPoint = attractorPoints.get(j + 1);
            lastPoint.setIsNull(true);
            boolean isIntersect = false;
            boolean on = false;
            int length = randomInt(8, 16);
            for (int i = 0; i < randomInt(500, 1500); i++) {
                if(i % length == 0) {
                    on = !on;
                    length = on ? randomInt(8, 16) : randomInt(4, 6);
                    if(on) beginShape();
                    else endShape();
                }
                Point p = attractorPointOne.getPoint();
                if (!lastPoint.isNull()) {
                    Line line = null;
                    try {
                        line = new Line(lastPoint.clone(), p.clone());
                        for (Line otherLine : lines) {
                            if (otherLine.p() == line.p() || otherLine.p() == line.q()) break;
                            if (otherLine.q() == line.p() || otherLine.q() == line.q()) break;
                            isIntersect = line.isIntersect(otherLine);
                            lastPoint.setIsNull(true);
                            if (isIntersect) break;
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    if (isIntersect) break;
                    if(on) lines.add(line);
                }
                if(on) curveVertex(p.x(), p.y());
                lastPoint.setX(p.x());
                lastPoint.setY(p.y());
                lastPoint.setIsNull(false);
            }
            if(on) endShape();
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
