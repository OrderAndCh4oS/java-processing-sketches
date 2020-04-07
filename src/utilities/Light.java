package utilities;

import java.util.ArrayList;

import static processing.core.PApplet.*;

/**
 * https://ncase.me/sight-and-light/
 */
public class Light {
    Vector _source;
    ArrayList<Polygon> _polygons = new ArrayList<>();

    public Light(float x, float y, float sceneWidth, float sceneHeight) {
        _source = new Vector(x, y);
        Polygon sceneBounds = new Polygon(
                new Point(0, 0),
                new Point(sceneWidth, 0),
                new Point(sceneWidth, sceneHeight),
                new Point(0, sceneHeight)
        );
        _polygons.add(sceneBounds);
    }

    public Light(Point p) {
        _source = new Vector(p);
    }

    public static void main(String[] args) {
        Light l = new Light(11, 11, 30, 30);
        l.addPolygon(new Polygon(
                new Point(1, 1),
                new Point(10, 1),
                new Point(10, 25),
                new Point(1, 25)
        ));
        ArrayList<Point> points = l.rayTracer();
        for (Point p : points) {
            System.out.println(p.x() + " " + p.y());
        }
    }

    public Vector getSource() {
        return _source;
    }

    public Point getPoint() {
        return _source.getPoint();
    }

    public ArrayList<Polygon> getPolygons() {
        return _polygons;
    }

    public void addPolygon(Polygon polygon) {
        _polygons.add(polygon);
    }

    public ArrayList<Point> rayTracer() {
        ArrayList<Point> intersects = new ArrayList<>();
        for (Polygon polygon : _polygons) {
            for (Point point : polygon.getPoints()) {
                Point closestIntersect = getClosestIntersect(point);
                intersects.add(closestIntersect);
                float offset = 0.00001f;
                float length = 2000;
                Point closestOffsetIntersectOne = getClosestIntersect(getOffsetPoint(point, offset, length));
                Point closestOffsetIntersectTwo = getClosestIntersect(getOffsetPoint(point, -offset, length));
                intersects.add(closestOffsetIntersectOne);
                intersects.add(closestOffsetIntersectTwo);
            }
        }

        return intersects;
    }

    private Point getOffsetPoint(Point point, float offset, float length) {
        float angle = getAngleTo(point) + offset;
        float x = _source.x() + cos(angle) * length;
        float y = _source.y() + sin(angle) * length;
        return new Point(x, y);
    }

    public float getAngleTo(Point point) {
        return atan2(_source.y() - point.y(), _source.x() - point.x()) + PI;
    }

    public Point getClosestIntersect(Point point) {
        Point closestIntersect = null;
        float minDistance = -1;
        Line rayTrace = new Line(_source.getPoint(), point);
        for (Polygon polygon : _polygons) {
            for (Line l : polygon.getLines()) {
                if (l.isIntersect(rayTrace)) {
                    Vector intersect = new Vector(l.getIntersect(rayTrace));
                    float distance = _source.distanceTo(intersect);
                    if (distance < minDistance || minDistance == -1) {
                        closestIntersect = intersect.getPoint();
                        minDistance = distance;
                    }
                }
            }
        }

        if (closestIntersect == null) {
            return point;
        }

        return closestIntersect;
    }
}
