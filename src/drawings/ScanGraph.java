package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.PointHorizontalComparator;
import utilities.graph.Graph;

import java.util.ArrayList;

public class ScanGraph extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.ScanGraph");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1910, 1080, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            drawFibreTexture(colour.white(), 250000, 0.2f, 0.5f);
            stroke(colour.white());
            drawScanGraph(10000, 1.5f);
            stroke(colour.rand());
            drawScanGraph(2500, 2.5f);
            stroke(colour.rand());
            drawScanGraph(800, 5f);
            save("scan-graph", name);
        });
    }

    public void drawScanGraph(int nodeCount, float strokeWidth) {
        strokeWeight(strokeWidth);
        Graph graph = new Graph();
        for(int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(-50 + random(_width + 100), -50 + random(_height + 100)));
        }
        graph.joinNodesByHorizontalScan();
        for(Line line : graph.getLines()) {
            line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
        }
    }
}
