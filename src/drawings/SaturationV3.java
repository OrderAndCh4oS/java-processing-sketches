package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;
import utilities.colour.Hsl;

import static utilities.Bezier.cubicBezier;
import static utilities.Random.randomInt;

public class SaturationV3 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.SaturationV3");
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
            background(colour.black());
            noFill();
            int whiteBlack;
            for (int i = 0; i < 80; i++) {
                whiteBlack = i % 2 + 1;
                float x1 = random(-64, -96);
                float y1 = random(_height + 100) - 50;
                float x2 = _width + 20;
                float y2 = random(_height + 100) - 50;
                float x1h = x1 + random(80, 200);
                float y1h = y1 + random(80, 200);
                float x2h = x2 - random(80, 200);
                float y2h = y2 - random(80, 200);
                Point a = new Point(x1, y1);
                Point d = new Point(x2, y2);
                Point c = new Point(x1h, y1h);
                Point b = new Point(x2h, y2h);
                noStroke();
                Hsl c1 = new Hsl(colour.rand());
                int iteration = 0;
                int steps = randomInt(1000, 3000);
                boolean bw = random(1) > 0.5;
                float size = randomInt(32, 128);
                for (float t = 0; t < 1; t += 0.00005) {
                    c1.setH((int) map(t, 0, 1, 0, 360));
                    fill(c1.getRgba());
                    int bwColour = floor(t * 100) % whiteBlack == 0 ? colour.black() : colour.white();
                    int fillColour = bw ? bwColour : c1.getRgba();
                    fill(fillColour);
                    Point p = cubicBezier(a, b, c, d, t);
                    pushMatrix();
                    translate(p.x(), p.y());
                    ellipseMode(CORNERS);
                    ellipse(0, 0, size, size);
                    popMatrix();
                    iteration++;
                    if(iteration > steps) {
                        iteration = 0;
                        steps = randomInt(1000, 3000);
                        bw = !bw;
                    }
                }
            }
            save("saturation-v3", name);
        });
    }
}
