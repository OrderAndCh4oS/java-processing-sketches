package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import sketch.Sketch;
import utilities.Light;
import utilities.Point;
import utilities.Polygon;
import utilities.Vector;

import java.util.ArrayList;
import java.util.TreeMap;

import static utilities.Map.*;

public class RaytraceText extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.RaytraceText");
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
                background(colour.black());
                fill(colour.white());
                translate(_width / 2, _height / 2);
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
