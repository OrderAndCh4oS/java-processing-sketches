package utilities.graph;

import java.util.ArrayList;

public class GraphLoops extends GraphScan {

    public GraphLoops() {
    }

    public Path findShortestPathToSelf(Node startNode) {
        ArrayList<Node> visitedNodes = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        Path foundPath = new Path();

        for (Edge edgeDepthOne : startNode.getEdges()) {
            if (edgeDepthOne.destination().equals(startNode) || visitedNodes.contains(edgeDepthOne.destination()))
                continue;
            visitedNodes.add(edgeDepthOne.destination());
            for (Edge edgeDepthTwo : edgeDepthOne.destination().getEdges()) {
                if (edgeDepthTwo.destination().equals(startNode) || visitedNodes.contains(edgeDepthTwo.destination()))
                    continue;
                Path path = new Path();
                path.addEdge(edgeDepthOne);
                path.addEdge(edgeDepthTwo);
                paths.add(path);
                visitedNodes.add(edgeDepthTwo.destination());
            }
        }

        boolean found = false;
        boolean pathsToExplore = true;

        while (pathsToExplore) {
            if (found) break;
            ArrayList<Path> newPaths = new ArrayList<>();
            int newPathCount = 0;
            for (Path path : paths) {
                if (found) break;

                Edge edgeLast = path.getLastEdge();
                for (Edge edgeDepthN : edgeLast.destination().getEdges()) {
                    if (path.hasVisitedNode(edgeDepthN.destination())) continue;
                    Path nextPath = new Path(path);
                    nextPath.addEdge(edgeDepthN);
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

    public ArrayList<Path> findNShortestPathsToSelf(Node startNode, int n) {
        ArrayList<Node> visitedNodes = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Path> foundPaths = new ArrayList<>();

        for (Edge edgeDepthOne : startNode.getEdges()) {
            if (edgeDepthOne.destination().equals(startNode))
                continue;
            visitedNodes.add(edgeDepthOne.destination());
            for (Edge edgeDepthTwo : edgeDepthOne.destination().getEdges()) {
                if (edgeDepthTwo.destination().equals(startNode))
                    continue;
                Path path = new Path();
                path.addEdge(edgeDepthOne);
                path.addEdge(edgeDepthTwo);
                paths.add(path);
            }
        }

        int found = 0;
        boolean pathsToExplore = true;

        while (pathsToExplore) {
            if (found >= n) break;
            ArrayList<Path> newPaths = new ArrayList<>();
            int newPathCount = 0;
            for (Path path : paths) {
                if (found >= n) break;

                Edge edgeLast = path.getLastEdge();
                for (Edge edgeDepthN : edgeLast.destination().getEdges()) {
                    if (path.hasVisitedNode(edgeDepthN.destination()) && !startNode.equals(edgeDepthN.destination())) continue;
                    Path nextPath = new Path(path);
                    nextPath.addEdge(edgeDepthN);
                    newPaths.add(nextPath);
                    if (edgeDepthN.destination().equals(startNode)) {
                        foundPaths.add(nextPath);
                        found++;
                        break;
                    }
                    visitedNodes.add(edgeDepthN.destination());
                    newPathCount++;
                }
            }
            if (newPathCount == 0) pathsToExplore = false;
            paths = newPaths;
        }

        return foundPaths;
    }
}
