package drawings;

import processing.core.PApplet;
import sketch.Sketch;

public class FauxAirbrush extends Sketch {
    public static void main(String... args) {
        PApplet.main("drawings.FauxAirbrush");
    }

    @Override
    public void settings() {
        _save = true;
        super.settings(1024, 1024);
    }

    @Override
    public void sketch() {
        _colours.getColours("sci-fi", "copper", "orange", "salmon-lemon").forEach((name, colour) -> {
            for (int image = 0; image < 5; image++) {
                background(colour.black());
                for (int i = 0; i < 28; i++) {
                    int f = (int) random(4);
                    int c = (int) random(colour.size()) + 1;
                    float d = (random(0.15f) + 0.05f);
                    float l = random(1024);
                    float r1 = random(1);
                    float r2 = (random(0.4f) + 0.2f);
                    for (int j = 0; j < 1024; j++) {
                        stroke(colour.get(c));
                        switch (f) {
                            case 0:
                                triangleFromLeft(j, l, d, r1, r2);
                                break;
                            case 1:
                                triangleFromRight(j, l, d, r1, r2);
                                break;
                            case 2:
                                traingleFromTop(j, l, d, r1, r2);
                                break;
                            case 3:
                                triangleFromBottom(j, l, d, r1, r2);
                                break;
                        }
                    }
                }
                save(name);
            }
        });
        println("~~Fin~~");
    }


    void triangleFromLeft(float x, float y, float density, float ratioOne, float ratioTwo) {
        for (int i = 0; i < x * density + 1; i++) {
            float wave = (random(x) * ratioOne) - ((x * ratioTwo) / 2);
            point(x + i, y + wave);
        }
    }

    void triangleFromRight(float x, float y, float density, float ratioOne, float ratioTwo) {
        for (int i = 0; i < 1024 * density - (x * 0.1 + 1); i++) {
            float wave = (1024 * ratioOne) - (random(1024 - x) * ratioOne) - ((x * ratioTwo) / 2);
            point(x + i, y + wave);
        }
    }

    void traingleFromTop(float y, float x, float density, float ratioOne, float ratioTwo) {
        for (int i = 0; i < y * density + 1; i++) {
            float wave = (random(y) * ratioOne) - ((y * ratioTwo) / 2);
            point(x + wave, y + i);
        }
    }

    void triangleFromBottom(float y, float x, float density, float ratioOne, float ratioTwo) {
        for (int i = 0; i < 1024 * density - (y * 0.1 + 1); i++) {
            float wave = (1024 * ratioOne) - (random(1024 - y) * ratioOne) - ((y * ratioTwo) / 2);
            point(x + wave, y + i);
        }
    }
}
