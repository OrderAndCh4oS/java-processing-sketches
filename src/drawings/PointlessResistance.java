package drawings;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Vector;

public class PointlessResistance extends Sketch {
    Vector gravity = new Vector(0, 0.01f);

    public static void main(String... args) {
        PApplet.main("drawings.PointlessResistance");
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
            background(colour.bg());
            for (int i = 0; i < 12; i++) {
                stroke(colour.rand());
                fill(colour.rand());
                Particle p = new Particle(0, 5 * i, random(3, 8));
                for (int j = 0; j < 3000; j++) {
                    p.update();
                    p.draw();
                }
            }
            save("pointless-resistance", name);
        });
    }

    private class Particle {
        Vector _position;
        Vector _v1;
        float _velocity;
        float _angle = 0;


        Particle(float x, float y, float v) {
            _position = new Vector(x, y);
            _velocity = v;
            _v1 = new Vector(1, 1);
            _v1.setAngle(_angle);
            _v1.setLength(_velocity);
        }

        void update() {
            _v1.addTo(gravity);
            _v1.multiplyBy(0.996f);
            try {
                Vector _p = _position.clone();
                _p.addTo(_v1);
                _p.addTo(gravity);
                if(_p.x() < 5 || _p.x() > _width -5) {
                    _v1.setX(-_v1.x());
                }
                if(_p.y() < 5 || _p.y() > _height -5) {
                    _v1.setY(-_v1.y());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            _position.addTo(_v1);
            _position.addTo(gravity);
        }

        void draw() {
            ellipse(_position.x(),_position.y(), 5, 5);
        }
    }
}
