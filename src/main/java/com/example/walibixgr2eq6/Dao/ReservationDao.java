package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    private final Connection connection;

    public ReservationDao(Connection connection) {
        this.connection = connection;
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.reservation_id, r.date, r.heure, r.user_id, r.attraction_id, " +
                "r.prix_total, r.prix_avec_reduc, a.nom " +
                "AS attraction_nom FROM reservation r JOIN attraction a ON r.attraction_id = a.attraction_id " +
                "WHERE r.user_id = ? " +
                "ORDER BY r.date ASC, r.heure ASC;\n";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("heure").toLocalTime(),
                        rs.getInt("user_id"),
                        rs.getInt("attraction_id"),
                        rs.getString("attraction_nom"),
                        rs.getDouble("prix_total"),
                        rs.getDouble("prix_avec_reduc")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}