package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

public class ChunkyLinesAbstract extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.ChunkyLinesAbstract");
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
            blendMode(MULTIPLY);
            ArrayList<AttractorPoint> attractorPointsLeft = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsRight = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsTop = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsBottom = new ArrayList<>();
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
            float innerMargin = 150;
            for (int i = 0; i < 25; i++) {
                attractorPointsTop.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsBottom.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsLeft.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
                attractorPointsRight.add(new AttractorPoint(random(innerMargin, _width - innerMargin), random(innerMargin, _height - innerMargin)));
            }
            ArrayList<Line> lines = new ArrayList<>();
            Point lastPoint = new Point();
            noStroke();
            fill(c1, 0.8f);
            drawAttractorPaths(attractorPointsLeft, lines, lastPoint);
            fill(c2, 0.8f);
            drawAttractorPaths(attractorPointsTop, lines, lastPoint);
            fill(c3, 0.8f);
            drawAttractorPaths(attractorPointsBottom, lines, lastPoint);
            fill(c4, 0.8f);
            drawAttractorPaths(attractorPointsRight, lines, lastPoint);
            save("chunky-lines-abstract", name);
        });
    }

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        try {
            for (int i = 0; i < attractorPoints.size() - 1; i++) {
                AttractorPoint attractorPointA = attractorPoints.get(i).clone();
                AttractorPoint attractorPointB = attractorPoints.get(i + 1).clone();
                ArrayList<Point> pointsA = new ArrayList<>();
                ArrayList<Point> pointsB = new ArrayList<>();
                for (int j = 0; j < 50; j++) {
                    pointsA.add(attractorPointA.getPoint());
                    pointsB.add(attractorPointB.getPoint());
                }
                ArrayList<Point> reversed = reverseArrayList(pointsB);
                pointsA.addAll(reversed);
                beginShape();
                for (Point p : pointsA) {
                    curveVertex(p.x(), p.y());
                }
                endShape(CLOSE);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Point> reverseArrayList(ArrayList<Point> list) {
        ArrayList<Point> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }

        return reversedList;
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
            float scale = 0.0035f;
            float dx = (_x - width / 2f) * scale;
            float dy = (_y - height / 2f) * scale;

            float x1 = sin(a * dy) + c * cos(a * dx);
            float y1 = sin(b * dx) + d * cos(b * dy);

            return atan2(y1 - dy, x1 - dx);
        }
    }
}
