package utilities;

import processing.core.PImage;

public class Image {

    public static int extractColorFromImage(PImage img) {
        img.loadPixels();
        int r = 0, g = 0, b = 0;
        for (int i=0; i<img.pixels.length; i++) {
            int c = img.pixels[i];
            r += c>>16&0xFF;
            g += c>>8&0xFF;
            b += c&0xFF;
        }
        r /= img.pixels.length;
        g /= img.pixels.length;
        b /= img.pixels.length;

        return (r<<16)|(g<<8)|b;
    }

    public static float extractShadeFromImage(PImage img) {
        img.loadPixels();
        int r = 0, g = 0, b = 0;
        for (int i=0; i<img.pixels.length; i++) {
            int c = img.pixels[i];
            r += c>>16&0xFF;
            g += c>>8&0xFF;
            b += c&0xFF;
        }
        r /= img.pixels.length;
        g /= img.pixels.length;
        b /= img.pixels.length;

        return (float)(0.2126*r + 0.7152*g + 0.0722*b) / 255;
    }
}
