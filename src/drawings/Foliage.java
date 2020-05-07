package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;
import utilities.colour.Colours;

import java.util.ArrayList;
import java.util.Random;

public class Foliage extends Sketch {

    int totalCount = 0;
    float scale = 0.95f;

    public static void main(String... args) {
        PApplet.main("drawings.Foliage");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1500, 1500, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            for (int i = 0; i < 5; i++) {
                ArrayList<Line> drawLines = new ArrayList<Line>();
                ArrayList<Line> collisionLines = new ArrayList<Line>();
                background(colour.black());
                noStroke();
                String colourName = "warm-grey";
                int lineCount = round(random(1) * 7500) + 7500;
                initStems(drawLines, collisionLines, colour, _colours.get(colourName), 1);
                int drawnCount = 0;
                while (drawnCount < lineCount) {
                    float leanLimit = 0.75f;
                    float angleRange = PI / 9;
                    float minSize = 15;
                    drawnCount = drawLines(drawLines, collisionLines, drawnCount, leanLimit, angleRange, minSize);
                }
                save("foliage-" + colourName, lineCount + "-" + name);
            }
        });
    }

    private int drawLines(ArrayList<Line> drawLines, ArrayList<Line> collisionLines, int drawnCount, float leanLimit, float angleRange, float minSize) {
        Line nextLineBase = getRandomLine(drawLines);
        float nextLength = nextLineBase.getLength() * scale;
        Point nextPoint = nextLineBase.q();
        float angleRand = PI * angleRange - random(PI * angleRange * nextLineBase.getLeanAngle());
        float angle = nextLineBase.getAngle() + angleRand;
        Lean nextLean = getNextLean(leanLimit, nextLineBase);
        Line nextLine = new Line(nextPoint, nextLength, angle, nextLineBase.getFoliageColours(), nextLineBase.getStemColours(), nextLean);
        boolean doesIntersect = false;
        for (Line l : collisionLines) {
            if (nextLine.isIntersect(l)) {
                doesIntersect = true;
                break;
            }
        }
        if (!doesIntersect && nextLength > minSize && (isInBounds(nextLine.p()) || isInBounds(nextLine.q()))) {
            nextLine.draw();
            drawLines.add(nextLine);
            collisionLines.add(nextLine);
            drawnCount++;
            totalCount++;
        }
        return drawnCount;
    }

    private Lean getNextLean(float leanLimit, Line nextLineBase) {
        Lean newLean = nextLineBase.getLean();
        if (random(1) > leanLimit) {
            if (newLean == Lean.LEFT) {
                newLean = Lean.RIGHT;
            } else {
                newLean = Lean.LEFT;
            }
        }
        return newLean;
    }

    void initStems(ArrayList<Line> drawLines, ArrayList<Line> collisionLines, Colours foliageColours, Colours stemColours, int stemCount) {
        for (int i = 0; i < stemCount; i++) {
            Vector centre = new Vector(750, 750);
            Vector start = new Vector(500 + random(500), 500 + random(500));
            Line l = new Line(
                    start.getPoint(),
                    125,
                    start.angleTo(centre),
                    foliageColours,
                    stemColours,
                    random(1) > 0.5 ? Lean.LEFT : Lean.RIGHT);
            drawLines.add(l);
            collisionLines.add(l);
            l.draw();
        }
    }

    boolean isInBounds(Point p) {
        return !(p.x() < 0 || p.y() < 0 || p.x() > _width || p.y() > _height);
    }

    Line getRandomLine(ArrayList<Line> drawLines) {
        return drawLines.get(new Random().nextInt(drawLines.size()));
    }

    enum Lean {
        LEFT,
        RIGHT
    }

    class Line {
        private final Point _p;
        private final Point _q;
        private float _length;
        private Colours _foliageColours;
        private Colours _stemColours;
        private final Lean _lean;

        public Line(Point p, Point q) {
            this._p = p;
            this._q = q;
            _lean = random(1) > 0.5 ? Lean.LEFT : Lean.RIGHT;
        }

        public Line(Point p, float len, float angle, Lean lean) {
            _p = p;
            _length = len;
            Vector temp = new Vector(0, 0);
            temp.setLength(_length);
            temp.setAngle(angle);
            temp.addTo(new Vector(_p));
            _q = temp.getPoint();
            _lean = lean;
        }

        public Line(Point p, float len, float angle, Colours foliageColours, Colours stemColours, Lean lean) {
            this(p, len, angle, lean);
            _foliageColours = foliageColours;
            _stemColours = stemColours;
        }

        public Point getIntersect(Line l2) {
            float a1 = _p.y() - _q.y();
            float b1 = _q.x() - _p.x();
            float c1 = a1 * _q.x() + b1 * _q.y();
            float a2 = l2.p().y() - l2.q().y();
            float b2 = l2.q().x() - l2.p().x();
            float c2 = a2 * l2.q().x() + b2 * l2.q().y();
            float delta = a1 * b2 - a2 * b1;
            return new Point((b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta);
        }

        public boolean isIntersect(Line l2) {
            Point intersect = getIntersect(l2);
            return intersect.isFinite() && onSegment(intersect) && l2.onSegment(intersect);
        }

        public boolean onSegment(Point r) {
            return r.x() <= max(p().x(), q().x()) && r.x() >= min(p().x(), q().x()) &&
                    r.y() <= max(p().y(), q().y()) && r.y() >= min(p().y(), q().y());
        }

        public float maxX() {
            return max(_p.x(), _q.x());
        }

        public float maxY() {
            return max(_p.y(), _q.y());
        }

        public float minX() {
            return min(_p.x(), _q.x());
        }

        public float minY() {
            return min(_p.y(), _q.y());
        }

        public Point getMidPoint() {
            float dX = (_p.x() + _q.x()) / 2;
            float dY = (_p.y() + _q.y()) / 2;
            return new Point(dX, dY);
        }

        public Point getRandomPoint() {
            float t = random(1);
            float dX = _p.x() + t * (_q.x() - _p.x());
            float dY = _p.y() + t * (_q.y() - _p.y());
            return new Point(dX, dY);
        }

        private Point drawTo() {
            float dX = _p.x() + 0.95f * (_q.x() - _p.x());
            float dY = _p.y() + 0.95f * (_q.y() - _p.y());
            return new Point(dX, dY);
        }

        public Point p() {
            return _p;
        }

        public Point q() {
            return _q;
        }

        public float getLength() {
            return _length;
        }

        float getAngle() {
            float dX = _p.x() - _q.x();
            float dY = _p.y() - _q.y();
            float theta = atan2(dY, dX);
            return theta + PI;
        }

        Colours getFoliageColours() {
            return _foliageColours;
        }

        Colours getStemColours() {
            return _stemColours;
        }

        Lean getLean() {
            return _lean;
        }

        float getLeanAngle() {
            switch (_lean) {
                case LEFT:
                    return 0.5f + random(0.5f);
                case RIGHT:
                    return 1 + random(0.5f);
                default:
                    return 0;
            }
        }

        void draw() {
            Vector base = new Vector(_p);
            Vector move = new Vector(0, 0);
            move.setLength(1);
            move.setAngle(getAngle() + PI * 0.5f);
            base.addTo(move);

            Vector baseTwo = new Vector(_p);
            Vector moveTwo = new Vector(0, 0);
            moveTwo.setLength(1);
            moveTwo.setAngle(getAngle() + PI * 1.5f);
            baseTwo.addTo(moveTwo);

            if (_length < 65) {
                fill(_foliageColours.rand());
            } else {
                fill(_stemColours.rand());
            }
            triangle(base.getPoint().x(), base.getPoint().y(), baseTwo.getPoint().x(), baseTwo.getPoint().y(), drawTo().x(), drawTo().y());
        }
    }
}

