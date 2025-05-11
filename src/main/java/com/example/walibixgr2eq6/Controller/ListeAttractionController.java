package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DAOAttraction;
import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Model.Attraction;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * controleur de l'interface ListeAttraction
 * permet à l'utilisateur de choisir l'attraction qu'il souhaite (dans toutes celles proposées dans la bdd)
 * puis passe à l'écran pour avoir les détails de l'attraction et pouvoir choisir le créneau qu'il souhaite
 */
public class ListeAttractionController {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button validerButton;

    @FXML
    private Label messageErreur;

    private DAOAttraction daoAttraction;

    /**
     * constructeur par défaut
     * initialise la connexion à la bdd et création du DAOAttraction
    */
    public ListeAttractionController() {
        DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");
        this.daoAttraction = new DAOAttraction(daoFactory);
    }

    private Reservation reservation;

    /**
     * récupère les infos de la réservation en cours données par l'utilisateur
     * @param reservation
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            System.out.println("Date récupérée : " + reservation.getDate());
            System.out.println("Id du client recupéré : " +Session.getCurrentUserId());
        } else {
            System.out.println("Erreur : Reservation nulle ou date nulle");
        }
    }

    /**
     * charge les attractions de la bdd pour les afficher dans la combobox
     * ignore les attractions supprimées
     */
    @FXML
    public void initialize() {
        var attractions = daoAttraction.getAllAttractions();
        messageErreur.setVisible(false);

        for (Attraction attraction : attractions) {
            if ("Attraction Supprimé".equals(attraction.getNom())) {
                continue;
            }
            String item = attraction.getNom() + " - " + attraction.getTypeAttrac();
            comboBox.getItems().add(item);
        }
    }

    /**
     * gère le bouton valider
     * récupère l'attraction selectionnée par l'utilisateur et met à jour la réservation
     * puis, affiche l'interface de DetailsCreneauxAttractions
     * @param event
     * @throws SQLException
     */
    @FXML
    private void valider(ActionEvent event) throws SQLException { //si on clique sur le bouton valider :
        String choix = comboBox.getValue();

        if (choix == null){
            messageErreur.setVisible(true);
            return;
        }

        if (choix != null && !choix.isEmpty()) { //verifie qu'une attraction est choisie
            String nomAttraction = choix.split(" - ")[0]; // recupère le nom de l'attraction avant le - dans la combobox
            // recup nom attraction
            Attraction attraction = daoAttraction.getAttractionNom(nomAttraction);
            // recup id attraction
            int idAttraction = attraction.getAttractionId();
            reservation.setAttractionId(idAttraction);
            reservation.setAttractionNom(nomAttraction);
            System.out.println("Attraction choisie : " + nomAttraction + " - " + idAttraction);
            // passage fenetre suivante
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/DetailsCreneauxAttraction.fxml")); //va lire le fichier fxml
                Parent root = loader.load();

                DetailsCreneauxAttractionController detailsCreneauxAttractionController = loader.getController();
                detailsCreneauxAttractionController.setReservation(reservation);
                detailsCreneauxAttractionController.setAttractionNom(nomAttraction);

                Stage stage = (Stage) comboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 900, 600));
                stage.setTitle("Attraction"); //titre de la page
                stage.show(); //affiche la nouvelle page avec les infos de l'attraction choisie
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * gère le clic sur le logo
     * si l'utilisateur clique sur le logo, cela redirige vers l'interface de départ
     * @param event
     */
    @FXML
    private void onLogoClicked(MouseEvent event) { //recup code julien pour faire le retour à l'accueil en appuyant sur le logo
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
