package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Random.randomInt;

public class RaggedTwo extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.RaggedTwo");
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
            noFill();
            stroke(colour.black());
            float marginX = _width / 6;
            float marginY = _height / 6;
            ArrayList<ArrayList<Point>> verticalThreads = new ArrayList<>();
            ArrayList<ArrayList<Point>> horizontalThreads = new ArrayList<>();
            float y = marginY;
            float x;
            int lean;
            while (y < _height - marginY) {
                ArrayList<Point> verticalThread = new ArrayList<>();
                x = mouseX - 40;
                while (x < _width - marginX) {
                    verticalThread.add(new Point(x, y));
                    x += 5;
                }
                verticalThreads.add(verticalThread);
                y += 5;
            }
            x = marginX;
            while (x < _width - marginX) {
                ArrayList<Point> horizontalThread = new ArrayList<>();
                y = mouseY - 40;
                while (y < _height - marginY) {
                    horizontalThread.add(new Point(x, y));
                    y += 5;
                }
                horizontalThreads.add(horizontalThread);
                x += 5;
            }

            for (ArrayList<Point> t : verticalThreads) {
                float offsetY = 0;
                lean = 0;
                beginShape();
                for (Point p : t) {
                    if (random(1) > 0.99995) {
                        lean = randomInt(0, 2);
                    }
                    switch (lean) {
                        case 0:
                            offsetY += wobble();
                            break;
                        case 1:
                            offsetY += wobbleLeft();
                            break;
                        case 2:
                            offsetY += wobbleRight();
                            break;
                    }
                    vertex(p.x(), p.y() + offsetY);
                }
                endShape();
            }

            for (ArrayList<Point> t : horizontalThreads) {
                float offsetX = 0;
                lean = 0;
                beginShape();
                for (Point p : t) {
                    if (random(1) > 0.99995) {
                        lean = randomInt(0, 2);
                    }
                    switch (lean) {
                        case 0:
                            offsetX += wobble();
                            break;
                        case 1:
                            offsetX += wobbleLeft();
                            break;
                        case 2:
                            offsetX += wobbleRight();
                            break;
                    }
                    vertex(p.x() + offsetX, p.y());
                }
                endShape();
            }
            save("ragged-two", name);
        });
    }

    float wobble() {
        return random(1) > 0.75 ? 0 : random(1) > 0.5 ? -random(1) : random(1);
    }

    float wobbleLeft() {
        return random(1) > 0.95 ? -random(1) : random(1);
    }

    float wobbleRight() {
        return random(1) > 0.05 ? -random(1) : random(1);
    }
}
