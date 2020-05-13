package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class BenDayDots extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BenDayDots");
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
            drawBenDayTexture(colour.black(), 10, 0.8f);
            save("ben-day-dots", name);
        });
    }
}
