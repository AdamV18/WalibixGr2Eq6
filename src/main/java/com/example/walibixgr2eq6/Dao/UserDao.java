package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour gérer les opérations liées aux utilisateurs
 * permet de récupérer, modifier, supprimer et vérifier les utilisateurs dans la bdd
 */
public class UserDao {
    private DaoFactory daoFactory;

    /**
     * constructeur du user
     * @param daoFactory
     */
    public UserDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * recherche un utilisateur par son id
     * @param userId
     * @return
     */
    public User findById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("email"),
                            resultSet.getString("mot_de_passe"),
                            resultSet.getBoolean("admin"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getObject("date_naissance", LocalDate.class),
                            resultSet.getString("type_client"),
                            resultSet.getObject("offre_reduc_id") != null ? resultSet.getInt("offre_reduc_id") : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * vérifie les infos de connexion d'un utilisateur
     * @param email
     * @param motDePasse
     * @return
     */
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

    /**
     * vérifie, par le mail, si l'utilisateur existe déjà
     * @param email
     * @return
     */
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

    /**
     * récupère tous les utilisateurs actifs (pas admin, ni archivé)
     * @return
     */
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
                        rs.getObject("date_naissance", LocalDate.class),
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


    /**
     * met à jour les infos d'un utilisateur existant
     * @param user
     */
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

    /**
     * supprime un utilisateur de la bdd à partir de son id
     * @param userId
     */
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