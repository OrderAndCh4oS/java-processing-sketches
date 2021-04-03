package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.Point;

import java.util.ArrayList;

public class RaggedMeshV2 extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.RaggedMeshV2");
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
            blendMode(MULTIPLY);
            drawRaggedMeshBackground(colour.getAll(), 10, 25, 127.5f, 5);
            save("ragged-mesh-v2", name);
        });
    }
}
