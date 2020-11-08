package utilities.graph;

import utilities.Point;

import java.util.ArrayList;

public class Node {
    private Point _point;
    private ArrayList<Edge> edges = new ArrayList<>();

    public Node(Point point) {
        try {
            _point = point.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void addEdge(Node toNode) {
        edges.add(new Edge(this, toNode));
    }

    public Point getPoint() {
        return _point;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}
