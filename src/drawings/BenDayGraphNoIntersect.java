package drawings;

import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Colours;
import utilities.graph.GraphLoops;
import utilities.graph.Node;
import utilities.graph.Path;

import java.util.ArrayList;
import java.util.Random;

import static utilities.Map.EASE_IN_OUT;
import static utilities.Map.SINUSOIDAL;
import static utilities.Random.randomInt;

public class BenDayGraphNoIntersect extends Sketch {

    ArrayList<PGraphics> sources;
    ArrayList<PGraphics> masks;

    public static void main(String... args) {
        PApplet.main("drawings.BenDayGraphNoIntersect");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            sources = new ArrayList<>();
            int layerCount = 36;
            for(int i = 0; i < layerCount; i++) {
                PGraphics source = createGraphics((int) _width, (int) _height);
                source.beginDraw();
                int c1 = colour.rand();
                int c2;
                do {
                    c2 = colour.rand();
                } while (c1 == c2);
                drawTexture(source, c2, 0.15f, Direction.rand(), SINUSOIDAL, EASE_IN_OUT);
                drawBenDayTexture(source, c1, random(8, 16), random(0.33f, 0.85f));
                source.endDraw();
                sources.add(source);
            }
            masks = new ArrayList<>();
            for(int i = 0; i < layerCount; i++) {
                PGraphics mask = createGraphics((int) _width, (int) _height);
                mask.beginDraw();
                mask.fill(0xff000000);
                mask.noStroke();
                mask.rect(0, 0, _width, _height);
                masks.add(mask);
            }
            int c1 = colour.randWithWhite();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            background(colour.black());
            drawWaveTexture(c1, 0.25f, 6, 4, 1, 255 * 0.7f);
            drawTexture(c2, 0.75f, Direction.TOP, SINUSOIDAL, EASE_IN_OUT);
            noStroke();
            drawBlock(colour);
            for (int i = 0; i < sources.size(); i++) {
                PGraphics source = sources.get(i);
                source.mask(masks.get(i));
                image(source, 0, 0);
            }
            save("ben-day-graph-no-intersect", name);
        });
    }

    private void drawBlock(Colours colour) {
        int noOfLines = 256;
        GraphLoops graph = new GraphLoops();
        for (int i = 0; i < noOfLines / 3; i++) {
            Point point = new Point(
                    random(_width * 0.9f) + _width * 0.05f,
                    random(_height * 0.9f) + _height * 0.05f
            );
            Node newNode = new Node(point);
            graph.addNode(newNode);
        }
        int lineCount = 0;
        while (lineCount < noOfLines) {
            Node nodeOne = graph.getRandomNode();
            Node nodeTwo;
            do {
                nodeTwo = graph.getRandomNode();
            } while (nodeOne == nodeTwo);
            if (graph.addConnectionNoIntersects(nodeOne, nodeTwo)) {
                lineCount++;
            }
        }
        for (Node node : graph.getNodes()) {
            ArrayList<Path> paths = graph.findNShortestPathsToSelf(node, node.edgeCount());
            for (Path path : paths) {
                drawShape(path);
            }
        }
    }

    private void drawShape(Path path) {
        int randomIndex = new Random().nextInt(this.masks.size());
        PGraphics mask = this.masks.get(randomIndex);
        mask.fill(0xffffffff);
        mask.beginShape();
        for (Point p : path.getPoints()) {
            mask.vertex(p.x(), p.y());
        }
        mask.endShape(CLOSE);
    }

    public void drawBenDayTexture(PGraphics source, int colour, float diameter, float scale) {
        source.fill(colour);
        source.noStroke();
        int rowIndex = 0;
        for (float x = -diameter; x < _width + diameter; x += diameter) {
            for (float y = rowIndex % 2 == 0 ? 0 : -(diameter / 2f); y < _height + diameter; y += diameter) {
                source.ellipse(x, y, diameter * scale, diameter * scale);
            }
            rowIndex++;
        }
    }
}

