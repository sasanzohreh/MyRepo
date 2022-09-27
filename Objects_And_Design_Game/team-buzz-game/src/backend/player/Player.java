package backend.player;

import backend.*;
import backend.map.Door;
import backend.map.ExitDirection;
import backend.map.Map;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Player {
    private String name;
    private int gold;
    private int lives;
    private int maxLives;
    private int damage;
    private String avatar;
    private Inventory inventory;
    private HBox avatarBox;
    private Map map;
    private boolean doorCollide = false;
    private boolean monsterCollide = false;
    private Button attack;
    private StackPane stackPane;
    private Label healthLabel;
    private Label goldLabel;
    private Label monsterHealthLabel;
    private ImageView weaponView;
    private Potion damagePotion;

    public Player(String name, Difficulty difficulty, String avatar) {
        this.name = name;
        this.gold = difficulty.getGold();
        this.lives = difficulty.getLives();
        this.maxLives = difficulty.getLives();
        this.avatar = avatar;
        this.damage = 0;
        this.inventory = new Inventory();
        this.weaponView = new ImageView();
    }

    public String toString() {
        String result = name + " has " + gold + " gold and " + lives + " lives.\n";
        result += "They can do " + damage + " damage and play as " + avatar;
        return result;
    }


    public void equipWeapon(String weaponName) {
        inventory.equipWeapon(weaponName);
        damage = inventory.getEquippedWeapon().getDamage();
        //update view
        try {
            String fileName = "media/" + weaponName + ".png";
            FileInputStream stream = new FileInputStream(fileName);
            weaponView.setImage(new Image(stream));
            weaponView.setFitHeight(60);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    public void setWeaponView(ImageView view) {
        this.weaponView = view;
    }


    public List<Weapon> getWeaponsList() {
        return inventory.getWeapons();
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getAvatar() {
        return avatar;
    }

    public Weapon getEquippedWeapon() {
        return inventory.getEquippedWeapon();
    }

    public int getHealth() {
        return lives;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public int getGold() {
        return gold;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        if (damagePotion != null) {
            return damage + damagePotion.useDamagePotion();
        }
        return damage;
    }


    public void setAvatarBox(HBox box) {
        this.avatarBox = box;
    }

    public void setMonsterCollide(boolean monsterCollide) {
        this.monsterCollide = monsterCollide;
    }

    public void setDoorCollide(boolean doorCollide) {
        this.doorCollide = doorCollide;
    }

    public void setLabels(Label health, Label gold, Label monsterHealth) {
        this.healthLabel = health;
        this.goldLabel = gold;
        this.monsterHealthLabel = monsterHealth;
    }


    public boolean isDoorCollide() {
        return doorCollide;
    }

    public void movePlayer(KeyCode code) {
        checkDoorCollide();
        checkMonsterCollide();
        if (!monsterCollide) {
            if (code == KeyCode.UP && canMoveUp(avatarBox)) {
                avatarBox.setLayoutY(avatarBox.getLayoutY() - 10);
            } else if (code == KeyCode.DOWN && canMoveDown(avatarBox)) {
                avatarBox.setLayoutY(avatarBox.getLayoutY() + 10);
            } else if (code == KeyCode.LEFT && canMoveLeft(avatarBox)) {
                avatarBox.setLayoutX(avatarBox.getLayoutX() - 10);
            } else if (code == KeyCode.RIGHT && canMoveRight(avatarBox)) {
                avatarBox.setLayoutX(avatarBox.getLayoutX() + 10);
            }
        }
    }

    public void updatePlayerLocation(ExitDirection entrance) {
        if (entrance == ExitDirection.UP) {
            avatarBox.setLayoutX(700);
            avatarBox.setLayoutY(100);
        } else if (entrance == ExitDirection.LEFT) {
            avatarBox.setLayoutX(100);
            avatarBox.setLayoutY(450);
        } else if (entrance == ExitDirection.DOWN) {
            avatarBox.setLayoutX(680);
            avatarBox.setLayoutY(715);
        } else {
            avatarBox.setLayoutX(1200);
            avatarBox.setLayoutY(450);
        }
    }

    public void checkDoorCollide() {
        for (Door door : map.getCurrentDoors()) {
            if (door.isCollide(this)) {
                map.traverse(door.getDirection());
                updatePlayerLocation(door.getDirection().getOpposite());
                setDoorCollide(true);
                break;
            } else {
                setDoorCollide(false);
            }
        }
    }

    private void checkMonsterCollide() {
        attack.setDisable(true);
        attack.setVisible(false);
        for (Monster monster: map.getCurrentRoom().getMonsters()) {
            if (monster.isCollide(this)) {
                setMonsterCollide(true);
                attack.setDisable(false);
                attack.setVisible(true);
                attack.setLayoutX(getX() + 80);
                attack.setLayoutY(getY() + 80);
                monster.setFightingPlayer(true);
                monsterHealthLabel.setText(String.valueOf(monster.getHealth()));
            }
        }
    }

    public Map getMap() {
        return map;
    }


    private boolean canMoveLeft(Node node) {
        return node.getLayoutX() - 10 >= 0;
    }
    private boolean canMoveRight(Node node) {
        return node.getLayoutX() + 10 < Constants.WIDTH - 100;
    }
    private boolean canMoveUp(Node node) {
        return node.getLayoutY() - 10 >= 0;
    }
    private boolean canMoveDown(Node node) {
        return node.getLayoutY() + 10 < Constants.HEIGHT - 100;
    }

    public double getX() {
        return avatarBox.getLayoutX();
    }
    public double getY() {
        return avatarBox.getLayoutY();
    }

    public void setAttack(Button attack) {
        this.attack = attack;
    }

    private void updateHealthLabel() {
        healthLabel.setText(String.valueOf(lives));
    }
    private void updateGoldLabel() {
        goldLabel.setText(String.valueOf(gold));
    }

    private void playerDamageAnimation() {
        Node mainPane = healthLabel.getParent().getParent().getParent().getParent();
        FadeTransition fade = new FadeTransition(Duration.millis(2000), mainPane);
        fade.setFromValue(0.4);
        fade.setToValue(1.0);
        fade.setAutoReverse(true);
        fade.play();
    }
    public void takeDamage(int amount) {
        if (lives > 0) {
            lives -= amount;
            playerDamageAnimation();
            updateHealthLabel();
        }
    }

    public void takeDamageTest(int amount) {
        if (lives > 0) {
            lives -= amount;
        }
    }


    public int getSmallHealthPotionCount() {
        return inventory.getSmallHealthPotionsCount();
    }
    public int getSmallDamagePotionCount() {
        return inventory.getSmallDamagePotionsCount();
    }
    public int getLargeHealthPotionCount() {
        return inventory.getLargeHealthPotionsCount();
    }
    public int getLargeDamagePotionCount() {
        return inventory.getLargeDamagePotionsCount();
    }



    public void drinkPotion(Potion potion) {

        if (potion.getName().equals("Small_Healing")) {
            if (lives + potion.getHealth() > maxLives) {
                return;
            }
            lives += potion.getHealth();
            updateHealthLabel();
            inventory.removePotion(potion);
        } else if (potion.getName().equals("Large_Healing")) {
            if (lives == maxLives) {
                return;
            //if large potion overheals, set player health to max lives
            } else if (lives > maxLives - potion.getHealth()) {
                lives = maxLives;
                updateHealthLabel();
                inventory.removePotion(potion);
                return;
            }
            lives += potion.getHealth();
            updateHealthLabel();
            inventory.removePotion(potion);
        } else if (potion.getName().equals("Small_Damage")) {
            inventory.removePotion(potion);
            this.damagePotion = potion;
        } else if (potion.getName().equals("Large_Damage")) {
            inventory.removePotion(potion);
            this.damagePotion = potion;
        }

    }

    public void addItemToInventory(Object item) {
        if (item instanceof Integer) {
            Integer monsterGold = (Integer) item;
            gold += monsterGold;
            updateGoldLabel();
        } else if (item instanceof Potion) {
            Potion potion = (Potion) item;
            inventory.addPotion(potion);
        } else {
            Weapon weapon = (Weapon) item;
            inventory.addWeapon(weapon);
        }
    }

    public boolean purchasePotion(int cost, String potionName) {
        if (gold >= cost) {
            gold -= cost;
            addItemToInventory(new Potion(potionName));
            updateGoldLabel();
            return true;
        }
        return false;
    }
}
