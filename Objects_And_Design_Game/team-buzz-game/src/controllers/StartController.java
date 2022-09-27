package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import backend.Constants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class StartController {
    @FXML
    private ImageView backgroundView;

    public void initialize() {
        setBackgroundView();
    }

    public void setBackgroundView() {
        try {
            FileInputStream imageSource = new FileInputStream("media/cave.png");
            Image caveImage = new Image(imageSource);
            backgroundView.setImage(caveImage);
            backgroundView.setFitHeight(Constants.HEIGHT);
        } catch (FileNotFoundException f) {
            f.printStackTrace();
        }
    }
    @FXML
    public void startClicker(ActionEvent event) {
        try {
            String name = "frontend/playerConfiguration.fxml";
            URL temp = getClass().getClassLoader().getResource(name);
            FXMLLoader loader = new FXMLLoader(temp);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load(), Constants.WIDTH, Constants.HEIGHT);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
