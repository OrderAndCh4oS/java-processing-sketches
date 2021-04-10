package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.graph.GraphLoops;
import utilities.graph.Node;
import utilities.graph.Path;

import java.util.ArrayList;

import static utilities.Map.EASE_IN_OUT;
import static utilities.Map.SINUSOIDAL;

public class GraphInnerPolygons extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.GraphNoIntersect");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            drawTexture(colour.rand(), 0.75f, Direction.TOP, SINUSOIDAL, EASE_IN_OUT);
            stroke(colour.black());
            strokeWeight(2);
            int noOfLines = 256;
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
                ;
            }
            for (Node node : graph.getNodes()) {
                ArrayList<Path> paths = graph.findNShortestPathsToSelf(node, node.edgeCount());
                for (Path path : paths) {
                    fill(colour.rand());
                    beginShape();
                    for (Point p : path.getPoints()) {
                        vertex(p.x(), p.y());
                    }
                    endShape(CLOSE);
                }
            }
            save("graph-no-intersect", name);
        });
    }
}
