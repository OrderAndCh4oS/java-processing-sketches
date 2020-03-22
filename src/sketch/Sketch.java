package sketch;
import collections.ColourCollection;
import processing.core.PApplet;

import static utilities.RandomString.uuid;

abstract public class Sketch extends PApplet {
    protected float _width = 1500;
    protected float _height = 1500;
    protected boolean _save = false;
    protected ColourCollection _colours = new ColourCollection();

    public void settings(int width, int height) {
        _width = width;
        _height = height;
        size((int) width, (int) height);
    }

    public void settings() {
        size((int) _width, (int) _height);
    }

    public void setup() {
        sketch();
    }

    public void draw() {

    }

    public void keyPressed() {
        if(keyCode == 32) saveImage();
    }

    public void save() {
        if(_save) saveImage();
    }

    public void saveImage() {
        String filename = uuid() + ".png";
        saveFrame("/Users/seancooper/Processing Stills/images/" + filename);
    }

    public void save(String prefix) {
        if(_save) saveImage(prefix);
    }

    public void saveImage(String prefix) {
        String filename = uuid() + ".png";
        saveFrame("/Users/seancooper/Processing Stills/images/" + prefix + "-" + filename);
    }

    abstract public void sketch();
}
