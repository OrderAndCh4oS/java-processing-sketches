package drawings;

import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Random;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class DeadEndV2 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DeadEndV2");
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
            blendMode(NORMAL);
            background(colour.white());
            drawLineTextureWithGaps(colour.black(), _height/(_height/5f), 1, Direction.TOP);
            float margin = 100;
            fill(colour.white());
            stroke(colour.black());
            strokeWeight(1);
            strokeCap(MITER);
            blendMode(NORMAL);
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            PGraphics source = createGraphics((int) (_width - margin * 2), (int) (_height - margin * 2));
            source.beginDraw();
            ArrayList<ArrayList<Line>> paths = new ArrayList<>();
            HashMap<Integer, Vector> pathTracers = new HashMap<>();
            ArrayList<Vector> moves = new ArrayList<>();
            moves.add(new Vector(10, 0));
            moves.add(new Vector(-10, 0));
            moves.add(new Vector(0, 10));
            moves.add(new Vector(0, -10));
            for (int i = 0; i < 1500; i++) {
                pathTracers.put(i, new Vector(Random.random(-100, _width - margin * 2 + 100), Random.random(-100, _height - margin * 2 + 100)));
                paths.add(new ArrayList<>());
            }
            for (int i = 0; i < 125; i++) {
                Iterator<Entry<Integer, Vector>> iter = pathTracers.entrySet().iterator();
                while (iter.hasNext()) {
                    Entry<Integer, Vector> entry = iter.next();
                    Vector pathTracer = entry.getValue();
                    int index = entry.getKey();
                    try {
                        Point p = pathTracer.getPoint().clone();
                        pathTracer.addTo(moves.get(Random.randomInt(0, 3)));
                        Line l = new Line(p, pathTracer.getPoint().clone());
                        if (outOfBounds(pathTracer.getPoint())) {
                            iter.remove();
                            continue;
                        }
                        paths.get(index).add(l);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                if (pathTracers.size() == 0) {
                    break;
                }
            }
            for (ArrayList<Line> path : paths) {
                source.stroke(colour.randWithBlack(), 127.5f);
                source.noFill();
                source.beginShape();
                for (Line l : path) {
                    source.vertex(l.p().x(), l.p().y());
                }
                source.endShape();
            }
            source.endDraw();
            image(source, margin, margin);
            save("dead-end-v2", name);
        });
    }

    private boolean outOfBounds(Point p) {
        return p.x() < 0 || p.x() > 1500 || p.y() < 0 || p.x() > 1500;
    }

    private boolean hasSamePoint(Line l, Line l2) {
        return l2.p() == l.p() || l2.q() == l.q() || l2.p() == l.q() || l2.q() == l.p();
    }
}
