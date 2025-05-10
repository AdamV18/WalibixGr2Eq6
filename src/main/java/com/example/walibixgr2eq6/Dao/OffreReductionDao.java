package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.OffreReduction;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class OffreReductionDao {
    private static DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    public static OffreReduction findById(int offreId) {
        String sql = "SELECT * FROM OffreReduction WHERE offre_reduc_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, offreId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new OffreReduction(
                            resultSet.getInt("offre_reduc_id"),
                            resultSet.getString("nom"),
                            resultSet.getString("description"),
                            resultSet.getDouble("pourcentage"),
                            resultSet.getInt("condition_age_min"),
                            resultSet.getInt("condition_age_max")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<OffreReduction> getAllOffres() {
        List<OffreReduction> list = new ArrayList<>();
        String sql = "SELECT * FROM OffreReduction";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OffreReduction offre = new OffreReduction(
                        rs.getInt("offre_reduc_id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("pourcentage"),
                        rs.getInt("condition_age_min"),
                        rs.getInt("condition_age_max")
                );
                list.add(offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void addOffreReduc(OffreReduction offre) {
        String sql = "INSERT INTO OffreReduction (nom, description, pourcentage, condition_age_min, condition_age_max) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, offre.getNom());
            pstmt.setString(2, offre.getDescription());
            pstmt.setDouble(3, offre.getPourcentage());
            pstmt.setInt(4, offre.getConditionAgeMin());
            pstmt.setInt(5, offre.getConditionAgeMax());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateOffreReduc(OffreReduction offre) {
        String sql = "UPDATE OffreReduction SET nom=?, description=?, pourcentage=?, condition_age_min=?, condition_age_max=? WHERE offre_reduc_id=?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, offre.getNom());
            pstmt.setString(2, offre.getDescription());
            pstmt.setDouble(3, offre.getPourcentage());
            pstmt.setInt(4, offre.getConditionAgeMin());
            pstmt.setInt(5, offre.getConditionAgeMax());
            pstmt.setInt(6, offre.getOffreReducId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOffreReduc(int id) {
        String sql = "DELETE FROM OffreReduction WHERE offre_reduc_id=?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void detachOffreFromUsers(int offreReducId) {
        String sql = "UPDATE user SET offre_reduc_id = NULL WHERE offre_reduc_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, offreReducId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignOffresToUsersByAge() {
        String getUsers = """
        SELECT user_id, date_naissance
        FROM user
        WHERE offre_reduc_id IS NULL
          AND admin = 0
          AND type_client != 'invité'
    """;

        String getOffres = "SELECT * FROM OffreReduction";
        String updateUser = "UPDATE user SET offre_reduc_id = ? WHERE user_id = ?";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt1 = conn.createStatement();
             Statement stmt2 = conn.createStatement();
             ResultSet rsUsers = stmt1.executeQuery(getUsers);
             ResultSet rsOffres = stmt2.executeQuery(getOffres)) {

            List<OffreReduction> offres = new ArrayList<>();
            while (rsOffres.next()) {
                offres.add(new OffreReduction(
                        rsOffres.getInt("offre_reduc_id"),
                        rsOffres.getString("nom"),
                        rsOffres.getString("description"),
                        rsOffres.getDouble("pourcentage"),
                        rsOffres.getInt("condition_age_min"),
                        rsOffres.getInt("condition_age_max")
                ));
            }

            while (rsUsers.next()) {
                int userId = rsUsers.getInt("user_id");
                Date dateNaissance = rsUsers.getDate("date_naissance");
                int age = calculateAge(dateNaissance.toLocalDate());

                for (OffreReduction offre : offres) {
                    if (age >= offre.getConditionAgeMin() && age <= offre.getConditionAgeMax()) {
                        try (PreparedStatement pstmt = conn.prepareStatement(updateUser)) {
                            pstmt.setInt(1, offre.getOffreReducId());
                            pstmt.setInt(2, userId);
                            pstmt.executeUpdate();
                        }
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}

