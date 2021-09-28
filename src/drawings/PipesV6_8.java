package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;

import static utilities.Bezier.cubicBezier;
import static utilities.Random.randomInt;

public class PipesV6_8 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.PipesV6_8");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 20; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                int bg = colour.rand();
                background(bg);
                drawTexture(colour.rand(), 0.5f, 0.25f);
                drawTexture(colour.rand(), 0.75f, 0.2f, 1024f);
                strokeCap(ROUND);
                fill(colour.black());
                strokeWeight(0.66f);
                stroke(colour.black(), 0.125f * 255);
                squiggleGroup(colour.getAllWithWhite());
                save("pipes-v6.8", name);
            });
        }
    }

    private void squiggleGroup(ArrayList<Integer> colours) {
        float high = _width * 0.185f;
        float low = _width * 0.165f;
        float frequencyXA = random(0.05f, 1);
        float phaseXA = random(low, high);
        float amplitudeXA = random(low, high);
        float dampingXA = random(0.0005f, 0.01f);

        float frequencyXB = random(0.05f, 1);
        float phaseXB = random(low, high);
        float amplitudeXB = random(low, high);
        float dampingXB = random(0.0005f, 0.01f);

        float frequencyYA = random(0.05f, 1);
        float phaseYA = random(low, high);
        float amplitudeYA = random(low, high);
        float dampingYA = random(0.0005f, 0.01f);

        float frequencyYB = random(0.05f, 1);
        float phaseYB = random(low, high);
        float amplitudeYB = random(low, high);
        float dampingYB = random(0.0005f, 0.01f);

        float amplitudeXAStep = random(-18, 18);
        float amplitudeYAStep = random(-18, 18);
        float amplitudeXBStep = random(-18, 18);
        float amplitudeYBStep = random(-18, 18);

        while (abs(frequencyXA - frequencyXB) < 0.15) {
            frequencyXB = random(0.05f, 1);
        }

        while (abs(frequencyYA - frequencyYB) < 0.15) {
            frequencyYB = random(0.05f, 1);
        }
        int max = randomInt(6, 10);
        for (int i = 0; i < max; i++) {
            amplitudeXA += amplitudeXAStep;
            amplitudeYA += amplitudeYAStep;
            amplitudeXB += amplitudeXBStep;
            amplitudeYB += amplitudeYBStep;
            fill(colours.get(i % colours.size()));
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
            for (float t = 1; t >= 0; t -= 1 / 120f) {
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
            ellipse(p.x(), p.y(), 100 * scale, 100 * scale);
            scale *= 0.99975;
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
