package utilities.graph;

import utilities.Line;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Trigonometry.getDistance;

public class GraphScan extends Graph {

    public GraphScan() {
    }

    private boolean isIntersect(Point a, Point b) {
        Line line = new Line(a, b);
        ArrayList<Line> lines = getLines();
        for (Line l : lines) {
            if(l.q() == a || l.p() == a || l.q() == b || l.p() == b) continue;
            if (line.isIntersect(l)) return true;
        }
        return false;
    }

    public void joinNodesByHorizontalScan() {
        nodes.sort(new NodeHorizontalComparator());
        for (int i = 0; i < nodes.size(); i++) {
            float yMin = 9999;
            float yMinTwo = 9999;
            float yMinThree = 9999;
            int closestNodeOne = -1;
            int closestNodeTwo = -1;
            int closestNodeThree = -1;
            for (int j = i + 1; j < nodes.size(); j++) {
                Point a = nodes.get(i).getPoint();
                Point b = nodes.get(j).getPoint();
                float distance = getDistance(a, b);
                if (distance < yMin && !isIntersect(a, b)) {
                    yMin = distance;
                    closestNodeOne = j;
                }
                if (distance < yMinTwo && j != closestNodeOne && !isIntersect(a, b)) {
                    yMinTwo = distance;
                    closestNodeTwo = j;
                }
                if (distance < yMinThree && j != closestNodeOne && j != closestNodeTwo && !isIntersect(a, b)) {
                    yMinThree = distance;
                    closestNodeThree = j;
                }
            }

            if (closestNodeOne != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeOne));
            }
            if (closestNodeTwo != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeTwo));
            }
            if (closestNodeThree != -1) {
                nodes.get(i).addEdge(nodes.get(closestNodeThree));
            }
        }
    }
}
