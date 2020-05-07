package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Random;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class DeadEnd extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DeadEnd");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1500, 1500, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            ArrayList<ArrayList<Line>> paths = new ArrayList<>();
            HashMap<Integer, Vector> pathTracers = new HashMap<>();
            ArrayList<Vector> moves = new ArrayList<>();
            moves.add(new Vector(10, 0));
            moves.add(new Vector(-10, 0));
            moves.add(new Vector(0, 10));
            moves.add(new Vector(0, -10));
            for (int i = 0; i < 1500; i++) {
                pathTracers.put(i, new Vector(Random.random(50, 1450), Random.random(50, 1450)));
                paths.add(new ArrayList<>());
            }
            for (int i = 0; i < 100; i++) {
                Iterator<Entry<Integer, Vector>> iter = pathTracers.entrySet().iterator();
                while (iter.hasNext()) {
                    Entry<Integer, Vector> entry = iter.next();
                    Vector pathTracer = entry.getValue();
                    int index = entry.getKey();
                    try {
                        Point p = pathTracer.getPoint().clone();
                        pathTracer.addTo(moves.get(Random.randomInt(0, 3)));
                        Line l = new Line(p, pathTracer.getPoint().clone());
//                        boolean isIntersect = false;
//                        for (ArrayList<Line> path : paths) {
//                            for (Line l2 : path) {
//                                if (l2.isIntersect(l) && !hasSamePoint(l, l2)) {
//                                    isIntersect = true;
//                                    break;
//                                }
//                            }
//                            if (isIntersect) break;
//                        }
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
                stroke(colour.rand());
                for (Line l : path) {
                    line(l.p().x(), l.p().y(), l.q().x(), l.q().y());
                }
            }
            save("dead-end", name);
        });
    }

    private boolean outOfBounds(Point p) {
        return p.x() < 0 || p.x() > 1500 || p.y() < 0 || p.x() > 1500;
    }

    private boolean hasSamePoint(Line l, Line l2) {
        return l2.p() == l.p() || l2.q() == l.q() || l2.p() == l.q() || l2.q() == l.p();
    }
}
