package sketch;

import collections.ColourCollection;
import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;

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
//        exit();
    }

    public void draw() {}

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

    public void drawTexture3d(int colour, float density, float alpha) {
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = -750; x < _width + 750; x++) {
            for (int y = -750; y < _height + 750; y++) {
                if (random(1) < density) {
                    stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    point(x, y, -1000);
                }
            }
        }
    }

    public void drawTexture3d(int colour, float density, float alpha, float radius) {
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = -750; x < _width + 750; x++) {
            for (int y = -750; y < _height + 750; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) < map2(distance, 0, radius, 0, density, QUADRATIC, EASE_IN)) {
                    stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    point(x, y, -1000);
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

    public void drawWaveTexture(Colours colour, float wave, float waveHeight, float stepY, float stepX, float alpha) {
        stroke(colour.black(), alpha);
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

    public void drawWaveTexture(int colour, float wave, float waveHeight, float stepY, float stepX, float alpha) {
        stroke(colour, alpha);
        strokeWeight(1f);
        noFill();
        for (int y = -10; y < _height + 10; y += stepY) {
            float a = 0;
            beginShape();
            for (int x = -10; x < _width + 10; x += stepX) {
                float dY = sin(a) * waveHeight;
                float newY = dY - dY / 2;
                curveVertex(x, y + newY);
                a += wave;
            }
            endShape();
        }
    }

    public void drawWaveTexture(int colour, float gap, float strokeWeight, float wave, float waveHeight, float stepY, float stepX, float alpha) {
        stroke(colour, alpha);
        strokeWeight(strokeWeight);
        noFill();
        for (int y = -10; y < _height + gap; y += stepY) {
            float a = 0;
            beginShape();
            for (int x = -10; x < _width + 10; x += stepX) {
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
                for (int i = -50; i < _height + 50; i++) {
                    float lastX = 0;
                    for (int x = 0; x < _width + 50; x += on ? randomInt(4, 12) : randomInt(3, 6)) {
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

    public void drawLineTextureWithGapsThick(int colour, float step, float strokeWidth, Direction direction) {
        stroke(colour);
        strokeWeight(strokeWidth);
        strokeCap(ROUND);
        boolean on = true;
        switch (direction) {
            case TOP:
            case BOTTOM:
                for (int i = -50; i < _height + 50; i++) {
                    float lastX = 0;
                    for (int x = -50; x < _width + 50; x += on ? randomInt(8, 16) : randomInt(8, 12)) {
                        float y = i * step;
                        if (on) line(lastX, y, x, y);
                        on = !on;
                        lastX = x;
                    }
                }
                break;
            case LEFT:
            case RIGHT:
                for (int i = -50; i < _width + 50; i++) {
                    float lastY = 0;
                    for (int y = -50; y < _height + 50; y += on ? randomInt(4, 12) : randomInt(3, 6)) {
                        float x = i * step;
                        if (on) line(x, lastY, x, y);
                        on = !on;
                    }
                }
                break;
        }
    }

    public void drawLineTextureWithGapsAlternate(ArrayList<Integer> colours, float step, float strokeWidth, Direction direction) {
        strokeWeight(strokeWidth);
        strokeCap(ROUND);
        boolean on = true;
        int index = 0;
        switch (direction) {
            case TOP:
            case BOTTOM:
                for (int i = -50; i < _height + 50; i++) {
                    float lastX = 0;
                    for (int x = -50; x < _width + 50; x += on ? randomInt(8, 16) : randomInt(8, 12)) {
                        float y = i * step;
                        if (on) {
                            stroke(colours.get(index % colours.size()));
                            line(lastX, y, x, y);
                            index++;
                        }
                        on = !on;
                        lastX = x;
                    }
                }
                break;
            case LEFT:
            case RIGHT:
                for (int i = -50; i < _width + 50; i++) {
                    float lastY = 0;
                    for (int y = -50; y < _height + 50; y += on ? randomInt(4, 12) : randomInt(3, 6)) {
                        float x = i * step;
                        if (on) {
                            stroke(colours.get(index % colours.size()));
                            line(x, lastY, x, y);
                            index++;
                        }
                        on = !on;
                    }
                }
                break;
        }
    }

    public void drawRaggedMeshBackground(ArrayList<Integer> colour, float strokeWidth, float gap, float alpha, float wobbleMagnitude) {
        strokeWeight(strokeWidth);
        noFill();
        float offsetX = 0;
        float offsetY = 0;
        ArrayList<ArrayList<Point>> verticalThreads = new ArrayList<>();
        ArrayList<ArrayList<Point>> horizontalThreads = new ArrayList<>();
        for (int y = 0; y < (_height / gap); y++) {
            ArrayList<Point> verticalThread = new ArrayList<>();
            for (int x = -40; x < (_width / gap) + 10; x++) {
                verticalThread.add(new Point(x * gap, y * gap));
            }
            verticalThreads.add(verticalThread);
        }
        for (int x = 0; x < (_width / gap); x++) {
            ArrayList<Point> horizontalThread = new ArrayList<>();
            for (int y = -20; y < (_height / gap) + gap; y++) {
                horizontalThread.add(new Point(x * gap, y * gap));
            }
            horizontalThreads.add(horizontalThread);
        }
        int i = 0;
        for (ArrayList<Point> t : verticalThreads) {
            stroke(colour.get(i % colour.size()), alpha);
            beginShape();
            for (Point p : t) {
                curveVertex(offsetX + p.x(), offsetY + p.y() + wobble() * wobbleMagnitude * random(1));
            }
            endShape();
            i++;
        }
        i = 0;
        for (ArrayList<Point> t : horizontalThreads) {
            stroke(colour.get(i % colour.size()), alpha);
            beginShape();
            for (Point p : t) {
                curveVertex(offsetX + p.x() + wobble() * wobbleMagnitude * random(1), offsetY + p.y());
            }
            endShape();
            i++;
        }
    }

    float wobble() {
        return random(1) > 0.75 ? 0 : random(1) > 0.5 ? -random(1) : random(1);
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

    public void drawBenDayTexture(float x, float y, float w, float h, int colour, float diameter, float scale, float alpha) {
        fill(colour, alpha);
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

    public void drawBenDayTexturePerlin(float x, float y, float w, float h, int colour, float diameter) {
        fill(colour);
        noStroke();
        int rowIndex = 0;
        float xEnd = x + w + diameter;
        float min = 0.2f;
        float noiseScale = 0.02f;
        float dotSize = 0.8f;
        noiseSeed((long) random(1, 100000));
        for (float i = x - diameter; i < xEnd; i += diameter) {
            float yEnd = y + h + diameter + (diameter / 2f);
            for (float j = y + (rowIndex % 2 == 0 ? 0 : -(diameter / 2f)); j < yEnd; j += diameter) {
                float scale = noise(i * noiseScale * dotSize, j * noiseScale * dotSize) + min;
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


    public void drawGrainTexture(int colour, float density, float radius, int type, int ease) {
        strokeCap(ROUND);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) > map2(distance, 0, radius, 0, density, type, ease)) {
                    stroke(colour, 255);
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
                    source.stroke(colour, 255 * random(max(alpha - 0.1f, 0), min(alpha + 0.1f, 1)));
                    source.point(i, j);
                }
            }
        }
        source.blendMode(NORMAL);
    }

    public void drawTexture(PGraphics source, int colour, float alpha, Direction direction, int type, int ease) {
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
                    source.stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
                    source.point(x, y);
                }
            }
        }
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

    public void square(float x, float y, float width, float height, Direction direction, int type, int ease, int colour) {
        for (float i = x; i < x + width; i++) {
            for (float j = y; j < y + height; j++) {
                float iX = i - x;
                float jY = j - y;
                float density;
                switch (direction) {
                    case TOP:
                        density = map2(height - jY, 0, height, 0.05f, 0.9f, type, ease);
                        break;
                    case RIGHT:
                        density = map2(iX, 0, width, 0.05f, 0.9f, type, ease);
                        break;
                    case BOTTOM:
                        density = map2(jY, 0, height, 0.05f, 0.9f, type, ease);
                        break;
                    case LEFT:
                        density = map2(width - iX, 0, width, 0.05f, 0.9f, type, ease);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
                stroke(colour, random(0.8f, 1) * 255);
                if (random(1) > density) {
                    point(i, j);
                }
            }
        }
    }

    public void squareToSource(PGraphics source, float x, float y, float width, float height, Direction direction, int type, int ease, int colour) {
        for (float i = x; i < x + width; i++) {
            for (float j = y; j < y + height; j++) {
                float iX = i - x;
                float jY = j - y;
                float density;
                switch (direction) {
                    case TOP:
                        density = map2(height - jY, 0, height, 0.05f, 0.9f, type, ease);
                        break;
                    case RIGHT:
                        density = map2(iX, 0, width, 0.05f, 0.9f, type, ease);
                        break;
                    case BOTTOM:
                        density = map2(jY, 0, height, 0.05f, 0.9f, type, ease);
                        break;
                    case LEFT:
                        density = map2(width - iX, 0, width, 0.05f, 0.9f, type, ease);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + direction);
                }
                source.stroke(colour, random(0.8f, 1) * 255);
                if (random(1) > density) {
                    source.point(i, j);
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
        float dY = a.y() - b.y();

        return sqrt((dX * dX) + (dY * dY));
    }
}
