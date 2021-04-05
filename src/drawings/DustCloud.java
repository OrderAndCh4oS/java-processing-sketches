package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Vector;

import java.util.ArrayList;

import static utilities.Ellipse.getEllipsePoint;

public class DustCloud extends Sketch {
    ArrayList<Line> lines;

    public static void main(String... args) {
        PApplet.main("drawings.DustCloud");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600, P2D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            lines = new ArrayList<>();
            background(colour.black());
            blendMode(SCREEN);
            noFill();
            strokeWeight(1);
            for (int i = 0; i < 25; i++) {
                stroke(colour.white());
                randomWalkTowardPoint(_width / 2, _height / 2, random(_width), random(_height));
            }
            save("dust-cloud", name);
        });
    }

    private void randomWalkTowardPoint(float x, float y, float r, float t) {
        try {
            Vector c = new Vector(_width / 2, _height / 2);
            Vector v = new Vector(getEllipsePoint(x, y, r, t));
            Point lastPoint = new Point();
            Vector v2 = new Vector(0, 0);
            v2.addTo(v);
            v2.setAngleTo(c);
            v2.setAngle(v2.getAngle() + PI * 0.5f);
            v2.setLength(5);
            ArrayList<Line> path = new ArrayList<>();
            beginShape();
            while (v.distanceTo(new Vector(_width / 2, _height / 2)) > 80) {
                boolean isIntersect = false;
                v.addTo(tendTowardCenter(v));
                v.addTo(v2);
                if (!lastPoint.isNull()) {
                    Line line = new Line(lastPoint, v.getPoint().clone());
                    for (Line otherLine : lines) {
                        isIntersect = line.isIntersect(otherLine);
                        if (isIntersect) break;
                    }
                    if (isIntersect) break;
                    path.add(line);
                }
                lastPoint = v.getPoint().clone();
                curveVertex(v.getPoint().x(), v.getPoint().y());
            }
            endShape();
            lines.addAll(path);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private Vector tendTowardCenter(Vector v1) {
        Vector vMod = new Vector(_width / 2, _height / 2);
        vMod.subtractFrom(v1);
        vMod.divideBy(5);
        return vMod;
    }
}

