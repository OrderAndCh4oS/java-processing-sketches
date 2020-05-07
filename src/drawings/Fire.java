package drawings;

import processing.core.PApplet;
import processing.core.PFont;
import sketch.Sketch;

public class Fire extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.Fire");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        PFont notoSC = createFont("NotoSerifSC-Bold", 1000);
        textFont(notoSC);
        _colours.getColours().forEach((name, colour) -> {
            blendMode(NORMAL);
            background(colour.black());
            for (int i = 0; i < random(20000, 30000); i++) {
                point(random(_width), random(_height));
            }
            fill(colour.black());
            for (int i = 0; i < 300; i++) {
                float waveHeight = random(5, 24);
                float modV = random(0.0002f, 0.001f);
                float x = random(_width);
                noStroke();
                Flame w = new Flame(x, _height, PI, 3.25f, 75f, waveHeight, modV);
                fill(colour.rand());
                for (int j = 0; j < random(180, 260); j++) {
                    w.update();
                    w.draw();
                }
                Flame w2 = new Flame(x, _height, PI, 3.25f, 45f, waveHeight, modV);
                fill(colour.rand());
                for (int j = 0; j < random(180, 260); j++) {
                    w2.update();
                    w2.draw();
                }
                Flame w3 = new Flame(x, _height, PI, 3.25f, 25f, waveHeight, modV);
                fill(colour.rand());
                for (int j = 0; j < random(180, 260); j++) {
                    w3.update();
                    w3.draw();
                }
            }
            fill(0xffffffff);
            blendMode(DIFFERENCE);
            text("火", 24, 908);
            save("fire", name);
        });
    }

    public class Flame {
        float _x;
        float _y;
        float _a;
        float _s;
        float _startX;
        float _v = PI / 60;
        float _waveHeight;
        float _modV;
        float _radius;

        Flame(float x, float y, float a, float speed, float radius, float waveHeight, float modV) {
            _x = x;
            _y = y;
            _a = a;
            _s = speed;
            _startX = x;
            _radius = radius;
            _waveHeight = waveHeight;
            _modV = modV;
        }

        public void update() {
            _a += _v;
            float dX = sin(_a) * _waveHeight;
            _y = _y - _s;
            _x = _startX + dX;
            _radius *= 0.975;
            _v += _modV;
            _s += 0.015;
        }

        public void draw() {
            ellipse(_x, _y, _radius * 2, _radius * 2);
        }
    }
}

