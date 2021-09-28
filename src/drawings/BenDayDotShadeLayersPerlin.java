package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class BenDayDotShadeLayersPerlin extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BenDayDotShadeLayersPerlin");
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
            drawFibreTexture(colour.black(), 450000, 0.2f, 0.5f);
            blendMode(MULTIPLY);
            bendayGrid(colour.get(0), 90 * (TAU/360));
            bendayGrid(colour.get(1), 105 * (TAU/360));
            bendayGrid(colour.get(2), 75 * (TAU/360));
            bendayGrid(colour.size() >= 4 ? colour.get(3) : colour.black(), 15 * (TAU/360));
            save("ben-day-dot-shade-layers-perlin", name);
        });
    }

    public void bendayGrid(int colour, float angle) {
        int columnCount = 4;
        float margin = 180;
        float gutter = 120;
        float size = ((_width - margin * 2) / columnCount) - (gutter / columnCount) * (columnCount - 1);
        for (float y = margin; y <= _height - margin - size; y += size + gutter) {
            for (float x = margin; x <= _width - margin - size; x += size + gutter) {
                pushMatrix();
                translate(x + size / 2, y + size / 2);
                rotate(angle);
                drawBenDayTexturePerlin(-size / 2, -size / 2, size, size, colour, 6);
                popMatrix();
            }
        }
    }
}
