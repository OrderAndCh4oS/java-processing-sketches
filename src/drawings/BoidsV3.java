package drawings;

import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;

import java.util.*;

import static utilities.Random.randomInt;

/**
 * http://www.vergenet.net/~conrad/boids/pseudocode.html
 */
public class BoidsV3 extends Sketch {
    public Data _data;
    public ColourData _colourData;
    private ArrayList<Boid> _boids = new ArrayList<>();

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
//        _data = new Data(24, 0, 5000, 0.00005f, 0.00004f, 0.00005f);
//        _data = new Data(50, 5000,2500, 0.0001f, 0.0002f, 0.00015f);
//        _data = new Data(36, 50, 1500, 0.0003f, 0.0002f, 0.00015f);
        _data = new Data(36, 500, 750, 0.0003f, 0.0006f, 0.0004f);
        _colours.getColours().forEach((name, colour) -> {
//            _colourData = new ColourData(colour.white(), MULTIPLY, colour.rand(), 0.2f, 0.25f, colour.getAll(), 0.1f);
//        _colourData = new ColourData(colour.white(), NORMAL, colour.rand(), 0.2f, 0.25f, colour.getAll(), 0.6f);
//            _colourData = new ColourData(colour.black(), SCREEN, colour.white(), 0.2f, 0.15f, colour.getAllWithBlackAndWhite(), 0.15f);
//            _colourData = new ColourData(colour.black(), SCREEN, colour.white(), 0.2f, 0.25f, colour.getAll(), 0.15f);
//            _colourData = new ColourData(colour.rand(), NORMAL, colour.black(), 0.2f, 0.15f, colour.getBlackAndWhiteOnly(), 0.5f);
            _colourData = new ColourData(colour.white(), MULTIPLY, colour.rand(), 0.4f, 0.15f, colour.getAll(), 0.1f);
            DrawShape _shape = new DrawDot();
            switch (Shape.rand()) {
                case DOT:
                    _shape = new DrawDot();
                    break;
                case RINGS:
                    _shape = new DrawRings();
                    break;
                case CROSS:
                    _shape = new DrawCross();
                    break;
            }
            background(_colourData.bg);
            blendMode(NORMAL);
            drawTexture(_colourData.texture(), _colourData.textureDensity(), _colourData.textureAlpha());
            int boidsInit = randomInt(0, 2);
            switch (boidsInit) {
                case 0:
                    initBoidsRandomWidth();
                    break;
                case 1:
                    initBoidsRandomQuarter();
                    break;
                case 2:
                    initBoidsRandom();
                    break;
            }
            noStroke();
            blendMode(_colourData.blend);
            for (int i = 0; i < _data.preIterations; i++) {
                for (Boid b : _boids) {
                    Vector v1 = moveTowardCentreOfMass(b);
                    Vector v2 = moveAwayFromOtherBoids(b);
                    Vector v3 = matchVelocityOfNearBoids(b);
                    Vector v4 = tendTowardCenter(b);
                    b.update(v1, v2, v3, v4);
                }
            }
            for (int i = 0; i < _data.iterations; i++) {
                for (Boid b : _boids) {
                    Vector v1 = moveTowardCentreOfMass(b);
                    Vector v2 = moveAwayFromOtherBoids(b);
                    Vector v3 = matchVelocityOfNearBoids(b);
                    Vector v4 = tendTowardCenter(b);
                    b.update(v1, v2, v3, v4);
                    _shape.draw(b.getPosition().x(), b.getPosition().y(), b.get_colour());
                }
            }

            save("boids-v3", name);
        });
    }

    private void initBoidsRandomWidth() {
        _boids = new ArrayList<>();
        for (int i = 0; i < _data.boids(); i++) {
            _boids.add(new Boid(random(_width), _height / 2, _colourData.rand()));
        }
    }

    private void initBoidsRandom() {
        _boids = new ArrayList<>();
        for (int i = 0; i < _data.boids(); i++) {
            _boids.add(new Boid(random(_width), random(_height), _colourData.rand()));
        }
    }

    private void initBoidsRandomQuarter() {
        _boids = new ArrayList<>();
        for (int i = 0; i < _data.boids(); i++) {
            _boids.add(new Boid(random(_width / 2), random(_height / 2), _colourData.rand()));
        }
    }

    private Vector tendTowardCenter(Boid b) {
        Vector v4 = new Vector(_width / 2, _height / 2);
        v4.subtractFrom(b.getPosition());
        v4.multiplyBy(_data.tendTowardCenterMod);
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
        v3.multiplyBy(_data.matchVelocityMod);

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
        v1.multiplyBy(_data.tendTowardMassMod);
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

    public enum Shape {
        DOT,
        RINGS,
        CROSS;

        private static final List<Shape> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Shape rand() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    interface DrawShape {
        void draw(float x, float y, int colour);
    }

    public class Data {
        private final int boids;
        private final int preIterations;
        private final int iterations;
        private final float matchVelocityMod;
        private final float tendTowardMassMod;
        private final float tendTowardCenterMod;

        Data(int boids, int preIterations, int iterations, float tendTowardCenterMod, float matchVelocityMod, float tendTowardMassMod) {
            this.boids = boids;
            this.preIterations = preIterations;
            this.iterations = iterations;
            this.matchVelocityMod = matchVelocityMod;
            this.tendTowardMassMod = tendTowardMassMod;
            this.tendTowardCenterMod = tendTowardCenterMod;
        }

        public int boids() {
            return boids;
        }

        public int preIterations() {
            return preIterations;
        }

        public int iterations() {
            return iterations;
        }

        public float matchVelocityMod() {
            return matchVelocityMod;
        }

        public float tendTowardMassMod() {
            return tendTowardMassMod;
        }

        public float tendTowardCenterMod() {
            return tendTowardCenterMod;
        }
    }

    public class ColourData {
        private final int bg;
        private final int blend;
        private final int texture;
        private final float textureAlpha;
        private final float textureDensity;
        private final ArrayList<Integer> colourList;
        private final float colourAlpha;

        ColourData(
                int bg,
                int blend,
                int texture,
                float textureDensity,
                float textureAlpha,
                ArrayList<Integer> colourList,
                float colourAlpha
        ) {
            this.bg = bg;
            this.blend = blend;
            this.texture = texture;
            this.textureAlpha = textureAlpha;
            this.textureDensity = textureDensity;
            this.colourList = colourList;
            this.colourAlpha = colourAlpha;
        }

        public int bg() {
            return bg;
        }

        public int blend() {
            return blend;
        }

        public int texture() {
            return texture;
        }

        public float textureAlpha() {
            return textureAlpha;
        }

        public float textureDensity() {
            return textureDensity;
        }

        public ArrayList<Integer> colourList() {
            return colourList;
        }

        public float colourAlpha() {
            return colourAlpha * 255;
        }

        public int rand() {
            return colourList.get(new Random().nextInt(colourList.size()));
        }

    }

    class DrawDot implements DrawShape {
        public void draw(float x, float y, int colour) {
            noStroke();
            fill(colour, _colourData.colourAlpha());
            float r = random(6, 16);
            ellipse(x, y, r, r);
        }
    }

    class DrawCross implements DrawShape {
        public void draw(float x, float y, int colour) {
            float r = random(3, 20);
            strokeCap(ROUND);
            strokeWeight(r * 0.4f);
            noFill();
            stroke(colour, _colourData.colourAlpha());
            line(x, y, x + r, y + r);
            line(x + r, y, x, y + r);
        }
    }

    class DrawRings implements DrawShape {
        public void draw(float x, float y, int colour) {
            stroke(colour, _colourData.colourAlpha());
            strokeWeight(1);
            noFill();
            float r = random(3, 6);
            for (int i = 0; i < r; i++) {
                ellipse(x, y, i * 5, i * 5);
            }
        }
    }

    class Boid {
        private final Vector _velocity;
        private final Vector _position;
        private final ArrayList<Point> _points = new ArrayList<>();
        private final int _colour;

        Boid(float x, float y, int colour) {
            _colour = colour;
            _position = new Vector(x, y);
            _velocity = new Vector(random(4) - 2, random(4) - 2);
        }

        public int get_colour() {
            return _colour;
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
