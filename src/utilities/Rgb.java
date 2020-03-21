package utilities;

/**
 * https://stackoverflow.com/a/4801433/2562137
 */
public class Rgb {
    public static int getRed(int rgb) {
        return (rgb >> 16) & 0x0ff;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0x0ff;
    }

    public static int getBlue(int rgb) {
        return (rgb) & 0x0ff;
    }

    public static int toRgb(int r, int g, int b) {
        return ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

    public static int[] get(int rgb) {
        return new int[]{Rgb.getRed(rgb), Rgb.getGreen(rgb), Rgb.getBlue(rgb)};
    };
}
