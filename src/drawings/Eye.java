package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;
import utilities.Point;
import utilities.Vector;

import static utilities.Bezier.quadraticBezier;
import static utilities.Ellipse.getEllipsePoint;
import static utilities.Map.EASE_OUT;
import static utilities.Map.QUARTIC;

public class Eye extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Eye");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600, P2D);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            blendMode(BLEND);
            background(colour.black());
            drawTexture(colour.rand(), 0.75f, 0.15f);
            drawTexture(colour.rand(), 0.6f, 0.4f, _width / 2.1f);
            pushMatrix();
            translate(_width / 2, _height / 2);
            fill(colour.black());
            ellipse(0, 0, _width / 4, _width / 4);
            noFill();
            strokeWeight(4);
            strokeCap(ROUND);
            for (int i = 0; i < random(5, 10); i++) {
                stroke(colour.randWithBlack());
                drawRotations();
            }
            popMatrix();
            float x = 0;
            float y = 0;
            float r = _width / 4.8f;
            float r1 = r + _width / 5.1f;
            float step = TAU / 120;
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
            drawRadialTextureToSource(source, 0xffFFFAD4, 0.4f, 0.9f, _width / 3.3f, QUARTIC, EASE_OUT);
            source.endDraw();
            source.mask(mask);
            image(source, 0, 0);
            for (int i = 0; i < random(640, 720); i++) {
                randomWalkTowardPoint(_width / 2, _height / 2, r1 + 8, random(TAU));
            }
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
        blendMode(LIGHTEST);
        rotate(random(TAU));
        float x = 0;
        float y = 0;
        float r = random(_width / 18f, _width / 13f);
        float r1 = r + random(_width / 20f, _width / 12f);
        float r2 = r1 + random(_width / 12f, _width / 8f);
        float step = TAU / 120;
        float mag = random(-3, 3);
        float mag1 = random(4, 12);
        for (float t = step; t <= TAU + step; t += step) {
            Point p = getEllipsePoint(x, y, r, t);
            Point p1 = getEllipsePoint(x, y, r1, t + (step * mag));
            Point p2 = getEllipsePoint(x, y, r2, t + (step * mag1));
            beginShape();
            for (float t2 = 0; t2 < 1; t2 += 0.01) {
                Point p3 = quadraticBezier(p, p1, p2, t2);
                curveVertex(p3.x(), p3.y());
            }
            endShape();
        }
        blendMode(BLEND);
    }

    private void randomWalkTowardPoint(float x, float y, float r, float t) {
        Vector v = new Vector(getEllipsePoint(x, y, r, t));
        stroke(0xffF2E0C9, random(80, 175));
        strokeWeight(12);
        beginShape();
        for (int i = 0; i < random(5, 88); i++) {
            curveVertex(v.x(), v.y());
            v.addTo(tendTowardCenter(v));
            if(i > 0) v.addTo(new Vector(random(16) - 8,random(16) - 8));
        }
        endShape();
    }

    private Vector tendTowardCenter(Vector v1) {
        Vector vMod = new Vector(_width / 2, _height / 2);
        vMod.subtractFrom(v1);
        vMod.divideBy(100);
        return vMod;
    }
}

