package drawings;

import processing.core.PApplet;
import processing.core.PShape;
import sketch.Sketch;
import utilities.Ellipse;
import utilities.Light;
import utilities.Point;
import utilities.Polygon;

import java.util.ArrayList;

public class InnerPolygonsV2 extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.InnerPolygonsV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for(int iter = 0; iter < 10; iter++) {
            _colours.getColours("constructivist-real").forEach((name, colour) -> {
                blendMode(BLEND);
                background(colour.white());
                drawBenDayTexture(0, 0, _width, _height, colour.rand(), 12, 0.5f);
                ArrayList<Polygon> polygons = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    float x = random(_width / 2) + _width / 4;
                    float y = random(_height / 2) + _height / 4;
                    float r1 = random(_width * 1.25f, _width * 1.5f);
                    float r2 = random(_width * 1.75f, _width * 2f);
                    ArrayList<Point> points = new ArrayList<>();
                    for (float angle = 0; angle <= TAU; angle += TAU * 0.15f) {
                        points.add(Ellipse.getEllipsePoint(x, y, r1, r2, angle));
                    }
                    polygons.add(new Polygon(points.toArray(new Point[0])));
                }
                for (Polygon polygon : polygons) {
                    for (int band = 0; band < 30; band++) {
                        PShape s = createShape();
                        polygon.scale(0.85f);
                        noStroke();
                        fill(colour.randWithBlack());
                        s.beginShape();
                        for (Point p : polygon.getPoints()) {
                            s.vertex(p.x(), p.y());
                        }
                        polygon.scale(0.85f);
                        s.beginContour();
                        for (Point p : polygon.getReversedPoints()) {
                            s.vertex(p.x(), p.y());
                        }
                        s.endContour();
                        s.endShape(CLOSE);
                        shape(s);
                    }
                }

                save("inner-polygons-v2", name);
            });
        }
    }

    private void drawLight(Light light, int c1) {
        for (Point point : light.rayTracer()) {
            stroke(c1);
            line(light.getPoint().x(), light.getPoint().y(), point.x(), point.y());
        }
    }
}
