package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class BenDayDotShadeLayers extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BenDayDotShadeLayers");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(2048, 2048, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            blendMode(BLEND);
            background(colour.white());
            drawFibreTexture(colour.black(), 250000, 0.2f, 0.5f);
            blendMode(MULTIPLY);
            bendayGrid(colour.rand(), 0);
            bendayGrid(colour.rand(), PI / 36);
            bendayGrid(colour.rand(), -(PI / 36));
            save("ben-day-dot-shade-layers", name);
        });
    }

    public void bendayGrid(int colour, float angle) {
        int columnCount = 12;
        float scale = 1f / (12f * 12f);
        float margin = 80;
        float gutter = 64;
        float size = ((_width - margin * 2) / columnCount) - (gutter / columnCount) * (columnCount - 1);
        for (float y = margin; y <= _height - margin - size; y += size + gutter) {
            for (float x = margin; x <= _width - margin - size; x += size + gutter) {
                pushMatrix();
                translate(x + size / 2, y + size / 2);
                rotate(angle);
                drawBenDayTexture(-size / 2, -size / 2, size, size, colour, 12, scale);
                popMatrix();
                scale += 1f / (12f * 12f);
            }
        }
    }
}
