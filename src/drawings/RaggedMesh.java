package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class RaggedMesh extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.RaggedMesh");
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
            float offsetX = 200;
            float offsetY = 200;
            ArrayList<ArrayList<Point>> verticalThreads = new ArrayList<>();
            ArrayList<ArrayList<Point>> horizontalThreads = new ArrayList<>();
            for (int y = 0; y < 80; y++) {
                ArrayList<Point> verticalThread = new ArrayList<>();
                for (int x = -40; x < 180; x++) {
                    verticalThread.add(new Point(x * 5, y * 5));
                }
                verticalThreads.add(verticalThread);
            }
            for (int x = 0; x < 120; x++) {
                ArrayList<Point> horizontalThread = new ArrayList<>();
                for (int y = -20; y < 100; y++) {
                    horizontalThread.add(new Point(x * 5, y * 5));
                }
                horizontalThreads.add(horizontalThread);
            }
            for (ArrayList<Point> t : verticalThreads) {
                beginShape();
                for (Point p : t) {
                    vertex(offsetX + p.x(), offsetY + p.y() + wobble());
                }
                endShape();
            }
            for (ArrayList<Point> t : horizontalThreads) {
                beginShape();
                for (Point p : t) {
                    vertex(offsetX + p.x() + wobble(), offsetY + p.y());
                }
                endShape();
            }
            save("ragged-mesh", name);
        });
    }

    float wobble() {
        return random(1) > 0.75 ? 0 : random(1) > 0.5 ? -random(1) : random(1);
    }
}
