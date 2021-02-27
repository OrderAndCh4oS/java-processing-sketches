package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.graph.GraphLoops;
import utilities.graph.Node;
import utilities.graph.Path;

import java.util.ArrayList;

public class FillLoopsGraph extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.FillLoopsGraph");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3072, 1910, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            GraphLoops graph = new GraphLoops();
            stroke(colour.black());
            connectGraph(graph,500, 2);
            for(Node node : graph.getNodes()) {
                ArrayList<Path> paths = graph.findNShortestPathsToSelf(node, node.edgeCount());
                for(Path path : paths) {
                    fill(colour.rand());
                    beginShape();
                    for(Point p : path.getPoints()) {
                        vertex(p.x(), p.y());
                    }
                    endShape();
                }
            }
            for (Line line : graph.getLines()) {
                line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
            }
            save("fill-loops-graph", name);
        });
    }

    public void connectGraph(GraphLoops graph, int nodeCount, float strokeWidth) {
        strokeWeight(strokeWidth);
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(-50 + random(_width + 100), -50 + random(_height + 100)));
        }
        graph.joinNodesByHorizontalScan();
    }
}
