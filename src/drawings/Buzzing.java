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
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            int bg = colour.rand();
            background(bg);
            pushMatrix();
            translate(_width / 2, _height / 2);
            int iterations = 124;
            int c1 = colour.black();
            for (int i = 0; i < iterations; i++) {
                ArrayList<Point> points = new ArrayList<>();
                float radius = ((_width * 0.75f) / iterations) * i;
                float amplitude = map(i, 0, iterations, 1, 24);
                float frequency = 24;
                drawWaveLoop(c1, points, radius, amplitude, frequency);
            }
            popMatrix();
            save("buzzing", name);
        });
    }

    private void drawWaveLoop(int c1, ArrayList<Point> points, float radius, float amplitude, float frequency) {
        for (int j = 0; j < 360; j++) {
            float angle = (j / 360f) * TAU;
            Point p = sineAroundCircle(0, 0, radius, amplitude, angle, frequency);
            points.add(p);
        }

        stroke(c1);
        strokeWeight(0.5f);
        noFill();

        beginShape();
        for (Point p : points) {
            curveVertex(p.x(), p.y());
        }
        endShape(CLOSE);
    }

    public Point sineAroundCircle(float cx, float cy, float radius, float amplitude, float angle, float frequency) {
        float x = cx + (radius + amplitude * sin(frequency * angle)) * cos(angle);
        float y = cy + (radius + amplitude * sin(frequency * angle)) * sin(angle);
        return new Point(x, y);
    }
}
