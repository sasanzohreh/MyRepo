import backend.map.Door;
import backend.map.ExitDirection;
import backend.map.RoomNode;
import backend.map.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class M3MainTest extends ApplicationTest {

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
        clickOn("OK");
    }

    @Test
    public void testAllDoorsVisible() {
        //first screen should have all four doors visible
        verifyThat("#topDoor", Node::isVisible);
        verifyThat("#leftDoor", Node::isVisible);
        verifyThat("#bottomDoor", Node::isVisible);
        verifyThat("#rightDoor", Node::isVisible);
    }

    @Test
    public void testTopRoom() {
        //takes 32 clicks to reach top door from main screen
        for (int i = 0; i < 32; i++) {
            press(KeyCode.UP);
            release(KeyCode.UP);
        }

        verifyThat("#bottomDoor", Node::isVisible);

    }

    @Test
    public void testBottomRoom() {
        for (int i = 0; i < 32; i++) {
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        }
        verifyThat("#topDoor", Node::isVisible);
    }

    @Test
    public void testSetRoom() {
        RoomNode room = new RoomNode(false, new Random(), 0);
        RoomNode toproom = new RoomNode(false, new Random(), 0);
        room.setAdjacent(toproom, ExitDirection.UP);
        assertEquals(room.getTopRoom(), toproom);
    }

    @Test
    public void testGetCurrDoors() {
        Map map = new Map(100);
        ArrayList<Door> doors = map.getCurrentDoors();
        assertEquals(4, doors.size());
    }

    @Test
    public void testTransverse() {
        Map map = new Map(100);
        RoomNode start = map.getCurrentRoom();
        map.traverse(ExitDirection.UP);
        assertEquals(start, map.getCurrentRoom().getBottomRoom());
    }
    @Test
    public void testLeftRoom() {
        //takes 32 clicks to reach top door from main screen
        for (int i = 0; i < 10; i++) {
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        }
        for (int i = 0; i < 68; i++) {
            press(KeyCode.LEFT);
            release(KeyCode.LEFT);
        }
        verifyThat("#rightDoor", Node::isVisible);

    }

    @Test
    public void testRightRoom() {
        for (int i = 0; i < 10; i++) {
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        }
        for (int i = 0; i < 68; i++) {
            press(KeyCode.RIGHT);
            release(KeyCode.RIGHT);
        }
        verifyThat("#leftDoor", Node::isVisible);
    }

    @Test
    public void testRoomConnectToStart() {
        Map map = new Map(100);
        RoomNode start = map.getCurrentRoom();
        map.traverse(ExitDirection.DOWN);
        RoomNode curr = map.getCurrentRoom();
        assertEquals(start, curr.getTopRoom());
    }

    @Test
    public void testMoveToFinal() {
        Map map = new Map(100);
        map.traverse(ExitDirection.DOWN);
        while (!map.getCurrentRoom().isFinal()) {
            ExitDirection direction = ExitDirection.UP;
            for (Door door : map.getCurrentDoors()) {
                if (door.getDirection() != direction) {
                    direction = door.getDirection();
                    break;
                }
            }
            map.traverse(direction);
        }
        Assert.assertTrue(map.getCurrentRoom().isFinal());
    }
}
