package utilities.colour;

import java.awt.*;

import static java.lang.Math.round;
import static utilities.colour.Rgb.*;

public class Hsb {
    int _h;
    int _s;
    int _b;

    public Hsb(int rgb) {
        this(getRed(rgb), getGreen(rgb), getBlue(rgb));
    }

    public Hsb(int r, int g, int b) {
        float[] hsb = Color.RGBtoHSB(r, g, b, null);
        _h = round(hsb[0] * 360);
        _s = round(hsb[2] * 100);
        _b = round(hsb[1] * 100);
    }

    public int h() {
        return _h;
    }

    public int s() {
        return _s;
    }

    public int b() {
        return _b;
    }

    public void darken(int value) {
        _b -= value;
        if (_b < 0) {
            _b = 0;
        }
    }

    public void lighten(int value) {
        _b -= value;
        if (_b > 100) {
            _b = 100;
        }
    }

    public int getRgb() {
        return Color.HSBtoRGB(_h / (float) 360, _s / (float) 100, _b / (float) 100);
    }
}
