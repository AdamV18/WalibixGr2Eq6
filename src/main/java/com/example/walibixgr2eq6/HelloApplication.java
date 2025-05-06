package com.example.walibixgr2eq6;

import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import com.example.walibixgr2eq6.Dao.*;
import com.example.walibixgr2eq6.Controller.*;
import com.example.walibixgr2eq6.Model.*;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Bienvenue sur WALIBIX");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        // Déclaration et instanciation des objets des classes DaoFactory, et les DaoImpl
        DaoFactory dao = DaoFactory.getInstance("walibix", "root", "");

        launch();
    }
}