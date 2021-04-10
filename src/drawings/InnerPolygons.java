package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Ellipse;
import utilities.*;

import java.util.ArrayList;

public class InnerPolygons extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.InnerPolygons");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours("cool-bw", "constructivist-real", "constructivist", "candy").forEach((name, colour) -> {
            blendMode(BLEND);
            background(colour.white());
            ArrayList<Polygon> polygons = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                float x = random(_width);
                float y = random(_height);
                float r1 = random(_width + 450, _width + 700);
                float r2 = random(_width + 900, _width + 1350);
                ArrayList<Point> points = new ArrayList<>();
                for (float angle = 0; angle <= TAU; angle += TAU * 0.2f) {
                    points.add(Ellipse.getEllipsePoint(x, y, r1, r2, angle));
                }
                polygons.add(new Polygon(points.toArray(new Point[0])));
            }
            for (Polygon polygon : polygons) {
                for (int band = 0; band < 85; band++) {
                    polygon.scale(0.9f);
                    noStroke();
                    fill(colour.randWithBlack());
                    beginShape();
                    for (Point p : polygon.getPoints()) {
                        vertex(p.x(), p.y());
                    }
                    endShape(CLOSE);
                }
            }

            save("inner-polygons", name);
        });
    }

    private void drawLight(Light light, int c1) {
        for (Point point : light.rayTracer()) {
            stroke(c1);
            line(light.getPoint().x(), light.getPoint().y(), point.x(), point.y());
        }
    }
}
