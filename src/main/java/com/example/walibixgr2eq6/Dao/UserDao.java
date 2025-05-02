package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.User;

import java.sql.*;
import java.time.LocalDate;

public class UserDao {
    private DaoFactory daoFactory;

    public UserDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public User checkLogin(String email, String motDePasse) {
        User user = null;

        String sql = "SELECT * FROM user WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getBoolean("admin"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("type_client"),
                            rs.getObject("offre_reduc_id") != null ? rs.getInt("offre_reduc_id") : null
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}