package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Vector;

import java.util.ArrayList;

import static utilities.Map.*;

public class WildCard extends Sketch {
    ArrayList<Line> lines;

    public static void main(String... args) {
        PApplet.main("drawings.WildCard");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            lines = new ArrayList<>();
            Vector midV = new Vector(_midPoint);
            background(colour.bg());
            drawDepth(colour.rand(), 0.18f, Direction.TOP, CUBIC, EASE_IN);
            strokeCap(ROUND);
            for (int i = 0; i < random(800, 100000); i++) {
                strokeWeight(random(1,3));
                stroke(colour.rand());
                Vector v = new Vector(random(_width), random(_height));
                Line l = new Line(v.getPoint(), 2048, v.angleTo(midV) + random(PI/2) - PI/4);
                for (Line l2 : lines) {
                    if (l.isIntersect(l2)) {
                        l = new Line(l.p(), l.getIntersect(l2));
                    }
                }
                line(l.p().x(), l.p().y(), l.q().x(), l.q().y());
                lines.add(l);
            }
            save("wild-card", name);
        });
    }
}
