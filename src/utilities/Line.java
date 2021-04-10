package utilities;

import java.util.Objects;

import static processing.core.PApplet.*;
import static utilities.Random.random;

public class Line {
    private final Point _p;
    private final Point _q;
    private float _length;

    public Line(Point p, Point q) {
        this._p = p;
        this._q = q;
    }

    public Line(Point p, float len, float angle) {
        _p = p;
        _length = len;
        Vector temp = new Vector(0, 0);
        temp.setLength(_length);
        temp.setAngle(angle);
        temp.addTo(new Vector(_p));
        _q = temp.getPoint();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return _p.equals(line._p) && _q.equals(line._q);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p, _q);
    }

    public Point getIntersect(Line l2) {
        float a1 = _p.y() - _q.y();
        float b1 = _q.x() - _p.x();
        float c1 = a1 * _q.x() + b1 * _q.y();

        float a2 = l2.p().y() - l2.q().y();
        float b2 = l2.q().x() - l2.p().x();
        float c2 = a2 * l2.q().x() + b2 * l2.q().y();

        float delta = a1 * b2 - a2 * b1;
        return new Point((b2 * c1 - b1 * c2) / delta, (a1 * c2 - a2 * c1) / delta);
    }

    public boolean isIntersect(Line l2) {
        Point intersect = getIntersect(l2);
        return intersect.isFinite() && onSegment(intersect) && l2.onSegment(intersect);
    }

    public boolean onSegment(Point r) {
        return r.x() <= max(p().x(), q().x()) && r.x() >= min(p().x(), q().x()) &&
                r.y() <= max(p().y(), q().y()) && r.y() >= min(p().y(), q().y());
    }

    public Point getMidPoint() {
        float dX = (_p.x() + _q.x()) / 2;
        float dY = (_p.y() + _q.y()) / 2;
        return new Point(dX, dY);
    }

    public Point getRandomPoint() {
        float t = random(1);
        float dX = _p.x() + t * (_q.x() - _p.x());
        float dY = _p.y() + t * (_q.y() - _p.y());
        return new Point(dX, dY);
    }

    private Point getPercentPoint(float percent) {
        float dX = _p.x() + percent * (_q.x() - _p.x());
        float dY = _p.y() + percent * (_q.y() - _p.y());
        return new Point(dX, dY);
    }

    public float getLength() {
        return sqrt(_p.x() * _q.x() + _p.y() * _q.y());
    }

    public float getAngle() {
        float dX = _p.x() - _q.x();
        float dY = _p.y() - _q.y();
        float theta = atan2(dY, dX);
        return theta + PI;
    }

    public boolean sharesSamePoint(Line l2) {
        return _p.equals(l2.p()) || _p.equals(l2.q()) || _q.equals(l2.p()) || _q.equals(l2.q());
    }

    public float maxX() {
        return max(_p.x(), _q.x());
    }

    public float maxY() {
        return max(_p.y(), _q.y());
    }

    public float minX() {
        return min(_p.x(), _q.x());
    }

    public float minY() {
        return min(_p.y(), _q.y());
    }

    public Point p() {
        return _p;
    }

    public Point q() {
        return _q;
    }
}
