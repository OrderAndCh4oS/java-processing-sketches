package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;

import java.util.ArrayList;
import java.util.Collections;

public class GlitchBirdsV2 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.GlitchBirdsV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(4096, 4096, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 10; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                a = random(1) * 6 - 3;
                b = random(1) * 6 - 3;
                c = random(1) * 4 - 2;
                d = random(1) * 4 - 2;
                blendMode(NORMAL);
                background(colour.white());
                drawWaveTexture(colour, 1, 8, 16, 4, 255 * 0.7f);
                fill(colour.white());
                stroke(colour.black());
                strokeCap(MITER);
                float margin = 400;
                rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
                strokeWeight(3);
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
                    attractorPointsTop.add(new AttractorPoint(random(innerMargin / 4, 1024 - innerMargin / 4), random(innerMargin / 4, 1024 - innerMargin / 4)));
                    attractorPointsBottom.add(new AttractorPoint(random(innerMargin / 4, 1024 - innerMargin / 4), random(innerMargin / 4, 1024 - innerMargin / 4)));
                    attractorPointsLeft.add(new AttractorPoint(random(innerMargin / 4, 1024 - innerMargin / 4), random(innerMargin / 4, 1024 - innerMargin / 4)));
                    attractorPointsRight.add(new AttractorPoint(random(innerMargin / 4, 1024 - innerMargin / 4), random(innerMargin / 4, 1024 - innerMargin / 4)));
                }
                ArrayList<Line> lines = new ArrayList<>();
                Point lastPoint = new Point();
                noStroke();
                fill(c1);
                drawAttractorPaths(attractorPointsLeft, lines, lastPoint);
                fill(c2);
                drawAttractorPaths(attractorPointsTop, lines, lastPoint);
                fill(c3);
                drawAttractorPaths(attractorPointsBottom, lines, lastPoint);
                fill(c4);
                drawAttractorPaths(attractorPointsRight, lines, lastPoint);
                save("glitch-birds-v2", name);
            });
        }
    }

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        for (int i = 0; i < attractorPoints.size() - 1; i++) {
            ArrayList<Point> pointsA = new ArrayList<>();
            ArrayList<Point> pointsB = new ArrayList<>();
            ArrayList<Point> pointsCombined = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                pointsA.add(attractorPoints.get(i).getPoint());
                pointsB.add(attractorPoints.get(i + 1).getPoint());
            }
            Collections.reverse(pointsB);
            pointsCombined.addAll(pointsA);
            pointsCombined.addAll(pointsB);
            lastPoint.setIsNull(true);
            boolean isIntersect = false;
            beginShape();
            for (Point p : pointsCombined) {
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
                    lines.add(line);
                }

                curveVertex(p.x(), p.y());
                lastPoint.setX(p.x());
                lastPoint.setY(p.y());
                lastPoint.setIsNull(false);
            }
            endShape(CLOSE);
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
            return new Point(_x * 4, _y * 4);
        }

        float getValue() {
            // clifford attractor
            // http://paulbourke.net/fractals/clifford/

            float scale = 0.0035f;
            float dx = (_x - 1024 / 2f) * scale;
            float dy = (_y - 1024 / 2f) * scale;

            float x1 = sin(a * dy) + c * cos(a * dx);
            float y1 = sin(b * dx) + d * cos(b * dy);

            return atan2(y1 - dy, x1 - dx);
        }
    }
}
