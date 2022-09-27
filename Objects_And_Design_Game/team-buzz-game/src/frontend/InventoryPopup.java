package frontend;

import backend.player.Player;
import backend.player.Potion;
import backend.player.Weapon;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class InventoryPopup {

    @FXML
    private ImageView backgroundView;
    private Stage popupWindow;
    private Player player;
    @FXML
    private ComboBox<String> weaponDropDown;

    @FXML
    private Label smallHealthPotionLabel;

    @FXML
    private Label smallDamagePotionLabel;

    @FXML
    private Label largeHealthPotionLabel;

    @FXML
    private Label largeDamagePotionLabel;

    public void configurePlayer(Player player) {
        this.player = player;
        configureInventory();
    }

    public void initialize() {
        setBackgroundView();
    }

    private void configureInventory() {
        String text = "Small Health Potions: ";
        smallHealthPotionLabel.setText(text + player.getSmallHealthPotionCount());

        text = "Small Damage Potions: ";
        smallDamagePotionLabel.setText(text + player.getSmallDamagePotionCount());

        text = "Large Health Potions: ";
        largeHealthPotionLabel.setText(text + player.getLargeHealthPotionCount());

        text = "Large Damage Potions: ";
        largeDamagePotionLabel.setText(text + player.getLargeDamagePotionCount());
        List<Weapon> weapons = player.getWeaponsList();
        for (Weapon weapon : weapons) {
            weaponDropDown.getItems().add(weapon.getName());
        }
        weaponDropDown.setValue(player.getEquippedWeapon().getName());
    }

    public void setPopupWindow(Stage stage) {
        popupWindow = stage;
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


    @FXML
    private void saveAndClose(MouseEvent mouseEvent) {
        if (weaponDropDown.getValue() == null) {
            Alert info = new Alert(Alert.AlertType.ERROR);
            info.setContentText("Please select a weapon!");
            info.show();
        }
        player.equipWeapon(weaponDropDown.getValue());
        popupWindow.close();
    }

    private void displayPurchaseNote(String text, int cost) {
        Alert info = new Alert(Alert.AlertType.CONFIRMATION);
        info.setContentText("You do not have enough " + text + " in your inventory! Would you like"
            + "to purchase this potion for " + cost + " gold?");
        Optional<ButtonType> result = info.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!player.purchasePotion(cost, text)) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Insufficient Funds");
                String errorText = "Sorry, you do not have enough gold to purchase the ";
                error.setContentText(errorText + text + " potion.");
                error.show();
            }
        }
    }

    private void displayHealthAtMax() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setContentText("Your health is already at max");
        info.show();
    }

    @FXML
    private void useSmallHealthPotion(MouseEvent mouseEvent) {
        if (player.getSmallHealthPotionCount() == 0) {
            displayPurchaseNote("Small_Healing", 30);
        } else if (player.getMaxLives() == player.getHealth()) {
            displayHealthAtMax();
        } else {
            player.drinkPotion(new Potion("Small_Healing"));
        }
        String text = "Small Health Potions: ";
        smallHealthPotionLabel.setText(text + player.getSmallHealthPotionCount());
    }

    @FXML
    private void useSmallDamagePotion(MouseEvent mouseEvent) {
        if (player.getSmallDamagePotionCount() == 0) {
            displayPurchaseNote("Small_Damage", 30);
        } else {
            player.drinkPotion(new Potion("Small_Damage"));
        }
        String text = "Small Damage Potions: ";
        smallDamagePotionLabel.setText(text + player.getSmallDamagePotionCount());
    }

    @FXML
    private void useLargeHealthPotion(MouseEvent mouseEvent) {
        if (player.getLargeHealthPotionCount() == 0) {
            displayPurchaseNote("Large_Healing", 60);
        } else if (player.getMaxLives() == player.getHealth()) {
            displayHealthAtMax();
        } else {
            player.drinkPotion(new Potion("Large_Healing"));
        }
        String text = "Large Health Potions: ";
        largeHealthPotionLabel.setText(text + player.getLargeHealthPotionCount());
    }

    @FXML
    private void useLargeDamagePotion(MouseEvent mouseEvent) {
        if (player.getLargeDamagePotionCount() == 0) {
            displayPurchaseNote("Large_Damage", 60);
        } else {
            player.drinkPotion(new Potion("Large_Damage"));
        }
        String text = "Large Damage Potions: ";
        largeDamagePotionLabel.setText(text + player.getLargeDamagePotionCount());
    }
}
