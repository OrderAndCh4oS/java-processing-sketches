package utilities.colour;

import java.util.Arrays;

import static java.lang.Math.round;
import static utilities.colour.Rgb.*;

public class Hsl {
    float _r;
    float _g;
    float _b;
    float _min;
    float _max;
    int _h = 0;
    int _s = 0;
    int _l = 0;

    Hsl(int rgb) {
        this(getRed(rgb), getGreen(rgb), getBlue(rgb));
    }

    Hsl(int r, int g, int b) {
        _r = (float) r / 255;
        _g = (float) g / 255;
        _b = (float) b / 255;
        float[] rgbNormalised = {_r, _g, _b};
        Arrays.sort(rgbNormalised);
        _min = rgbNormalised[0];
        _max = rgbNormalised[2];
        System.out.println(_min);
        System.out.println(_max);
        _h = calcH();
        _l = calcL();
        _s = calcS();
    }

    private int calcH() {
        if (_max == _r) {
            _h = round(((_g - _b) / (_max - _min)) * 60);
        } else if (_max == _g) {
            _h = round(((float) 2.0 + (_b - _r) / (_max - _min)) * 60);
        } else if (_max == _b) {
            _h = round(((float) 4.0 + (_r - _g) / (_max - _min)) * 60);
        }
        if (_h < 0) _h += 360;
        return _h;
    }

    private int calcL() {
        return round(((_min + _max) / 2) * 100);
    }

    private int calcS() {
        if (_min == _max) return 0;
        System.out.println("l");
        System.out.println(_l);
        if (_l < 50) {
            _s = round(((_max - _min) / (_max + _min)) * 100);
        } else {
            _s = round(((_max - _min) / ((float) 2.0 - _max - _min)) * 100);
        }
        return _s;
    }

    public int h() {
        return _h;
    }

    public int s() {
        return _s;
    }

    public int l() {
        return _l;
    }

    public float r() {
        return _r;
    }

    public float g() {
        return _g;
    }

    public float b() {
        return _b;
    }

    public void darken(int value) {
        _l -= value;
        if (_l < 0) {
            _l = 0;
        }
    }

    public void lighten(int value) {
        _l -= value;
        if (_l > 100) {
            _l = 100;
        }
    }

    public int getRgb() {
        float l = _l / (float) 100;
        float s = _s / (float) 100;
        if (s == 0) {
            int grey = round(l * 255);
            return Rgb.toRgb(grey, grey, grey);
        }
        float x1, x2, x3, tr, tg, tb;

        if (l < 50) {
            x1 =  l * ((float) 1.0 + s);
        } else {
            x1 = l + s - (l * s);
        }

        System.out.println(x1);

        x2 = 2 * l - x1;
        x3 = _h / (float) 360;

        tr = x3 + (float) 0.333;
        if (tr > 1) tr = tr - 1;
        tg = x3;
        tb = x3 - (float) 0.333;
        if (tb < 0) tb = tb - 1;

        _r = toRgb(x1, x2, tr);
        _g = toRgb(x1, x2, tg);
        _b = toRgb(x1, x2, tb);

        return Rgb.toRgb(round(_r * 255), round(_g * 255), round(_b * 255));
    }

    private float toRgb(float x1, float x2, float tc) {
        if ((6 * tc) < 1) return x2 + (x1 - x2) * 6 * tc;
        else if ((2 * tc) < 1) return x1;
        else if ((3 * tc) < 2) return (x2 + (x1 - x2) * ((float) 0.666 - tc) * 6);
        else return x2;
    }
}

class Main {
    public static void main(String[] args) {
        Hsl hsl = new Hsl(0x186277);
        System.out.println(hsl.h());
        System.out.println(hsl.s());
        System.out.println(hsl.l());
        System.out.println(hsl.r() * 255);
        System.out.println(hsl.g() * 255);
        System.out.println(hsl.b() * 255);
        System.out.println(hsl.getRgb());
    }
}
