package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.colour.Colours;
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
            runDraw(colour);
            save("fill-loops-graph", name);

        });
//        Colours colour = _colours.rand();
//        runDraw(colour);
//        save("fill-loops-graph", "fixed");
    }

    private void runDraw(Colours colour) {
        background(colour.white());
        GraphLoops graph = new GraphLoops();
        stroke(colour.black());
        strokeWeight(2);
        connectScanGraph(graph, 250);
//      connectManual(graph);

        for (Node node : graph.getNodes()) {
            ArrayList<Path> paths = graph.findNShortestPathsToSelf(node, node.edgeCount());
            for (Path path : paths) {
                fill(colour.rand());
                beginShape();
                for (Point p : path.getPoints()) {
                    vertex(p.x(), p.y());
                }
                endShape();
            }
        }
        for (Line line : graph.getLines()) {
            line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
        }
    }

    private void connectManual(GraphLoops graph) {
        Node n1 = new Node(new Point(10, 10));
        graph.addNode(n1);
        Node n2 = new Node(new Point(10, 60));
        graph.addNode(n2);
        Node n3 = new Node(new Point(60, 60));
        graph.addNode(n3);
        Node n4 = new Node(new Point(60, 10));
        graph.addNode(n4);

        n1.addEdge(n2);
        n2.addEdge(n3);
        n3.addEdge(n4);
        n4.addEdge(n1);
    }

    public void connectScanGraph(GraphLoops graph, int nodeCount) {
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(-50 + random(_width + 100), -50 + random(_height + 100)));
        }
        graph.joinNodesByHorizontalScan();
    }
}
