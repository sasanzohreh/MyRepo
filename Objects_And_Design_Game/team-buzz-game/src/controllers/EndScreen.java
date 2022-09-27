package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EndScreen {
    @FXML
    private ImageView winScreen;

    public void initialize() {
        setBackground();
    }

    public void setBackground() {
        try {
            String fileName = "media/backgrounds/endScreen.png";
            Image endingBackground = new Image(new FileInputStream(fileName));
            winScreen.setImage(endingBackground);
            winScreen.setFitWidth(2000);
            winScreen.setFitHeight(1100);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }
}