package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class Witness extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Witness");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            int c1 = colour.rand();
            int c2;
            do {
                c2 = colour.rand();
            } while (c1 == c2);
            background(c1);
            drawTexture(colour.black(), 0.1f, 0.8f);
            noFill();
            stroke(c2);
            strokeWeight(12);
            strokeJoin(MITER);
            for (int i = 0; i < 50; i++) {
                float y = i * 16 + 100;
                if (i == 20 || i == 21 || i == 22) {
                    beginShape();
                    vertex(100, y);
                    if (i == 20) {
                        vertex(_width / 2 - 120, y + 15);
                    }
                    if (i == 21) {
                        vertex(_width / 2 - 120, y + 10);
                    }
                    if (i == 22) {
                        vertex(_width / 2 - 120, y + 5);
                    }
                    vertex(_width - 100, y);
                    endShape();
                } else {
                    line(100, y, _width - 100, y);
                }
            }
            save("witness", name);
        });
    }
}
