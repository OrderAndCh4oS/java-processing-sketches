package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;

public class CliffordAttractorChunkyLinesV2 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractorChunkyLinesV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iteration = 0; iteration < 5; iteration++) {
            _colours.getColours("constructivist-real", "order-and-chaos", "candy").forEach((name, colour) -> {
                a = random(1) * 6 - 3;
                b = random(1) * 6 - 3;
                c = random(1) * 4 - 2;
                d = random(1) * 4 - 2;
                blendMode(NORMAL);
                background(colour.white());
                ArrayList<AttractorPoint> attractorPointsLeft = new ArrayList<>();
                ArrayList<AttractorPoint> attractorPointsRight = new ArrayList<>();
                ArrayList<AttractorPoint> attractorPointsTop = new ArrayList<>();
                ArrayList<AttractorPoint> attractorPointsBottom = new ArrayList<>();
                int lineCount = 25;
                for (int i = 0; i < lineCount + 1; i++) {
                    attractorPointsTop.add(new AttractorPoint((_width / lineCount) * i, 0));
                    attractorPointsBottom.add(new AttractorPoint((_width / lineCount) * i, _height));
                    attractorPointsLeft.add(new AttractorPoint(0, (_height / lineCount) * i));
                    attractorPointsRight.add(new AttractorPoint(_width, (_height / lineCount) * i));
                }
                ArrayList<Line> lines = new ArrayList<>();
                Point lastPoint = new Point();
                noStroke();
                drawAttractorFills(attractorPointsLeft, lines, lastPoint, colour);
                drawAttractorFills(attractorPointsTop, lines, lastPoint, colour);
                drawAttractorFills(attractorPointsBottom, lines, lastPoint, colour);
                drawAttractorFills(attractorPointsRight, lines, lastPoint, colour);
                save("clifford-attractor-chunky-lines-v2", name);
            });
        }
    }

    private void drawAttractorFills(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint, Colours colour) {
        try {
            for (int i = 0; i < attractorPoints.size() - 1; i += 1) {
                AttractorPoint attractorPointA = attractorPoints.get(i).clone();
                AttractorPoint attractorPointB = attractorPoints.get(i + 1).clone();
                ArrayList<Point> pointsA = new ArrayList<>();
                ArrayList<Point> pointsB = new ArrayList<>();
                for (int j = 0; j < 420; j++) {
                    pointsA.add(attractorPointA.getPoint());
                    pointsB.add(attractorPointB.getPoint());
                }
                ArrayList<Point> reversed = reverseArrayList(pointsB);
                pointsA.addAll(reversed);
//                fill(i % 2 == 0 ? colour.black() : colour.white());
                fill(colour.randWithBlack());
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

    private void drawAttractorPaths(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint) {
        for (AttractorPoint attractorPoint : attractorPoints) {
            beginShape();
            for (int i = 0; i < 250; i++) {
                Point p = attractorPoint.getPoint();
                curveVertex(p.x(), p.y());
            }
            endShape();
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
