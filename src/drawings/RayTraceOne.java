package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Light;
import utilities.Line;
import utilities.Point;
import utilities.Polygon;

import java.util.ArrayList;

public class RayTraceOne extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.RayTraceOne");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        Light light = new Light(10, 10, _width, _height);
        for (int i = 0; i < 30; i++) {
            float x = random(_width);
            float y = random(_height);
            float min = random(15, 25);
            float max = random(35, 40);
            ArrayList<Point> points = new ArrayList<>();
            points.add(new Point(x, y));
            points.add(new Point(x + random(min, max), y + random(min, max)));
            points.add(new Point(x + random(min, max), y + random(min, max)));
            light.addPolygon(new Polygon(points.toArray(new Point[0])));
        }

        for (Polygon polygon : light.getPolygons()) {
            for (Line line : polygon.getLines()) {
                line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
            }
        }

        stroke(0xffff0000);

        for (Point point : light.rayTracer()) {
            line(light.getPoint().x(), light.getPoint().y(), point.x(), point.y());
        }
    }
}
