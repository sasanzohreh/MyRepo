package controllers;

import backend.player.Weapon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import backend.Difficulty;
import backend.player.Player;
import backend.Constants;

public class PlayerConfiguration {
    @FXML
    private ImageView weaponView;
    @FXML
    private ImageView maceView;
    @FXML
    private ImageView swordView;
    @FXML
    private ImageView knifeView;
    @FXML
    private TextField nameTextField;
    @FXML
    private ImageView dinosaurView;
    @FXML
    private ImageView cowboyView;
    @FXML
    private ImageView cowgirlView;
    @FXML
    private ImageView pumpkinView;
    @FXML
    private ImageView knightView;
    @FXML
    private ImageView boyView;
    @FXML
    private ImageView characterView;
    @FXML
    private ImageView backgroundView;
    @FXML
    private ToggleGroup difficultyGroup;

    private String selectedAvatar;
    private String selectedWeapon;




    public void initialize() {
        setBackgroundView();
        setCharacterGrid();
        styleDifficulty();
        setWeaponsGrid();
        characterView.setFitHeight(300);
        weaponView.setFitHeight(250);
    }

    private void setBackgroundView() {
        try {
            FileInputStream imageSource = new FileInputStream("media/backgrounds/config_cave.jpg");
            Image caveImage = new Image(imageSource);
            backgroundView.setImage(caveImage);
            backgroundView.setFitHeight(900);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    private void setCharacterGrid() {
        configureCharacter(boyView, "boy");
        configureCharacter(cowboyView, "cowboy");
        configureCharacter(cowgirlView, "cowgirl");
        configureCharacter(dinosaurView, "dinosaur");
        configureCharacter(pumpkinView, "pumpkin");
        configureCharacter(knightView, "knight");
    }

    private void configureCharacter(ImageView character, String avatar) {
        try {
            character.setImage(new Image(new FileInputStream("media/avatars/" + avatar + ".png")));
            character.setFitHeight(150);
            character.setOnMouseClicked((e) -> {
                characterView.setImage(character.getImage());
                selectedAvatar = avatar;
            });
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    private void styleDifficulty() {
        for (Toggle toggle : difficultyGroup.getToggles()) {
            RadioButton button = (RadioButton) toggle;
            button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            button.setStyle("-fx-text-fill: #ffe76e;");
        }
    }

    private void setWeaponsGrid() {
        configureWeapons(maceView, "mace");
        configureWeapons(swordView, "sword");
        configureWeapons(knifeView, "knife");
    }

    private void configureWeapons(ImageView weapon, String weaponName) {
        try {
            weapon.setImage(new Image(new FileInputStream("media/" + weaponName + ".png")));
            weapon.setFitHeight(150);
            weapon.setOnMouseClicked((e) -> {
                weaponView.setImage(weapon.getImage());
                selectedWeapon = weaponName;
            });
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    @FXML
    private void handleNextClicked(ActionEvent actionEvent) {
        boolean validIcons = characterView.getImage() != null && weaponView.getImage() != null;
        boolean validDifficulty = difficultyGroup.getSelectedToggle() != null;
        boolean validName = nameTextField.getText() != null
                && !nameTextField.getText().isBlank()
                && !nameTextField.getText().isEmpty();

        if (!validIcons || !validDifficulty || !validName) {
            Alert configError = new Alert(Alert.AlertType.ERROR);
            configError.setContentText("Choose an avatar, weapon, difficulty, and a valid name.");
            configError.show();
        } else {
            Difficulty difficulty;
            RadioButton button = (RadioButton) difficultyGroup.getSelectedToggle();
            if (button.getText().equals("Easy")) {
                difficulty = Difficulty.EASY;
            } else if (button.getText().equals("Medium")) {
                difficulty = Difficulty.MEDIUM;
            } else {
                difficulty = Difficulty.HARD;
            }
            Player player = new Player(nameTextField.getText(), difficulty, selectedAvatar);
            Weapon weapon = new Weapon(selectedWeapon);
            player.addItemToInventory(weapon);
            player.equipWeapon(selectedWeapon);
            try {
                URL temp = getClass().getClassLoader().getResource("frontend/Room.fxml");
                FXMLLoader loader = new FXMLLoader(temp);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load(), Constants.WIDTH, Constants.HEIGHT);
                scene.getRoot().requestFocus();
                stage.setScene(scene);
                RoomController controller = loader.getController();
                controller.initData(player);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
