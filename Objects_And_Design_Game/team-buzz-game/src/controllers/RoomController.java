package controllers;

import backend.*;
import backend.map.Door;
import backend.map.ExitDirection;
import backend.map.Map;
import backend.player.Player;
import backend.player.Potion;
import backend.player.Weapon;
import frontend.InventoryPopup;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RoomController {

    @FXML
    private ImageView monsterHealthView;
    @FXML
    private Label monsterHealthLabel;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView topDoor;
    @FXML
    private ImageView leftDoor;
    @FXML
    private ImageView bottomDoor;
    @FXML
    private ImageView rightDoor;
    @FXML
    private Pane mainPane;
    @FXML
    private HBox avatarBox;
    private Player player;
    @FXML
    private Button attack;


    @FXML
    private ImageView roomBackgroundView;
    @FXML
    private ImageView avatarView;
    @FXML
    private ImageView healthView;
    @FXML
    private ImageView goldView;
    @FXML
    private ImageView weaponView;
    @FXML
    private Label goldLabel;
    @FXML
    private Label healthLabel;

    private Map map;

    private ArrayList<ImageView> monsterImages;

    public void initialize() {
        createRoom();
        setupHealth();
        setupGold();
        setupMonsterHealth();
        configureDoorImage(topDoor);
        configureDoorImage(leftDoor);
        configureDoorImage(bottomDoor);
        configureDoorImage(rightDoor);
        monsterImages = new ArrayList<>();
    }

    public void initData(Player player) {
        this.player = player;
        player.setAvatarBox(avatarBox);
        player.setLabels(healthLabel, goldLabel, monsterHealthLabel);
        placeAvatar();
        if (player.getMap() == null) {
            map = new Map(player.getName().hashCode());
            player.setMap(map);
        } else {
            map = player.getMap();
        }

        player.setAttack(attack);
        player.setWeaponView(weaponView);
        attack.setVisible(false);
        attack.setFocusTraversable(false);
        setDoorViews();
        displayAttackNote();
    }




    private void displayAttackNote() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setContentText("Welcome to the dungeon, " + player.getName()
                + "! You will have to navigate through the maze of rooms and fight monsters to"
                + " reach the end. Every monster you choose to attack has a chance to attack back, "
                + "so be careful! Good luck...");
        info.show();
    }

    public void createRoom() {
        try {
            FileInputStream stream = new FileInputStream("media/roomObjects/room.png");
            roomBackgroundView.setImage(new Image(stream));
            roomBackgroundView.setFitHeight(900);
            roomBackgroundView.setFitWidth(1400);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    public void placeAvatar() {
        try {
            String fileName = "media/avatars/" + player.getAvatar() + ".png";
            FileInputStream stream = new FileInputStream(fileName);
            avatarView.setImage(new Image(stream));
            avatarView.setFitHeight(100);
            fileName = "media/" + player.getEquippedWeapon().getName() + ".png";
            stream = new FileInputStream(fileName);
            weaponView.setImage(new Image(stream));
            weaponView.setFitHeight(60);
            weaponView.setRotate(45.0);
            healthLabel.setText(String.valueOf(player.getHealth()));
            goldLabel.setText(String.valueOf(player.getGold()));
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }
    public void setupHealth() {
        try {
            healthView.setImage(new Image(new FileInputStream("media/roomObjects/heart.png")));
            healthView.setFitHeight(50);
            healthView.setFitWidth(50);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    public void setupMonsterHealth() {
        try {
            String fileName = "media/monsters/monsterIcon.png";
            monsterHealthView.setImage(new Image(new FileInputStream(fileName)));
            monsterHealthView.setFitHeight(50);
            monsterHealthView.setFitWidth(50);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    public void setupGold() {
        try {
            goldView.setImage(new Image(new FileInputStream("media/roomObjects/gold.png")));
            goldView.setFitHeight(50);
            goldView.setFitWidth(50);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    private void configureDoorImage(ImageView imageView) {
        try {
            imageView.setImage(new Image(new FileInputStream("media/roomObjects/door1.png")));
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    private void configureMonsterImages(ArrayList<Monster> monsters) {
        try {
            for (Monster monster: monsters) {
                String file = "media/monsters/" + monster.getAvatar() + ".PNG";
                ImageView imageView = new ImageView();
                imageView.setImage(new Image(new FileInputStream(file)));
                imageView.setVisible(true);
                imageView.setLayoutX(monster.getX());
                imageView.setLayoutY(monster.getY());
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(100);
                monsterImages.add(imageView);
            }
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
        mainPane.getChildren().addAll(monsterImages);
    }

    private void setDoorViews() {
        topDoor.setVisible(false);
        leftDoor.setVisible(false);
        bottomDoor.setVisible(false);
        rightDoor.setVisible(false);
        mainPane.getChildren().removeAll(monsterImages);
        monsterImages.clear();
        ArrayList<Monster> currentMonsters = map.getCurrentRoom().getMonsters();
        configureMonsterImages(currentMonsters);

        for (Door door : map.getCurrentDoors()) {
            if (door.getDirection() == ExitDirection.UP) {
                topDoor.setVisible(true);
            } else if (door.getDirection() == ExitDirection.LEFT) {
                leftDoor.setVisible(true);
            } else if (door.getDirection() == ExitDirection.DOWN) {
                bottomDoor.setVisible(true);
            } else {
                rightDoor.setVisible(true);
            }
        }
    }

    private void playRoomTransition() {
        FadeTransition fade = new FadeTransition(Duration.millis(2000), stackPane);
        fade.setFromValue(0.4);
        fade.setToValue(1.0);
        fade.setAutoReverse(true);
        fade.play();
    }




    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        player.movePlayer(keyEvent.getCode());
        if (player.isDoorCollide()) {
            if (map.getCurrentRoom().isFinal()) {
                try {
                    URL temp = getClass().getClassLoader().getResource("frontend/EndScreen.fxml");
                    FXMLLoader loader = new FXMLLoader(temp);
                    Stage stage = (Stage) stackPane.getScene().getWindow();
                    Scene scene = new Scene(loader.load(), Constants.WIDTH, Constants.HEIGHT);
                    scene.getRoot().requestFocus();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            playRoomTransition();
            setDoorViews();
        }

    }

    private void showGameOverError(Monster monster) {
        Alert deathError = new Alert(Alert.AlertType.ERROR);
        deathError.setContentText("Sorry " + player.getName() + ", the "
                + monster.getAvatar() + " killed you! Click to return to start screen.");
        deathError.show();
        deathError.setOnCloseRequest(e -> {
            try {
                String name = "frontend/WelcomeScreen01.fxml";
                URL temp = getClass().getClassLoader().getResource(name);
                FXMLLoader loader = new FXMLLoader(temp);
                Stage stage = (Stage) (stackPane.getScene().getWindow());
                Scene scene = new Scene(loader.load(), Constants.WIDTH, Constants.HEIGHT);
                stage.setScene(scene);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        });
    }

    private void displayItemMessage(Object item) {
        if (item instanceof Integer) {
            return;
        }
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        String text = "You have found an item! ";
        if (item instanceof Potion) {
            Potion potion = (Potion) item;
            text += potion.getName();
        } else {
            Weapon weapon = (Weapon) item;
            text += weapon.getName();
        }
        text += " will be added to your inventory.";
        info.setContentText(text);
        info.show();
    }

    @FXML
    private void attackAction() {
        Monster monsterFighter = null;
        for (Monster monster: map.getCurrentRoom().getMonsters()) {
            if (monster.isFightingPlayer()) {
                monsterFighter = monster;
            }
        }
        monsterFighter.takeDamage(player);
        monsterHealthLabel.setText(String.valueOf(monsterFighter.getHealth()));
        if (monsterFighter.getHealth() <= 0) {
            ArrayList<Monster> monsters = map.getCurrentRoom().getMonsters();
            monsterImages.remove(monsters.indexOf(monsterFighter)).setVisible(false);
            monsterFighter.setDead(true);
            monsters.remove(monsterFighter);
            attack.setDisable(true);
            attack.setVisible(false);
            player.setMonsterCollide(false);
            player.setAvatarBox(avatarBox);
            player.movePlayer(KeyCode.UP);
            if (monsters.isEmpty()) {
                map.unlockDoors();
            }
            Object item = monsterFighter.dropRandomObject();
            if (item != null) {
                displayItemMessage(item);
                player.addItemToInventory(item);
            }
        } else {
            monsterFighter.attackPlayer(player);
            if (player.getHealth() <= 0) {
                showGameOverError(monsterFighter);
            }
        }
    }

    @FXML
    private void showInventory(MouseEvent mouseEvent) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(player.getName() + "'s Inventory");

        String fileName = "frontend/InventoryPopup.fxml";
        URL temp = InventoryPopup.class.getClassLoader().getResource(fileName);
        FXMLLoader loader = new FXMLLoader(temp);

        try {
            Scene scene = new Scene(loader.load(), 500, 370);
            scene.getRoot().requestFocus();
            popupWindow.setScene(scene);
        } catch (IOException i) {
            i.printStackTrace();
        }
        InventoryPopup popup = loader.getController();
        popup.configurePlayer(player);
        popup.setPopupWindow(popupWindow);
        popupWindow.show();

    }
}
