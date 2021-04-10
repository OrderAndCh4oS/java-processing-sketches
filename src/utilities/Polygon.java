package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static utilities.Trigonometry.getDistance;

public class Polygon {
    ArrayList<Point> _points = new ArrayList<>();
    ArrayList<Line> _lines = new ArrayList<>();

    public Polygon(Point... points) {
        _points.addAll(Arrays.asList(points));
        for (int i = 0; i < _points.size(); i++) {
            _lines.add(new Line(_points.get(i), _points.get((i + 1) % _points.size())));
        }
    }

    public static void main(String[] args) {
        Polygon polygon = new Polygon(
                new Point(1, 1),
                new Point(10, 1),
                new Point(10, 25),
                new Point(1, 25)
        );
        System.out.println("Line count: " + polygon.getLines().size());
        System.out.println("Point count: " + polygon.getPoints().size());
        for (Line line : polygon.getLines()) {
            System.out.println("Line: " + line.p().x() + ", " + line.p().y() + ", " + line.q().x() + ", " + line.q().y());
        }
    }

    public ArrayList<Point> getPoints() {
        return _points;
    }

    public ArrayList<Point> getReversedPoints() {
        return this.reverseArrayList(_points);
    }

    public ArrayList<Line> getLines() {
        return _lines;
    }

    public boolean isIntersect(Line line) {
        for (Line l : _lines) {
            if (l.isIntersect(line)) return true;
        }

        return false;
    }

    public HashMap<Line, Point> getIntersectsLineAndPoint(Line line) {
        HashMap<Line, Point> hashMap = new HashMap<>();
        for (Line l : _lines) {
            if (l.isIntersect(line)) {
                hashMap.put(l, l.getIntersect(line));
            }
        }

        return hashMap;
    }

    public ArrayList<Point> getIntersects(Line line) {
        ArrayList<Point> intersects = new ArrayList<>();
        for (Line l : _lines) {
            if (l.isIntersect(line)) {
                intersects.add(l.getIntersect(line));
            }
        }

        return intersects;
    }

    public Point getClosestIntersect(Point p, Point q) {
        Point closestIntersect = null;
        float minDistance = -1;
        Line line = new Line(p, q);
        Vector source = new Vector(p);
        for (Line l : this.getLines()) {
            if (l.isIntersect(line)) {
                Vector intersect = new Vector(l.getIntersect(line));
                float distance = source.distanceTo(intersect);
                if (distance < minDistance || minDistance == -1) {
                    closestIntersect = intersect.getPoint();
                    minDistance = distance;
                }
            }
        }

        if (closestIntersect == null) {
            return q;
        }

        return closestIntersect;
    }

    public Point getCenterPoint() {
        int totalX = 0, totalY = 0;
        for (Point p : _points){
            totalX += p.x();
            totalY += p.y();
        }
        int centerX = totalX / _points.size();
        int centerY = totalY / _points.size();
        return new Point(centerX, centerY);
    }

    public void scale(float value) {
        Point center = getCenterPoint();
        for(Point p : _points) {
            p.setX((p.x() - center.x()) * value + center.x());
            p.setY((p.y() - center.y()) * value + center.y());
        }
    }

    private ArrayList<Point> reverseArrayList(ArrayList<Point> list) {
        ArrayList<Point> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }

        return reversedList;
    }

}
