package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;

import static utilities.Bezier.cubicBezier;
import static utilities.Map.*;

public class DragonPalaceV6 extends Sketch {

    private ArrayList<ArrayList<Point>> _pointsList;

    public static void main(String... args) {
        PApplet.main("drawings.DragonPalaceV6");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2400, 2400, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 5; iter++) {
            _colours.getColours().forEach((name, colour) -> {
                _pointsList = new ArrayList<>();
                background(colour.rand());
                drawTexture(colour.black(), 0.2f, 0.15f);
                strokeCap(ROUND);
                fill(colour.rand());
                strokeWeight(1);
                println(name);
                squiggleGroup(colour);
                pushMatrix();
                translate(_width / 2, _height / 2);
                for (int i = 0, pointsSize = _pointsList.get(0).size(); i < pointsSize; i++) {
                    for (int j = 0, pointsListSize = _pointsList.size(); j < pointsListSize; j++) {
                        ArrayList<Point> points = _pointsList.get(j);
                        if(points.size() <= i) continue;
                        Point p = points.get(i);
                        fill(colour.get(j % colour.size()));
                        ellipse(p.x(), p.y(), 18, 18);
                    }
                }
                popMatrix();
                save("dragons-palace-v6", name);
            });
        }
    }

    private void squiggleGroup(Colours colour) {
        stroke(colour.black());
        for (int i = 0; i < 24; i++) {
            float frequencyXA = random(0.05f, 1);
            float phaseXA = random(600, 900);
            float amplitudeXA = random(600, 900);
            float dampingXA = random(0.0005f, 0.01f);

            float frequencyXB = random(0.05f, 1);
            float phaseXB = random(600, 900);
            float amplitudeXB = random(600, 900);
            float dampingXB = random(0.0005f, 0.01f);

            float frequencyYA = random(0.05f, 1);
            float phaseYA = random(600, 900);
            float amplitudeYA = random(600, 900);
            float dampingYA = random(0.0005f, 0.01f);

            float frequencyYB = random(0.05f, 1);
            float phaseYB = random(600, 900);
            float amplitudeYB = random(600, 900);
            float dampingYB = random(0.0005f, 0.01f);

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

        ArrayList<Point> points = new ArrayList<>();
        Point lastPoint = new Point();
        for (int i = 0; i < 75; i++) {
            float x = xA.f(i) + xB.f(i);
            float y = yA.f(i) + yB.f(i);
            if (lastPoint.isNull()) {
                lastPoint = new Point(x, y);
                continue;
            }
            float distance = distanceTo(x, y, lastPoint.x(), lastPoint.y());
            float stepScale = 1 - map2(distance, 0, 1750, 0, 1, QUADRATIC, EASE_IN);
            for (float t = 1; t >= 0; t -= stepScale * 0.0055) {
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
        _pointsList.add(points);
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
