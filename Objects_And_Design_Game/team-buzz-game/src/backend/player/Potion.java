package backend.player;


public class Potion {
    private String name;
    private int health;
    private int damage;
    private int damagePotionUses;

    public Potion(String name) {
        if (name.equals("Small_Healing")) {
            this.name = name;
            this.health = 1;
            this.damage = 0;
        } else if (name.equals("Large_Healing")) {
            this.name = name;
            this.health = 2;
            this.damage = 0;
        } else if (name.equals("Small_Damage")) {
            this.name = name;
            this.health = 0;
            this.damage = 1;
            this.damagePotionUses = 5;
        } else if (name.equals("Large_Damage")) {
            this.name = name;
            this.health = 0;
            this.damage = 2;
            this.damagePotionUses = 3;
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int useDamagePotion() {
        damagePotionUses--;
        if (damagePotionUses == -1) {
            damagePotionUses = 0;
            return 0;
        }
        return damage;
    }

    public static Potion getRandomPotion() {
        int num = (int) (Math.random() * 4);
        if (num == 0) {
            return new Potion("Small_Healing");
        } else if (num == 1) {
            return new Potion("Large_Healing");
        } else if (num == 2) {
            return new Potion("Small_Damage");
        } else {
            return new Potion("Large_Damage");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Potion)) {
            return false;
        }
        Potion potion = (Potion) object;
        return this.name.equals(potion.name);

    }

}
