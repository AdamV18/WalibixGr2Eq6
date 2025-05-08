package com.example.walibixgr2eq6.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ConfirmationReservationController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label texteReservationEnCours;

    @FXML
    public void initialize() {
        startProgressAnimation();
    }

    private void startProgressAnimation() { //animation de la barre qui avance
        Timeline timeline = new Timeline();
        final double duration = 2.0; //durée de la progression, à voir si on modifie ou pas
        final int frames = 40; //nombre d'étape d'animation
        final double step = 1.0 / frames; //pas entre chaque étape pour l'animation

        for (int i = 0; i <= frames; i++) { //check l'état d'avancement de la barre
            double progress = i * step;
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(i * duration / frames),
                            e -> progressBar.setProgress(progress))
            );
        }

        timeline.setOnFinished(event -> { //quand la barre est à 100%
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/FinReservation.fxml")); //pour l'instant on met cette page car on n'a pas acces à l'acceuil
                Parent root = loader.load();

                Stage stage = (Stage) progressBar.getScene().getWindow();
                stage.setScene(new Scene(root, 900, 600));
                stage.setTitle("Réservation terminée");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        timeline.play();
    }
}
