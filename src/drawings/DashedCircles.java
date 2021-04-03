package drawings;

import enums.Direction;
import processing.core.PApplet;
import sketch.Sketch;

import java.util.ArrayList;

import static utilities.Map.*;
import static utilities.Random.randomInt;

public class DashedCircles extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.DashedCircles");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(3600, 3600, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.black());
            drawTexture(colour.white(), 0.2f, 0.4f);
            strokeWeight(4);
            strokeCap(ROUND);
            noFill();
            float radius = _height / 3;
            for(float r = 30; r < radius + 20; r += 20) {
                stroke(colour.randWithWhite());
                drawDashedCircle(r);
            }
            pushMatrix();
            translate(_width / 2, _height / 2);
            for (float a = 0; a < TAU; a += TAU / (256)) {
                pushMatrix();
                stroke(colour.randWithWhite());
                rotate(a);
                boolean on = true;
                float lastX = floor(radius + 16) / 2f;
                for (float x = lastX; x < _height / 2.2f; x += on ? randomInt(8, 16) : randomInt(6, 12)) {
                    if (on) line(lastX, 0, x, 0);
                    on = !on;
                    lastX = x;
                }
                popMatrix();
            }
            popMatrix();
            save("dashed-circles", name);
        });
    }

    private void drawDashedCircle(float radius) {
        pushMatrix();
        translate(_width / 2, _height / 2);
        ArrayList<Float> points = new ArrayList<>();
        float modifier = (TAU / 360) * (random(1) + 2f);
        float startTau = TAU * random(1);
        int i = 0;
        for (float t = startTau; t <= TAU + startTau + modifier * 2; t += modifier) {
            points.add(t);
            if (i % 2 != 0) {
                modifier = (TAU / 360) * (random(1) + 2f);
            } else {
                modifier = (TAU / 360) * (random(1) + 4f);
            }
            i++;
        }
        for (i = 0; i < floor(points.size() / 2f); i++) {
            int index = i * 2;
            float start = points.get(index);
            float end = points.get(index + 1);
            arc(0, 0, radius, radius, start, end);
        }
        popMatrix();
    }
}
