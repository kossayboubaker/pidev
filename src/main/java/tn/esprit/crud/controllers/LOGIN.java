package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LOGIN {

    @FXML
    private TextField LogInEmail;

    @FXML
    private PasswordField LogInMdp;


    @FXML
    void verspageadus(ActionEvent event) throws SQLException {
        // Récupérer l'email et le mot de passe entrés par l'utilisateur
        String email = LogInEmail.getText();
        String mdp = LogInMdp.getText();

        // Vérifier si l'email et le mot de passe correspondent à l'un des Admin
        if ((email.equals("azerbenamoradmin@gmail.com") && mdp.equals("azeradmin")) ||
                (email.equals("nessimayadiadmin@gmail.com") && mdp.equals("nessimayadiadmin")) ||
                (email.equals("marwanhamedadmin@gmail.com") && mdp.equals("marwanhamedadmin")) ||
                (email.equals("kossayboubakeradmin@gmail.com") && mdp.equals("kossayboubakeradmin")) ||
                (email.equals("amirbargouguiadmin@gmail.com") && mdp.equals("amirbargouguiadmin"))) {
            // Naviguer vers la page de l'administrateur (PageAdmin.fxml)
            navigateToPage("/tn/esprit/crud/test.fxml", event);
        } else {
            // Sinon, vérifier si les informations d'identification correspondent à un utilisateur dans la base de données
            UserService userService = new UserService();
            try {
                User user = userService.authentifier(email, mdp);
                if (user != null) {
                    // Naviguer vers la page utilisateur (PageUser.fxml) en passant les informations de l'utilisateur
                    navigateToPageUser("/tn/esprit/crud/PageUser.fxml", event, user);
                } else {
                    afficherErreur("Erreur", "Email ou mot de passe incorrect.");
                }
            } catch (SQLException e) {
                afficherErreur("Erreur", "Erreur lors de la connexion : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void navigateToPageUser(String pagePath, ActionEvent event, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pagePath));
            Parent root = loader.load();
            PageUser controller = loader.getController();
            controller.setNom(user.getNom());
            controller.setPrenom(user.getPrenom());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void navigateToPage(String pagePath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pagePath));
            Parent root = loader.load();
            Stage stage = (Stage) LogInEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void PageOubliermdp(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/MotDePasseOublier.fxml"));
        try {
            LogInMdp.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void pageSignUp(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SignUp.fxml"));
        try {
            LogInMdp.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
    @FXML
    void ToReclamation(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/Reclamation.fxml"));
        try {
            LogInMdp.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void quitter(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir quitter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Fermer l'application
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
