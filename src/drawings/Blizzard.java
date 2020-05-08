package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Bezier.cubicBezier;
import static utilities.Map.CUBIC;
import static utilities.Map.EASE_IN;

public class Blizzard extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Blizzard");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours("blizzard").forEach((name, colour) -> {
            for (int i = 0; i < 10; i++) {
                background(0xffffffff);
                drawTexture(colour.black(), 0.8f, Direction.LEFT, CUBIC, EASE_IN);
                for (int j = 0; j < 1000; j++) {
                    Point a = new Point(random(_width), random(_height));
                    Point b = new Point(random(_width), random(_height));
                    Point c = new Point(random(_width), random(_height));
                    Point d = new Point(random(_width), random(_height));
                    noFill();
                    stroke(colour.rand());
                    beginShape();
                    for (float t = 0; t < 1; t += 0.01) {
                        Point p = cubicBezier(a, b, c, d, t);
                        vertex(p.x(), p.y());
                    }
                    endShape();
                }
                save("blizzard", name);
            }
        });
    }
}
