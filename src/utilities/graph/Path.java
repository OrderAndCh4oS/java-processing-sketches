package utilities.graph;

import utilities.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Path {
    private ArrayList<Edge> _edges = new ArrayList<>();
    private Set<Node> _visitedNodes = new HashSet<Node>();

    public Path() {
    }

    public Path(Path path) {
        for (Edge edge : path.edges()) {
            addEdge(edge);
        }
    }

    public boolean hasVisitedNode(Node node) {
        return _visitedNodes.contains(node);
    }

    public void addEdge(Edge edge) {
        if (_visitedNodes.contains(edge.destination())) return;
        this._edges.add(edge);
        _visitedNodes.add(edge.source());
        _visitedNodes.add(edge.destination());
    }

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<>();
        if (_edges.size() == 0) return points;
        points.add(_edges.get(0).source().getPoint());
        for (Edge edge : _edges) {
            points.add(edge.destination().getPoint());
        }
        return points;
    }

    public ArrayList<Edge> edges() {
        return _edges;
    }

    public Edge getLastEdge() {
        return _edges.get(_edges.size() - 1);
    }
}
