package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class One extends Sketch {
    
    public static void main(String... args) {
        PApplet.main("drawings.One");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.get_colours().forEach((name, colour) -> {
            background(colour.bg());
            save("one", name);
        });
    }
}
