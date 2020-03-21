package utilities;

public class Colour {
    int _colour;

    public Colour(int r, int g, int b) {
        _colour = Rgb.toRgb(r, g, b);
    }

    public Colour(int colour) {
        _colour = colour;
    }

    public int r() {
        return Rgb.getRed(_colour);
    }

    public int g() {
        return Rgb.getGreen(_colour);
    }

    public int b() {
        return Rgb.getBlue(_colour);
    }

    public int get() {
        return _colour;
    }
}
