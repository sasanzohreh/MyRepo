package backend;

public enum Difficulty {
    EASY(7, 150), MEDIUM(5, 100), HARD(3, 100);
    private final int lives;
    private final int gold;

    private Difficulty(int lives, int gold) {
        this.lives = lives;
        this.gold = gold;
    }

    public int getLives() {
        return lives;
    }

    public int getGold() {
        return gold;
    }
}
