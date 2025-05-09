package com.example.walibixgr2eq6.Dao;

import com.example.walibixgr2eq6.Model.Attraction;
import com.example.walibixgr2eq6.Model.Reservation;
import com.example.walibixgr2eq6.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;


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

    // permet de creer la liste pour affichage grace a bdd et pas direct dans la combobox
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

    public void ajouter(Reservation reservation) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            // Exécution de la requête INSERT INTO de l'objet product en paramètre
            PreparedStatement preparedStatement = connexion.prepareStatement(
                    "INSERT INTO reservation(reservation_id, date, heure, user_id, attraction_id, prix_total,prix_avec_reduc) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, reservation.getReservationId());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(3, Time.valueOf(reservation.getHeure()));
            preparedStatement.setInt(4, Session.getCurrentUserId());
            preparedStatement.setInt(5, reservation.getAttractionId());
            preparedStatement.setDouble(6, reservation.getPrixTotal());
            preparedStatement.setDouble(7, reservation.getPrixAvecReduc());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de la réservation impossible");
        }
    }

}

