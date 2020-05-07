package utilities;

public class Image {

    public static int extractColorFromImage(int[] pixels) {
        int r = 0, g = 0, b = 0;
        for (int c : pixels) {
            r += c >> 16 & 0xFF;
            g += c >> 8 & 0xFF;
            b += c & 0xFF;
        }
        r /= pixels.length;
        g /= pixels.length;
        b /= pixels.length;

        return (r << 16) | (g << 8) | b;
    }

    public static float extractShadeFromImage(int[] pixels) {
        int r = 0, g = 0, b = 0;
        for (int c : pixels) {
            r += c >> 16 & 0xFF;
            g += c >> 8 & 0xFF;
            b += c & 0xFF;
        }
        r /= pixels.length;
        g /= pixels.length;
        b /= pixels.length;

        return (float) (0.2126 * r + 0.7152 * g + 0.0722 * b) / 255;
    }
}
