package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Attraction;
import com.example.walibixgr2eq6.Model.OffreReduction;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

/**
 * DAO pour lister, récupérer le nom des attractions
 * Dao présent dans la branche reservationAttraction
 */
public class DAOAttraction {

    private DaoFactory daoFactory; //recup DaoFactory pour la bdd

    /**
     * creation instance DAOAttraction avec factory
     * @param daoFactory
     */
    public DAOAttraction(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * recupère toutes attractions de la bdd grâce à leur nom
     * @param nom
     * @return
     */
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

    /**
     * permet de creer la liste pour affichage grâce a bdd et pas direct dans la combobox
     * @return
     */
    public List<Attraction> getAllAttractions() {
        List<Attraction> attractions = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM attraction");

            while (resultSet.next()) {
                int attractionId = resultSet.getInt("attraction_id");
                String nom = resultSet.getString("nom");
                String typeAttrac = resultSet.getString("type_attrac");
                String description = resultSet.getString("description");
                double prixBase = resultSet.getDouble("prix_base");
                String image = resultSet.getString("image");

                attractions.add(new Attraction(attractionId, nom, typeAttrac, description, prixBase, image));
            }

        } catch (SQLException e) {
            System.err.println("Erreur :" + e.getMessage());
            e.printStackTrace();
        }

        return attractions;
    }



}

