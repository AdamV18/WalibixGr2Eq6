package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Attraction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
                            //recup les info de l'attraction
                            int attractionId = resultSet.getInt("attraction_id");
                            nom = resultSet.getString("nom");
                            String typeAttrac = resultSet.getString("type_attrac");
                            String description = resultSet.getString("description");
                            double prixBase = resultSet.getDouble("prix_base");
                            String image = resultSet.getString("image");
                            System.out.println("Attraction sélectionnée : Id -> : " + attractionId + " Nom : " + nom + " Type : " +typeAttrac);
                            return new Attraction(attractionId, nom, typeAttrac, description, prixBase, image);
                }
            }
        } catch (SQLException e) {
            System.err.println("L'attraction n'a pas pu être récupérée : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // permet de creer la liste pour affichage grace a bdd
    public List<Attraction> getAllAttractions() {
        return null;
    }
}

