package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.Polygon;
import utilities.graph.GraphLoops;
import utilities.graph.Node;
import utilities.graph.Path;

import java.util.ArrayList;

public class GraphInnerPolygonsNoIntersects extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.GraphInnerPolygonsNoIntersects");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int iter = 0; iter < 10; iter++) {
            _colours.getColours("salmon-lemon").forEach((name, colour) -> {
                background(colour.black());
                int noOfLines = 120;
                ArrayList<Polygon> polygons = new ArrayList<>();
                GraphLoops graph = new GraphLoops();
                for (int i = 0; i < noOfLines / 3; i++) {
                    Point point = new Point(
                            random(_width * 0.8f) + _width * 0.1f,
                            random(_height * 0.8f) + _height * 0.1f
                    );
                    Node newNode = new Node(point);
                    graph.addNode(newNode);
                }
                int lineCount = 0;
                while (lineCount < noOfLines) {
                    Node nodeOne = graph.getRandomNode();
                    Node nodeTwo;
                    do {
                        nodeTwo = graph.getRandomNode();
                    } while (nodeOne == nodeTwo);
                    if (graph.addConnectionNoIntersects(nodeOne, nodeTwo)) {
                        lineCount++;
                    }
                }
                for (Node node : graph.getNodes()) {
                    ArrayList<Path> paths = graph.findNShortestPathsToSelf(node, node.edgeCount());
                    for (Path path : paths) {
                        polygons.add(new Polygon(path.getPoints().toArray(new Point[0])));
                    }
                }
                for (Polygon polygon : polygons) {
                    noStroke();
                    fill(colour.randWithBlack());
                    beginShape();
                    for (Point p : polygon.getPoints()) {
                        vertex(p.x(), p.y());
                    }
                    polygon.scale(0.85f);
                    endShape(CLOSE);
                }
                save("graph-inner-polygons", name);
            });
        }
    }
}
