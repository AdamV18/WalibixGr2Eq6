package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Dao.UserDao;
import com.example.walibixgr2eq6.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.sql.Date;

public class SignupController implements Initializable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordVisibleField;
    @FXML private Label signupMessageLabel;
    @FXML private ComboBox<Integer> dayCombo;
    @FXML private ComboBox<Integer> monthCombo;
    @FXML private ComboBox<Integer> yearCombo;
    @FXML private ImageView logoImageView;

    private final DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int currYear = LocalDate.now().getYear();
        for (int y = currYear; y >= 1900; y--) yearCombo.getItems().add(y);
        for (int m = 1; m <= 12; m++) monthCombo.getItems().add(m);
        for (int d = 1; d <= 31; d++) dayCombo.getItems().add(d);

        dayCombo.valueProperty().addListener((obs, ov, nv)
                -> updateAgeLabel());
        monthCombo.valueProperty().addListener((obs, ov, nv)
                -> updateAgeLabel());
        yearCombo.valueProperty().addListener((obs, ov, nv)
                -> updateAgeLabel());
    }

    @FXML
    protected void onSignupButtonClick(ActionEvent event) {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String motDePasse = passwordField.isVisible() ? passwordField.getText() : passwordVisibleField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                signupMessageLabel.setText("Champs manquants, Veuillez remplir tous les champs.");
                return;
            }

            if (!isValidEmail(email)) {
                signupMessageLabel.setText("Email invalide, Veuillez entrer une adresse email valide.");
                return;
            }

            LocalDate localDate = getSelectedBirthDate();
            if (localDate == null || localDate.isAfter(LocalDate.now())) {
                signupMessageLabel.setText("Veuillez sélectionner une date de naissance valide.");
                return;
            }

            UserDao userDao = new UserDao(daoFactory);
            if (userDao.userExists(email)) {
                signupMessageLabel.setText("Un utilisateur avec cet email existe déjà.");
                return;
            }

            Date date = Date.valueOf(localDate);
            int age = Period.between(localDate, LocalDate.now()).getYears();
            int categorie;

            if (age < 18) {
                categorie = 1;
            } else if (age >= 65) {
                categorie = 3;
            } else {
                categorie = 2;
            }

            try (Connection conn = daoFactory.getConnection()) {
                String sql = "INSERT INTO user (email, mot_de_passe, admin, nom, prenom, date_naissance, type_client, offre_reduc_id) VALUES (?, ?, false, ?, ?, ?, 'Membre', ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, email);
                stmt.setString(2, motDePasse);
                stmt.setString(3, nom);
                stmt.setString(4, prenom);
                stmt.setDate(5, date);
                stmt.setInt(6, categorie);
                stmt.executeUpdate();
                signupMessageLabel.setText("Compte créé avec succès !");
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                int userId = 0;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
                Session.setCurrentUserId(userId);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
                Scene scene = new Scene(loader.load(), 900, 600);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                signupMessageLabel.setText("Erreur lors de la création: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            signupMessageLabel.setText("Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogoClicked(MouseEvent event) {
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

    @FXML
    protected void togglePasswordVisibility() {
        boolean visible = passwordVisibleField.isVisible();
        if (visible) {
            passwordField.setText(passwordVisibleField.getText());
            passwordVisibleField.setVisible(false);
            passwordVisibleField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
        } else {
            passwordVisibleField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordVisibleField.setVisible(true);
            passwordVisibleField.setManaged(true);
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private void updateAgeLabel() {
        LocalDate date = getSelectedBirthDate();
        int calculatedAge = -1;
        if (date != null && !date.isAfter(LocalDate.now())) {
            calculatedAge = Period.between(date, LocalDate.now()).getYears();
        } else {
            calculatedAge = -1;
        }
    }

    private LocalDate getSelectedBirthDate() {
        Integer day = dayCombo.getValue();
        Integer month = monthCombo.getValue();
        Integer year = yearCombo.getValue();
        if (day == null || month == null || year == null) return null;
        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            return null;
        }
    }
}