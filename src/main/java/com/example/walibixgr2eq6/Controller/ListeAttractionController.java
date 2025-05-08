package com.example.walibixgr2eq6.Controller;

import com.example.walibixgr2eq6.Dao.DAOAttraction;
import com.example.walibixgr2eq6.Dao.DaoFactory;
import com.example.walibixgr2eq6.Model.Attraction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ListeAttractionController {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button validerButton;

    private DAOAttraction daoAttraction;

    public ListeAttractionController() {
        DaoFactory daoFactory = DaoFactory.getInstance("walibix", "root", "");
        this.daoAttraction = new DAOAttraction(daoFactory);
    }

    @FXML
    public void initialize() {
       // à voir si on rajoute pour cacher le bouton tant que rien n'est choisi mais pas forcement necessaire
    }

    @FXML
    private void valider(ActionEvent event) { //si on clique sur le bouton valider :
        String choix = comboBox.getValue();
        if (choix != null && !choix.isEmpty()) { //verifie qu'une attraction est choisie
            String nomAttraction = choix.split(" - ")[0]; // recupère le nom de l'attraction avant le - dans la combobox
            Attraction attraction = daoAttraction.getAttractionNom(nomAttraction);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walibixgr2eq6/DetailsCreneauxAttraction.fxml")); //va lire le fichier fxml
                Parent root = loader.load();

                DetailsCreneauxAttractionController controller = loader.getController();
                controller.setAttractionNom(attraction.getNom()); // passe à la page des creneaux de l'attraction choisir

                Stage stage = (Stage) comboBox.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Attraction"); //titre de la page
                stage.show(); //affiche la nouvelle page avec les infos de l'attraction choisie
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
