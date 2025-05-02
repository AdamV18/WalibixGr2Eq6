package com.example.walibixgr2eq6.Controller;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;


public class ConfirmationReservationController implements Initializable {
    @FXML
    private ImageView logo;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label texteReservationEnCours;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // si ce n’est pas déjà fait, injecte ta ProgressBar
        // @FXML private ProgressBar progressBar;

        // Timeline : 0 → 100% en 5 secondes
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.setCycleCount(1);  // ou Timeline.INDEFINITE pour boucler
        timeline.play();
    }

}
