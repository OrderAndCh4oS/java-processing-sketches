package utilities.graph;

import utilities.Point;

import java.util.ArrayList;
import java.util.UUID;

public class Node {
    String _name;
    String _uuid;
    private Point _point;
    private final ArrayList<Edge> edges = new ArrayList<>();

    public String getUuid() {
        return _uuid;
    }

    public String getName() {
        return _name;
    }

    public Node(String name, Point point) {
        this(point);
        this._name = name;
    }

    public Node(Point point) {
        this._uuid = UUID.randomUUID().toString();
        try {
            _point = point.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void addEdge(Node toNode) {
        Edge newEdge = new Edge(this, toNode);
        if(edges.contains(newEdge)) return;
        edges.add(newEdge);
        toNode.addEdge(this);
    }

    public Point getPoint() {
        return _point;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int edgeCount() {return edges.size();}
}
