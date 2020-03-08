package utilities;

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

    public Point clone() throws CloneNotSupportedException {
        return (Point) super.clone();
    }
}
