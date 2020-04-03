package drawings;

import processing.core.PApplet;
import sketch.Sketch;

import java.util.ArrayList;
import java.util.ListIterator;

public class WavesDisc extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.WavesDisc");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.get_colours().forEach((name, colour) -> {
            ArrayList<Line> lines = new ArrayList<Line>();
            for (int i = 0; i < 128; i++) {
                lines.add(new Line(150, 50));
                for (int j = 0; j < 200; j++ ) {
                    lines.get(i).update();
                }
            }
            background(colour.bg());
            pushMatrix();
            translate(_width/2, _height/2);
            for (int i = 0; i < 128; i++) {
                fill(colour.rand());
                noStroke();
//                stroke(colour.bg());
//                strokeWeight(0.5f);
                rotate(map(i, 0, 128, 0, PI*2));
                lines.get(i).draw();
            }
            popMatrix();
            save("hole", name);
        });
    }

    class Point {
        private float _x;
        private float _y;
        private float _a = 0;
        private float _sz = 5;
        private float _sp = 3.5f;
        private float _c = 7f;

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

        Line() {}

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
            while(listIterator.hasPrevious()){
                Point p = listIterator.previous();
                p.draw();
            }
        }
    }
}
