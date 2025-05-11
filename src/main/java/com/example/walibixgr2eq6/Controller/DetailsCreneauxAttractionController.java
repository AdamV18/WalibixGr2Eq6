package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.*;
import com.example.walibixgr2eq6.Model.Attraction;
import com.example.walibixgr2eq6.Model.OffreReduction;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Model.User;
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
import java.sql.SQLException;
import java.time.LocalTime;

/**
 * controleur pour affichae des détails de l'attraction choisie et sélection d'un créneau
 * charge les infos de l'attraction (nom, description, image, prix avant et après réduc)
 * calcul de la réduc si applicable
 * choix du créneau pour la résa
 */
public class DetailsCreneauxAttractionController {

    @FXML
    private Label nomLabel;

    @FXML
    private Text descriptionAttraction;

    @FXML
    private Label prixBase;

    @FXML
    private Label prixAvecReduc;

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

    @FXML
    private Label messageErreur;

    private DAOAttraction daoAttraction;
    private String attractionNom;
    private Reservation reservation;

    /**
     * constructeur par défaut
     * initialise la connexion à la bdd et création du DAOAttraction
     */
    public DetailsCreneauxAttractionController() { //connection à la bdd
        DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");
        this.daoAttraction = new DAOAttraction(daoFactory);
    }

    /**
     * conserve date choisie, id client et attraction choisie pour la résa
     * @param reservation
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            System.out.println("Détails créneaux : \n");
            System.out.println("Date récupérée : " + reservation.getDate());
            System.out.println("Id du client recupéré : " +Session.getCurrentUserId());
            System.out.println("Attraction récupérée : " +reservation.getAttractionId() + reservation.getAttractionNom());
        } else {
            System.out.println("Erreur : Reservation nulle ou date nulle");
        }
    }

    /**
     * définit le nom de l'attraction choisie et charge ses détails
     * @param nom
     * @throws SQLException
     */
    public void setAttractionNom(String nom) throws SQLException{
        this.attractionNom = nom;
        detailsAttraction();
    }

    public void calculReduction(Reservation reservation, OffreReduction offreReduc) {
        double prixTotal = reservation.getPrixTotal();
        double pourcentage = (offreReduc != null) ? offreReduc.getPourcentage() : 0.0;
        double montantReduc = prixTotal * pourcentage/100.0;
        double prixAvecReduc = prixTotal - montantReduc;
        reservation.setPrixAvecReduc(prixAvecReduc);
    }

    /**
     * recupère les infos de l'attraction choisie depuis la bdd
     * calucl de la réduc si applicable grâce à daoAttraction.calculReduction
     * choix du créneau
     * @throws SQLException
     */
    private void detailsAttraction() throws SQLException { //mettre les détails de l'attraction sur la page
        Attraction attraction = daoAttraction.getAttractionNom(attractionNom);
        messageErreur.setVisible(false);

        if (attraction != null) {
            nomLabel.setText(attraction.getNom());
            descriptionAttraction.setText(attraction.getDescription());
            typeAttraction.setText(attraction.getTypeAttrac());
            prixBase.setText("Prix avant réduction : " +attraction.getPrixBase() + " €");
            reservation.setPrixTotal(attraction.getPrixBase());
            System.out.println("Prix avant réduc : " +reservation.getPrixTotal());

            int userId = Session.getCurrentUserId();
            UserDao userDao = new UserDao(DaoFactory.getInstance("walibix", "root", ""));
            User user = userDao.findById(userId);
            OffreReduction offreReduction = null;
            if (user != null && user.getDateNaissance() !=null) {
                if (!"Invite".equals(user.getTypeClient()) && user.getOffreReducId() != null) {
                    offreReduction = OffreReductionDao.findById(user.getOffreReducId());
                }
            }
            calculReduction(reservation, offreReduction);
            prixAvecReduc.setText("Prix après réduction (si applicable) : " +reservation.getPrixAvecReduc() + " €");
            reservation.setPrixAvecReduc(reservation.getPrixAvecReduc());
            System.out.println("Prix après réduc (si applicable) : " +reservation.getPrixAvecReduc());
        }


        if (attraction.getImage() != null) { //recup l'image associée à l'attraction
            Image image = new Image(getClass().getResourceAsStream("/images/" + attraction.getImage()));
            imageView.setImage(image);
        } else {
            nomLabel.setText("L'attraction n'a pas été trouvée");
        }

        creneauxComboBox.setItems(FXCollections.observableArrayList( //pouur remplir la combobox avec les différents créneaux possibles
                "10:00 - 10:30", "10:30 - 11:00", "11:00 - 11:30", "11:30 - 12:00",
                "12:00 - 12:30", "12:30 - 13:00", "13:00 - 13:30", "13:30 - 14:00",
                "14:00 - 14:30", "14:30 - 15:00", "15:00 - 15:30", "15:30 - 16:00",
                "16:00 - 16:30", "16:30 - 17:00", "17:00 - 17:30", "17:30 - 18:00",
                "18:00 - 18:30", "18:30 - 19:00"
        ));

        }

    /**
     * gestion du bouton retour
     * retour à ListeAttraction.fxml
     * reinitialise l'id de l'attraction choisie
      */
    @FXML
    private void retour() { //gère le bouton retour
        try { //retour sur la page de choix de l'attraction
            reservation.setAttractionId(0); // reinitialisation id attraction pour nouveau choix
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

    /**
     * validation du créneau select et passage à la confirmation
     * si aucun créneau affichage d'un message d'erreur
     * recupère le choix du créneau et l'ajoute à la résa en cours
     * chargement de la vue ConfirmationReservation.fxml quand on clique sur valider
     */
    @FXML
    private void valider() { //gère le bouton valider
        String choix = creneauxComboBox.getValue();

        if (choix == null){
            messageErreur.setVisible(true);
            return;
        }

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

    /**
     * permet de retourner à l'accueil en cliquant sur le logo Walibix
     * chargement de la vue client-view.fxml
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