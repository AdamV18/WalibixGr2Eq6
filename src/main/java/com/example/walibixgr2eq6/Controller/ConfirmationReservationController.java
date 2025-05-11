package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Dao.DAOAttraction;
import com.example.walibixgr2eq6.Session;
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

/**
 * controleur de la vue de confirmationReservation
 * affiche une barre de progression pendant enregistrement en bdd
 * redirection vers vue finale une fois la progress bar finie
 */
public class ConfirmationReservationController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label texteReservationEnCours;

    /**
     * methode d'nitialisation
     * lance l'animation de la barre de progression
     */
    @FXML
    public void initialize() {
        startProgressAnimation();
    }

    private DAOAttraction daoAttraction;

    private Reservation reservation;

    /**
     * constructeur par défaut
     * initialise la connexion à la bdd et création du DAOAttraction
     */
    public ConfirmationReservationController() { //connection à la bdd
        DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");
        this.daoAttraction = new DAOAttraction(daoFactory);
    }

    /**
     * définition de la reservation à confirmer
     * enregistrement en bdd de la résa grâce à daoAttraction.ajouter
     * affichage dans console pour vérif pendant code
     * @param reservation
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            //reservation.setUserId(1);
            System.out.println("Réservation en cours : \n");
            System.out.println("Date récupérée : " + reservation.getDate());
            System.out.println("Id du client recupéré : " +Session.getCurrentUserId());
            System.out.println("Attraction récupérée (ID) : " +reservation.getAttractionId());
            System.out.println("Attraction récupérée (Nom) : " +reservation.getAttractionNom());
            System.out.println("Prix avant réduc : " +reservation.getPrixTotal());
            System.out.println("Prix après réduc (si applicable) : " +reservation.getPrixAvecReduc());
            System.out.println("Créneau récupéré : " +reservation.getHeure());
        } else {
            System.out.println("Erreur : Reservation nulle ou date nulle");
        }
        daoAttraction.ajouter(reservation);
    }

    /**
     * lancement animation de la progress bar
     * animation pendant pendant 2 secondes
     * quand l'animation est finie chargement de FinReservation.fxml
     */
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

                FinReservationController controller = loader.getController();
                controller.setReservation(reservation);

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
