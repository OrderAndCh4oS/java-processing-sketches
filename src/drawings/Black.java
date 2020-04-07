package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Light;
import utilities.Point;
import utilities.Polygon;

import java.util.ArrayList;
import java.util.TreeMap;

import static utilities.Map.*;

public class Black extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Black");
    }

    @Override
    public void settings() {
        _save = true;
        _width = 1024;
        _height = 1024;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 15; iter++) {
            _colours.getColours("plum", "lemon", "warm-grey", "warm-bw", "cool-bw").forEach((name, colour) -> {
                background(colour.bg());
                stroke(colour.rand(), 255 * 0.4f);
                for (int i = 0; i < _width; i++) {
                    for (int j = 0; j < _height; j++) {
                        if (random(1) < 0.1f) {
                            point(i, j);
                        }
                    }
                }
                PGraphics sourceImage = createGraphics((int) _width, (int) _height);
                Light light = new Light(_width / 2, _height / 2, _width, _height);
                for (int i = 0; i < 60; i++) {
                    float x = random(_width);
                    float y = random(_height);
                    ArrayList<Point> points = new ArrayList<>();
                    points.add(new Point(x, y));
                    points.add(new Point(x + random(15, 35), y + random(5)));
                    points.add(new Point(x + random(15, 35), y + random(15, 35)));
                    points.add(new Point(x + random(5), y + random(15, 35)));
                    light.addPolygon(new Polygon(points.toArray(new Point[0])));
                }

                TreeMap<Float, Point> sortedPoints = new TreeMap<>();
                for (Point p : light.rayTracer()) {
                    sortedPoints.put(light.getAngleTo(p), p);
                }

                PGraphics maskImage = createGraphics((int) _width, (int) _height);
                maskImage.beginDraw();
                maskImage.fill(0xff000000);
                maskImage.noStroke();
                maskImage.rect(0, 0, _width, _height);
                maskImage.fill(0xffffffff);
                maskImage.beginShape();
                for (Point p : sortedPoints.values()) {
                    maskImage.vertex(p.x(), p.y());
                }
                maskImage.endShape(CLOSE);
                maskImage.endDraw();

                sourceImage.beginDraw();
                sourceImage.fill(colour.bg());
                sourceImage.rect(0, 0, _width, _height);
                sourceImage.strokeCap(ROUND);
                sourceImage.strokeWeight(1);
                for (int i = 0; i < _width; i++) {
                    for (int j = 0; j < _height; j++) {
                        float distance = getDistance(light.getPoint(), new Point(i, j));
                        float density = map2(distance,0, _width / 1.5f,  0.1f, 1, CUBIC, EASE_IN);
                        sourceImage.stroke(colour.rand(), 255);
                        if (random(1) > density) {
                            sourceImage.point(i, j);
                        }
                    }
                }
                sourceImage.endDraw();
                sourceImage.mask(maskImage);

                image(sourceImage, 0, 0);
                noStroke();
                for (int i = 0; i < light.getPolygons().size(); i++) {
                    if (i == 0) continue;
                    fill(colour.rand());
                    beginShape();
                    for (Point p : light.getPolygons().get(i).getPoints()) {
                        vertex(p.x(), p.y());
                    }
                    endShape(CLOSE);
                }
                save("black", name);
            });
        }
    }

    private float getDistance(Point p, Point q) {
        float dX = p.x() - q.x();
        float dY = p.y() - q.y();

        return sqrt((dX * dX) + (dY * dY));
    }
}
