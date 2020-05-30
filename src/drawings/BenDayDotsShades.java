package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class BenDayDotsShades extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.BenDayDotsShades");
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
            background(colour.white());
            drawFibreTexture(colour.black(), 250000, 0.2f, 0.5f);
            int columnCount = 12;
            float scale = 1f / (12f * 12f);
            float margin = 80;
            float gutter = 64;
            float size = ((_width - margin * 2) / columnCount) - (gutter / columnCount) * (columnCount - 1);
            for (float y = margin; y <= _height - margin - size; y += size + gutter) {
                for (float x = margin; x <= _width - margin - size; x += size + gutter) {
                    drawBenDayTexture(x, y, size, size, colour.black(), 12, scale);
                    scale += 1f / (12f * 12f);
                }
            }
            save("ben-day-dots-shades", name);
        });
    }
}
