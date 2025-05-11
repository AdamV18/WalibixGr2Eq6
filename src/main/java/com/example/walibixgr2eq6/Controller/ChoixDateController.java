package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.HelloApplication;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Session;

import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.io.IOException;

/**
 * controleur de la selection de la date de réservation
 * permet à l'utilisateur de choisir une date pour sa réservation
 * redirection à la fin vers listeAttraction ou clinet-view si clic sur le logo Walibix
 */
public class ChoixDateController {
    @FXML
    private DatePicker choixDate;
    @FXML
    private Button validerJour;
    @FXML
    private Label messageErreur;

    /**
     * objet de réservation temporaire permettant de stocker les infos de la résa en cours
     */
    private Reservation reservation = new Reservation();

    /**
     *
     */
    private DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    /**
     * initialisation du controleur Javafx
     * masque le message d'erreur au début
     * configuration du datePicker pour que la date soit ultérieure à celle d'aujourd'hui
     */
    @FXML
    private void initialize() {
        //choixDate.setValue(LocalDate.now());
        messageErreur.setVisible(false);
        choixDate.setDayCellFactory(choixDate->new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())){
                    setDisable(true);
                }
            }
        });
    }

    /**
     * valide la date de la réservation
     * si aucune date n'est sélectionnée -> message d'erreur
     * stocke la date et l'identifiant de l'user dans la réservation
     * chargement de la vue listeAttraction.fxml en lui passant la réservation
     * @param event
     * @throws IOException
     */
    @FXML
    private void confirmerDate(ActionEvent event) throws IOException {
        LocalDate dateChoisie = choixDate.getValue();

        if (dateChoisie == null){
            messageErreur.setVisible(true);
            return;
        }

        reservation.setDate(dateChoisie);
        System.out.println("Date choisie : " + dateChoisie);

        int userId = Session.getCurrentUserId();
        reservation.setUserId(userId);
        System.out.println("Id de user connecté : " + userId);

        try {
            URL fxmlUrl = HelloApplication.class.getResource("/com/example/walibixgr2eq6/ListeAttraction.fxml");
            if (fxmlUrl == null) {
                System.out.println("FxmlUrl introuvable");
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            Parent root = fxmlLoader.load();

            ListeAttractionController listeAttractionController = fxmlLoader.getController();
            listeAttractionController.setReservation(reservation);

            Stage stage = (Stage) validerJour.getScene().getWindow();
            stage.setScene(new Scene(root,  900, 600));
            stage.setTitle("Date");
        } catch (IOException e) {
            e.printStackTrace();
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