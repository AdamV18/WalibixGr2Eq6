package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.OffreReductionDao;
import com.example.walibixgr2eq6.Model.OffreReduction;
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
 * controleur de vue edition des offres de reduc
 * permet de lister, ajouter, modifier, supprimer et attrivuer des offres de reduc
 */
public class EditOffreReducController {

    @FXML private Button EditOffreRetourButton;
    @FXML private TableView<OffreReduction> offreTable;
    @FXML private TableColumn<OffreReduction, Integer> colId;
    @FXML private TableColumn<OffreReduction, String> colNom;
    @FXML private TableColumn<OffreReduction, Double> colPourcentage;
    @FXML private TableColumn<OffreReduction, Integer> colAgeMin;
    @FXML private TableColumn<OffreReduction, Integer> colAgeMax;

    @FXML private TextField nomField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField pourcentageField;
    @FXML private TextField ageMinField;
    @FXML private TextField ageMaxField;

    private ObservableList<OffreReduction> offreList;

    /**
     * initialisation colonnes et chargement des données
     * association de chaque colonne à la propriété correspondante
     * chargement de la liste et écoute la selection pour afficher les détails
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("offreReducId"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPourcentage.setCellValueFactory(new PropertyValueFactory<>("pourcentage"));
        colAgeMin.setCellValueFactory(new PropertyValueFactory<>("conditionAgeMin"));
        colAgeMax.setCellValueFactory(new PropertyValueFactory<>("conditionAgeMax"));

        loadOffres();

        offreTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOffreDetails(newValue)
        );
    }

    /**
     * chargement de la liste des offres depuis la bdd et affichage dans la table
     */
    private void loadOffres() {
        offreList = FXCollections.observableArrayList(OffreReductionDao.getAllOffres());
        offreTable.setItems(offreList);
    }

    /**
     * affiche les details de l'offre select
     * @param offre
     */
    private void showOffreDetails(OffreReduction offre) {
        if (offre != null) {
            nomField.setText(offre.getNom());
            descriptionArea.setText(offre.getDescription());
            pourcentageField.setText(String.valueOf(offre.getPourcentage()));
            ageMinField.setText(String.valueOf(offre.getConditionAgeMin()));
            ageMaxField.setText(String.valueOf(offre.getConditionAgeMax()));
        }
    }

    /**
     * ajoute une nouvelle offre avec valeurs saisies
     * si erreur sur le format des champs numeriques -> affichage alerte  du type WARNING
     */
    @FXML
    private void handleAddOffreReduc() {
        try {
            OffreReduction offre = new OffreReduction(
                    0,
                    nomField.getText(),
                    descriptionArea.getText(),
                    Double.parseDouble(pourcentageField.getText()),
                    Integer.parseInt(ageMinField.getText()),
                    Integer.parseInt(ageMaxField.getText())
            );
            OffreReductionDao.addOffreReduc(offre);
            loadOffres();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Vérifie que les champs numériques sont bien remplis.");
        }
    }

    /**
     * mise a jour de l'offre selectionnée avec valeurs saisies
     * si erreur sur le format des champs numeriques -> affichage alerte du type WARNING
     */
    @FXML
    private void handleUpdateOffreReduc() {
        OffreReduction selected = offreTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setNom(nomField.getText());
                selected.setDescription(descriptionArea.getText());
                selected.setPourcentage(Double.parseDouble(pourcentageField.getText()));
                selected.setConditionAgeMin(Integer.parseInt(ageMinField.getText()));
                selected.setConditionAgeMax(Integer.parseInt(ageMaxField.getText()));

                OffreReductionDao.updateOffreReduc(selected);
                offreTable.refresh();
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Champs numériques invalides.");
            }
        } else {
            showAlert("Aucune sélection", "Sélectionne une offre à modifier.");
        }
    }

    /**
     * suppression de l'offre select
     * détache offre des user et suppression dans bdd
     * si aucune selection -> message d'alerte
     */
    @FXML
    private void handleDeleteOffreReduc() {
        OffreReduction selected = offreTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            OffreReductionDao.detachOffreFromUsers(selected.getOffreReducId());
            OffreReductionDao.deleteOffreReduc(selected.getOffreReducId());
            loadOffres();
            clearFields();
        } else {
            showAlert("Aucune sélection", "Sélectionne une offre à supprimer.");
        }
    }

    /**
     * attribution des offres automatiquement aux user en fonction de leur age
     */
    @FXML
    private void handleAssignOffres() {
        OffreReductionDao.assignOffresToUsersByAge();
        showAlert("Succès", "Offres attribuées aux utilisateurs selon leur âge.");
    }

    /**
     * vide tous les champs de saisie
     */
    private void clearFields() {
        nomField.clear();
        descriptionArea.clear();
        pourcentageField.clear();
        ageMinField.clear();
        ageMaxField.clear();
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
            Stage stage = (Stage) EditOffreRetourButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/admin-view.fxml"));
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
