package sketch;
import processing.core.PApplet;

import static utilities.RandomString.uuid;

abstract public class Sketch extends PApplet {
    float width = 1500;
    float height = 1500;

    public void settings() {
        size((int) width, (int) height);
    }

    public void setup() {
        sketch();
    }

    public void draw() {

    }

    public void keyPressed() {
        if(keyCode == 32) {
            String filename = uuid() + ".tif";
            saveFrame("/Users/seancooper/Processing Stills/images/" + filename);
        }
    }

    abstract public void sketch();
}
