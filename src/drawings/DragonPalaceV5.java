package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;

import static utilities.Bezier.cubicBezier;

public class DragonPalaceV5 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DragonPalaceV5");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(4096, 4096, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 5; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                background(colour.white());
                drawTexture(colour.rand(), 0.2f, 0.1f);
                drawTexture(colour.rand(), 0.9f, 0.2f, 2048.0f);
                strokeCap(ROUND);
                fill(colour.rand());
                strokeWeight(2);
                println(name);
                squiggleGroup(colour);
                save("dragons-palace-v4", name);
            });
        }
    }

    private void squiggleGroup(Colours colour) {
        float frequencyXA = random(0.05f, 1);
        float phaseXA = random(640, 960);
        float amplitudeXA = random(640, 960);
        float dampingXA = random(0.0005f, 0.01f);

        float frequencyXB = random(0.05f, 1);
        float phaseXB = random(640, 960);
        float amplitudeXB = random(640, 960);
        float dampingXB = random(0.0005f, 0.01f);

        float frequencyYA = random(0.05f, 1);
        float phaseYA = random(640, 960);
        float amplitudeYA = random(640, 960);
        float dampingYA = random(0.0005f, 0.01f);

        float frequencyYB = random(0.05f, 1);
        float phaseYB = random(640, 960);
        float amplitudeYB = random(640, 960);
        float dampingYB = random(0.0005f, 0.01f);

        float amplitudeXAStep = random(-40, 40);
        float amplitudeYAStep = random(-40, 40);
        float amplitudeXBStep = random(-40, 40);
        float amplitudeYBStep = random(-40, 40);

        stroke(colour.black());
        for (int i = 0; i < 12; i++) {
            amplitudeXA += amplitudeXAStep;
            amplitudeYA += amplitudeYAStep;
            amplitudeXB += amplitudeXBStep;
            amplitudeYB += amplitudeYBStep;
            fill(colour.randWithWhite());
            squiggle(
                    frequencyXA, phaseXA, amplitudeXA, dampingXA,
                    frequencyXB, phaseXB, amplitudeXB, dampingXB,
                    frequencyYA, phaseYA, amplitudeYA, dampingYA,
                    frequencyYB, phaseYB, amplitudeYB, dampingYB
            );
        }
    }

    private void squiggle(
            float _frequencyXA, float _phaseXA, float _amplitudeXA, float _dampingXA,
            float _frequencyXB, float _phaseXB, float _amplitudeXB, float _dampingXB,
            float _frequencyYA, float _phaseYA, float _amplitudeYA, float _dampingYA,
            float _frequencyYB, float _phaseYB, float _amplitudeYB, float _dampingYB
    ) {
        Harmonograph xA = new Harmonograph(_frequencyXA, _phaseXA, _amplitudeXA, _dampingXA);
        Harmonograph xB = new Harmonograph(_frequencyXB, _phaseXB, _amplitudeXB, _dampingXB);
        Harmonograph yA = new Harmonograph(_frequencyYA, _phaseYA, _amplitudeYA, _dampingYA);
        Harmonograph yB = new Harmonograph(_frequencyYB, _phaseYB, _amplitudeYB, _dampingYB);

        float scale = 1;
        ArrayList<Point> points = new ArrayList<>();
        Point lastPoint = new Point();
        for (int i = 0; i < 75; i++) {
            float x = xA.f(i) + xB.f(i);
            float y = yA.f(i) + yB.f(i);
            if (lastPoint.isNull()) {
                lastPoint = new Point(x, y);
                continue;
            }
            for (float t = 1; t >= 0; t -= 1 / 110f) {
                points.add(cubicBezier(
                        new Point(x, y),
                        new Point(x, lastPoint.y()),
                        new Point(lastPoint.x(), y),
                        new Point(lastPoint.x(), lastPoint.y()),
                        t
                ));
            }
            lastPoint = new Point(x, y);
        }
        pushMatrix();
        translate(_width / 2, _height / 2);
        for (Point p : points) {
            ellipse(p.x(), p.y(), 150 * scale + 1, 150 * scale + 1);
            scale *= 0.9998;
        }
        popMatrix();
    }

    static class Harmonograph {
        private final float _frequency;
        private final float _phase;
        private final float _amplitude;
        private final float _damping;

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
