package backend.map;

import java.util.Random;

public enum ExitDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN;


    public static ExitDirection getRandomDirection(Random rand, ExitDirection offBounds) {
        ExitDirection result = null;
        while (result == offBounds.getOpposite() || result == null) {
            int num = rand.nextInt(4);
            if (num == 0) {
                result = UP;
            } else if (num == 1) {
                result = LEFT;
            } else if (num == 2) {
                result = DOWN;
            } else {
                result = RIGHT;
            }
        }
        return result;
    }

    public ExitDirection getOpposite() {
        if (this == UP) {
            return DOWN;
        } else if (this == LEFT) {
            return RIGHT;
        } else if (this == DOWN) {
            return UP;
        } else {
            return LEFT;
        }
    }
}
