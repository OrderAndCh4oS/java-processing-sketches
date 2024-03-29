package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class OmniscientV2 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.OmniscientV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            noFill();
            drawTexture(colour.rand(), 0.1f, 0.1f);
            drawTexture(colour.rand(), 0.9f, 0.2f, 1536);
            strokeCap(ROUND);
            stroke(colour.rand());
            squiggle();
            stroke(colour.rand());
            squiggle();
            save("omniscient-v2", name);
        });
    }

    private void squiggle() {
        Harmonograph xA = new Harmonograph(random(0.05f, 1), random(240, 540), random(240, 1080), random(0.0005f, 0.01f));
        Harmonograph xB = new Harmonograph(random(0.05f, 1), random(240, 540), random(240, 1080), random(0.0005f, 0.01f));
        Harmonograph yA = new Harmonograph(random(0.05f, 1), random(240, 540), random(240, 1080), random(0.0005f, 0.01f));
        Harmonograph yB = new Harmonograph(random(0.05f, 1), random(240, 540), random(240, 1080), random(0.0005f, 0.01f));
        pushMatrix();
        translate(_width / 2, _height / 2);
        beginShape();
        for (int i = 0; i < 1500; i++) {
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
