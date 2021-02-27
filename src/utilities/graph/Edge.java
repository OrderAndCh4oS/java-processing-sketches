package utilities.graph;

import utilities.Line;

public class Edge {
    private Node _source;
    private Node _destination;
    private Line _line;

    Edge(Node source, Node destination) {
        _source = source;
        _destination = destination;
        _line = new Line(_source.getPoint(), _destination.getPoint());
    }

    public Node source() {
        return _source;
    }

    public Node destination() {
        return _destination;
    }

    public Line line() {
        return _line;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (obj instanceof Edge)
        {
            isEqual = this._destination == ((Edge) obj)._destination && this._source == ((Edge) obj)._source;
        }

        return isEqual;
    }
}
