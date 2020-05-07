package drawings;

import enums.Direction;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;

import java.util.ArrayList;

import static utilities.Map.CUBIC;
import static utilities.Map.EASE_OUT;

/**
 * http://www.vergenet.net/~conrad/boids/pseudocode.html
 */
public class BoidsFrames extends Sketch {
    private ArrayList<Boid> _boids;

    public static void main(String... args) {
        PApplet.main("drawings.BoidsFrames");
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
            int c1 = colour.rand();
            int c2 = colour.rand();
            _boids = new ArrayList<>();
            for (int i = 0; i < 250; i++) {
                _boids.add(new Boid(random(_width), _height + random(25) + 150, colour.rand()));
                _boids.add(new Boid(random(_width), -(random(25) + 150), colour.rand()));
            }
            for (int i = 0; i < 900; i++) {
                background(colour.black());
                drawDepth(c1, 0.9f, 0.1f);
                drawDepth(c2, 1f, Direction.TOP, CUBIC, EASE_OUT);
                for (Boid b : _boids) {
                    Vector v1 = moveTowardCentreOfMass(b);
                    Vector v2 = moveAwayFromOtherBoids(b);
                    Vector v3 = matchVelocityOfNearBoids(b);
                    Vector v4 = tendTowardCenter(b);
                    b.update(v1, v2, v3, v4);
                    fill(b.getColour());
                    float minScale = 0.5f;
                    float maxScale = 6;
                    float scale;
                    if (b.getPoints().size() >= 5) {
                        for (int j = b.getPoints().size() - 5; j < b.getPoints().size(); j++) {
                            scale = map(j, b.getPoints().size() - 5, b.getPoints().size(), minScale, maxScale);
                            ellipse(b.getPoints().get(j).x(), b.getPoints().get(j).y(), scale, scale);
                        }
                    }
                }
                StringBuilder iStr = new StringBuilder("" + i);
                while (iStr.length() < 5) {
                    iStr.insert(0, "0");
                }
                save("boids-frames/" + name, iStr.toString());
            }

        });
    }

    private Vector tendTowardCenter(Boid b) {
        Vector v4 = new Vector(_width / 2, _height / 2);
        v4.subtractFrom(b.getPosition());
        v4.divideBy(400);
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
        v3.divideBy(12);

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
        v1.divideBy(300);
        return v1;
    }

    @NotNull
    private Vector moveAwayFromOtherBoids(Boid b) {
        Vector v2 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            if (b.getPosition().distanceTo(b2.getPosition()) < 15) {
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

        Boid(float x, float y, int colour) {
            _position = new Vector(x, y);
            _velocity = new Vector(random(2) - 1, random(2) - 1);
            _colour = colour;
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

        public int getColour() {
            return this._colour;
        }
    }
}
