package drawings;

import processing.core.PApplet;
import sketch.Sketch;
import utilities.colour.Hsl;

public class RingIllusion extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.RingIllusion");
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
            noStroke();
            Hsl hsl = new Hsl(0xff000000);
            hsl.setS(99);
            hsl.setL(66);
            for (int x = 100; x < _width - 100; x++) {
                int h = (int) map(x, 100, _width - 100, 0, 360);
                hsl.setH(h);
                fill(hsl.getRgba());
                for (int y = 100; y < _height - 100; y++) {
                    if (random(1) > 0.5f) {
                        rect(x, y, 1, 1);
                    }
                }
            }
            fill(colour.white());
            rect(200, 200, _width - 400, _height - 400);
            fill(colour.black());
            for (int x = 0; x < _width; x+=2) {
                for (int y = 0; y < _height; y+=2) {
                    if (random(1) > 0.6f) {
                        rect(x, y, 2, 2);
                    }
                }
            }
            noFill();
            stroke(0xffff0000);
            strokeWeight(6);
            line(_width/2 - 12, _height/2, _width/2 + 12, _height/2);
            line(_width/2, _height/2 - 12, _width/2, _height/2 + 12);
            save("ring-illusion", name);
        });
    }
}
