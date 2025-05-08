package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DAOAttraction;
import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Model.Attraction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

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

    public DetailsCreneauxAttractionController() { //connection à la bdd
        DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");
        this.daoAttraction = new DAOAttraction(daoFactory);
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

            if (attraction.getImage() != null) { //recup l'image associée à l'attraction
                Image image = new Image(getClass().getResourceAsStream("/images/" + attraction.getImage()));
                imageView.setImage(image);
            }

            creneauxComboBox.setItems(FXCollections.observableArrayList( //pouur remplir la combobox avec les différents créneaux possibles
                    "10h00 - 10h30", "10h30 - 11h00", "11h00 - 11h30", "11h30 - 12h00",
                    "12h00 - 12h30", "12h30 - 13h00", "13h00 - 13h30", "13h30 - 14h00",
                    "14h00 - 14h30", "14h30 - 15h00", "15h00 - 15h30", "15h30 - 16h00",
                    "16h00 - 16h30", "16h30 - 17h00", "17h00 - 17h30", "17h30 - 18h00",
                    "18h00 - 18h30", "18h30 - 19h00"
            ));
        } else {
            nomLabel.setText("L'attraction n'a pas été trouvée");
        }
    }

    @FXML
    private void retour() { //gère le bouton retour
        try { //retour sur la page de choix de l'attraction
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/ListeAttraction.fxml"));
            Parent root = loader.load();

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
            try { //affichage de ConfirmationReservation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/ConfirmationReservation.fxml"));
                Parent root = loader.load();

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

}