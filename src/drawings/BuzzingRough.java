package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class BuzzingRough extends Sketch {
    int count = 60;
    float[] offsets = new float[count];

    public static void main(String... args) {
        PApplet.main("drawings.BuzzingRough");
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
            for (int i = 0; i < count; i++) {
                offsets[i] = random(30) - 15;
            }
            int bg = colour.black();
            background(bg);
            pushMatrix();
            translate(_width / 2, _height / 2);
            int iterations = 60;
            int c1 = colour.white();
            for (int i = 0; i < iterations; i++) {
                ArrayList<Point> points = new ArrayList<>();
                float radius = ((_width * 0.75f) / iterations) * i;
                float amplitude = map(i, 0, iterations, 1, 24);
                float frequency = 24;
                drawWaveLoop(c1, points, radius, amplitude, frequency);
            }
            popMatrix();
            save("buzzing-rough", name);
        });
    }

    private void drawWaveLoop(int c1, ArrayList<Point> points, float radius, float amplitude, float frequency) {
        for (int j = count - 1; j > 0; j--) {
            float angle = (j / (float) count) * TAU;
            Point p = sineAroundCircle(0, 0, radius, amplitude, angle, frequency, offsets[j]);
            offsets[j] = offsets[j] * 1.09f;
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

    public Point sineAroundCircle(float cx, float cy, float radius, float amplitude, float angle, float frequency, float offset) {
        float x = cx + (radius + offset + amplitude * sin(frequency * angle)) * cos(angle);
        float y = cy + (radius + offset + amplitude * sin(frequency * angle)) * sin(angle);
        return new Point(x, y);
    }
}
