package tn.esprit.crud.controllers;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tn.esprit.crud.services.UserService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.crud.services.UserService;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tn.esprit.crud.models.CodePromo;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.PromoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.crud.test.HelloApplication;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterCodeAdmin implements Initializable {

    @FXML
    private TextField CodeAdmin;

    @FXML
    private DatePicker DateAdmin;

    @FXML
    private TextField UserIdAdmin;

    @FXML
    private TextField UtiliseAdmin;

    @FXML
    private ListView<String> ListUsers;

    private UserService userService;



    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<User> utilisateurs = userService.recuppererSansChampsSensibles2();
            ObservableList<String> userList = FXCollections.observableArrayList();

            for (User utilisateur : utilisateurs) {
                String userData = String.format("id : %-5d\nNom : %-20s\nPrénom : %-20s\nAdresse Email : %-20s",
                        utilisateur.getId(),
                        utilisateur.getNom(),
                        utilisateur.getPrenom(),
                        utilisateur.getEmail());
                userList.add(userData);
            }

            ListUsers.setItems(userList);
            ListUsers.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                        setFont(Font.font("System", FontWeight.BOLD, 12));
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception de récupération des utilisateurs depuis la base de données
        }
    }
    private void afficherAlerteConfirmationEnvoiEmail(User user) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation d'envoi d'e-mail");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Voulez-vous envoyer un e-mail à l'utilisateur pour lui annoncer qu'il a gagné un code promo ?");

        ButtonType ouiButton = new ButtonType("Oui");
        ButtonType nonButton = new ButtonType("Non");

        confirmationAlert.getButtonTypes().setAll(ouiButton, nonButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ouiButton) {
            // Envoyer l'e-mail à l'utilisateur
            String email = user.getEmail();
            String codeRecuperation = "Votre code promo est : " + CodeAdmin.getText(); // Récupérer le code ajouté
            // Appeler la méthode pour envoyer l'e-mail de récupération
            userService.envoyerEmailRecuperation2(email, codeRecuperation);
            afficherMessage("Succès", "E-mail envoyé avec succès à " + email);
        }
    }


    @FXML
    void AjouterCodeAdmin(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (CodeAdmin.getText().isEmpty() || DateAdmin.getValue() == null || UserIdAdmin.getText().isEmpty() || UtiliseAdmin.getText().isEmpty()) {
            afficherErreur("Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        // Vérifier si les valeurs saisies sont valides
        try {
            int code = Integer.parseInt(CodeAdmin.getText());
            LocalDate date_exp = DateAdmin.getValue();
            int user_id = Integer.parseInt(UserIdAdmin.getText());
            int utilise = Integer.parseInt(UtiliseAdmin.getText());

            // Ajouter le code promo
            PromoService promoService = new PromoService();
            CodePromo codePromo = new CodePromo();
            codePromo.setCode(code);
            codePromo.setDate_exp(java.sql.Date.valueOf(date_exp));
            codePromo.setUser_id(user_id);
            codePromo.setUtilise(utilise);

            promoService.ajouter(codePromo);
            afficherMessage("Succès", "Code promo ajouté avec succès.");

            // Récupérer l'utilisateur associé à l'ID et envoyer un e-mail
            User user = userService.getById(user_id);
            if (user != null) {
                afficherAlerteConfirmationEnvoiEmail(user);
            } else {
                afficherErreur("Erreur", "Impossible de trouver l'utilisateur avec l'ID spécifié.");
            }

        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir des valeurs numériques valides pour le code et l'utilisateur.");
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de l'ajout du code promo : " + e.getMessage());
            e.printStackTrace(); // Affichez la pile d'erreur pour un débogage approfondi
        }
    }


    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    public AjouterCodeAdmin() {
        try {
            userService = new UserService();
        } catch (SQLException e) {
            // Gérer l'exception
            e.printStackTrace();
        }
    }
    @FXML
    void VersCodeAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/test.fxml"));
        try {
            CodeAdmin.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    }



