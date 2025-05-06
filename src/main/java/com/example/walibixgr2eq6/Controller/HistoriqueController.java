package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Dao.ReservationDao;
import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HistoriqueController {

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, Integer> colId;
    @FXML private TableColumn<Reservation, String> colAttraction;
    @FXML private TableColumn<Reservation, String> colDate;
    @FXML private TableColumn<Reservation, String> colHeure;
    @FXML private TableColumn<Reservation, Double> colPrix;

    private final ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        if (!Session.isLoggedIn()) {
            System.out.println("Aucun utilisateur connecté.");
            return;
        }

        int userId = Session.getCurrentUserId();
        // DAO access
        Connection connection = DaoFactory.getInstance("walibix", "root", "").getConnection();
        ReservationDao reservationDao = new ReservationDao(connection);
        List<Reservation> reservations = reservationDao.getReservationsByUserId(userId);
        reservationList.setAll(reservations);

        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colAttraction.setCellValueFactory(new PropertyValueFactory<>("attractionNom"));
        colDate.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getDate().toString()));
        colHeure.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getHeure().toString()));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixAvecReduc"));

        reservationTable.setItems(reservationList);
    }

    @FXML
    private void onRetourClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/client-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}