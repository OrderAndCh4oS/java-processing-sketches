package utilities.graph;


import java.util.Comparator;

public class NodeHorizontalComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Float.compare(o1.getPoint().x(), o2.getPoint().x());
    }
}
