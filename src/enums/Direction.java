package enums;

import java.util.Random;

public enum Direction {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    private static final Direction[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static Direction rand()  {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
