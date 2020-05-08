package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Vector;

import static utilities.Bezier.cubicBezier;
import static utilities.Map.CUBIC;
import static utilities.Map.EASE_IN;

public class YamataNoOrochi extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.YamataNoOrochi");
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
            background(colour.black());
            drawTexture(colour.rand(), 0.8f, Direction.TOP, CUBIC, EASE_IN);
            float x1 = random(_width);
            for (int i = 0; i < 8; i++) {
                float y1 = _height;
                float x2 = random(_width);
                float y2 = random(0, _height / 3);
                float x1h = x1 + random(800) - 400;
                float y1h = y1 + random(100) - 50;
                float x2h = x2 + random(800) - 400;
                float y2h = y2 + random(100) - 50;
                Point a = new Point(x1, y1);
                Point d = new Point(x2, y2);
                Point c = new Point(x1h, y1h);
                Point b = new Point(x2h, y2h);
                int c1 = colour.rand();
                int c2;
                int c3;
                do {
                    c2 = colour.rand();
                } while (c1 == c2);
                do {
                    c3 = colour.rand();
                } while (c1 == c3 || c2 == c3);
                noStroke();
                Point lastPoint = new Point();
                for (float t = 0; t <= 1; t += 0.05) {
                    Point p = cubicBezier(a, b, c, d, t);
                    if (lastPoint.isNull()) {
                        lastPoint = p;
                        continue;
                    }
                    float size = 12;
                    if (t > 0.95) {
                        size = 28;
                    }
                    fill(c1);
                    triangleOnLine(lastPoint, p, size, 1.5f);
                    fill(c2);
                    size /= 1.66f;
                    triangleOnLine(lastPoint, p, size, 1.5f);
                    fill(c3);
                    size /= 1.66f;
                    triangleOnLine(lastPoint, p, size, 1.5f);
                    lastPoint = p;
                }
            }
            save("yamata-no-orochi", name);
        });
    }

    private Point drawTo(Point p, Point q, float percent) {
        float dX = p.x() + percent * (q.x() - p.x());
        float dY = p.y() + percent * (q.y() - p.y());

        return new Point(dX, dY);
    }

    void triangleOnLine(Point p, Point q, float w, float s) {
        Line l = new Line(p, q);
        Vector base = new Vector(p);
        Vector move = new Vector(0, 0);
        move.setLength(w);
        move.setAngle(l.getAngle() + PI * 0.5f);
        base.addTo(move);

        Vector baseTwo = new Vector(p);
        Vector moveTwo = new Vector(0, 0);
        moveTwo.setLength(w);
        moveTwo.setAngle(l.getAngle() + PI * 1.5f);
        baseTwo.addTo(moveTwo);

        triangle(base.getPoint().x(), base.getPoint().y(), baseTwo.getPoint().x(), baseTwo.getPoint().y(), drawTo(p, q, s).x(), drawTo(p, q, s).y());
    }
}

