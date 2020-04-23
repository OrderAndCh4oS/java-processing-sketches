package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import sketch.Sketch;

import static utilities.Map.*;
import static utilities.Random.randomInt;

public class TwoYearsAgo extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.TwoYearsAgo");
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
            background(colour.rand());
            noFill();
            strokeWeight(random(2, 4));
            strokeJoin(MITER);
            strokeCap(ROUND);
            int rowHeight = randomInt(8, 14);
            float frequency = random(20, 50);
            float amplitude = random(6, 30);
            stroke(colour.bg());
            for (int i = 0; i < _height / rowHeight; i++) {
                float minX = random(24, 100);
                float y = i * rowHeight;
                float angle = 0;
                beginShape();
                for (int x = (int) _width; x > minX; x--) {
                    float d = distanceToMidpoint(x, y);
                    float currentAmplitude = map(d, 0, _width/2, amplitude, 0);
                    float newY = 0;
                    if (d < 750f / 2f) {
                        float dY = sin(frequency * angle) * currentAmplitude;
                        newY = dY - dY / 2;
                    }
                    curveVertex(x, y + newY);
                    angle += TAU / _width;
                }
                endShape();
                beginShape();
                float arrowLength = (rowHeight / 2f) - 1;
                vertex(minX + arrowLength, y - arrowLength);
                vertex(minX, y);
                vertex(minX + arrowLength, y + arrowLength);
                endShape();
            }
            fill(colour.rand());
            PFont font = createFont("ModernTwoSxtnITCStd-Bold", 800);
            textFont(font);
            textAlign(CENTER);
            text("2", _width/2, 758);
            save("two-years-ago", name);
        });
    }

    private float distanceToMidpoint(float x, float y) {
        float dX = _midPoint.x() - x;
        float dY = _midPoint.y() - y;

        return sqrt((dX * dX) + (dY * dY));
    }
}
