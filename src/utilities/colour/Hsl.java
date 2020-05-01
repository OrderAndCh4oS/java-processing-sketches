package utilities.colour;

import java.util.Arrays;

import static java.lang.Math.round;
import static utilities.colour.Rgb.*;

/**
 * http://www.niwa.nu/2013/05/math-behind-colorspace-conversions-rgb-hsl/
 */
public class Hsl {
    int _a;
    float _r;
    float _g;
    float _b;
    float _min;
    float _max;
    int _h = 0;
    int _s = 0;
    int _l = 0;

    public Hsl(int rgb) {
        this(getRed(rgb), getGreen(rgb), getBlue(rgb));
        _a = getAlpha(rgb);
    }

    public Hsl(int r, int g, int b) {
        _r = r / 255f;
        _g = g / 255f;
        _b = b / 255f;
        float[] rgbNormalised = {_r, _g, _b};
        Arrays.sort(rgbNormalised);
        _min = rgbNormalised[0];
        _max = rgbNormalised[2];
        _h = calcH();
        _l = calcL();
        _s = calcS();
    }

    private int calcH() {
        if (_max == _r) {
            _h = round(((_g - _b) / (_max - _min)) * 60);
        } else if (_max == _g) {
            _h = round((2.0f + (_b - _r) / (_max - _min)) * 60);
        } else if (_max == _b) {
            _h = round((4.0f + (_r - _g) / (_max - _min)) * 60);
        }

        if (_h < 0) {
            _h += 360;
        }

        return _h;
    }

    private int calcL() {
        return round(((_min + _max) / 2) * 100);
    }

    private int calcS() {
        if (_min == _max) return 0;
        if (_l < 50) {
            _s = round(((_max - _min) / (_max + _min)) * 100);
        } else {
            _s = round(((_max - _min) / (2.0f - _max - _min)) * 100);
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

    public void setH(int value) {
        if (_h > 360) {
            _h = 360;
        } else if (_h < 0) {
            _h = 0;
        } else {
            _h = value;
        }
    }

    public void setS(int value) {
        if (_s > 100) {
            _s = 100;
        } else if (_s < 0) {
            _s = 0;
        } else {
            _s = value;
        }
    }

    public void setL(int value) {
        if (_l > 100) {
            _l = 100;
        } else if (_l < 0) {
            _l = 0;
        } else {
            _l = value;
        }
    }

    public int getRgb() {
        float l = _l / 100f;
        float s = _s / 100f;
        float h = _h / 360f;

        if (s == 0) {
            int grey = round(l * 255);
            return Rgb.toRgb(grey, grey, grey);
        }

        float t1, t2, tr, tg, tb;

        if (l < 0.5f) {
            t1 = l * (1.0f + s);
        } else {
            t1 = l + s - (l * s);
        }

        t2 = (2 * l) - t1;

        tr = h + 0.333f;
        tg = h;
        tb = h - 0.333f;

        if (tr > 1) tr = tr - 1;
        if (tg > 1) tg = tg - 1;
        if (tb > 1) tb = tb - 1;

        if (tr < 0) tr = tr + 1;
        if (tg < 0) tg = tg + 1;
        if (tb < 0) tb = tb + 1;

        float r = this.toRgb(t1, t2, tr);
        float g = this.toRgb(t1, t2, tg);
        float b = this.toRgb(t1, t2, tb);

        return Rgb.toRgb(round(r * 255), round(g * 255), round(b * 255));
    }

    public int getRgba() {
        return toRgba(_a, this.getRgb());
    }

    private float toRgb(float x1, float x2, float tc) {
        if ((6 * tc) < 1) {
            return x2 + (x1 - x2) * 6 * tc;
        } else if ((2 * tc) < 1) {
            return x1;
        } else if ((3 * tc) < 2) {
            return (x2 + (x1 - x2) * (0.666f - tc) * 6);
        } else {
            return x2;
        }
    }
}

class Main {
    public static void main(String[] args) {
        Hsl hsl = new Hsl(0xff186277);
        System.out.println(0xff186277);
        System.out.println(0x186277);
        System.out.println(hsl.getRgb());
        System.out.println(hsl.getRgba());
        System.out.println(hsl.h());
        System.out.println(hsl.s());
        System.out.println(hsl.l());
        System.out.println(hsl.r() * 255);
        System.out.println(hsl.g() * 255);
        System.out.println(hsl.b() * 255);
        System.out.println(hsl.getRgb());
        System.out.println(hsl.getRgba());
        System.out.println("=============");
        Hsl hsl2 = new Hsl(0xffEFFF45);
        System.out.println(0xffEFFF45);
        System.out.println(0xEFFF45);
        System.out.println(hsl2.getRgb());
        System.out.println(hsl2.getRgba());
        System.out.println("=============");
        Hsl hsl3 = new Hsl(0xffA83BFF);
        System.out.println(0xffA83BFF);
        System.out.println(0xA83BFF);
        System.out.println(hsl3.getRgb());
        System.out.println(hsl3.getRgba());
        System.out.println("=============");
        Hsl hsl4 = new Hsl(0xff52FFD1);
        System.out.println(0xff52FFD1);
        System.out.println(0x52FFD1);
        System.out.println(hsl4.getRgb());
        System.out.println(hsl4.getRgba());
        System.out.println("=============");
    }
}
