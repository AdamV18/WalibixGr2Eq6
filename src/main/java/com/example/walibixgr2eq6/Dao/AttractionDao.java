package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Attraction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDao {
    private static DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");

    /*public AttractionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }*/



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
