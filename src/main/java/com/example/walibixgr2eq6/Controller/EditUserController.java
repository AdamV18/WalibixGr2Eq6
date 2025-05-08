package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Dao.ReservationDao;
import com.example.walibixgr2eq6.Dao.UserDao;
import com.example.walibixgr2eq6.Model.User;
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
import java.time.LocalDate;

public class EditUserController {

    @FXML private Button EditUserRetourButton;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colNom;
    @FXML private TableColumn<User, String> colPrenom;
    @FXML private TableColumn<User, String> colTypeClient;

    @FXML private TextField emailField;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker dateNaissancePicker;
    @FXML private ChoiceBox<String> typeClientBox;

    private ObservableList<User> userList;




    /*
    public EditUserController(UserDao userDao) {
        this.userDao = userDao;
    } */

    private static DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    private final UserDao userDao = new UserDao(daoFactory);

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTypeClient.setCellValueFactory(new PropertyValueFactory<>("typeClient"));

        typeClientBox.setItems(FXCollections.observableArrayList("Membre", "Invite"));

        loadUsers();

        userTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldUser, newUser) -> showUserDetails(newUser)
        );
    }

    private void loadUsers() {
        userList = FXCollections.observableArrayList(userDao.getAllActiveUsers());
        userTable.setItems(userList);
    }

    private void showUserDetails(User user) {
        if (user != null) {
            emailField.setText(user.getEmail());
            nomField.setText(user.getNom());
            prenomField.setText(user.getPrenom());
            dateNaissancePicker.setValue(user.getDateNaissance());
            typeClientBox.setValue(user.getTypeClient());
        }
    }

    @FXML
    private void handleUpdateUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setEmail(emailField.getText());
                selected.setNom(nomField.getText());
                selected.setPrenom(prenomField.getText());
                selected.setDateNaissance(dateNaissancePicker.getValue());
                selected.setTypeClient(typeClientBox.getValue());

                userDao.updateUser(selected);
                loadUsers();
                showAlert("Succès", "Utilisateur mis à jour.");
            } catch (Exception e) {
                showAlert("Erreur", "Impossible de mettre à jour l'utilisateur.");
                e.printStackTrace();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un utilisateur.");
        }
    }

    @FXML
    private void handleDeleteUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int archiveUserId = 1;
            ReservationDao.reassignReservationsToArchiveUser(selected.getUserId(), archiveUserId);
            userDao.deleteUserById(selected.getUserId());
            loadUsers();
            showAlert("Succès", "Utilisateur supprimé et réservations réassignées.");
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un utilisateur.");
        }
    }

    public void RetourMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) EditUserRetourButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/admin-view.fxml"));
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
