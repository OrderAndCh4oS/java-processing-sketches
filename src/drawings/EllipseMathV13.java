package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.CircleComparable;
import utilities.Point;

import java.util.ArrayList;
import java.util.Collections;

import static utilities.Ellipse.getEllipsePoint;

public class EllipseMathV13 extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.EllipseMathV13");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            blendMode(NORMAL);
            int c1 = colour.rand();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            background(c1);
            ArrayList<Integer> meshColours = new ArrayList<>();
            meshColours.add(c2);
//            blendMode(MULTIPLY);
            drawRaggedMeshBackground(meshColours, 1, 5, 255 * 0.4f, 1);
            float margin = 180;
            blendMode(NORMAL);
            fill(c1);
            stroke(c2);
            strokeWeight(2);
            strokeCap(MITER);
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            PGraphics source = createGraphics((int) (_width - margin), (int) (_height - margin));
            source.beginDraw();
            drawTextureToSourceArea(
                new Point(margin, margin),
                new Point(_width - margin, _height - margin),
                source,
                colour.black(),
                0.2f,
                0.2f
            );
            source.endDraw();
            image(source, 0, 0);
            blendMode(BLEND);
            noFill();
            strokeWeight(6);
            strokeCap(ROUND);
            Point centre = new Point(random(_width - 380) + 190, random(_height - 380) + 190);
            ArrayList<CircleComparable> circles = new ArrayList<>();
            for (int i = 0; i < 44; i++) {
                Point point = new Point();
                float r;
                boolean isIntersect;
                CircleComparable c;
                do {
                    isIntersect = false;
                    point.setX(map(random(100), 0, 100, 100, _width - 100));
                    point.setY(map(random(100), 0, 100, 100, _height - 100));
                    r = random(60, 90);
                    c = new CircleComparable(point, distanceTo(point, centre), r);
                    for (CircleComparable circle : circles) {
                        isIntersect = c.isIntersect(circle);
                        if (isIntersect) break;
                    }
                } while (distanceTo(point, centre) < 250 || isIntersect);
                circles.add(c);
            }
            circles.sort(Collections.reverseOrder());
            for (CircleComparable point : circles) {
                int c = colour.blackOrWhite();
                for (float t = TAU / 90; t <= TAU + TAU / 90; t += TAU / 90) {
                    Point ellipsePoint = getEllipsePoint(point.x(), point.y(), point.r(), point.r(), t);
                    stroke(c, 255 * 0.5f);
                    line(centre.x(), centre.y(), ellipsePoint.x(), ellipsePoint.y());
                }
            }

            save("ellipse-math-v13", name);
        });
    }
}

