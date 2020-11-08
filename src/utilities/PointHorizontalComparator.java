package utilities;

import java.util.Comparator;

public class PointHorizontalComparator implements Comparator<Point> {
    public int compare(Point a, Point b) {
        return Float.compare(a.x(), b.x());
    }
}
