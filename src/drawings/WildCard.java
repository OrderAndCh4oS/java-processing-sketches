package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Vector;

import java.util.ArrayList;

import static utilities.Map.CUBIC;
import static utilities.Map.EASE_IN;

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
        for(int iter = 0; iter < 10; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                strokeCap(ROUND);
                int c1 = colour.randAll();
                int c2;
                do {
                    c2 = colour.randAll();
                } while (c1 == c2);
                int c3 = colour.randAll();
                if (colour.getAll().size() > 2) {
                    do {
                        c3 = colour.randAll();
                    } while (c1 == c3 || c2 == c3);
                }
                background(c1);
                drawTexture(colour.black(), 0.18f, Direction.TOP, CUBIC, EASE_IN);
                drawBenDayTexture(c2, random(12, 24), random(0.66f, 0.85f));
                stroke(c3);
                drawLines();
                save("wild-card", name);
            });
        }
    }

    private void drawLines() {
        lines = new ArrayList<>();
        Vector midV = new Vector(_midPoint);
        for (int i = 0; i < random(800, 100000); i++) {
            strokeWeight(random(4, 16));
            Vector v = new Vector(random(_width), random(_height));
            Line l = new Line(v.getPoint(), 2048, v.angleTo(midV) + random(PI / 2) - PI / 4);
            for (Line l2 : lines) {
                if (l.isIntersect(l2)) {
                    l = new Line(l.p(), l.getIntersect(l2));
                }
            }
            line(l.p().x(), l.p().y(), l.q().x(), l.q().y());
            lines.add(l);
        }
    }
}
