package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DAOAttraction;
import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Model.Attraction;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class DetailsCreneauxAttractionController {

    @FXML
    private Label nomLabel;

    @FXML
    private Text descriptionAttraction;

    @FXML
    private Label prixBase;

    @FXML
    private Label typeAttraction;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<String> creneauxComboBox;

    @FXML
    private Button boutonValider;

    @FXML
    private Button boutonRetour;

    private DAOAttraction daoAttraction;
    private String attractionNom;
    private Reservation reservation;

    public DetailsCreneauxAttractionController() { //connection à la bdd
        DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");
        this.daoAttraction = new DAOAttraction(daoFactory);
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            System.out.println("Détails créneaux : \n");
            System.out.println("Date récupérée : " + reservation.getDate());
            System.out.println("Id du client recupéré : " +Session.getCurrentUserId());
            System.out.println("Attraction récupérée : " +reservation.getAttractionId());
        } else {
            System.out.println("Erreur : Reservation nulle ou date nulle");
        }
    }
    public void setAttractionNom(String nom) { //pour recup le nom de l'attraction et ses infos, à voir pour utiliser la méthode d'ines
        this.attractionNom = nom;
        detailsAttraction();
    }

    private void detailsAttraction() { //mettre les détails de l'attraction sur la page
        Attraction attraction = daoAttraction.getAttractionNom(attractionNom);

        if (attraction != null) {
            nomLabel.setText(attraction.getNom());
            descriptionAttraction.setText(attraction.getDescription());
            typeAttraction.setText(attraction.getTypeAttrac());
            prixBase.setText(attraction.getPrixBase() + " €");
            reservation.setPrixTotal(attraction.getPrixBase());
            System.out.println("Prix avant réduc : " +reservation.getPrixTotal());

            if (attraction.getImage() != null) { //recup l'image associée à l'attraction
                Image image = new Image(getClass().getResourceAsStream("/images/" + attraction.getImage()));
                imageView.setImage(image);
            }

            creneauxComboBox.setItems(FXCollections.observableArrayList( //pouur remplir la combobox avec les différents créneaux possibles
                    "10:00 - 10:30", "10:30 - 11:00", "11:00 - 11:30", "11:30 - 12:00",
                    "12:00 - 12:30", "12:30 - 13:00", "13:00 - 13:30", "13:30 - 14:00",
                    "14:00 - 14:30", "14:30 - 15:00", "15:00 - 15:30", "15:30 - 16:00",
                    "16:00 - 16:30", "16:30 - 17:00", "17:00 - 17:30", "17:30 - 18:00",
                    "18:00 - 18:30", "18:30 - 19:00"
            ));
        } else {
            nomLabel.setText("L'attraction n'a pas été trouvée");
        }
    }

    @FXML
    private void retour() { //gère le bouton retour
        try { //retour sur la page de choix de l'attraction
            reservation.setAttractionId(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/ListeAttraction.fxml"));
            Parent root = loader.load();

            ListeAttractionController ListeAttractioncontroller = loader.getController();
            ListeAttractioncontroller.setReservation(reservation);

            Stage stage = (Stage) boutonRetour.getScene().getWindow(); //affiche l'autre page
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Choix Attractions");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void valider() { //gère le bouton valider
        String choix = creneauxComboBox.getValue();

        if (choix != null && !choix.isEmpty()) { //verif si un creneau est choisi
            String heure = choix.split(" - ")[0];
            LocalTime creneau = LocalTime.parse(heure);
            reservation.setHeure(creneau);
            System.out.println("Créneau récupéré : " +creneau);

            try { //affichage de ConfirmationReservation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/ConfirmationReservation.fxml"));
                Parent root = loader.load();

                ConfirmationReservationController confirmationReservationController = loader.getController();
                confirmationReservationController.setReservation(reservation);

                Stage stage = (Stage) boutonValider.getScene().getWindow();
                stage.setScene(new Scene(root, 900, 600));
                stage.setTitle("Confirmation Reservation");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //System.out.println("choisir un créneau"); //test bouton valider si creneau non choisi (dans la console)
        }
    }

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