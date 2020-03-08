package utilities;

import java.util.UUID;

public class RandomString {
    public static void main(String[] args) {
        System.out.println(uuid());
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
