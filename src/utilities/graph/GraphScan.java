package utilities.graph;

import utilities.Point;

import static utilities.Trigonometry.getDistance;

public class GraphScan extends Graph {

    public GraphScan() {
    }

    public void joinNodesByHorizontalScan() {
        nodes.sort(new NodeHorizontalComparator());
        for (int i = 0; i < nodes.size(); i++) {
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
            if (closestNodeOne != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeOne));
            }
            if (closestNodeTwo != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeTwo));
            }
        }
    }
}
