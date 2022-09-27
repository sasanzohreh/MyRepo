import backend.Difficulty;
import backend.player.Inventory;
import backend.player.Player;
import backend.player.Potion;
import backend.player.Weapon;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class M5MainTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Main controller = new Main();
        controller.start(stage);
    }


    public void setup() {
        clickOn("START");
        clickOn("#dinosaurView");
        clickOn("#maceView");
        clickOn("Easy");
        clickOn("#nameTextField");
        write("Tester");
        clickOn("Next");
        clickOn("OK");
    }

    @Test
    public void testNoPotionCountAtStart() {
        setup();
        clickOn("Inventory");
        verifyThat("Large Health Potions: 0", NodeMatchers.isNotNull());
        verifyThat("Large Damage Potions: 0", NodeMatchers.isNotNull());
        verifyThat("Small Health Potions: 0", NodeMatchers.isNotNull());
        verifyThat("Small Damage Potions: 0", NodeMatchers.isNotNull());
    }

    @Test
    public void testDrinkPotion() {
        Player player = new Player("Test", Difficulty.MEDIUM, "dinosaur");
        player.addItemToInventory(new Weapon("mace"));
        player.equipWeapon("mace");
        Assert.assertEquals(1, player.getDamage());
        player.drinkPotion(new Potion("Large_Damage"));
        Assert.assertEquals(3, player.getDamage());

    }

    @Test
    public void testDrinkPotion2() {
        Player player = new Player("Test2", Difficulty.EASY, "dinosaur");
        player.addItemToInventory(new Weapon("mace"));
        player.equipWeapon("mace");
        Assert.assertEquals(1, player.getDamage());
        player.drinkPotion(new Potion("Small_Damage"));
        Assert.assertEquals(2, player.getDamage());
    }

    @Test
    public void testDrinkPotion3() {
        Player player = new Player("Test3", Difficulty.HARD, "dinosaur");
        player.addItemToInventory(new Weapon("mace"));
        player.equipWeapon("mace");
        Assert.assertEquals(1, player.getDamage());
        player.drinkPotion(new Potion("Small_Healing"));
        Assert.assertEquals(1, player.getDamage());
    }

    @Test
    public void testEquipped() {
        Player player = new Player("Test3", Difficulty.HARD, "dinosaur");
        player.addItemToInventory(new Weapon("mace"));
        player.addItemToInventory(new Weapon("sword"));
        player.equipWeapon("sword");
        Assert.assertEquals(new Weapon("sword"), player.getEquippedWeapon());
    }

    @Test
    public void testAddWeapon() {
        Player player = new Player("Test3", Difficulty.HARD, "dinosaur");
        player.addItemToInventory(new Weapon("mace"));
        Assert.assertEquals(player.getInventory().getWeapons().get(0), new Weapon("mace"));
    }

    @Test
    public void testInventoryGetPotions() {
        Inventory inventory = new Inventory();
        inventory.addPotion(new Potion("Small_Damage"));
        inventory.addPotion(new Potion("Large_Damage"));
        inventory.addPotion(new Potion("Small_Damage"));
        inventory.addPotion(new Potion("Small_Healing"));
        inventory.removePotion(new Potion("Large_Damage"));
        Assert.assertEquals(inventory.getPotions().size(), 3);
        Assert.assertEquals(inventory.getSmallDamagePotionsCount(), 2);
        Assert.assertEquals(inventory.getLargeDamagePotionsCount(), 0);


    }

    @Test
    public void testPurchasePotion() {
        setup();
        clickOn("Inventory");
        clickOn("Use Potion");
        clickOn("OK");
        verifyThat("Small Health Potions: 1", NodeMatchers.isNotNull());
    }

    @Test
    public void testDrinkPotion4() {
        Player player = new Player("Test", Difficulty.HARD, "boy");
        player.addItemToInventory(new Weapon("sword"));
        player.equipWeapon("sword");
        Assert.assertEquals(1, player.getDamage());
        player.drinkPotion(new Potion("Large_Healing"));
        Assert.assertEquals(1, player.getDamage());
    }

    @Test
    public void testBuyAndConsumePotion() {
        setup();
        clickOn("Inventory");
        clickOn("Use Potion");
        clickOn("OK");
        verifyThat("Small Health Potions: 1", NodeMatchers.isNotNull());
        clickOn("Use Potion");
        clickOn("OK");
        verifyThat("Small Health Potions: 1", NodeMatchers.isNotNull());
    }
}
