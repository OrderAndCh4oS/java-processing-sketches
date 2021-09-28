package drawings;

import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;

import java.util.ArrayList;

/**
 * http://www.vergenet.net/~conrad/boids/pseudocode.html
 */
public class BoidsV3 extends Sketch {
    private ArrayList<Boid> _boids;

    public static void main(String... args) {
        PApplet.main("drawings.BoidsV3");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            drawTexture(colour.rand(), 0.2f, 0.25f);
            _boids = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                _boids.add(new Boid(random(_width), _height / 2, colour.rand()));
            }
            noStroke();
            blendMode(MULTIPLY);
            for (int i = 0; i < 5000; i++) {
                for (Boid b : _boids) {
                    Vector v1 = moveTowardCentreOfMass(b);
                    Vector v2 = moveAwayFromOtherBoids(b);
                    Vector v3 = matchVelocityOfNearBoids(b);
                    Vector v4 = tendTowardCenter(b);
                    b.update(v1, v2, v3, v4);
                    fill(b.get_colour(), 0.1f);
                    float r = random(6, 16);
                    ellipse(b.getPosition().x(), b.getPosition().y(), r, r);
                }
            }

            save("boids-v3", name);
        });
    }

    private Vector tendTowardCenter(Boid b) {
        Vector v4 = new Vector(_width / 2, _height / 2);
        v4.subtractFrom(b.getPosition());
        v4.multiplyBy(0.00005f);
        return v4;
    }

    @NotNull
    private Vector matchVelocityOfNearBoids(Boid b) {
        Vector v3 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            v3.addTo(b2.getVelocity());
        }
        v3.divideBy(_boids.size() - 1);
        v3.subtractFrom(b.getVelocity());
        v3.multiplyBy(0.00005f);

        return v3;
    }

    @NotNull
    private Vector moveTowardCentreOfMass(Boid b) {
        Vector v1 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            v1.addTo(b2.getPosition());
        }
        v1.divideBy(_boids.size() - 1);
        v1.subtractFrom(b.getPosition());
        v1.multiplyBy(0.00004f);
        return v1;
    }

    @NotNull
    private Vector moveAwayFromOtherBoids(Boid b) {
        Vector v2 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            if (b.getPosition().distanceTo(b2.getPosition()) < 10) {
                v2.subtractFrom(b.getPosition().subtractVector(b2.getPosition()));
            }
        }
        return v2;
    }

    class Boid {
        private final Vector _velocity;
        private final Vector _position;
        private final ArrayList<Point> _points = new ArrayList<>();
        private final int _colour;

        public int get_colour() {
            return _colour;
        }

        Boid(float x, float y, int colour) {
            _colour = colour;
            _position = new Vector(x, y);
            _velocity = new Vector(random(4) - 2, random(4) - 2);
        }

        void update(Vector v1, Vector v2, Vector v3, Vector v4) {
            _velocity.addTo(v1);
            _velocity.addTo(v2);
            _velocity.addTo(v3);
            _velocity.addTo(v4);
            _position.addTo(_velocity);
            _points.add(new Point(_position.x(), _position.y()));
        }

        public ArrayList<Point> getPoints() {
            return _points;
        }

        public Vector getPosition() {
            return _position;
        }

        public Vector getVelocity() {
            return _velocity;
        }
    }
}
