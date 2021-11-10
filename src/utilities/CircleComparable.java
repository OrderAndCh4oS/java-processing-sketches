package utilities;

import org.jetbrains.annotations.NotNull;

import static com.jogamp.opengl.math.FloatUtil.sqrt;

public class CircleComparable implements Comparable<CircleComparable> {
    private final Point _p;
    private final float _r;
    private final float _distance;

    public CircleComparable(Point p, float distance, float r) {
        _p = p;
        _distance = distance;
        _r = r;
    }

    public float distance() {
        return _distance;
    }

    public float x() {
        return _p.x();
    }

    public float y() {
        return _p.y();
    }

    public float r() {
        return _r;
    }

    public boolean isIntersect(CircleComparable b) {
        return this.distanceTo(b) < this._r + b.r() + 12;
    }

    public float distanceTo(CircleComparable b) {
        float dX = _p.x() - b.x();
        float dY = _p.y() - b.y();
        return sqrt((dX * dX) + (dY * dY));
    }

    @Override
    public int compareTo(@NotNull CircleComparable b) {
        return Float.compare(_distance, b.distance());
    }
}
