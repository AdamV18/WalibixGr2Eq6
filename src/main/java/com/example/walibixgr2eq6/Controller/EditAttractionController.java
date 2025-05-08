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

    private void loadAttractions() {
        attractionList = FXCollections.observableArrayList(AttractionDao.getAllAttractions());
        attractionTable.setItems(attractionList);
    }

    private void showAttractionDetails(Attraction attraction) {
        if (attraction != null) {
            nomField.setText(attraction.getNom());
            typeField.setText(attraction.getTypeAttrac());
            prixField.setText(String.valueOf(attraction.getPrixBase()));
            imageField.setText(attraction.getImage());
            descriptionArea.setText(attraction.getDescription());
        }
    }

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

    private void clearFields() {
        nomField.clear();
        typeField.clear();
        prixField.clear();
        imageField.clear();
        descriptionArea.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


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

