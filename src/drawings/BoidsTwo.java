package drawings;

import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;

import java.util.ArrayList;

import static utilities.Map.*;

/**
 * http://www.vergenet.net/~conrad/boids/pseudocode.html
 */
public class BoidsTwo extends Sketch {
    private ArrayList<Boid> _boids;

    public static void main(String... args) {
        PApplet.main("drawings.BoidsTwo");
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
            drawDepth(colour.rand(), 0.9f, 0.1f);
            drawDepth(colour.rand(), 0.8f, 0.05f, 480f);
            _boids = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                _boids.add(new Boid(random(_width), _height + random(25) + 50));
                _boids.add(new Boid(random(_width), -(random(25) + 50)));
            }
            for (int i = 0; i < 100; i++) {
                for (Boid b : _boids) {
                    Vector v1 = moveTowardCentreOfMass(b);
                    Vector v2 = moveAwayFromOtherBoids(b);
                    Vector v3 = matchVelocityOfNearBoids(b);
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

                for(Point p : b.getPoints()) {
                    curveVertex(p.x(), p.y());
                }
                curveVertex(b.getPosition().x(), b.getPosition().y());
                endShape();
                noStroke();
                fill(c1);
                int tail = 32;
                int minScale = 1;
                int maxScale = 15;
                int start = b.getPoints().size() - tail;
                for (int i = start; i < b.getPoints().size(); i++) {
                    float scale = map(i, start, start + tail, minScale, maxScale);
                    ellipse(b.getPoints().get(i).x(), b.getPoints().get(i).y(), scale, scale);

                }
                ellipse(b.getPosition().x(), b.getPosition().y(), 15, 15);
            }
            save("boids-two", name);
        });
    }

    private Vector tendTowardCenter(Boid b) {
        Vector v4 = new Vector(_width / 2, _height / 2);
        v4.subtractFrom(b.getPosition());
        v4.divideBy(80);
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
        v3.divideBy(6);

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
        v1.divideBy(160);
        return v1;
    }

    @NotNull
    private Vector moveAwayFromOtherBoids(Boid b) {
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
            _velocity = new Vector((random(2) - 1) * 0.1f, (random(2) - 1) * 0.1f);
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

    private void drawDepth(int colour, float density, float alpha) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (random(1) > density) {
                    point(x, y);
                }
            }
        }
    }

    private void drawDepth(int colour, float density, float alpha, float radius) {
        stroke(colour, 255 * random(alpha - 0.1f, alpha + 0.1f));
        strokeCap(ROUND);
        strokeWeight(1);
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                float distance = getDistance(_midPoint, new Point(x, y));
                if (random(1) > map2(distance, 0, radius, 0, density, QUADRATIC, EASE_IN)) {
                    point(x, y);
                }
            }
        }
    }

    private float getDistance(Point p, Point q) {
        float dX = p.x() - q.x();
        float dY = p.y() - q.y();

        return sqrt((dX * dX) + (dY * dY));
    }
}
