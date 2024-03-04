package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ModifierUserAdmin {

    @FXML
    private ListView<String> LVcmd;

    @FXML
    private TextField NouveauAdresse;

    @FXML
    private TextField NouveauEmail;

    @FXML
    private TextField NouveauMdp;

    @FXML
    private TextField NouveauNom;

    @FXML
    private TextField NouveauPrenom;

    private UserService userService;

    {
        try {
            userService = new UserService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private int userId;

        public void initData(int userId) {
            this.userId = userId;
            // Utilisez l'ID de l'utilisateur pour effectuer des opérations spécifiques dans votre vue
        }

        // Autres méthodes de votre contrôleur...



    @FXML
    void initialize() {
        try {
            List<User> users = userService.recupperer();

            // Créer une ObservableList pour stocker les données des utilisateurs
            ObservableList<String> userDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-20s %-20s %-20s %-20s %-20s", "Nom", "Prénom", "Email", "Adresse", "Mot de passe");
            userDataList.add(columnTitles);

            // Itérer à travers la liste des utilisateurs et ajouter leurs détails à la userList
            for (User user : users) {
                String userData = String.format("%-20s %-20s %-20s %-20s %-20s",
                        user.getNom(),
                        user.getPrenom(),
                        user.getEmail(),
                        user.getAdresse(),
                        user.getMdp());
                userDataList.add(userData);
            }

            // Définir les éléments pour la ListView
            LVcmd.setItems(userDataList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Add an event handler to the ListView to detect selection changes
        LVcmd.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.startsWith("Nom")) {
                // Parse the selected item to extract user information
                String[] userInfo = newValue.split("\\s+");
                String nom = userInfo[0];
                String prenom = userInfo[1];
                String email = userInfo[2];
                String adresse = userInfo[3];
                String mdp = userInfo[4];

                // Populate the text fields with the user information
                NouveauNom.setText(nom);
                NouveauPrenom.setText(prenom);
                NouveauEmail.setText(email);
                NouveauAdresse.setText(adresse);
                NouveauMdp.setText(mdp);
            }
        });
    }

    @FXML
        void ModifierUserAdmin(ActionEvent event) {
            // Récupérer les nouvelles valeurs des champs de texte
            String nouveauNom = NouveauNom.getText();
            String nouveauPrenom = NouveauPrenom.getText();
            String nouveauEmail = NouveauEmail.getText();
            String nouveauAdresse = NouveauAdresse.getText();
            String nouveauMdp = NouveauMdp.getText();

            // Crypter le nouveau mot de passe
            String mdpCrypte = BCrypt.hashpw(nouveauMdp, BCrypt.gensalt());

            // Créer un objet User avec les nouvelles valeurs
            User user = new User();
            user.setNom(nouveauNom);
            user.setPrenom(nouveauPrenom);
            user.setEmail(nouveauEmail);
            user.setAdresse(nouveauAdresse);
            user.setMdp(mdpCrypte); // Utiliser le mot de passe crypté

            // Appeler le service pour mettre à jour l'utilisateur dans la base de données
            UserService userService = null;
            try {
                userService = new UserService();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                userService.modifierParEmail(user);
                afficherMessage("Succès", "L'utilisateur a été modifié avec succès.");
            } catch (SQLException e) {
                afficherErreur("Erreur", "Impossible de modifier l'utilisateur : " + e.getMessage());
            }
        }

        // Méthode utilitaire pour afficher une boîte de dialogue d'information
        private void afficherMessage(String titre, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titre);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        // Méthode utilitaire pour afficher une boîte de dialogue d'erreur
        private void afficherErreur(String titre, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(titre);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        @FXML
        void BackToAffiche(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/test.fxml"));
        try {
            NouveauEmail.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
