package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsController {

    @FXML
    private Button StatsRetourButton;

    //private DaoFactory daoFactory;
    private DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    @FXML
    public void initialize() {
        loadReservationParAttraction();
        loaduserParTrancheAge();
        loaduserParTypeClient();

        loadRevenuParAttraction();
        loadChiffreTotalParMois();
    }


    @FXML
    private PieChart reservationParAttraction;

    public void loadReservationParAttraction() {
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

        String query = """
        SELECT a.nom, COUNT(r.reservation_id) as nb_reservations
        FROM reservation r
        JOIN attraction a ON r.attraction_id = a.attraction_id
        GROUP BY a.nom
    """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String attractionName = rs.getString("Nom");
                int count = rs.getInt("nb_reservations");
                dataList.add(new PieChart.Data(attractionName + " (" + count + ")", count));
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des statistiques : " + e.getMessage());
        }

        reservationParAttraction.setData(dataList);
        //reservationParAttraction.setLegendVisible(false);
    }


    @FXML
    private PieChart userParTrancheAge;

    public void loaduserParTrancheAge() {
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

        String query = """
        SELECT 
            CASE 
                WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) < 18 THEN '-18 ans'
                WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) BETWEEN 18 AND 25 THEN '18-25 ans'
                WHEN TIMESTAMPDIFF(YEAR, date_naissance, CURDATE()) BETWEEN 26 AND 65 THEN '25-65 ans'
                ELSE '65+ ans'
            END AS tranche_age,
            COUNT(*) AS nb_user
        FROM user
        GROUP BY tranche_age
    """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tranche = rs.getString("tranche_age");
                int count = rs.getInt("nb_user");
                dataList.add(new PieChart.Data(tranche + " (" + count + ")", count));
            }

        } catch (SQLException e) {
            System.out.println("Erreur (âge user) : " + e.getMessage());
        }

        userParTrancheAge.setData(dataList);
        //userParTrancheAge.setLegendVisible(false);
    }



    @FXML
    private PieChart userParTypeClient;

    public void loaduserParTypeClient() {
        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();

        String query = """
            SELECT type_client, COUNT(*) AS nb
            FROM User
            GROUP BY type_client
        """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("type_client");
                int nb = rs.getInt("nb");
                dataList.add(new PieChart.Data(type + " (" + nb + ")", nb));
            }

        } catch (SQLException e) {
            System.out.println("Erreur (type client) : " + e.getMessage());
        }

        userParTypeClient.setData(dataList);
        //userParTypeClient.setLegendVisible(false);
    }






    @FXML
    private BarChart<String, Number> revenuParAttraction;

    public void loadRevenuParAttraction() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Chiffre d'affaires (€)");

        String query = """
        SELECT a.nom, SUM(r.prix_avec_reduc) AS total_revenu
        FROM reservation r
        JOIN attraction a ON r.attraction_id = a.attraction_id
        GROUP BY a.nom
        ORDER BY total_revenu DESC
    """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nom = rs.getString("Nom");
                float revenu = rs.getFloat("total_revenu");
                series.getData().add(new XYChart.Data<>(nom, revenu));
            }

        } catch (SQLException e) {
            System.out.println("Erreur (revenu) : " + e.getMessage());
        }

        revenuParAttraction.getData().add(series);
        revenuParAttraction.setLegendVisible(false);
    }


    @FXML
    private BarChart<String, Number> chiffreTotalParMois;

    public void loadChiffreTotalParMois() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Chiffre d'affaires mensuel (€)");

        String query = """
        SELECT DATE_FORMAT(date, '%Y-%m') AS mois, SUM(prix_avec_reduc) AS total
        FROM reservation
        GROUP BY mois
        ORDER BY mois
    """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String mois = rs.getString("mois");
                float total = rs.getFloat("total");
                series.getData().add(new XYChart.Data<>(mois, total));
            }

        } catch (SQLException e) {
            System.out.println("Erreur (chiffre/mois) : " + e.getMessage());
        }

        chiffreTotalParMois.getData().add(series);
        chiffreTotalParMois.setLegendVisible(false);
    }


    public void RetourMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) StatsRetourButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/walibixgr2eq6/admin-view.fxml"));
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
