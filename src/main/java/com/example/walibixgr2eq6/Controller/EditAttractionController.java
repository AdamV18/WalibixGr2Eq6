package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.AttractionDao;
import com.example.walibixgr2eq6.Dao.ReservationDao;
import com.example.walibixgr2eq6.Model.Attraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * controleur de la vue edition des attractions (espace admin)
 * permet de lister, select, modifier, ajouter et supp des attractions
 */
public class EditAttractionController {

    @FXML
    private Button EditAttracRetourButton;

    @FXML private TableView<Attraction> attractionTable;
    @FXML private TableColumn<Attraction, Integer> colId;
    @FXML private TableColumn<Attraction, String> colNom;
    @FXML private TableColumn<Attraction, String> colType;
    @FXML private TableColumn<Attraction, Double> colPrix;

    @FXML private TextField nomField;
    @FXML private TextField typeField;
    @FXML private TextField prixField;
    @FXML private TextField imageField;
    @FXML private TextArea descriptionArea;

    private ObservableList<Attraction> attractionList;

    /**
     * initialisation colonnes et chargement des données
     * association de chaque colonne à la propriété correspondante
     * chargement de la liste et écoute la selection pour afficher les détails
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("attractionId"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeAttrac"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixBase"));

        loadAttractions();

        attractionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAttractionDetails(newValue)
        );
    }

    /**
     * chargement de la liste des attractions depuis la bdd et affichage dans la table
     */
    private void loadAttractions() {
        attractionList = FXCollections.observableArrayList(AttractionDao.getAllAttractions());
        attractionTable.setItems(attractionList);
    }

    /**
     * affichage des détails de l'attraction select
     * @param attraction
     */
    private void showAttractionDetails(Attraction attraction) {
        if (attraction != null) {
            nomField.setText(attraction.getNom());
            typeField.setText(attraction.getTypeAttrac());
            prixField.setText(String.valueOf(attraction.getPrixBase()));
            imageField.setText(attraction.getImage());
            descriptionArea.setText(attraction.getDescription());
        }
    }

    /**
     * mise a jour de l'attraction selectionnée avec valeurs saisies
     * si aucune attraction select -> affichage d'une alerte du type WARNING
     */
    @FXML
    private void handleUpdateAttraction() {
        Attraction selected = attractionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(nomField.getText());
            selected.setTypeAttrac(typeField.getText());
            selected.setPrixBase(Double.parseDouble(prixField.getText()));
            selected.setImage(imageField.getText());
            selected.setDescription(descriptionArea.getText());

            AttractionDao.updateAttraction(selected);
            attractionTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune attraction sélectionnée");
            alert.setContentText("Veuillez sélectionner une attraction dans la table.");
            alert.showAndWait();
        }
    }


    /**
     * ajoute une nouvelle attraction avec valeurs saisies
     * si erreur sur le format du prix -> affichage message d'errreur
     */
    @FXML
    private void handleAddAttraction() {
        try {
            String nom = nomField.getText();
            String type = typeField.getText();
            double prix = Double.parseDouble(prixField.getText());
            String image = imageField.getText();
            String description = descriptionArea.getText();

            Attraction newAttraction = new Attraction(0, nom, type, description, prix, image); // ID = 0 si auto-incrémenté
            AttractionDao.addAttraction(newAttraction);

            loadAttractions(); // recharge la liste
            clearFields(); // vide les champs

        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Le prix doit être un nombre valide.");
        }
    }

    /**
     * suppression de l'attraction select
     * réassignation de l'id dans les resa associées (résa archivées -> id =1)
     * suppression attraction dans la bdd
     * si aucune selection -> message d'alerte
     */
    @FXML
    private void handleDeleteAttraction() {
        Attraction selected = attractionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ReservationDao.reassignAttractionInReservations(selected.getAttractionId(), 1); // ID 1 (archive)
            AttractionDao.deleteAttraction(selected.getAttractionId());
            loadAttractions();
            clearFields();
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une attraction à supprimer.");
        }
    }

    /**
     * vide tous les champs de saisie
     */
    private void clearFields() {
        nomField.clear();
        typeField.clear();
        prixField.clear();
        imageField.clear();
        descriptionArea.clear();
    }

    /**
     * affichage de l'alerte de type WARNING aevc titre et message donné
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * retourne au menu admin (admin-view.fxml)
     * @param event
     */
    public void RetourMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) EditAttracRetourButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/admin-view.fxml"));
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

