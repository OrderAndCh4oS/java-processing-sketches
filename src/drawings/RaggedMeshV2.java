package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class RaggedMeshV2 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.RaggedMeshV2");
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
            blendMode(MULTIPLY);
            drawRaggedMeshBackground(colour.getAll(), 10, 25, 127.5f, 5);
            save("ragged-mesh-v2", name);
        });
    }

    private void drawRaggedMeshBackground(ArrayList<Integer> colour, float strokeWidth, float gap, float alpha, float wobbleMagnitude) {
        strokeWeight(strokeWidth);
        noFill();
        float offsetX = 0;
        float offsetY = 0;
        ArrayList<ArrayList<Point>> verticalThreads = new ArrayList<>();
        ArrayList<ArrayList<Point>> horizontalThreads = new ArrayList<>();
        for (int y = 0; y < (_height / gap); y++) {
            ArrayList<Point> verticalThread = new ArrayList<>();
            for (int x = -40; x < (_width / gap) + 10; x++) {
                verticalThread.add(new Point(x * gap, y * gap));
            }
            verticalThreads.add(verticalThread);
        }
        for (int x = 0; x < (_width / gap); x++) {
            ArrayList<Point> horizontalThread = new ArrayList<>();
            for (int y = -20; y < (_height / gap) + gap; y++) {
                horizontalThread.add(new Point(x * gap, y * gap));
            }
            horizontalThreads.add(horizontalThread);
        }
        int i = 0;
        for (ArrayList<Point> t : verticalThreads) {
            stroke(colour.get(i % colour.size()), alpha);
            beginShape();
            for (Point p : t) {
                curveVertex(offsetX + p.x(), offsetY + p.y() + wobble() * wobbleMagnitude * random(1));
            }
            endShape();
            i++;
        }
        i = 0;
        for (ArrayList<Point> t : horizontalThreads) {
            stroke(colour.get(i % colour.size()), alpha);
            beginShape();
            for (Point p : t) {
                curveVertex(offsetX + p.x() + wobble() * wobbleMagnitude * random(1), offsetY + p.y());
            }
            endShape();
            i++;
        }
    }

    float wobble() {
        return random(1) > 0.75 ? 0 : random(1) > 0.5 ? -random(1) : random(1);
    }
}
