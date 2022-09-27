package backend.map;

import backend.Monster;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Random;

public class RoomNode {

    private RoomNode topRoom;
    private RoomNode leftRoom;
    private RoomNode bottomRoom;
    private RoomNode rightRoom;

    private Door topDoor;
    private Door leftDoor;
    private Door bottomDoor;
    private Door rightDoor;
    private Button attack = new Button("Attack");

    //measurement of max numbers of monsters to generate.
    // The farther from the start the more monsters
    private int intesity;

    private boolean isFinal;

    private ArrayList<Monster> monsters;
    //contains locations that a new monster can spawn in
    private ArrayList<Integer> monsterSpawns;

    public RoomNode(boolean isFinal, Random rand, int intesity) {
        this.isFinal = isFinal;
        this.intesity = intesity;
        monsters = new ArrayList<>();
        monsterSpawns = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            monsterSpawns.add(i);
        }
        generateMonsters(rand);
    }

    private void generateMonsters(Random rand) {
        for (int i = 0; i < intesity; i++) {
            int num = rand.nextInt(4);
            int spawn = monsterSpawns.remove(rand.nextInt(monsterSpawns.size()));
            if (num == 0) {
                monsters.add(new Monster("TreeMonster", spawn));
            } else if (num == 1) {
                monsters.add(new Monster("RockGolem", spawn));
            } else if (num == 2) {
                monsters.add(new Monster("IceGolem", spawn));
            } else {
                monsters.add(new Monster("Skeleton", spawn));
            }
        }
    }



    public void setAdjacent(RoomNode room, ExitDirection direction) {
        if (direction == ExitDirection.UP) {
            this.topRoom = room;
            this.topDoor = new Door(direction);
        } else if (direction == ExitDirection.LEFT) {
            this.leftRoom = room;
            this.leftDoor = new Door(direction);
        } else if (direction == ExitDirection.DOWN) {
            this.bottomRoom = room;
            this.bottomDoor = new Door(direction);
        } else {
            this.rightRoom = room;
            this.rightDoor = new Door(direction);
        }
    }

    public RoomNode getTopRoom() {
        return topRoom;
    }
    public RoomNode getLeftRoom() {
        return leftRoom;
    }
    public RoomNode getBottomRoom() {
        return bottomRoom;
    }
    public RoomNode getRightRoom() {
        return rightRoom;
    }

    public boolean canTraverseUp() {
        return topRoom != null;
    }
    public boolean canTraverseLeft() {
        return leftRoom != null;
    }
    public boolean canTraverseBottom() {
        return bottomRoom != null;
    }
    public boolean canTraverseRight() {
        return rightRoom != null;
    }

    public Door getTopDoor() {
        return topDoor;
    }
    public Door getLeftDoor() {
        return leftDoor;
    }
    public Door getBottomDoor() {
        return bottomDoor;
    }
    public Door getRightDoor() {
        return rightDoor;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
