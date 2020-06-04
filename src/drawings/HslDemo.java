package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Hsl;

import java.util.stream.IntStream;

public class HslDemo extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.HslDemo");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        background(0xffffffff);
        noStroke();
        Hsl hsl = new Hsl(0xff000000);
        hsl.setS(99);
        for (int h = 0; h < 360; h++) {
            hsl.setH(h);
            float x = map(h, 0, 360, 0, 1024);
            IntStream.range(0, 100).forEach(s -> {
                hsl.setL(s);
                float y = map(s, 0, 100, 0, 1024);
                fill(hsl.getRgba());
                rect(x, y, x + 1024 / 360f, 1024 / 100f);
            });
        }
        save("hsl", "saturation");
        background(0xffffffff);
        for (int h = 0; h < 360; h++) {
            hsl.setH(h);
            float x = map(h, 0, 360, 0, 1024);
            IntStream.range(0, 100).forEach(l -> {
                hsl.setL(l);
                float y = map(l, 0, 100, 0, 1024);
                fill(hsl.getRgba());
                rect(x, y, x + 1024 / 360f, 1024 / 100f);
            });
        }
        save("hsl", "lightness");

    }
}
