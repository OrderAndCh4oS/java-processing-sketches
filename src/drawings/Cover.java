package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import sketch.Sketch;

public class Cover extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.Cover");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1446, 2064, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            background(colour.white());
            drawFibreTexture(colour.black(), 250000, 0.2f, 0.5f);
//            String[] hiragana = {"あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "さ", "し", "す", "せ", "そ", "た", "ち", "つ", "て", "と", "な", "に", "ぬ", "ね", "の", "は", "ひ", "ふ", "へ", "ほ", "ま", "み", "む", "め", "も", "や", "ゆ", "よ", "ら", "り", "る", "れ", "ろ", "わ"};
//            noStroke();
//            int c1 = colour.rand();
//            float size = 92;
//            float x = 0;
//            float y = 0;
//            float offset = 231;
//            float gap = 16;
//            PFont yumin = createFont("YuMin-Medium", size / 3 * 2);
//            PFont agmena = createFont("AgmenaPro-Book", 210);
//            for (String character : hiragana) {
//                PGraphics source = createGraphics((int) size, (int) size);
//                source.beginDraw();
//                source.textFont(yumin);
//                source.textAlign(CENTER);
//                source.noStroke();
//                source.fill(c1);
//                source.ellipse(size / 2, size / 2, size, size);
//                source.fill(colour.white());
//                source.text(character, size / 2, (size / 3 * 2) + 4);
//                source.endDraw();
//                image(source, offset + x, offset + y);
//                x = (x + size + gap) % ((size + gap) * 9);
//                if (x == 0) {
//                    y += size + gap;
//                }
//            }
//
//            fill(colour.black());
//            textFont(agmena);
//            text("PCD Tokyo", 231, 1424);
//            fill(c1);
//            textSize(410);
//            text("2020", 226, 1754);
            save("cover", name);
        });
    }
}
