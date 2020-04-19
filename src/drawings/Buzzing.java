package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

/**
 * http://www.vergenet.net/~conrad/boids/pseudocode.html
 */
public class Buzzing extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Buzzing");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.bg());
            pushMatrix();
            translate(_width / 2, _height / 2);
            int iterations = 100;
            stroke(colour.rand());
            strokeWeight(3);
            noFill();
            for (int i = 0; i < iterations; i++) {
                ArrayList<Point> points = new ArrayList<>();
                for (int j = 0; j < 360; j++) {
                    float radius = (_width / iterations) * i;
                    float amplitude = map(i, 0, iterations, 1, 18);
                    float angle = (j / 360f) * TAU;
                    Point p = sineAroundCircle(0, 0, radius, amplitude, angle, 30);
                    points.add(p);
                }
                beginShape();
                for (Point p : points) {
                    curveVertex(p.x(), p.y());
                }
                endShape(CLOSE);
            }

            popMatrix();
            save("buzzing", name);
        });
    }

    public Point sineAroundCircle(float cx, float cy, float radius, float amplitude, float angle, float frequency) {
        float x = cx + (radius + amplitude * sin(frequency * angle)) * cos(angle);
        float y = cy + (radius + amplitude * sin(frequency * angle)) * sin(angle);
        return new Point(x, y);
    }
}
