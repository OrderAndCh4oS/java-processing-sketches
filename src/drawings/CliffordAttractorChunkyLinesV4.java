package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.colour.Colours;

import java.util.ArrayList;
import java.util.Random;

public class CliffordAttractorChunkyLinesV4 extends Sketch {

    float a;
    float b;
    float c;
    float d;

    public static void main(String... args) {
        PApplet.main("drawings.CliffordAttractorChunkyLinesV4");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours(
//            "bw"
//            "candy"
//            "candyfloss"
//            "mocha"
//            "furnace"
//            "azure"
//            "mustard-punch"
//            "cyber-punk"
//            "cyberpunk-night"
//            "lime"
//            "rubine"
//            "neon"
//            "pistachio-and-peach"
//            "pastel"
//            "sunset-beach"
//            "toxic"
//            "slate-and-peach"
//            "rust",
//            "oz",
//            "warm-bw",
//            "warm-grey",
//            "cool-bw",
//            "dragons",
//            "constructivist",
//            "constructivist-v2",
//            "lemon",
//            "spy",
//            "happy-time",
//            "sci-fi",
//            "copper",
//            "orange-v2",
//            "orange-v3",
//            "salmon-lemon",
//            "hot-dusk",
//            "cool-dusk",
            "constructivist-real",
            "red-blue",
            "order-and-chaos"
        ).forEach((name, colour) -> {
            a = random(1) * 6 - 3;
            b = random(1) * 6 - 3;
            c = random(1) * 4 - 2;
            d = random(1) * 4 - 2;
            blendMode(NORMAL);
            background(colour.black());
            drawBenDayTexture(colour.randWithWhite(), random(8, 24), random(0.15f, 0.85f));
            ArrayList<AttractorPoint> attractorPointsLeft = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsRight = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsTop = new ArrayList<>();
            ArrayList<AttractorPoint> attractorPointsBottom = new ArrayList<>();
            int lineCount = 25;
            for (int i = 0; i < lineCount + 1; i++) {
                attractorPointsTop.add(new AttractorPoint((_width / lineCount) * i, -15));
                attractorPointsBottom.add(new AttractorPoint((_width / lineCount) * i, _height + 15));
                attractorPointsLeft.add(new AttractorPoint(-15, (_height / lineCount) * i));
                attractorPointsRight.add(new AttractorPoint(_width + 15, (_height / lineCount) * i));
            }
            ArrayList<Line> lines = new ArrayList<>();
            Point lastPoint = new Point();
            noStroke();
            drawAttractorFills(attractorPointsLeft, lines, lastPoint, colour);
            drawAttractorFills(attractorPointsTop, lines, lastPoint, colour);
            drawAttractorFills(attractorPointsBottom, lines, lastPoint, colour);
            drawAttractorFills(attractorPointsRight, lines, lastPoint, colour);
            save("clifford-attractor-chunky-lines-v4", name);
            println("~~Fin~~");
        });
    }

    private void drawAttractorFills(ArrayList<AttractorPoint> attractorPoints, ArrayList<Line> lines, Point lastPoint, Colours colour) {
        try {
            for (int i = 0; i < attractorPoints.size() - 1; i++) {
                AttractorPoint attractorPointA = attractorPoints.get(i).clone();
                AttractorPoint attractorPointB = attractorPoints.get(i + 1).clone();
                ArrayList<Point> pointsA = new ArrayList<>();
                ArrayList<Point> pointsB = new ArrayList<>();
                for (int j = 0; j < 360; j++) {
                    pointsA.add(attractorPointA.getPoint());
                    pointsB.add(attractorPointB.getPoint());
                }
                ArrayList<Point> reversed = reverseArrayList(pointsB);
                pointsA.addAll(reversed);
                PGraphics mask = createGraphics((int) _width, (int) _height);
                mask.beginDraw();
                mask.fill(0xff000000);
                mask.noStroke();
                mask.rect(0, 0, _width, _height);
                mask.fill(0xffffffff);
                mask.beginShape();
                for (Point p : pointsA) {
                    mask.curveVertex(p.x(), p.y());
                }
                mask.endShape(CLOSE);
                mask.endDraw();
                PGraphics source = createGraphics((int) _width, (int) _height);
                source.beginDraw();
                drawBenDayTexture(source, colour.randWithWhite(), random(8, 24), random(0.15f, 0.85f));
                source.endDraw();
                source.mask(mask);
                image(source, 0, 0);
                mask.dispose();
                source.dispose();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Point> reverseArrayList(ArrayList<Point> list) {
        ArrayList<Point> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }

        return reversedList;
    }

    class AttractorPoint implements Cloneable {
        private float _x;
        private float _y;
        private float _vx;
        private float _vy;

        public AttractorPoint(float x, float y) {
            _x = x;
            _y = y;
        }

        public void setX(float x) {
            _x = x;
        }

        public void setY(float y) {
            _y = y;
        }

        public float x() {
            return _x;
        }

        public float y() {
            return _y;
        }

        public boolean isFinite() {
            return !Float.isInfinite(_x) && !Float.isInfinite(_y);
        }

        public AttractorPoint clone() throws CloneNotSupportedException {
            return (AttractorPoint) super.clone();
        }

        Point getPoint() {
            float value = getValue();
            _vx += cos(value) * 0.66; // 0.5
            _vy += sin(value) * 0.66; // 0.5
            _x += _vx;
            _y += _vy;
            _vx *= 0.88;
            _vy *= 0.88;
            return new Point(_x, _y);
        }

        float getValue() {
            // clifford attractor
            // http://paulbourke.net/fractals/clifford/

            float scale = 0.003f;
            float dx = (_x - width / 2f) * scale;
            float dy = (_y - height / 2f) * scale;

            float x1 = sin(a * dy) + c * cos(a * dx);
            float y1 = sin(b * dx) + d * cos(b * dy);

            return atan2(y1 - dy, x1 - dx);
        }
    }

    public void drawBenDayTexture(PGraphics source, int colour, float diameter, float scale) {
        source.fill(colour);
        source.noStroke();
        int rowIndex = 0;
        for (float x = -diameter; x < _width + diameter; x += diameter) {
            for (float y = rowIndex % 2 == 0 ? 0 : -(diameter / 2f); y < _height + diameter; y += diameter) {
                source.ellipse(x, y, diameter * scale, diameter * scale);
            }
            rowIndex++;
        }
    }

    public void drawBenDayTexture(int colour, float diameter, float scale) {
        fill(colour);
        noStroke();
        int rowIndex = 0;
        for (float x = -diameter; x < _width + diameter; x += diameter) {
            for (float y = rowIndex % 2 == 0 ? 0 : -(diameter / 2f); y < _height + diameter; y += diameter) {
                ellipse(x, y, diameter * scale, diameter * scale);
            }
            rowIndex++;
        }
    }
}
