package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.HelloApplication;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Dao.DaoFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class ChoixDateController {
    @FXML
    private DatePicker choixDate;
    @FXML
    private Button validerJour;

    private Reservation reservation = new Reservation();
    private DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @FXML
    private void initialize() {
        //choixDate.setValue(LocalDate.now());
        validerJour.setDisable(true);
        choixDate.setDayCellFactory(choixDate->new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())){
                    setDisable(true);
                }
            }
        });
        validerJour.disableProperty().bind(choixDate.valueProperty().isNull());
    }

    @FXML
    private void confirmerDate(ActionEvent event) throws IOException {
        LocalDate dateChoisie = choixDate.getValue();
        reservation.setDate(dateChoisie);
        System.out.println("Date choisie : " + dateChoisie);

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
}