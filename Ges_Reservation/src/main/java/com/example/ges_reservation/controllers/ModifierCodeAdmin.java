package com.example.ges_reservation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.ges_reservation.models.CodePromo;
import com.example.ges_reservation.services.PromoService;
import com.example.ges_reservation.test.HelloApplication;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ModifierCodeAdmin {

    @FXML
    private ListView<CodePromo> ListeCodes; // Correction : Définir le type générique comme CodePromo

    @FXML
    private TextField NouveauCode;

    @FXML
    private TextField NouveauUserId;

    @FXML
    private TextField NouveauUtilise;

    @FXML
    private DatePicker NouvelleDate;

    private PromoService promoService;

    private CodePromo selectedCode;

    public ModifierCodeAdmin() {
        try {
            promoService = new PromoService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        ListeCodes.setCellFactory(param -> new ListCell<CodePromo>() {
            @Override
            protected void updateItem(CodePromo codePromo, boolean empty) {
                super.updateItem(codePromo, empty);

                if (empty || codePromo == null) {
                    setText(null);
                } else {
                    String text = String.format("ID = %d%n" +
                                    "Code = %d%n" +
                                    "Date d'expiration = %s%n" +
                                    "Utilisé = %d%n" +
                                    "User ID = %d%n",
                            codePromo.getId(), codePromo.getCode(), codePromo.getDate_exp(), codePromo.getUtilise(), codePromo.getUserId());
                    setText(text);
                }
            }
        });

        try {
            List<CodePromo> codesPromo = promoService.recupperer();
            ObservableList<CodePromo> observableList = FXCollections.observableArrayList(codesPromo);
            ListeCodes.setItems(observableList);

            ListeCodes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedCode = newValue;
                    afficherDetailsCodePromo(newValue);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void afficherDetailsCodePromo(CodePromo codePromo) {
        // Afficher les détails du code promo dans les champs de texte
        NouveauCode.setText(String.valueOf(codePromo.getCode()));
        NouveauUserId.setText(String.valueOf(codePromo.getUserId()));
        NouveauUtilise.setText(String.valueOf(codePromo.getUtilise()));
        // Note : Ne pas afficher l'ID et la date d'expiration
        // La date d'expiration est déjà affichée dans la cellule de la ListView
    }


    @FXML
    void Modifier(ActionEvent event) {
        try {
            if (selectedCode == null) {
                showAlert(Alert.AlertType.WARNING, "Aucun code promo sélectionné", "Veuillez sélectionner un code promo à modifier.");
                return;
            }

            // Vérifier si les champs sont vides
            if (NouveauCode.getText().isEmpty() || NouveauUserId.getText().isEmpty() || NouveauUtilise.getText().isEmpty() || NouvelleDate.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Champs vides", "Veuillez remplir tous les champs.");
                return;
            }

            // Vérifier si les valeurs saisies sont valides
            int nouveauCode = Integer.parseInt(NouveauCode.getText());
            int utiliseNouv = Integer.parseInt(NouveauUtilise.getText());
            int useridNouv = Integer.parseInt(NouveauUserId.getText());
            LocalDate dateExp = NouvelleDate.getValue();

            // Vérifier si le code est composé de 6 chiffres exactement
            if (NouveauCode.getText().length() != 6) {
                showAlert(Alert.AlertType.WARNING, "Code invalide", "Le code doit être composé de 6 chiffres exactement.");
                return;
            }

            // Vérifier si la date d'expiration est valide (postérieure à la date actuelle)
            if (dateExp.isBefore(LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, "Date invalide", "La date d'expiration doit être postérieure à la date actuelle.");
                return;
            }

            // Mettre à jour les attributs de l'objet CodePromo sélectionné
            selectedCode.setCode(nouveauCode);
            selectedCode.setDate_exp(java.sql.Date.valueOf(dateExp));
            selectedCode.setUtilise(utiliseNouv);
            selectedCode.setUser_id(useridNouv);

            // Appeler le service pour modifier le code promo dans la base de données
            promoService.modifier(selectedCode);

            // Afficher un message de confirmation
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le code promo a été modifié avec succès.");

            // Rafraîchir la liste après la modification
            List<CodePromo> codesPromo = promoService.recupperer();
            ObservableList<CodePromo> observableList = FXCollections.observableArrayList(codesPromo);
            ListeCodes.setItems(observableList);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs numériques valides.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le code promo.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void ToCodeAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/CodeAdmin.fxml"));
        try {
            NouveauCode.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ToDashboard(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Dashboard.fxml"));
        try {
            NouveauCode.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ToUserAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/UserAdmin.fxml"));
        try {
            NouveauCode.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void BackToLogin(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Charger la vue de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LOGIN.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Configurer la scène
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void ToAfficherCode(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test.fxml"));
        try {
            NouveauCode.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void initData(int codeId) {
    }
}