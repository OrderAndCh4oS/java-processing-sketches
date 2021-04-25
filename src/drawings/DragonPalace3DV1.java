package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Colours;

/*
Lemon
frequencyXA 0.9637483
phaseXA 600.2138
amplitudeXA 929.95374
dampingXA 8.0978195E-4
frequencyXB 0.28475916
phaseXB 506.58966
amplitudeXB 1253.1698
dampingXB 0.009248902
frequencyYA 0.13761048
phaseYA 606.4281
amplitudeYA 583.8144
dampingYA 0.0012528216
frequencyYB 0.5896416
phaseYB 503.60498
amplitudeYB 591.1001
dampingYB 0.001880873
Mocha
frequencyXA 0.3041939
phaseXA 409.56128
amplitudeXA 1063.2168
dampingXA 0.001198192
frequencyXB 0.98111266
phaseXB 330.69525
amplitudeXB 474.39072
dampingXB 0.0068038525
frequencyYA 0.64915776
phaseYA 341.74744
amplitudeYA 1138.2437
dampingYA 0.00838641
frequencyYB 0.67633194
phaseYB 479.08344
amplitudeYB 991.11444
dampingYB 0.008452351

 */
public class DragonPalace3DV1 extends Sketch {
    Colours _colour;
    int _bg;

    int frame = 0;

    float frequencyXA = random(0.05f, 1);
    float phaseXA = random(320, 720);
    float amplitudeXA = random(320, 1440);
    float dampingXA = random(0.0005f, 0.01f);

    float frequencyXB = random(0.05f, 1);
    float phaseXB = random(320, 720);
    float amplitudeXB = random(320, 1440);
    float dampingXB = random(0.0005f, 0.01f);

    float frequencyYA = random(0.05f, 1);
    float phaseYA = random(320, 720);
    float amplitudeYA = random(320, 1440);
    float dampingYA = random(0.0005f, 0.01f);

    float frequencyYB = random(0.05f, 1);
    float phaseYB = random(320, 720);
    float amplitudeYB = random(320, 1440);
    float dampingYB = random(0.0005f, 0.01f);

    float frequencyZA = random(0.05f, 1);
    float phaseZA = random(320, 720);
    float amplitudeZA = random(320, 1440);
    float dampingZA = random(0.0005f, 0.01f);

    float frequencyZB = random(0.05f, 1);
    float phaseZB = random(320, 720);
    float amplitudeZB = random(320, 1440);
    float dampingZB = random(0.0005f, 0.01f);

    float amplitudeXAStep = random(-40, 40);
    float amplitudeXBStep = random(-40, 40);
    float amplitudeYAStep = random(-40, 40);
    float amplitudeYBStep = random(-40, 40);
    float amplitudeZAStep = random(-40, 40);
    float amplitudeZBStep = random(-40, 40);

    public static void main(String... args) {
        PApplet.main("drawings.DragonPalace3DV1");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(4096, 4096, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            _colour = colour;
            _bg = colour.rand();
            noFill();
//            drawTexture3d(colour.rand(), 0.9f, 0.8f);
//            drawTexture3d(colour.white(), 0.1f, 0.25f, 2048.0f);
            strokeCap(ROUND);
            strokeJoin(ROUND);
            strokeWeight(12);
            perspective(PI / 2, _width / _height, 0.1f, 1000000);

        });
    }

    public void draw() {
        if (frame == 60) exit();
        camera();
        background(_bg);
        pushMatrix();
        float angle = map(frame % 60, 0, 60, -PI, PI);
        rotateZ(angle);
        squiggleGroup(_colour);
        popMatrix();
        save("dragons-palace-3d-v1", "frame_" + frame);
        frame++;
    }

    private void squiggleGroup(Colours colour) {

        float strands = random(12, 18);

        for (int i = 0; i < strands; i++) {
            amplitudeXA += amplitudeXAStep;
            amplitudeXB += amplitudeXBStep;
            amplitudeYA += amplitudeYAStep;
            amplitudeYB += amplitudeYBStep;
            amplitudeZA += amplitudeZAStep;
            amplitudeZB += amplitudeZBStep;
            stroke(colour.blackOrWhite());
            squiggle(
                    frequencyXA, phaseXA, amplitudeXA, dampingXA,
                    frequencyXB, phaseXB, amplitudeXB, dampingXB,
                    frequencyYA, phaseYA, amplitudeYA, dampingYA,
                    frequencyYB, phaseYB, amplitudeYB, dampingYB,
                    frequencyZA, phaseZA, amplitudeZA, dampingZA,
                    frequencyZB, phaseZB, amplitudeZB, dampingZB
            );
        }
    }

    private void squiggle(
            float _frequencyXA, float _phaseXA, float _amplitudeXA, float _dampingXA,
            float _frequencyXB, float _phaseXB, float _amplitudeXB, float _dampingXB,
            float _frequencyYA, float _phaseYA, float _amplitudeYA, float _dampingYA,
            float _frequencyYB, float _phaseYB, float _amplitudeYB, float _dampingYB,
            float _frequencyZA, float _phaseZA, float _amplitudeZA, float _dampingZA,
            float _frequencyZB, float _phaseZB, float _amplitudeZB, float _dampingZB
    ) {
        Harmonograph xA = new Harmonograph(_frequencyXA, _phaseXA, _amplitudeXA, _dampingXA);
        Harmonograph xB = new Harmonograph(_frequencyXB, _phaseXB, _amplitudeXB, _dampingXB);
        Harmonograph yA = new Harmonograph(_frequencyYA, _phaseYA, _amplitudeYA, _dampingYA);
        Harmonograph yB = new Harmonograph(_frequencyYB, _phaseYB, _amplitudeYB, _dampingYB);
        Harmonograph zA = new Harmonograph(_frequencyZA, _phaseZA, _amplitudeZA, _dampingZA);
        Harmonograph zB = new Harmonograph(_frequencyZB, _phaseZB, _amplitudeZB, _dampingZB);
        pushMatrix();
        translate(_width / 2, _height / 2);
        beginShape();
        for (int i = 0; i < random(50, 750); i++) {
            curveVertex(xA.f(i) + xB.f(i), yA.f(i) + yB.f(i), zA.f(i) + zB.f(i));
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
