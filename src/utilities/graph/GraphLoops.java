package utilities.graph;

import java.util.ArrayList;

public class GraphLoops extends GraphScan {

    public GraphLoops() {
    }

    public ArrayList<Edge> findShortestPathToSelf(Node startNode) {
        ArrayList<Node> visitedNodes = new ArrayList<>();
        ArrayList<ArrayList<Edge>> paths = new ArrayList<>();
        ArrayList<Edge> foundPath = new ArrayList<>();

        for (Edge edgeDepthOne : startNode.getEdges()) {
            if (edgeDepthOne.destination().equals(startNode) || visitedNodes.contains(edgeDepthOne.destination()))
                continue;
            visitedNodes.add(edgeDepthOne.destination());
            for (Edge edgeDepthTwo : edgeDepthOne.destination().getEdges()) {
                if (edgeDepthTwo.destination().equals(startNode) || visitedNodes.contains(edgeDepthTwo.destination()))
                    continue;
                ArrayList<Edge> path = new ArrayList<>();
                path.add(edgeDepthOne);
                path.add(edgeDepthTwo);
                paths.add(path);
                visitedNodes.add(edgeDepthTwo.destination());
            }
        }

        boolean found = false;
        boolean pathsToExplore = true;

        while (pathsToExplore) {
            if (found) break;
            ArrayList<ArrayList<Edge>> newPaths = new ArrayList<>();
            int newPathCount = 0;
            for (ArrayList<Edge> path : paths) {
                if (found) break;

                Edge edgeLast = path.get(path.size() - 1);
                for (Edge edgeDepthN : edgeLast.destination().getEdges()) {
                    if (visitedNodes.contains(edgeDepthN.destination())) continue;
                    ArrayList<Edge> nextPath = new ArrayList<>();
                    for (Edge e : path) {
                        nextPath.add(e);
                    }
                    nextPath.add(edgeDepthN);
                    newPaths.add(nextPath);
                    if (edgeDepthN.destination().equals(startNode)) {
                        foundPath = nextPath;
                        found = true;
                        break;
                    }
                    visitedNodes.add(edgeDepthN.destination());
                    newPathCount++;
                }
            }
            if (newPathCount == 0) pathsToExplore = false;
            paths = newPaths;
        }

        return foundPath;
    }
}
