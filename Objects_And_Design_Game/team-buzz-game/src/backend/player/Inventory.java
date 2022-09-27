package backend.player;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Weapon> weapons;
    private ArrayList<Potion> potions;
    private int smallDamagePotionCount;
    private int smallHealthPotionCount;
    private int largeDamagePotionCount;
    private int largeHealthPotionCount;

    public Inventory() {
        this.weapons = new ArrayList<>();
        this.potions = new ArrayList<>();
        this.smallDamagePotionCount = 0;
        this.smallHealthPotionCount = 0;
        this.largeDamagePotionCount = 0;
        this.largeHealthPotionCount = 0;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void addPotion(Potion potion) {
        potions.add(potion);
        if (potion.getName().equals("Small_Healing")) {
            smallHealthPotionCount++;
        } else if (potion.getName().equals("Large_Healing")) {
            largeHealthPotionCount++;
        } else if (potion.getName().equals("Small_Damage")) {
            smallDamagePotionCount++;
        } else if (potion.getName().equals("Large_Damage")) {
            largeDamagePotionCount++;
        }
    }

    public int getWeaponDamage(String weaponName) {
        int damage = 0;
        for (Weapon weapon : weapons) {
            if (weaponName.equals(weapon.getName())) {
                damage = weapon.getDamage();
            }
        }
        return damage;
    }

    public void equipWeapon(String weaponName) {
        int index = 0;
        for (int i = 0; i < weapons.size(); i++) {
            if (weaponName.equals(weapons.get(i).getName())) {
                index = i;
            }
        }
        Weapon temp = weapons.get(0);
        weapons.set(0, weapons.get(index));
        weapons.set(index, temp);
    }

    public int getSmallHealthPotionsCount() {
        return smallHealthPotionCount;
    }

    public int getSmallDamagePotionsCount() {
        return smallDamagePotionCount;
    }

    public int getLargeHealthPotionsCount() {
        return largeHealthPotionCount;
    }

    public int getLargeDamagePotionsCount() {
        return largeDamagePotionCount;
    }



    public Weapon getEquippedWeapon() {
        return weapons.get(0);
    }

    public void removePotion(Potion potion) {
        potions.remove(potion);
        if (potion.getName().equals("Small_Healing")) {
            smallHealthPotionCount--;
        } else if (potion.getName().equals("Large_Healing")) {
            largeHealthPotionCount--;
        } else if (potion.getName().equals("Small_Damage")) {
            smallDamagePotionCount--;
        } else if (potion.getName().equals("Large_Damage")) {
            largeDamagePotionCount--;
        }
    }
}
