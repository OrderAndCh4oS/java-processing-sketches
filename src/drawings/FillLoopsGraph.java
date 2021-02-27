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
        super.settings(500, 500, P2D);
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
//        connectScanGraph(graph, 250);
        connectManual(graph);

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
        strokeJoin(ROUND);
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

        Node n5 = new Node(new Point(60, 100));
        graph.addNode(n5);
        Node n6 = new Node(new Point(100, 100));
        graph.addNode(n6);
        Node n7 = new Node(new Point(80, 10));
        graph.addNode(n7);

        Node n8 = new Node(new Point(60, 150));
        graph.addNode(n8);
        Node n9 = new Node(new Point(200, 150));
        graph.addNode(n9);
        Node n10 = new Node(new Point(100, 10));
        graph.addNode(n10);

        Node n11 = new Node(new Point(10, 120));
        graph.addNode(n11);
        Node n12 = new Node(new Point(40, 120));
        graph.addNode(n12);
        Node n13 = new Node(new Point(40, 100));
        graph.addNode(n13);

        Node n14 = new Node(new Point(490, 10));
        graph.addNode(n14);

        Node n15 = new Node(new Point(10, 490));
        graph.addNode(n15);

        Node n16 = new Node(new Point(490, 490));
        graph.addNode(n16);


        n1.addEdge(n2);
        n2.addEdge(n3);
        n3.addEdge(n4);
        n4.addEdge(n1);

        n3.addEdge(n5);
        n5.addEdge(n6);
        n6.addEdge(n7);
        n7.addEdge(n4);

        n5.addEdge(n8);
        n8.addEdge(n9);
        n9.addEdge(n10);
        n10.addEdge(n7);

        n2.addEdge(n11);
        n11.addEdge(n12);
        n12.addEdge(n13);
        n13.addEdge(n5);

        n9.addEdge(n14);
        n14.addEdge(n10);

        n11.addEdge(n15);
        n15.addEdge(n9);

        n14.addEdge(n15);

        n16.addEdge(n15);
        n16.addEdge(n14);
    }

    public void connectScanGraph(GraphLoops graph, int nodeCount) {
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(-50 + random(_width + 100), -50 + random(_height + 100)));
        }
        graph.joinNodesByHorizontalScan();
    }
}
