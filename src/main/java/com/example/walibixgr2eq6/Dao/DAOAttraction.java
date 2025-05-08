package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Attraction;

import java.sql.*;


public class DAOAttraction {

    private DaoFactory daoFactory; //recup DaoFactory pour la bdd

    public DAOAttraction(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Attraction getAttractionNom(String nom) { //récupérer les attractions par le nom
        String sql = "SELECT * FROM attraction WHERE nom = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, nom); //on recup le nom de l'attraction qui est en paramètre
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Attraction( //recup les info de l'attraction
                            resultSet.getInt("attraction_id"),
                            resultSet.getString("nom"),
                            resultSet.getString("type_attrac"),
                            resultSet.getString("description"),
                            resultSet.getDouble("prix_base"),
                            resultSet.getString("image")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("L'attraction n'a pas pu être récupérée : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
