package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class BuzzingV2 extends Sketch {
    int count = 60;
    float[] offsets = new float[count + 2];

    public static void main(String... args) {
        PApplet.main("drawings.BuzzingV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
            _colours.getColours().forEach((name, colour) -> {
                noFill();
                blendMode(BLEND);
                background(colour.black());
                strokeWeight(5);
                strokeJoin(ROUND);
                pushMatrix();
                translate(_width / 2, _height / 2);
                int iterations = 90; // << last change was 90
                for (int i = 0; i < iterations; i++) {
                    ArrayList<Point> points = new ArrayList<>();
                    float radius = ((_width * 0.98f) / iterations) * i;
                    float amplitude = map(i, 0, iterations, 0, 12);
                    float frequency = 48;
                    stroke(colour.white());
                    drawWaveLoop(points, radius, amplitude, frequency);
                }
                popMatrix();
                save("buzzing-v2", name);
            });
    }

    private void drawWaveLoop(ArrayList<Point> points, float radius, float amplitude, float frequency) {
        for (int j = count + 1; j > 0; j--) {
            float angle = (j / (float) count) * TAU;
            Point p = sineAroundCircle(0, 0, radius, amplitude, angle, frequency, offsets[j]);
            points.add(p);
        }

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
