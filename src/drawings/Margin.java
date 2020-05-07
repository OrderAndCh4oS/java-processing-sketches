package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Vector;

public class Margin extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Margin");
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
            background(colour.white());
            for (int i = 0; i < 12; i++) {
                stroke(colour.rand());
                fill(colour.rand());
                Particle p = new Particle(random(120, _height - 120), random(120, _height - 120), 6, random(TAU));
                for (int j = 0; j < 500; j++) {
                    p.update();
                    p.draw();
                }
            }
            save("margin", name);
        });
    }

    private class Particle {
        Vector _position;
        Vector _v1;
        float _velocity;
        float _margin = 120;

        Particle(float x, float y, float v, float a) {
            _position = new Vector(x, y);
            _velocity = v;
            _v1 = new Vector(1, 1);
            _v1.setAngle(a);
            _v1.setLength(_velocity);
        }

        void update() {
            try {
                Vector _p = _position.clone();
                _p.addTo(_v1);
                if (_p.x() < _margin + 5 || _p.x() > _width - _margin - 5) {
                    _v1.setX(-_v1.x());
                }
                if (_p.y() < _margin + 5 || _p.y() > _height - _margin - 5) {
                    _v1.setY(-_v1.y());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            _position.addTo(_v1);
        }

        void draw() {
            ellipse(_position.x(), _position.y(), 4, 4);
        }
    }
}
