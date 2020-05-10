package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;

public class Rule extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Rule");
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
            drawLineTextureWithGaps(colour.black(), _height/(_height/5f), 1, Direction.TOP);
            fill(colour.white());
            stroke(colour.black());
            strokeCap(MITER);
            strokeWeight(1);
            float margin = _height/(_height/150f);
            rect(margin, margin, _width - margin * 2, (_height - margin * 2) + 1);
            noFill();
            strokeWeight(1);
            for (float a = 0; a <= TAU; a += TAU / 16) {
                pushMatrix();
                translate(_width/2, _height/2);
                rotate(a);
                float x = 0;
                float y = 0;
                beginShape();
                for(int i = 1; i < 74; i++) {
                    if(i % 2 == 0) {
                        x += 5;
                    } else {
                        y += i % 3 == 0 ? -(5 * i / 10.5f) : (5 * i / 10.5f);
                    }
                    vertex(x, y);
                }
                endShape();
                popMatrix();
            }
            save("rule", name);
        });
    }
}
