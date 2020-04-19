package sketch;

import collections.ColourCollection;
import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import utilities.Point;

import static utilities.Map.*;
import static utilities.RandomString.uuid;
import static utilities.Trigonometry.getDistance;

abstract public class Sketch extends PApplet {
    protected float _width = 1500;
    protected float _height = 1500;
    protected boolean _save = false;
    protected ColourCollection _colours = new ColourCollection();
    protected Point _midPoint = new Point(_width / 2, _height / 2);

    public void settings(int width, int height) {
        _width = width;
        _height = height;
        _midPoint = new Point(_width / 2, _height / 2);
        size(width, height);
    }

    public void settings(int width, int height, String renderer) {
        _width = width;
        _height = height;
        _midPoint = new Point(_width / 2, _height / 2);
        size(width, height, renderer);
    }

    public void settings() {
        size((int) _width, (int) _height);
    }

    public void setup() {
        sketch();
        println("~~Fin~~");
    }

    public void draw() {

    }

    abstract public void sketch();

    public void save() {
        if (_save) saveImage();
    }

    public void saveImage() {
        String filename = uuid() + ".png";
        saveFrame("/Users/seancooper/Processing Stills/images/" + filename);
    }

    public void save(String prefix) {
        if (_save) saveImage(prefix);
    }

    public void save(String dir, String prefix) {
        if (_save) saveImage(dir, prefix);
    }

    public void saveImage(String prefix) {
        String filename = uuid() + ".png";
        saveFrame("/Users/seancooper/Processing Stills/images/" + prefix + "-" + filename);
    }

    public void saveImage(String dir, String prefix) {
        String filename = uuid() + ".png";
        saveFrame("/Users/seancooper/Processing Stills/images/" + dir + "/" + prefix + "-" + filename);
    }

    public void drawDepth(int colour, float density, float alpha) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (random(1) > density) {
                    point(x, y);
                }
            }
        }
    }

    public void drawDepth(int colour, float density, float alpha, float radius) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) > map2(distance, 0, radius, 0, density, QUADRATIC, EASE_IN)) {
                    point(x, y);
                }
            }
        }
    }

    public void drawDepth(int colour, float alpha, Direction direction, int type, int ease) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        float density;
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                switch (direction) {
                    case TOP:
                        density = map2(_height - y, 0, _height, 0.05f, 0.9f, type, ease);
                        break;
                    case RIGHT:
                        density = map2(x, 0, _width, 0.05f, 0.9f, type, ease);
                        break;
                    case BOTTOM:
                        density = map2(y, 0, _height, 0.05f, 0.9f, type, ease);
                        break;
                    case LEFT:
                        density = map2(_width - x, 0, _width, 0.05f, 0.9f, type, ease);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
                if (random(1) > density) {
                    point(x, y);
                }
            }
        }
    }

    public void drawDepthToSource(PGraphics source, int colour, float density, float alpha) {
        source.blendMode(MULTIPLY);
        source.stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        source.strokeCap(ROUND);
        source.strokeWeight(1);
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                if (random(1) > density) {
                    source.point(i, j);
                }
            }
        }
        source.blendMode(NORMAL);
    }

    public void drawDepthToSourceArea(Point p, Point q, PGraphics source, int colour, float density, float alpha) {
        source.blendMode(MULTIPLY);
        source.stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        source.strokeCap(ROUND);
        source.strokeWeight(1);
        for (int x = (int) p.x(); x <= (int) q.x(); x++) {
            for (int y = (int) p.y(); y <= (int) q.y(); y++) {
                if (random(1) > density) {
                    source.point(x, y);
                }
            }
        }
        source.blendMode(NORMAL);
    }
}
