package com.example.walibixgr2eq6.Dao;

import java.sql.*;
import java.util.ArrayList;

/**
 * La DAO Factory (DaoFactory.java) permet d'initialiser le DAO en chargeant notamment les drivers nécessaires
 * (ici un driver JDBC MySQL) et se connecte à la base de données. La Factory peut fournir plusieurs DAO
 */
public class DaoFactory {
    /**
     * Attributs private pour la connexion JDBC
     */
    private static String url;
    private String username;
    private String password;

    // constructeur
    public DaoFactory(String url, String username, String password) {
        DaoFactory.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode qui retourne 1 objet de DaoFactory
     * @param : url, username et password de la base de données
     * @return : objet de la classe DaoFactoru
     */
    public static DaoFactory getInstance(String database, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var4) {
            System.out.println("Erreur de connexion à la base de données");
        }

        url = "jdbc:mysql://localhost:3306/" + database;
        DaoFactory instance = new DaoFactory(url, username, password);
        return instance;
    }

    /**
     * Méthode qui retourne le driver de base de données approprié
     * @return : le driver approprié
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, this.username, this.password);
    }

    /**
     *     Fermer la connexion à la base de données
     */
    public void disconnect() {
        Connection connexion = null;

        try {
            connexion = this.getConnection();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de déconnexion à la base de données");
        }

    }
}
