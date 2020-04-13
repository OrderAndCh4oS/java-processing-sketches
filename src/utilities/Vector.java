package utilities;

import static processing.core.PApplet.*;

public class Vector implements Cloneable {
    private Point _point;

    public Vector(float x, float y) {
        _point = new Point(x, y);
    }

    public Vector(Point point) {
        try {
            _point = point.clone();
        } catch (CloneNotSupportedException e) {
            println("Cloning Point not supported.");
        }
    }

    public float x() {
        return _point.x();
    }

    public void setX(float x) {
        _point.setX(x);
    }

    public float y() {
        return _point.y();
    }

    public void setY(float y) {
        _point.setY(y);
    }

    public Point getPoint() {
        return _point;
    }

    public void setPoint(Point point) {
        _point = point;
    }

    public float getAngle() {
        return atan2(_point.y(), _point.x());
    }

    public void setAngle(float angle) {
        float _length = getLength();
        _point.setX(cos(angle) * _length);
        _point.setY(sin(angle) * _length);
    }

    public float getLength() {
        return sqrt(
            _point.x() * _point.x() +
                _point.y() * _point.y()
        );
    }

    public void setLength(float _length) {
        float angle = this.getAngle();
        _point.setX(cos(angle) * _length);
        _point.setY(sin(angle) * _length);
    }

    public Vector addVector(Vector v2) {
        return new Vector(_point.x() + v2.x(), _point.y() + v2.y());
    }

    public Vector subtractVector(Vector v2) {
        return new Vector(_point.x() - v2.x(), _point.y() - v2.y());
    }

    public Vector multiply(float value) {
        return new Vector(_point.x() * value, _point.y() * value);
    }

    public Vector divide(float value) {
        return new Vector(_point.x() / value, _point.y() / value);
    }

    public void addTo(Vector v2) {
        _point.setX(_point.x() + v2.x());
        _point.setY(_point.y() + v2.y());
    }

    public void subtractFrom(Vector v2) {
        _point.setX(_point.x() - v2.x());
        _point.setY(_point.y() - v2.y());
    }

    public void multiplyBy(float value) {
        _point.setX(_point.x() * value);
        _point.setY(_point.y() * value);
    }

    public void divideBy(float value) {
        _point.setX(_point.x() / value);
        _point.setY(_point.y() / value);
    }

    public float angleTo(Vector v2) {
        return atan2(
            v2.y() - _point.y(),
            v2.x() - _point.x()
        );
    }

    public void setAngleTo(Vector v2) {
        this.setAngle(this.angleTo(v2));
    }


    public void setLengthTo(Vector v2) {
        this.setLength(this.distanceTo(v2));
    }

    public float distanceTo(Vector v2) {
        float dX = _point.x() - v2.x();
        float dY = _point.y() - v2.y();
        return sqrt((dX * dX) + (dY * dY));
    }

    public Vector clone() throws CloneNotSupportedException {
        return (Vector) super.clone();
    }
}
