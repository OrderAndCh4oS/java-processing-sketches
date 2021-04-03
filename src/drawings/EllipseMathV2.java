package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

import static utilities.Ellipse.getEllipsePoint;

public class EllipseMathV2 extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.EllipseMathV2");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            blendMode(NORMAL);
            background(colour.white());
            ArrayList<Integer> meshColours = new ArrayList<>();
            meshColours.add(colour.black());
            drawRaggedMeshBackground(meshColours, 1, 5, 255 * 0.4f, 1);
            float margin = 100;
            fill(colour.white());
            stroke(colour.black());
            strokeWeight(1);
            strokeCap(MITER);
            blendMode(NORMAL);
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            PGraphics source = createGraphics((int) (_width - margin), (int) (_height - margin));
            source.beginDraw();
            drawTextureToSourceArea(
                new Point(margin, margin),
                new Point(_width - margin, _height - margin),
                source,
                colour.black(),
                0.2f,
                0.2f
            );
            source.endDraw();
            image(source, 0, 0);
            blendMode(MULTIPLY);
            noFill();
            strokeWeight(3);
            strokeCap(ROUND);
            for (int i = 0; i < random(40, 55); i++) {
                pushMatrix();
                translate(_width / 2, _height / 2);
                rotate(random(TAU));
                float x = map(random(_width), 0, _width, 128, _width / 2 - 128);
                float y = map(random(_height), 0, _height, 128, _height / 2 - 128);
                float a = random(35, 65);
                float b = a - random(10, 25);
                for (float t = TAU / 60; t <= TAU + TAU / 60; t += TAU / 60) {
                    Point p = getEllipsePoint(x, y, a, b, t);
                    stroke(colour.rand());
                    line(0, 0, p.x(), p.y());
                }
                popMatrix();
            }
            save("ellipse-math-v2", name);
        });
    }
}

