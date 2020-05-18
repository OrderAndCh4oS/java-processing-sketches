package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.quadraticBezier;
import static utilities.Ellipse.getEllipsePoint;
import static utilities.Map.EASE_IN;
import static utilities.Map.QUINTIC;

public class Eye extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Eye");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            drawTexture(colour.rand(), 0.8f, 0.2f);
            drawTexture(colour.rand(), 0.6f, 0.4f, 580f);
            pushMatrix();
            translate(_width / 2, _height / 2);
            fill(colour.black());
            ellipse(0, 0, 240, 240);
            noFill();
            strokeWeight(1);
            strokeCap(ROUND);
            for (int i = 0; i < random(4, 9); i++) {
                stroke(colour.rand());
                drawRotations();
            }
            popMatrix();
            float x = 0;
            float y = 0;
            float r = 240;
            float r1 = r + 225;
            float step = TAU / 60;
            PGraphics mask = createGraphics((int) _width, (int) _height);
            mask.beginDraw();
            mask.translate(_width / 2, _height / 2);
            mask.fill(0xff000000);
            mask.rect(0, 0, _width, _height);
            mask.fill(0xffffffff);
            eyeShapeToPGraphic(mask, x, y, r, r1, step);
            mask.endDraw();
            PGraphics source = createGraphics((int) _width, (int) _height);
            source.beginDraw();
            source.fill(0xffE8E7C1);
            source.rect(0, 0, _width, _height);
            drawRadialTextureToSource(source, 0xffFFFAD4, 0.4f, 0.9f, 465f, QUINTIC, EASE_IN);
            source.endDraw();
            source.mask(mask);
            image(source, 0, 0);
            save("eye", name);
        });
    }

    private void eyeShapeToPGraphic(PGraphics source, float x, float y, float r, float r1, float step) {
        source.noStroke();
        source.beginShape();
        for (float t = step; t <= TAU + step; t += step) {
            Point p1 = getEllipsePoint(x, y, r1, r1, t);
            source.vertex(p1.x(), p1.y());
        }
        source.beginContour();
        for (float t = TAU + step; t >= 0; t -= step) {
            Point p = getEllipsePoint(x, y, r, r, t);
            source.vertex(p.x(), p.y());
        }
        source.endContour();
        source.endShape(CLOSE);
    }

    private void drawRotations() {
        rotate(random(TAU));
        float x = 0;
        float y = 0;
        float r = random(65, 80);
        float r1 = r + random(50, 100);
        float r2 = r1 + random(75, 150);
        float step = TAU / 60;
        float mag = random(-3, 3);
        float mag1 = random(4, 12);
        for (float t = step; t <= TAU + step; t += step) {
            Point p = getEllipsePoint(x, y, r, r, t);
            Point p1 = getEllipsePoint(x, y, r1, r1, t + (step * mag));
            Point p2 = getEllipsePoint(x, y, r2, r2, t + (step * mag1));
            beginShape();
            for (float t2 = 0; t2 < 1; t2 += 0.01) {
                Point p3 = quadraticBezier(p, p1, p2, t2);
                curveVertex(p3.x(), p3.y());
            }
            endShape();
        }
    }
}

