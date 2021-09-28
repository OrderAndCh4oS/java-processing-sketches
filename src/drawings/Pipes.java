package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;

import static utilities.Bezier.cubicBezier;

public class Pipes extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Pipes");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 10; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                boolean colourModeRoll = random(1) > 0.5;
                int bg = colourModeRoll ? colour.white() : colour.black();
                background(bg);
                if(colourModeRoll) {
                    drawTexture(colour.rand(), 0.2f, 0.15f);
                    drawTexture(colour.rand(), 0.9f, 0.2f, 1024f);
                } else {
                    drawTexture(colour.rand(), 0.2f, 0.15f);
                    drawTexture(colour.rand(), 0.2f, 0.125f, 1024f);
                }
                println("##########");
                println(name);
                println("----------");
                strokeCap(ROUND);
                fill(colour.rand());
                strokeWeight(0.66f);
                stroke(colour.black(), 0.25f * 255);
                squiggleGroup(colour);
                save("pipes", name);
            });
        }
    }

    private void squiggleGroup(Colours colour) {
        float frequencyXA = random(0.05f, 1);
        float phaseXA = random(340, 380);
        float amplitudeXA = random(340, 380);
        float dampingXA = random(0.0005f, 0.01f);

        float frequencyXB = random(0.05f, 1);
        float phaseXB = random(340, 380);
        float amplitudeXB = random(340, 380);
        float dampingXB = random(0.0005f, 0.01f);

        float frequencyYA = random(0.05f, 1);
        float phaseYA = random(340, 380);
        float amplitudeYA = random(340, 380);
        float dampingYA = random(0.0005f, 0.01f);

        float frequencyYB = random(0.05f, 1);
        float phaseYB = random(340, 380);
        float amplitudeYB = random(340, 380);
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

        println(frequencyXA, "frequencyXA");
        println(frequencyXB, "frequencyXB");
        println(frequencyYA, "frequencyYA");
        println(frequencyYB, "frequencyYB");

        println(phaseXA, "phaseXA");
        println(phaseXB, "phaseXB");
        println(phaseYA, "phaseYA");
        println(phaseYB, "phaseYB");

        println(amplitudeXA, "amplitudeXA");
        println(amplitudeXB, "amplitudeXB");
        println(amplitudeYA, "amplitudeYA");
        println(amplitudeYB, "amplitudeYB");

        println(dampingXA, "dampingXA");
        println(dampingXB, "dampingXB");
        println(dampingYA, "dampingYA");
        println(dampingYB, "dampingYB");

        for (int i = 0; i < random(6, 9); i++) {
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
            for (float t = 1; t >= 0; t -= 1 / 90f) {
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
            ellipse(p.x(), p.y(), 100 * scale + 1, 100 * scale + 1);
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
