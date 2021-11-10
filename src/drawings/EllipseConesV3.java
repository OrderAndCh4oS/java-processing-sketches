package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Ellipse.getEllipsePoint;

public class EllipseConesV3 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.EllipseConesV3");
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
            background(colour.white());
            drawFibreTexture(colour.black(), 225000, 0.15f, 0.4f);
            stroke(colour.black());
            strokeWeight(2);
            strokeCap(ROUND);
            float x1 = _width / 2;
            float y1 = _height / 2;
            float x2 = random(_width / 2 - _width / 7,_width / 2 + _width / 7);
            float y2 = random(_height / 2 - _height / 7,_height / 2 + _height / 7);
            float x3 = _width / 2;
            float y3 = _height / 2;
            for (float a = 0; a < TAU; a += (TAU / 360f)) {
                Point p1 = getEllipsePoint(x1, y1, 450 * 2, 450 * 2, a);
                Point p2 = getEllipsePoint(x2, y2, 250 * 2, 250 * 2, a + (TAU / 360f) * 3);
                Point p3 = getEllipsePoint(x3, y3, 100 * 2, 100 * 2, a + -((TAU / 360f) * 3));
                line(p1.x(), p1.y(), p2.x(), p2.y());
                line(p2.x(), p2.y(), p3.x(), p3.y());
            }
            save("ellipse-cones-v2", name);
        });
    }
}
