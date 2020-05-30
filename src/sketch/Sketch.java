package sketch;

import collections.ColourCollection;
import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import utilities.Point;
import utilities.colour.Colours;

import static utilities.Map.*;
import static utilities.Random.randomInt;
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

    public void drawTexture(int colour, float density, float alpha) {
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (random(1) < density) {
                    stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    point(x, y);
                }
            }
        }
    }

    public void drawPerlinTexture(int colour, float density, float alpha, float scale, float roughness) {
        strokeCap(ROUND);
        strokeWeight(1);
        noiseSeed((long) random(100));
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (noise(x * scale, y * scale) < density) {
                    stroke(colour, 255 * random(alpha - roughness, alpha + roughness));
                    point(x, y);
                }
            }
        }
    }

    public void drawPerlinAlpha(int colour, float density, float scale) {
        strokeCap(ROUND);
        strokeWeight(1);
        noiseSeed((long) random(100));
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (random(1) < density) {
                    stroke(colour, 255 * noise(x * scale, y * scale));
                    point(x, y);
                }
            }
        }
    }

    public void drawWaveTexture(Colours colour, float wave, float waveHeight, float stepY, float stepX) {
        stroke(colour.black());
        strokeWeight(0.75f);
        noFill();
        for (int y = -10; y < _height + 10; y += stepY) {
            float a = 0;
            beginShape();
            for (int x = 0; x < _width; x += stepX) {
                float dY = sin(a) * waveHeight;
                float newY = dY - dY / 2;
                curveVertex(x, y + newY);
                a += wave;
            }
            endShape();
        }
    }

    public void drawLineTexture(int colour, float step, float strokeWidth, Direction direction) {
        stroke(colour);
        strokeWeight(strokeWidth);
        switch (direction) {
            case TOP:
            case BOTTOM:
                for (int i = 0; i < _height; i++) {
                    float y = i * step;
                    line(0, y, _width, y);
                }
                break;
            case LEFT:
            case RIGHT:
                for (int i = 0; i < _width; i++) {
                    float x = i * step;
                    line(x, 0, x, _height);
                }
                break;
        }
    }

    public void drawLineTextureWithGaps(int colour, float step, float strokeWidth, Direction direction) {
        stroke(colour);
        strokeWeight(strokeWidth);
        strokeCap(ROUND);
        boolean on = true;
        switch (direction) {
            case TOP:
            case BOTTOM:
                for (int i = -12; i < _height + 12; i++) {
                    float lastX = 0;
                    for (int x = 0; x < _width; x += on ? randomInt(4, 12) : randomInt(3, 6)) {
                        float y = i * step;
                        if (on) line(lastX, y, x, y);
                        on = !on;
                        lastX = x;
                    }
                }
                break;
            case LEFT:
            case RIGHT:
                for (int i = 0; i < _width; i++) {
                    float lastY = 0;
                    for (int y = 0; y < _height; y += on ? randomInt(4, 12) : randomInt(3, 6)) {
                        float x = i * step;
                        if (on) line(x, lastY, x, y);
                        on = !on;
                    }
                }
                break;
        }
    }

    public void drawTexture(int colour, float density, float alpha, float radius) {
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) < map2(distance, 0, radius, 0, density, QUADRATIC, EASE_IN)) {
                    stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    point(x, y);
                }
            }
        }
    }

    public void drawBenDayTexture(int colour, float diameter, float scale) {
        fill(colour);
        noStroke();
        int rowIndex = 0;
        for (float x = -diameter; x < _width + diameter; x += diameter) {
            for (float y = rowIndex % 2 == 0 ? 0 : -(diameter / 2f); y < _height + diameter; y += diameter) {
                ellipse(x, y, diameter * scale, diameter * scale);
            }
            rowIndex++;
        }
    }

    public void drawBenDayTexture(float x, float y, float w, float h, int colour, float diameter, float scale) {
        fill(colour);
        noStroke();
        int rowIndex = 0;
        float xEnd = x + w + diameter;
        for (float i = x - diameter; i < xEnd; i += diameter) {
            float yEnd = y + h + diameter + (diameter / 2f);
            for (float j = y + (rowIndex % 2 == 0 ? 0 : -(diameter / 2f)); j < yEnd; j += diameter) {
                ellipse(i, j, diameter * scale, diameter * scale);
            }
            rowIndex++;
        }
    }

    public void drawFibreTexture(int colour, int strands, float weight, float alpha) {
        strokeCap(ROUND);
        strokeWeight(weight);
        for (int i = 0; i < strands; i++) {
            float x = randomGaussian() > 0.5 ? randomGaussian() * _width : _width - (randomGaussian() * _width);
            float y = randomGaussian() > 0.5 ? randomGaussian() * _height : _height - (randomGaussian() * _height);
            pushMatrix();
            noFill();
            stroke(colour, 255 * (((randomGaussian() * 0.2f) - 0.1f) * alpha));
            rotate(randomGaussian() * (TAU - 0.0000001f));
            beginShape();
            for (int steps = randomInt(12, 18); steps > 0; steps--) {
                curveVertex(x, y);
                float xRand = randomGaussian();
                x += xRand > 0.1f ? random(1, 3) : 0;
                y += randomGaussian() < 0.33333f
                        ? 0
                        : randomGaussian() < 0.5f
                        ? random(0.1f, 2)
                        : -random(0.1f, 2);
            }
            endShape();
            popMatrix();
        }
    }

    public void drawTexture(int colour, float density, float alpha, float radius, int type, int ease) {
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) < map2(distance, 0, radius, 0, density, type, ease)) {
                    stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    point(x, y);
                }
            }
        }
    }

    public void drawTexture(int colour, float alpha, Direction direction, int type, int ease) {
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
                if (random(1) < density) {
                    stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    point(x, y);
                }
            }
        }
    }

    public void drawTextureToSource(PGraphics source, int colour, float density, float alpha) {
        source.blendMode(MULTIPLY);
        source.strokeCap(ROUND);
        source.strokeWeight(1);
        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                if (random(1) < density) {
                    source.stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    source.point(i, j);
                }
            }
        }
        source.blendMode(NORMAL);
    }

    public void drawTextureToSourceArea(Point p, Point q, PGraphics source, int colour, float density, float alpha) {
        source.blendMode(MULTIPLY);
        source.strokeCap(ROUND);
        source.strokeWeight(1);
        for (int x = (int) p.x(); x <= (int) q.x(); x++) {
            for (int y = (int) p.y(); y <= (int) q.y(); y++) {
                if (random(1) < density) {
                    source.stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    source.point(x, y);
                }
            }
        }
        source.blendMode(NORMAL);
    }

    public void drawRadialTextureToSource(PGraphics source, int colour, float density, float alpha, float radius, int type, int ease) {
        source.strokeCap(ROUND);
        source.strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) < map2(distance, 0, radius, 0, density, type, ease)) {
                    source.stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    source.point(x, y);
                }
            }
        }
    }

    public float distanceToMidpoint(float x, float y) {
        float dX = _midPoint.x() - x;
        float dY = _midPoint.y() - y;

        return sqrt((dX * dX) + (dY * dY));
    }

    public float distanceTo(float aX, float aY, float bX, float bY) {
        float dX = aX - bX;
        float dY = aY - bY;

        return sqrt((dX * dX) + (dY * dY));
    }

    public float distanceTo(Point a, Point b) {
        float dX = a.x() - b.x();
        float dY = b.y() - b.y();

        return sqrt((dX * dX) + (dY * dY));
    }
}
