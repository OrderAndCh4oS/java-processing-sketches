package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class PerlinTexture extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.PerlinTexture");
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
            background(colour.black());
//            drawPerlinAlpha(colour.rand(), 0.5f, 0.02f);
            drawTexture(colour.rand(), 0.85f, 0.3f);
            drawPerlinTexture(colour.rand(), 0.66f, 0.8f, 0.01f, 0.15f);
            save("perlin-texture", name);
        });
    }
}
