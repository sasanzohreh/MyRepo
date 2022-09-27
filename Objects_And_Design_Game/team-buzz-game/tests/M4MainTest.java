import backend.*;
import backend.map.Door;
import backend.map.ExitDirection;
import backend.map.Map;
import backend.map.RoomNode;
import backend.player.Player;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class M4MainTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Main controller = new Main();
        controller.start(stage);
    }

    @Before
    public void setup() {
        clickOn("START");
        clickOn("#dinosaurView");
        clickOn("#maceView");
        clickOn("Easy");
        clickOn("#nameTextField");
        write("Tester");
        clickOn("Next");
    }

    @Test
    public void testNoMonster() {
        Map map = new Map(100000);
        RoomNode startRoom = map.getCurrentRoom();
        Assert.assertEquals(0, startRoom.getMonsters().size());
    }

    @Test
    public void testOneMonster() {
        Map map = new Map(100000);
        map.traverse(ExitDirection.UP);
        RoomNode currentRoom = map.getCurrentRoom();
        Assert.assertEquals(1, currentRoom.getMonsters().size());
    }

    @Test
    public void testLockDoors() {
        Map map = new Map(100000);
        map.traverse(ExitDirection.DOWN);
        Door outDoor = null;
        for (Door door: map.getCurrentDoors()) {
            if (door.getDirection() != ExitDirection.UP) {
                outDoor = door;
            }
        }
        Assert.assertEquals(outDoor.isLocked(), true);
    }

    @Test
    public void testUnlockDoors() {
        Map map = new Map(100000);
        map.traverse(ExitDirection.DOWN);
        Door outDoor = null;
        for (Door door: map.getCurrentDoors()) {
            if (door.getDirection() != ExitDirection.UP) {
                outDoor = door;
            }
        }
        map.unlockDoors();
        Assert.assertEquals(outDoor.isLocked(), false);
    }

    @Test
    public void test2UnlockDoor() {
        Map map = new Map(100000);
        map.traverse(ExitDirection.RIGHT);
        Door opening = null;
        for (Door door: map.getCurrentDoors()) {
            if (door.getDirection() != ExitDirection.LEFT) {
                opening = door;
            }
        }
        map.unlockDoors();
        Assert.assertEquals(opening.isLocked(), false);
    }

    @Test
    public void test2LockDoor() {
        Map map = new Map(100000);
        map.traverse(ExitDirection.RIGHT);
        Door opening = null;
        for (Door door: map.getCurrentDoors()) {
            if (door.getDirection() != ExitDirection.LEFT) {
                opening = door;
            }
        }
        Assert.assertEquals(opening.isLocked(), true);
    }

    @Test
    public void testDamagedPlayer() {
        Player p = new Player("test", Difficulty.HARD, "Boy");
        Monster m = new Monster("Skeleton", 0);
        m.attackPlayerTest(p);
        Assert.assertEquals(p.getHealth(), 2);
    }

    @Test
    public void testDamagedMonster() {
        Player p = new Player("test", Difficulty.HARD, "Boy");
        Monster m = new Monster("Skeleton", 0);
        m.takeDamage(p);
        Assert.assertEquals(m.getHealth(), 3);
    }

    @Test
    public void testIsDead() {
        Player p = new Player("test", Difficulty.EASY, "Boy");
        Monster m = new Monster("Skeleton", 0);
        while (m.getHealth() > 0) {
            m.takeDamage(p);
        }
        Assert.assertTrue(m.isDead());
    }
    
    @Test
    public void testMonsterPosition() {
        Monster m = new Monster("Skeleton", 1);
        Assert.assertEquals(m.getMonsterView().getLayoutX(), 700, 0);
        Assert.assertEquals(m.getMonsterView().getLayoutY(), 150, 0);
    }
}
