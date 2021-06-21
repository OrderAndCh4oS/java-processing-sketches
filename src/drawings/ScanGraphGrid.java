package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Random;
import utilities.graph.GraphScan;

public class ScanGraphGrid extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.ScanGraphGrid");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(504, 698, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int i = 0; i < 20; i++) {
            _colours.getColours("warm-grey", "toxic", "candy", "pistachio-and-peach", "order-and-chaos").forEach((name, colour) -> {
                background(0xffF7F2EB);
                int c1 = colour.rand();
                int c2 = colour.rand();
                int c3 = colour.rand();
                while (c2 == c1) {
                    c2 = colour.rand();
                }
                while (c3 == c1 || c3 == c2) {
                    c3 = colour.rand();
                }
                drawScanGraph(100, 5f, c1);
                drawScanGraph(75, 2.5f, colour.white());
                drawScanGraph(50, 7f, c1);
                save("scan-graph-grid", name);
            });
            _colours.getColours("constructivist-real").forEach((name, colour) -> {
                background(0xff1A1C1E);
                int c1 = colour.rand();
                int c2 = colour.rand();
                int c3 = colour.rand();
                while (c2 == c1) {
                    c2 = colour.rand();
                }
                drawScanGraph(75, 3f, c1);
                drawScanGraph(50, 2f, colour.black());
                drawScanGraph(25, 5f, c1);
                save("scan-graph-grid", name);
            });
        }
    }

    public void drawScanGraph(int nodeCount, float strokeWidth, int colour) {
        strokeWeight(strokeWidth);
        stroke(colour);
        GraphScan graph = new GraphScan();
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(Random.randomInt(0, 1000/30) * 30 - 200, Random.randomInt(0, 1000/30) * 30 - 200));
        }
        graph.joinNodesByHorizontalScan();
        for (float i = 0.95f; i >= 0.0075; i -= (i - i * 0.33)) {
            for (Line line : graph.getLines()) {
                line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
            }
        }
    }
}
