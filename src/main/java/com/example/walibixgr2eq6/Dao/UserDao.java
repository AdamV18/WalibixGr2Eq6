package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public boolean userExists(String email) {
        String sql = "SELECT 1 FROM user WHERE email = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllActiveUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE type_client NOT IN ('Admin', 'Archive')";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
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
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public void updateUser(User user) {
        String sql = "UPDATE user SET email=?, nom=?, prenom=?, date_naissance=?, type_client=?, admin=? WHERE user_id=?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getNom());
            stmt.setString(3, user.getPrenom());
            stmt.setDate(4, Date.valueOf(user.getDateNaissance()));
            stmt.setString(5, user.getTypeClient());
            stmt.setBoolean(6, user.isAdmin());
            stmt.setInt(7, user.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserById(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}