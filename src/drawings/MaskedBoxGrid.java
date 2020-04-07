package drawings;

import processing.core.PApplet;
import processing.core.PGraphics;
import sketch.Sketch;

public class MaskedBoxGrid extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.MaskedBoxGrid");
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
            background(colour.bg());
            strokeCap(SQUARE);
            int boxSize = 60;
            int minimum = -2;
            int maximum = boxSize + 2;
            for (int i = 0; i < 1024 / boxSize; i++) {
                for (int j = 0; j < 1024 / boxSize; j++) {
                    PGraphics maskImage;
                    PGraphics sourceImage;
                    pushMatrix();
                    translate((boxSize * i), (boxSize * j));
                    sourceImage = createGraphics(boxSize, boxSize);
                    sourceImage.beginDraw();
                    for (int k = 0; k < 12; k++) {
                        sourceImage.strokeWeight(random(6) + 2);
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
                    maskImage.fill(0xffffffff);
                    maskImage.rect(0, 0, boxSize, boxSize);
                    maskImage.endDraw();
                    sourceImage.mask(maskImage);
                    image(sourceImage, 0, 0);
                    popMatrix();
                }
            }
            save("hashed-box-grid-two", name);
        });
    }
}

