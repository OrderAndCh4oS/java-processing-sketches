package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.graph.GraphLoops;
import utilities.graph.Node;
import utilities.graph.Path;

import java.util.ArrayList;

public class GraphGrid extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.GraphGrid");
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
            ArrayList<GraphLoops> graphList = new ArrayList<>();
            for (int j = 0; j < 64 * 11; j++) {
                GraphLoops graph = new GraphLoops();
                graphList.add(graph);
                for (int i = 0; i < 13; i++) {
                    String nodeName = Integer.toString(i);
                    Node newNode = new Node(nodeName, new Point(random(150), random(150)));
                    graph.addNode(newNode);
                }
                for (int i = 0; i < 64; i++) {
                    int nameOne = (int) random(12);
                    int nameTwo = (int) random(12);
                    while (nameOne == nameTwo) {
                        nameTwo = (int) random(12);
                    }
                    graph.addConnectionByNames(Integer.toString(nameOne), Integer.toString(nameTwo));
                }
            }
            int x = 0;
            int y = 0;
            for (GraphLoops g : graphList) {
                if (x > 0 && x % 66 == 0) {
                    y++;
                }
                pushMatrix();
                translate(x % 66 * 182 + 40, y * 182 + 20);
                for (Node node : g.getNodes()) {
                    ArrayList<Path> paths = g.findNShortestPathsToSelf(node, node.edgeCount());
                    for (Path path : paths) {
                        fill(colour.rand());
                        beginShape();
                        for (Point p : path.getPoints()) {
                            vertex(p.x(), p.y());
                        }
                        endShape();
                    }
                }
                popMatrix();
                x++;
            }
            save("graph-grid", name);
        });
    }
}
