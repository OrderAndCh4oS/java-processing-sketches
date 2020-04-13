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
public class Boids extends Sketch {
    private ArrayList<Boid> _boids;

    public static void main(String... args) {
        PApplet.main("drawings.Boids");
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
            background(colour.bg());
            _boids = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                _boids.add(new Boid(random(_width), _height + random(25) + 50));
                _boids.add(new Boid(random(_width), -(random(25) + 50)));
            }
            for (int i = 0; i < 75; i++) {
                for (Boid b : _boids) {
                    Vector v1 = getV1(b);
                    Vector v2 = getV2(b);
                    Vector v3 = getV3(b);
                    Vector v4 = tendTowardCenter(b);
                    b.update(v1, v2, v3, v4);
                }
            }

            for(Boid b : _boids) {
                int c1 = colour.rand();
                stroke(c1);
                strokeWeight(0.75f);
                strokeCap(ROUND);
                noFill();
                beginShape();
                for( Point p : b.getPoints()) {
                    curveVertex(p.x(), p.y());
                }
                curveVertex(b.getPosition().x(), b.getPosition().y());
                endShape();
                noStroke();
                fill(c1);
                int tail = 15;
                int minScale = 1;
                int maxScale = 25;
                int start = b.getPoints().size() - tail;
                for (int i = start; i < b.getPoints().size(); i++) {
                    float scale = map(i, start, start + tail, minScale, maxScale);
                    ellipse(b.getPoints().get(i).x(), b.getPoints().get(i).y(), scale, scale);

                }
                ellipse(b.getPosition().x(), b.getPosition().y(), 15, 15);
            }
            save("boids", name);
        });
    }

    private Vector tendTowardCenter(Boid b) {
        Vector v4 = new Vector(_width / 2, _height / 3);
        v4.subtractFrom(b.getPosition());
        v4.divideBy(100);
        return v4;
    }

    @NotNull
    private Vector getV3(Boid b) {
        Vector v3 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            v3.addTo(b2.getVelocity());
        }
        v3.divideBy(_boids.size() - 1);
        v3.subtractFrom(b.getVelocity());
        v3.divideBy(8);

        return v3;
    }

    @NotNull
    private Vector getV1(Boid b) {
        Vector v1 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            v1.addTo(b2.getPosition());
        }
        v1.divideBy(_boids.size() - 1);
        v1.subtractFrom(b.getPosition());
        v1.divideBy(100);
        return v1;
    }

    @NotNull
    private Vector getV2(Boid b) {
        Vector v2 = new Vector(0, 0);
        for (Boid b2 : _boids) {
            if (b.equals(b2)) {
                continue;
            }
            if (b.getPosition().distanceTo(b2.getPosition()) < 25) {
                v2.subtractFrom(b.getPosition().subtractVector(b2.getPosition()));
            }
        }
        return v2;
    }

    class Boid {
        private Vector _velocity;
        private Vector _position;
        private ArrayList<Point> _points = new ArrayList<>();

        Boid(float x, float y) {
            _position = new Vector(x, y);
            _velocity = new Vector(random(2) - 1, random(2) - 1);
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
