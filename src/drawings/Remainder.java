package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

public class Remainder extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Remainder");
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

            int step;
            PGraphics one = createGraphics((int) _width, (int) _height);
            PGraphics two = createGraphics((int) _width, (int) _height);
            PGraphics three = createGraphics((int) _width, (int) _height);
            PGraphics four = createGraphics((int) _width, (int) _height);
            one.beginDraw();
            two.beginDraw();
            three.beginDraw();
            four.beginDraw();
            for (int i = 0; i < _width / 4; i++) {
                float x = i * 4;
                float y = 0;
                int j = 1;
                one.stroke(colour.get(1), 0.2f * 255);
                one.strokeWeight(4);
                one.noFill();
                one.beginShape();
                one.vertex(x, y);
                two.stroke(colour.get(2), 0.5f * 255);
                two.strokeWeight(3);
                two.noFill();
                two.beginShape();
                two.vertex(x, y);
                three.stroke(colour.get(3), 0.8f * 255);
                three.strokeWeight(2);
                three.noFill();
                three.beginShape();
                three.vertex(x, y);
                four.stroke(colour.get(4));
                four.strokeWeight(1);
                four.noFill();
                four.beginShape();
                four.vertex(x, y);
                while (y <= _height) {
                    if ((int) y % j == 0) {
                        step = 4;
                        x = random(1) > 0.5f ? x - step : x + step;
                    } else if ((int) (x / y) % 2 == 0) {
                        step = 7;
                        x = random(1) > 0.5f ? x - step : x + step;
                    } else {
                        step = 7;
                    }

                    y += step;
                    one.vertex(x, y);
                    two.vertex(x, y);
                    three.vertex(x, y);
                    four.vertex(x, y);
                    j++;
                }
                one.endShape();
                two.endShape();
                three.endShape();
                four.endShape();
            }
            one.endDraw();
            two.endDraw();
            three.endDraw();
            four.endDraw();
            image(one, 0, 0);
            image(two, 0, 0);
            image(three, 0, 0);
            image(four, 0, 0);
            save("remainder", name);
        });
    }
}
