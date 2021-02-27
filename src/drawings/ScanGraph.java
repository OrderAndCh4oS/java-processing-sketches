package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.graph.GraphScan;

public class ScanGraph extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.ScanGraph");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3072, 1910, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            drawFibreTexture(colour.white(), 250000, 0.2f, 0.5f);
            stroke(colour.white());
            drawScanGraph(15000, 1.5f);
            stroke(colour.rand());
            drawScanGraph(3500, 3f);
            stroke(colour.rand());
            drawScanGraph(1000, 6f);
            save("scan-graph", name);
        });
    }

    public void drawScanGraph(int nodeCount, float strokeWidth) {
        strokeWeight(strokeWidth);
        GraphScan graph = new GraphScan();
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(-50 + random(_width + 100), -50 + random(_height + 100)));
        }
        graph.joinNodesByHorizontalScan();
        for (Line line : graph.getLines()) {
            line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
        }
    }
}
