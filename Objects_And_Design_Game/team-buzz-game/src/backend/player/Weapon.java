package backend.player;

public class Weapon {
    private String name;
    private int damage;

    public Weapon(String name) {
        this.name = name;
        this.damage = 1;
        if (name.equals("axe")) {
            this.damage = 2;
        }
        if (name.equals("sheath")) {
            this.damage = 3;
        }
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }



    public static Weapon getRandomWeapon() {
        int num = (int) (Math.random() * 5);
        if (num == 0) {
            return new Weapon("knife");
        } else if (num == 1) {
            return new Weapon("mace");
        } else if (num == 2) {
            return new Weapon("sword");
        } else if (num == 3) {
            return new Weapon("axe");
        } else {
            return new Weapon("sheath");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Weapon)) {
            return false;
        }
        Weapon weapon = (Weapon) other;
        return this.name.equals(weapon.name) && this.damage == weapon.damage;

    }

}
