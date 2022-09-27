package backend;

import backend.player.Player;
import backend.player.Potion;
import backend.player.Weapon;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Monster {
    private String avatar;
    private int gold;
    private int lives;
    private int damage;
    private ImageView monsterView;
    private boolean isDead;
    private boolean fightingPlayer;
    private double attackChance;

    public Monster(String avatar, int select) {
        this.avatar = avatar;
        this.monsterView = new ImageView();
        configureMonsterView(select);
        if (avatar.equals("TreeMonster")) {
            gold = 5;
            lives = 1;
            damage = 1;
            attackChance = 0.1;
        } else if (avatar.equals("RockGolem")) {
            gold = 10;
            lives = 2;
            damage = 1;
            attackChance = 0.15;
        } else if (avatar.equals("IceGolem")) {
            gold = 15;
            lives = 3;
            damage = 1;
            attackChance = 0.2;
        } else if (avatar.equals("Skeleton")) {
            gold = 20;
            lives = 4;
            damage = 1;
            attackChance = 0.25;
        }
        fightingPlayer = false;
    }

    public void configureMonsterView(int num) {
        if (num == 0) {
            monsterView.setLayoutX(700);
            monsterView.setLayoutY(450);
        } else if (num == 1) {
            monsterView.setLayoutX(700);
            monsterView.setLayoutY(150);
        } else if (num == 2) {
            monsterView.setLayoutX(150);
            monsterView.setLayoutY(450);
        } else if (num == 3) {
            monsterView.setLayoutX(700);
            monsterView.setLayoutY(600);
        } else if (num == 4) {
            monsterView.setLayoutX(1180);
            monsterView.setLayoutY(450);
        } else {
            monsterView.setLayoutX(1180);
            monsterView.setLayoutY(150);
        }

    }

    public boolean isCollide(Player player) {
        if (isDead) {
            return false;
        } else {
            return player.getX() > this.monsterView.getLayoutX() - 70
                    && player.getX() < this.monsterView.getLayoutX() + 70
                    && player.getY() < this.monsterView.getLayoutY() + 70
                    && player.getY() > this.monsterView.getLayoutY() - 70;
        }
    }

    public boolean isCollide(Monster monster) {
        if (isDead) {
            return false;
        } else {
            return monster.getX() > this.monsterView.getLayoutX() - 70
                    && monster.getX() < this.monsterView.getLayoutX() + 70
                    && monster.getY() < this.monsterView.getLayoutY() + 70
                    && monster.getY() > this.monsterView.getLayoutY() - 70;
        }
    }

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return lives;
    }

    public int getDamage() {
        return damage;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void takeDamage(Player player) {
        if (lives > 0) {
            int num = player.getDamage();
            lives -= num;
            if (lives <= 0) {
                lives = 0;
                isDead = true;
            }
        }

    }

    public Object dropRandomObject() {
        int num = (int) (Math.random() * 5);
        if (num == 0) {
            return gold;
        } else if (num == 1) {
            return Potion.getRandomPotion();
        } else if (num == 2) {
            return Weapon.getRandomWeapon();
        }
        return null;
    }

    public String getAvatar() {
        return avatar;
    }

    public ImageView getMonsterView() {
        return this.monsterView;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public boolean isFightingPlayer() {
        return fightingPlayer;
    }

    public void setFightingPlayer(boolean fightingPlayer) {
        this.fightingPlayer = fightingPlayer;
    }

    public double getX() {
        return monsterView.getLayoutX();
    }

    public double getY() {
        return monsterView.getLayoutY();
    }

    public void attackPlayer(Player player) {
        if (!isDead) {
            Random random = new Random();
            double temp = attackChance * 100;
            int num = random.nextInt(100);
            if (num < temp) {
                player.takeDamage(damage);
            }
        }
    }

    public void attackPlayerTest(Player player) {
        player.takeDamageTest(damage);
    }

}