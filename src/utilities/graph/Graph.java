package utilities.graph;

import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Random.randomInt;

public class Graph {
    protected final ArrayList<Node> nodes = new ArrayList<>();
    protected final ArrayList<Edge> edges = new ArrayList<>();

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

    public void addConnectionByNames(String srcNodeName, String destinationNodeName) {
        try {
            Node sourceNode = findNode(srcNodeName);
            Node destinationNode = findNode(destinationNodeName);
            if (!hasConnection(sourceNode, destinationNode)) {
                Edge edge = new Edge(sourceNode, destinationNode);
                edges.add(edge);
                sourceNode.addEdge(destinationNode);
            }
        } catch (NotFoundException e) {
            System.exit(1);
        }
    }

    public boolean hasConnection(Node source, Node destination) {
        for (Edge e : edges) {
            if (e.source() == source && e.destination() == destination) {
                return true;
            }
        }

        return false;
    }

    public Node findNode(String nodeName) throws NotFoundException {
        for (Node n : nodes) {
            if (n.getName().equals(nodeName)) {
                return n;
            }
        }

        throw new NotFoundException("Node not found");
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}

class NotFoundException extends Exception {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
