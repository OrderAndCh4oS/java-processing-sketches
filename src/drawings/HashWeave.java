package drawings;

import enums.Direction;
import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

public class HashWeave extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.HashWeave");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            drawLineTextureWithGaps(colour.black(), _height / (_height / 5f), 1, Direction.TOP);
            fill(colour.white());
            noStroke();
            float margin = _height / (_height / 150f);
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            noFill();
            strokeWeight(2);
            PGraphics source = createGraphics((int) (_width - margin * 2), (int) (_height - margin * 2));
            source.beginDraw();
            for (int i = 0; i < 10000; i++) {
                source.pushMatrix();
                source.translate(random(_width - margin * 2), random(_height - margin * 2));
                source.rotate(random(TAU));
                source.noStroke();
                source.fill(colour.white());
                source.rect(0, 0, 30, 12);
                source.noFill();
                source.stroke(colour.rand());
                for (int j = 0; j < 4; j++) {
                    float y = j * 4;
                    source.line(0, y, 30, y);
                }
                source.popMatrix();
            }
            source.endDraw();
            image(source, margin, margin);
            stroke(colour.black());
            strokeCap(MITER);
            strokeWeight(1);
            noFill();
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            save("hash-weave", name);
        });
    }
}
