package utilities.graph;

import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Trigonometry.getDistance;

public class Graph {
    private final ArrayList<Node> nodes = new ArrayList<Node>();

    public Graph() {
    }

    public void addNode(Point point) {
        nodes.add(new Node(point));
    }

    public void joinNodesByHorizontalScan() {
        nodes.sort(new NodeHorizontalComparator());
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(nodes.get(i).getPoint().x());
            float yMin = 9999;
            float yMinTwo = 9999;
            int closestNodeOne = -1;
            int closestNodeTwo = -1;
            for (int j = i + 1; j < nodes.size(); j++) {
                Point a = nodes.get(i).getPoint();
                Point b = nodes.get(j).getPoint();
                float distance = getDistance(a, b);
                if (distance < yMin) {
                    yMin = distance;
                    closestNodeOne = j;
                }
            }
            for (int j = i + 1; j < nodes.size(); j++) {
                Point a = nodes.get(i).getPoint();
                Point b = nodes.get(j).getPoint();
                float distance = getDistance(a, b);
                if (distance < yMinTwo && j != closestNodeOne) {
                    yMinTwo = distance;
                    closestNodeTwo = j;
                }
            }
            if(closestNodeOne != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeOne));
            }
            if(closestNodeTwo != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeTwo));
            }
        }
    }

    public ArrayList<Line> getLines() {
        ArrayList<Line> lines = new ArrayList<>();
        for(Node node : nodes) {
            for(Edge edge : node.getEdges()) {
                lines.add(edge.line());
            }
        }
        return lines;
    }
}
