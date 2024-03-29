package utilities;

import java.util.Comparator;
import java.util.Objects;

public class Point implements Cloneable {
    private float _x;
    private float _y;
    private boolean _isNull = false;

    public Point() {
        _isNull = true;
    }

    public Point(float x, float y) {
        _x = x;
        _y = y;
        _isNull = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point._x, _x) == 0 && Float.compare(point._y, _y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y);
    }

    public void setX(float x) {
        _x = x;
    }

    public void setY(float y) {
        _y = y;
    }

    public float x() {
        return _x;
    }

    public float y() {
        return _y;
    }

    public boolean isFinite() {
        return !Float.isInfinite(_x) && !Float.isInfinite(_y);
    }

    public boolean isNull() {
        return _isNull;
    }

    public void setIsNull(boolean _isNull) {
        this._isNull = _isNull;
    }

    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }
}
