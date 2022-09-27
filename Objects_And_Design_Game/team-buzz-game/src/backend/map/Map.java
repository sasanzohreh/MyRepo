package backend.map;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private RoomNode currentRoom;
    private Random rand;

    public Map(int seed) {
        this.rand = new Random(seed);
        this.generateRooms();
    }

    private void generateRooms() {
        //set up four initial exits
        RoomNode startRoom = new RoomNode(false, rand, 0);
        RoomNode top = new RoomNode(false, rand, 1);
        RoomNode left = new RoomNode(false, rand, 1);
        RoomNode bottom = new RoomNode(false, rand, 1);
        RoomNode right = new RoomNode(false, rand, 1);

        //top n left are there to satisfy four-room requirement
        startRoom.setAdjacent(top, ExitDirection.UP);
        top.setAdjacent(startRoom, ExitDirection.DOWN);
        startRoom.setAdjacent(left, ExitDirection.LEFT);
        left.setAdjacent(startRoom, ExitDirection.RIGHT);
        startRoom.setAdjacent(bottom, ExitDirection.DOWN);
        bottom.setAdjacent(startRoom, ExitDirection.UP);
        startRoom.setAdjacent(right, ExitDirection.RIGHT);
        right.setAdjacent(startRoom, ExitDirection.LEFT);

        //bottom and right will lead to randomized maze, previous used to make sure a room
        //doesn't create a next room from its previous room
        RoomNode current = right;
        ExitDirection previous = ExitDirection.RIGHT;
        for (int i = 0; i < 5; i++) {
            ExitDirection direction = ExitDirection.getRandomDirection(rand, previous);
            RoomNode newRoom = new RoomNode(false, rand, i + 1);
            current.setAdjacent(newRoom, direction);
            newRoom.setAdjacent(current, direction.getOpposite());
            lockDoors(previous.getOpposite(), current);
            current = newRoom;
            previous = direction;
        }
        current = bottom;
        previous = ExitDirection.DOWN;
        for (int i = 0; i < 5; i++) {
            ExitDirection direction = ExitDirection.getRandomDirection(rand, previous);
            RoomNode newRoom = new RoomNode(false, rand, i + 1);
            current.setAdjacent(newRoom, direction);
            newRoom.setAdjacent(current, direction.getOpposite());
            lockDoors(previous.getOpposite(), current);
            current = newRoom;
            previous = direction;
        }
        current.setFinal(true);
        currentRoom = startRoom;

    }

    public RoomNode getCurrentRoom() {
        return currentRoom;
    }

    public ArrayList<Door> getCurrentDoors() {
        ArrayList<Door> doors = new ArrayList<>();
        if (currentRoom.canTraverseUp()) {
            doors.add(currentRoom.getTopDoor());
        }
        if (currentRoom.canTraverseLeft()) {
            doors.add(currentRoom.getLeftDoor());
        }
        if (currentRoom.canTraverseBottom()) {
            doors.add(currentRoom.getBottomDoor());
        }
        if (currentRoom.canTraverseRight()) {
            doors.add(currentRoom.getRightDoor());
        }
        return doors;
    }

    public void traverse(ExitDirection direction) {
        if (direction == ExitDirection.UP && currentRoom.canTraverseUp()) {
            currentRoom = currentRoom.getTopRoom();
        } else if (direction == ExitDirection.LEFT && currentRoom.canTraverseLeft()) {
            currentRoom = currentRoom.getLeftRoom();
        } else if (direction == ExitDirection.DOWN && currentRoom.canTraverseBottom()) {
            currentRoom = currentRoom.getBottomRoom();
        } else {
            currentRoom = currentRoom.getRightRoom();
        }
    }

    public void lockDoors(ExitDirection direction, RoomNode room) {
        if (direction != ExitDirection.UP && room.getTopDoor() != null) {
            room.getTopDoor().setLocked(true);
        }
        if (direction != ExitDirection.LEFT && room.getLeftDoor() != null) {
            room.getLeftDoor().setLocked(true);
        }
        if (direction != ExitDirection.DOWN && room.getBottomDoor() != null) {
            room.getBottomDoor().setLocked(true);
        }
        if (direction != ExitDirection.RIGHT && room.getRightDoor() != null) {
            room.getRightDoor().setLocked(true);
        }
    }

    public void unlockDoors() {
        if (currentRoom.getTopDoor() != null) {
            currentRoom.getTopDoor().setLocked(false);
        }
        if (currentRoom.getLeftDoor() != null) {
            currentRoom.getLeftDoor().setLocked(false);
        }
        if (currentRoom.getBottomDoor() != null) {
            currentRoom.getBottomDoor().setLocked(false);
        }
        if (currentRoom.getRightDoor() != null) {
            currentRoom.getRightDoor().setLocked(false);
        }
    }

}
