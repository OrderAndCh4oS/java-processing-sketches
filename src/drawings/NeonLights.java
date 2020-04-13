package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Light;
import utilities.Point;
import utilities.Polygon;
import utilities.Vector;
import utilities.colour.Colours;

import java.util.ArrayList;
import java.util.TreeMap;

import static utilities.Map.*;

public class NeonLights extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.NeonLights");
    }

    @Override
    public void settings() {
        _save = true;
        _width = 1024;
        _height = 1024;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 5; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                background(colour.bg());
                stroke(colour.rand(), 255 * 0.4f);
                for (int i = 0; i < _width; i++) {
                    for (int j = 0; j < _height; j++) {
                        if (random(1) < 0.1f) {
                            point(i, j);
                        }
                    }
                }
                int c1 = colour.rand();
                int c2 = colour.rand();
                while (c2 == c1) {
                    c2 = colour.rand();
                }
                Light light = new Light(_width * 0.66f, _height * 0.66f, _width, _height);
                Light lightTwo = new Light(_width * 0.33f, _height * 0.33f, _width, _height);

                for (int i = 0; i < 60; i++) {
                    float x = random(_width);
                    float y = random(_height);
                    ArrayList<Point> points = new ArrayList<>();
                    points.add(new Point(x, y));
                    points.add(new Point(x + random(15, 35), y + random(5)));
                    points.add(new Point(x + random(15, 35), y + random(15, 35)));
                    points.add(new Point(x + random(5), y + random(15, 35)));
                    light.addPolygon(new Polygon(points.toArray(new Point[0])));
                    lightTwo.addPolygon(new Polygon(points.toArray(new Point[0])));
                }

                drawLight(light, c1);
                drawLight(lightTwo, c2);

                noStroke();
                for (int i = 0; i < light.getPolygons().size(); i++) {
                    if (i == 0) continue;
                    fill(colour.rand());
                    beginShape();
                    for (Point p : light.getPolygons().get(i).getPoints()) {
                        vertex(p.x(), p.y());
                    }
                    endShape(CLOSE);
                }
                save("neon-lights", name);
            });
        }
    }

    private void drawLight(Light light, int c1) {
        TreeMap<Float, Point> sortedPoints = new TreeMap<>();
        for (Point p : light.rayTracer()) {
            sortedPoints.put(light.getAngleTo(p), p);
        }
        Polygon litArea = new Polygon(sortedPoints.values().toArray(new Point[0]));
        strokeCap(ROUND);
        strokeWeight(1);
        float angle = 0.0001f;
        while (angle < TAU) {
            Vector lightSource = new Vector(light.getPoint());
            Vector lightEnd = new Vector(light.getPoint());
            Vector v1 = new Vector(1, 1);
            float distanceToEnd = _width * 2f;
            v1.setAngle(angle);
            v1.setLength(distanceToEnd);
            lightEnd.addTo(v1);
            Vector intersect = new Vector(litArea.getClosestIntersect(light.getPoint(), lightEnd.getPoint()));
            float distanceToIntersect = lightSource.distanceTo(intersect);
            float currentDistance = lightSource.distanceTo(lightEnd);
            while (distanceToIntersect >= 2 && currentDistance > 1) {
                distanceToIntersect = lightSource.distanceTo(intersect);
                Vector v2 = new Vector(lightEnd.getPoint());
                v2.subtractFrom(lightSource);
                v2.divideBy(lightSource.distanceTo(lightEnd));
                lightSource.addTo(v2);
                stroke(c1, (random(0.2f) + 0.6f) * 255);
                float density = map2(currentDistance, 0, distanceToEnd, 0.1f, 1f, CUBIC, EASE_IN);
                if (random(1) < density) {
                    point(lightSource.x(), lightSource.y());
                }
                currentDistance = lightSource.distanceTo(lightEnd);
            }
            angle += TAU / 360;
        }
    }
}
