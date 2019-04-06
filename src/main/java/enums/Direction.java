package enums;

import java.util.Random;

public enum Direction {
    UP,
    DOWN,
    NONE;
    public static Direction random(){
        Random random = new Random();
        int x = random.nextInt(Direction.class.getEnumConstants().length);
        return Direction.class.getEnumConstants()[x];
    }
}

