
import backend.Difficulty;
import backend.player.Player;
import backend.player.Weapon;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import java.util.ArrayList;
import static org.testfx.api.FxAssert.verifyThat;

public class M2MainTest extends ApplicationTest {


    @Override
    public void start(Stage stage) throws Exception {
        Main controller = new Main();
        controller.start(stage);
    }

    @Test
    public void testStart() {
        clickOn("START");
        verifyThat("Player Configuration", NodeMatchers.isNotNull());
    }

    @Test
    public void testEasyDifficulty() {
        clickOn("START");
        clickOn("#dinosaurView");
        clickOn("#maceView");
        clickOn("Easy");
        clickOn("#nameTextField");
        write("Tester");
        clickOn("Next");
        verifyThat("150", NodeMatchers.isNotNull());
        verifyThat("7", NodeMatchers.isNotNull());


    }

    @Test
    public void testAddWeapon() {
        Player player = new Player("Test", Difficulty.MEDIUM, "cowboy");
        player.addItemToInventory(new Weapon("mace"));
        player.addItemToInventory(new Weapon("sword"));
        player.addItemToInventory(new Weapon("knife"));

        ArrayList<Weapon> weapons = player.getInventory().getWeapons();

        ArrayList<Weapon> expected = new ArrayList<>();
        expected.add(new Weapon("mace"));
        expected.add(new Weapon("sword"));
        expected.add(new Weapon("knife"));

        Assert.assertEquals(expected.size(), weapons.size());

        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), weapons.get(i));
        }
    }

    @Test
    public void testEquippedWeapon() {
        Player player = new Player("Test", Difficulty.EASY, "knight");
        player.addItemToInventory(new Weapon("sword"));
        Assert.assertEquals(player.getEquippedWeapon(), new Weapon("sword"));
    }

    @Test
    public void testMediumDifficulty() {
        clickOn("START");
        clickOn("#cowboyView");
        clickOn("#swordView");
        clickOn("Medium");
        clickOn("#nameTextField");
        write("Test2");
        clickOn("Next");
        verifyThat("100", NodeMatchers.isNotNull());
        verifyThat("5", NodeMatchers.isNotNull());

    }
    @Test
    public void testHardDifficulty() {
        clickOn("START");
        clickOn("#boyView");
        clickOn("#knifeView");
        clickOn("Hard");
        clickOn("#nameTextField");
        write("Test3");
        clickOn("Next");
        verifyThat("100", NodeMatchers.isNotNull());
        verifyThat("3", NodeMatchers.isNotNull());
    }

    @Test
    public void testAvatarsAndWeaponsVisible() {
        clickOn("START");

        verifyThat("#boyView", NodeMatchers.isNotNull());
        verifyThat("#dinosaurView", NodeMatchers.isNotNull());
        verifyThat("#cowboyView", NodeMatchers.isNotNull());
        verifyThat("#cowgirlView", NodeMatchers.isNotNull());
        verifyThat("#pumpkinView", NodeMatchers.isNotNull());
        verifyThat("#knightView", NodeMatchers.isNotNull());

        verifyThat("#swordView", NodeMatchers.isNotNull());
        verifyThat("#maceView", NodeMatchers.isNotNull());
        verifyThat("#knifeView", NodeMatchers.isNotNull());
    }

    @Test
    public void testAvatarAndWeaponInRoom() {
        clickOn("START");
        clickOn("#cowboyView");
        clickOn("#knifeView");
        clickOn("Medium");
        clickOn("#nameTextField");
        write("Test4");
        clickOn("Next");
        verifyThat("#avatarView", NodeMatchers.isNotNull());
        verifyThat("#weaponView", NodeMatchers.isNotNull());
    }

    @Test
    public void testNoSelection() {
        clickOn("START");
        clickOn("Next");
        String errorMessage = "Choose an avatar, weapon, difficulty, and a valid name.";
        verifyThat(errorMessage, NodeMatchers.isNotNull());
    }

    @Test
    public void testNoAvatar() {
        clickOn("START");
        clickOn("#knifeView");
        clickOn("Medium");
        clickOn("#nameTextField");
        write("No Avatar");
        clickOn("Next");
        String errorMessage = "Choose an avatar, weapon, difficulty, and a valid name.";
        verifyThat(errorMessage, NodeMatchers.isNotNull());
    }

    @Test
    public void testInvalidName() {
        clickOn("START");
        clickOn("#dinosaurView");
        clickOn("#maceView");
        clickOn("Hard");
        clickOn("#nameTextField");
        write("              ");
        clickOn("Next");
        String errorMessage = "Choose an avatar, weapon, difficulty, and a valid name.";
        verifyThat(errorMessage, NodeMatchers.isNotNull());
    }
}