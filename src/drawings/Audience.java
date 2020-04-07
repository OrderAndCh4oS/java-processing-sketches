package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Random;
import utilities.colour.Colours;

import java.util.ArrayList;
import java.util.ListIterator;

public class Audience extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Audience");
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
            Colours otherColour = _colours.rand();
            int x = 0;
            int y = 0;
            int count = 0;
            pushMatrix();
            translate(90, 90);
            for (int e = 0; e < 25; e++) {
                Colours c = count % 2 == 0 ? colour : otherColour;
                float radius = 100;
                ArrayList<Line> lines = new ArrayList<Line>();
                int pupil = Random.randomInt(17, 27);
                for (int i = 0; i < 128; i++) {
                    lines.add(new Line(36 - pupil, pupil));
                    for (int j = 0; j < 36; j++) {
                        lines.get(i).update();
                    }
                }
                noStroke();
                fill(0xFFF9F6EE);
                ellipse(x * (radius * 2) + 20, y * (radius * 2) + 20, radius * 2, radius * 2);
                fill(c.bg());
                ellipse(x * (radius * 2) + 20, y * (radius * 2) + 20, radius, radius);
                pushMatrix();
                translate(x * (radius * 2) + 20, y * (radius * 2) + 20);
                for (int i = 0; i < 128; i++) {
                    fill(c.rand());
                    rotate(map(i, 0, 128, 0, PI * 2));
                    lines.get(i).draw();
                }
                popMatrix();
                x++;
                if (x > 4) {
                    x = 0;
                    y++;
                }
                count++;
            }
            popMatrix();
            save("audience", name);
        });
    }

    class Point {
        private float _x;
        private float _y;
        private float _a = 0;
        private float _sz = 4;
        private float _sp = 0.5f;
        private float _c = 4f;

        Point(float x, float y) {
            _x = x;
            _y = y;
        }

        void update() {
            float dX = sin(_a) * _c;
            _x = dX - dX / 2;
            _y += _sp;
            _a++;
            _sp += 0.05f;
            _sz += 0.075f;
        }

        void draw() {
            ellipse(_x, _y, _sz, _sz);
        }
    }

    class Line {
        int maxTail = 100;
        int tail = 0;
        int delay = 0;
        ArrayList<Point> points = new ArrayList<>();

        Line() {
        }

        Line(int maxTail) {
            this.maxTail = maxTail;
        }

        Line(int maxTail, int delay) {
            this.maxTail = maxTail;
            this.delay = delay;
        }

        void update() {
            tail++;
            for (Point point : points) {
                point.update();
            }
            if (tail < maxTail) {
                points.add(new Point(0, 0));
            }
            if (tail > maxTail + delay) {
                tail = 0;
            }
        }

        void draw() {
            ListIterator<Point> listIterator = points.listIterator(points.size());
            while (listIterator.hasPrevious()) {
                Point p = listIterator.previous();
                p.draw();
            }
        }
    }
}
