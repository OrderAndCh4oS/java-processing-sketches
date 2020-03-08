package utilities;

public class Random {
    public static float random() {
        java.util.Random random = new java.util.Random();
        return random.nextFloat();
    }

    public static float random(float min, float max) {
        java.util.Random rand = new java.util.Random();
        return rand.nextFloat() * (max - min) + min;
    }

    public static float random(float max) {
        java.util.Random rand = new java.util.Random();
        return rand.nextFloat() * max;
    }
}
