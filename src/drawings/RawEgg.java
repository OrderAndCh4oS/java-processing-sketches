package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import static utilities.Ellipse.getEllipsePoint;
import static utilities.Map.EASE_IN_OUT;
import static utilities.Map.SINUSOIDAL;

public class RawEgg extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.RawEgg");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            int bg1 = colour.rand();
            int bg2 = colour.rand();
            while (bg1 == bg2) bg2 = colour.rand();
            background(bg1);
            drawTexture(bg2, 0.75f, Direction.TOP, SINUSOIDAL, EASE_IN_OUT);
            noFill();
            strokeWeight(3);
            strokeCap(ROUND);
            for (int i = 0; i < 150; i++) {
                pushMatrix();
                translate(_width / 2, _height / 2);
                rotate(random(TAU));
                float x = random(_width / 2);
                float y = random(_height / 2);
                float a = random(35, 65);
                float b = a * 0.8f;
                int c1 = colour.rand();
                for (float t = TAU / 120; t <= TAU + TAU / 120; t += TAU / 120) {
                    int c2 = colour.rand();
                    while (c2 == c1) {
                        c2 = colour.rand();
                    }
                    stroke(c2);
                    Point p = getEllipsePoint(x, y, a, b, t);
                    line(x, y, p.x(), p.y());
                }
                noStroke();
                fill(c1);
                ellipse(x - 8, y, b * 1.25f, b * 1.25f);
                popMatrix();
            }
            save("raw-egg", name);
        });
    }
}

