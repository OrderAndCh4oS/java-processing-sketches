package utilities.graph;

import utilities.Line;

public class Edge {
    private Node _a;
    private Node _b;
    private Line _line;

    Edge(Node a, Node b) {
        _a = a;
        _b = b;
        _line = new Line(_a.getPoint(), _b.getPoint());
    }

    public Node a() {
        return _a;
    }

    public Node b() {
        return _b;
    }

    public Line line() {
        return _line;
    }
}
