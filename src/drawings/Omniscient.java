package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Map.*;

public class Omniscient extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Omniscient");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours("hot-dusk", "cool-dusk").forEach((name, colour) -> {
            background(colour.bg());
            noFill();
            drawDepth(colour.rand(), 0.9f, 0.1f);
            drawDepth(colour.rand(), 0.9f, 0.2f, 512f);
            strokeCap(ROUND);
            stroke(colour.rand());
            squiggle();
            stroke(colour.rand());
            squiggle();
            save("omniscient", name);
        });

    }

    private void squiggle() {
        Harmonograph xA = new Harmonograph(random(0.05f, 1), random(80, 180), random(80, 360), random(0.0005f, 0.01f));
        Harmonograph xB = new Harmonograph(random(0.05f, 1), random(80, 180), random(80, 360), random(0.0005f, 0.01f));
        Harmonograph yA = new Harmonograph(random(0.05f, 1), random(80, 180), random(80, 360), random(0.0005f, 0.01f));
        Harmonograph yB = new Harmonograph(random(0.05f, 1), random(80, 180), random(80, 360), random(0.0005f, 0.01f));
        pushMatrix();
        translate(_width / 2, _height / 2);
        beginShape();
        for (int i = 0; i < 1500; i++) {
            curveVertex(xA.f(i) + xB.f(i), yA.f(i) + yB.f(i));
        }
        endShape();
        popMatrix();
    }

    private void drawDepth(int colour, float density, float alpha) {
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

    private void drawDepth(int colour, float density, float alpha, float radius) {
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

    private float getDistance(Point p, Point q) {
        float dX = p.x() - q.x();
        float dY = p.y() - q.y();

        return sqrt((dX * dX) + (dY * dY));
    }

    static class Harmonograph {
        private float _frequency;
        private float _phase;
        private float _amplitude;
        private float _damping;

        public Harmonograph(float _frequency, float _phase, float _amplitude, float _damping) {
            this._frequency = _frequency;
            this._phase = _phase;
            this._amplitude = _amplitude;
            this._damping = _damping;
        }

        float f(float t) {
            float angle = t * _frequency + _phase;
            float x1 = _amplitude * sin(angle);
            return x1 * pow(exp(1.0f), -_damping * t);
        }
    }


}
