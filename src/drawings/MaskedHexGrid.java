package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

public class MaskedHexGrid extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.MaskedHexGrid");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024, P3D);
        smooth(8);
    }

    @Override
    public void sketch() {
        _colours.getColours().forEach((name, colour) -> {
            smooth(8);
            background(colour.black());
            strokeCap(SQUARE);
            int boxSize = 60;
            int minimum = -2;
            int maximum = boxSize + 2;
            for (int i = 0; i < 1024 / (boxSize * 0.75f); i++) {
                for (int j = 0; j < (1024f / boxSize) * 3f; j++) {
                    PGraphics maskImage;
                    PGraphics sourceImage;
                    pushMatrix();
                    float offset = 0;
                    if (j % 2 == 0) offset = boxSize - (boxSize * 0.25f);
                    translate(boxSize * i * 1.5f - offset, boxSize * j * 0.4f - 60);
                    sourceImage = createGraphics(boxSize, boxSize);
                    sourceImage.beginDraw();
                    for (int k = 0; k < 12; k++) {
                        sourceImage.stroke(colour.rand());
                        switch ((int) random(4)) {
                            case 0:
                                sourceImage.line(minimum, random(maximum), maximum, random(maximum));
                                break;
                            case 1:
                                sourceImage.line(random(maximum), minimum, maximum, random(maximum));
                                break;
                            case 2:
                                sourceImage.line(random(maximum), minimum, random(maximum), maximum);
                                break;
                            case 3:
                                sourceImage.line(minimum, random(maximum), random(maximum), maximum);
                                break;
                            case 4:
                                sourceImage.line(random(maximum), maximum, maximum, random(maximum));
                                break;
                            case 5:
                                sourceImage.line(maximum, random(maximum), random(maximum), maximum);
                                break;
                            case 6:
                                sourceImage.line(maximum, random(maximum), random(maximum), minimum);
                                break;
                            case 7:
                                sourceImage.line(random(maximum), maximum, random(maximum), minimum);
                                break;
                            case 8:
                                sourceImage.line(random(maximum), maximum, minimum, random(maximum));
                                break;
                            case 9:
                                sourceImage.line(maximum, random(maximum), minimum, random(maximum));
                                break;
                        }
                    }
                    sourceImage.noFill();
                    sourceImage.noStroke();
                    sourceImage.rect(0, 0, boxSize, boxSize);
                    sourceImage.endDraw();
                    maskImage = createGraphics(boxSize, boxSize);
                    maskImage.beginDraw();
                    maskImage.noStroke();
                    maskImage.fill(0xff000000);
                    maskImage.rect(0, 0, boxSize, boxSize);
                    maskImage.fill(0xffffffff);
                    polygonMask(maskImage, boxSize / 2f, boxSize / 2f, boxSize / 2f, 6);
                    maskImage.endDraw();
                    sourceImage.mask(maskImage);
                    image(sourceImage, 0, 0);
                    popMatrix();
                }
            }
            save("hashed-hex-grid-two", name);
        });
    }

    void polygonMask(PGraphics mask, float x, float y, float radius, int nPoints) {
        float angle = TWO_PI / nPoints;
        mask.beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius;
            float sy = y + sin(a) * radius;
            mask.vertex(sx, sy);
        }
        mask.endShape(CLOSE);
    }
}

