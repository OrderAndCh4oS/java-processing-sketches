package utilities.graph;

import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Random.randomInt;

public class Graph {
    protected final ArrayList<Node> nodes = new ArrayList<Node>();

    public Graph() {
    }

    public void addNode(Point point) {
        nodes.add(new Node(point));
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public ArrayList<Line> getLines() {
        ArrayList<Line> lines = new ArrayList<>();
        for (Node node : nodes) {
            for (Edge edge : node.getEdges()) {
                lines.add(edge.line());
            }
        }
        return lines;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getFirstNode() {
        return nodes.get(0);
    }

    public Node getRandomNode() {
        return nodes.get(randomInt(0, nodes.size() - 1));
    }
}
