package utilities;

import com.sun.javafx.geom.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

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
}
