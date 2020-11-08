package utilities;

import java.util.Comparator;

public class PointVerticalComparator implements Comparator<Point> {
    public int compare(Point a, Point b) {
        return Float.compare(a.y(), b.y());
    }
}
