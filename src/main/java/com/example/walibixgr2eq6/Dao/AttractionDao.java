package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Attraction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour lister, ajouter, modifier et supprimer des attractions
 * Dao présent dans la branche connexion
 */
public class AttractionDao {
    private static DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    /*public AttractionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }*/

    /**
     * recupère toutes attractions de la bdd
     * @return
     */
    public static List<Attraction> getAllAttractions() {
        List<Attraction> list = new ArrayList<>();
        String sql = "SELECT * FROM Attraction";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Attraction a = new Attraction(
                        rs.getInt("attraction_id"),
                        rs.getString("nom"),
                        rs.getString("type_attrac"),
                        rs.getString("description"),
                        rs.getDouble("prix_base"),
                        rs.getString("image")
                );
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * permet de mettre à jour les attractions de la bdd
     * @param attraction
     */
    public static void updateAttraction(Attraction attraction) {
        String sql = "UPDATE Attraction SET nom=?, type_attrac=?, description=?, prix_base=?, image=? WHERE attraction_id=?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getTypeAttrac());
            pstmt.setString(3, attraction.getDescription());
            pstmt.setDouble(4, attraction.getPrixBase());
            pstmt.setString(5, attraction.getImage());
            pstmt.setInt(6, attraction.getAttractionId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * permet d'ajouter une nouvelle attraction dans la bdd
     * l'id utilisé est auto incrementé dans la bdd
     * @param attraction
     */
    public static void addAttraction(Attraction attraction) {
        String sql = "INSERT INTO Attraction (nom, type_attrac, description, prix_base, image) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getTypeAttrac());
            pstmt.setString(3, attraction.getDescription());
            pstmt.setDouble(4, attraction.getPrixBase());
            pstmt.setString(5, attraction.getImage());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * permet de supprimer une attraction de la bdd
     * @param id
     */
    public static void deleteAttraction(int id) {
        String sql = "DELETE FROM Attraction WHERE attraction_id=?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
