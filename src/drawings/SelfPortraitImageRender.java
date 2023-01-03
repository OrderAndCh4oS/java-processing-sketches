package drawings;

import processing.core.PApplet;
import processing.core.PImage;
import sketch.Sketch;
import utilities.colour.Colours;

import static utilities.Image.extractShadeFromImage;
import static utilities.Random.randomInt;

public class SelfPortraitImageRender extends Sketch {

    public static void main(String... args) {
        PApplet.main("drawings.SelfPortraitImageRender");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1800, 1800, P2D);
        smooth(8);
    }

    @Override
    public void sketch() {
        for(int iter = 0; iter < 6; iter++) {
            _colours.getColours("constructivist-real").forEach((name, colour) -> {
                noiseSeed((long) random(100));
                PImage img = loadImage("/Users/seancooper/IdeaProjects/java-processing-sketches/src/images/me-2.png");
                img.filter(BLUR, 5);
                background(colour.white());
                renderImage(img, colour);
                save("self-portrait-image-render", name);
            });
        }
    }

    public void renderImage(PImage img, Colours colour) {
        int columnCount = 80;
        float margin = 90;
        float gutter = 0;
        float size = ((_width - margin * 2) / columnCount) - (gutter / columnCount) * (columnCount - 1);
        for (float y = margin; y <= _height - margin - size; y += size + gutter) {
            for (float x = margin; x <= _width - margin - size; x += size + gutter) {
                PImage section = img.get((int) (x * 0.55f), (int) (y * 0.55f), 10, 10);
                float shade = extractShadeFromImage(section.pixels);
                pushMatrix();
                translate(x + size / 2, y + size / 2);
                drawSection(-size / 2, -size / 2, size, size, shade, colour);
                popMatrix();
            }
        }
    }

    private void drawSection(float x, float y, float width, float height, float shade, Colours colour) {
        noStroke();
        if (shade < 0.575) {
            fill(colour.black());
            rect(x, y, width, height);
            stroke(colour.white());
        } else {
            fill(colour.white());
            rect(x, y, width, height);
            stroke(colour.get(0));
        }
        strokeWeight(shade * 7);
        strokeCap(ROUND);
        noFill();
        switch(randomInt(0,1)) {
            case 0:
                strokeWeight(shade * 7);
                drawCross(x, y, width, height);
            case 1:
                strokeWeight((shade * 7) + ((randomGaussian() - 0.5f) * 1.5f));
                drawCircle(x, y, width);
        }
    }

    private void drawCross(float x, float y, float width, float height) {
        line(x, y, x + width, y + height);
        line(x + width, y, x, y + height);
    }

    private void drawCircle(float x, float y, float size) {
        circle(x, y, size * 0.75f);
    }
}

