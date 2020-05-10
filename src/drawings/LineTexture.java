package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;

public class LineTexture extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.LineTexture");
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
            drawLineTextureWithGaps(colour.black(), 5, 1, Direction.TOP);
            save("line-texture", name);
        });
    }
}
