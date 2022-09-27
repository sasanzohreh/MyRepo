package backend.map;

import backend.Constants;
import backend.player.Player;
import javafx.scene.image.ImageView;

public class Door {
    private ImageView door;
    private ExitDirection direction;
    private boolean isLocked;

    public Door(ExitDirection direction) {
        this.direction = direction;
        this.door = new ImageView();
        this.configureDoorView();
        isLocked = false;
    }

    private void configureDoorView() {
        if (direction == ExitDirection.UP) {
            door.setLayoutX(Constants.WIDTH / 2);
            door.setLayoutY(20);
        } else if (direction == ExitDirection.LEFT) {
            door.setLayoutX(20);
            door.setLayoutY(Constants.HEIGHT / 2);
        } else if (direction == ExitDirection.DOWN) {
            door.setLayoutX((Constants.WIDTH / 2) - 20);
            door.setLayoutY(Constants.HEIGHT - 120);
        } else {
            door.setLayoutX(Constants.WIDTH - 120);
            door.setLayoutY(Constants.HEIGHT / 2);
        }
    }

    public boolean isCollide(Player player) {
        return !isLocked
                && player.getX() > this.door.getLayoutX() - 30
                && player.getX() < this.door.getLayoutX() + 30
                && player.getY() < this.door.getLayoutY() + 30
                && player.getY() > this.door.getLayoutY() - 30;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public ExitDirection getDirection() {
        return this.direction;
    }


}
