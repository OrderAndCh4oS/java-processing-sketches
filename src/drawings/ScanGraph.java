package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Line;
import utilities.Point;
import utilities.Random;
import utilities.colour.Colour;
import utilities.graph.GraphScan;

public class ScanGraph extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.ScanGraph");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for (int i = 0; i < 3; i++) {
            _colours.getColours().forEach((name, colour) -> {
                background(colour.black());
                drawFibreTexture(colour.white(), 250000, 0.2f, 0.5f);
                strokeCap(ROUND);
                int c1 = colour.rand();
                int c2 = colour.rand();
                int c3 = colour.rand();
                while (c2 == c1) {
                    c2 = colour.rand();
                }
                while (c3 == c1 || c3 == c2) {
                    c3 = colour.rand();
                }
                drawScanGraph(Random.randomInt(50, 100), 3f, colour.white());
                drawScanGraph(Random.randomInt(66, 132), 9f, c3);
                drawScanGraph(Random.randomInt(75, 150), 6f, c2);
                save("scan-graph", name);
            });
        }
    }

    public void drawScanGraph(int nodeCount, float strokeWidth, int colour) {
        strokeWeight(strokeWidth);
        GraphScan graph = new GraphScan();
        for (int i = 0; i < nodeCount; i++) {
            graph.addNode(new Point(-50 + random(_width + 100), -50 + random(_height + 100)));
        }
        graph.joinNodesByHorizontalScan();
        for (float i = 0.95f; i >= 0.0075; i -= (i - i * 0.33)) {
            strokeWeight(lerp(0.2f, strokeWidth, i));
            stroke(colour, 255 - (255 * i));
            for (Line line : graph.getLines()) {
                line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
            }
        }
        blendMode(SCREEN);
        strokeWeight(strokeWidth * 0.5f);
        stroke(colour, 255);
        for (Line line : graph.getLines()) {
            line(line.p().x(), line.p().y(), line.q().x(), line.q().y());
        }
        blendMode(NORMAL);
    }
}
