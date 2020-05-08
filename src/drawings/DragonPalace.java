package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

public class DragonPalace extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DragonPalace");
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
            background(colour.white());
            noFill();
            drawTexture(colour.rand(), 0.1f, 0.1f);
            drawTexture(colour.rand(), 0.9f, 0.2f, 512f);
            strokeCap(ROUND);
            squiggleGroup(colour);
            save("dragons-palace", name);
        });

    }

    private void squiggleGroup(Colours colour) {
        float frequencyXA = random(0.05f, 1);
        float phaseXA = random(80, 180);
        float amplitudeXA = random(80, 360);
        float dampingXA = random(0.0005f, 0.01f);

        float frequencyXB = random(0.05f, 1);
        float phaseXB = random(80, 180);
        float amplitudeXB = random(80, 360);
        float dampingXB = random(0.0005f, 0.01f);

        float frequencyYA = random(0.05f, 1);
        float phaseYA = random(80, 180);
        float amplitudeYA = random(80, 360);
        float dampingYA = random(0.0005f, 0.01f);

        float frequencyYB = random(0.05f, 1);
        float phaseYB = random(80, 180);
        float amplitudeYB = random(80, 360);
        float dampingYB = random(0.0005f, 0.01f);
        float amplitudeXAStep = random(-10, 10);
        float amplitudeYAStep = random(-10, 10);
        float amplitudeXBStep = random(-10, 10);
        float amplitudeYBStep = random(-10, 10);
        float strands = random(6, 12);
        for (int i = 0; i < strands; i++) {
            amplitudeXA += amplitudeXAStep;
            amplitudeYA += amplitudeYAStep;
            amplitudeXB += amplitudeXBStep;
            amplitudeYB += amplitudeYBStep;
            stroke(colour.rand());
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
        pushMatrix();
        translate(_width / 2, _height / 2);
        beginShape();
        for (int i = 0; i < random(50, 500); i++) {
            curveVertex(xA.f(i) + xB.f(i), yA.f(i) + yB.f(i));
        }
        endShape();
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
