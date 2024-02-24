package tn.esprit.pidev_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class ModifierCommande {

    @FXML
    private DatePicker datecomd;

    @FXML
    private TextField id_prodMo;

    @FXML
    private TextField id_userMo;

    @FXML
    private TextField idcmdMo;

    @FXML
    private TextField montanttotalMo;

    @FXML
    private TextField nom_userMo;

    @FXML
    private TextField prenom_userMo;

    @FXML
    private TextField quantiteMo;


    public void retourneListcomd(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AfficherCommande.fxml"));
        try {
            id_prodMo.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void modifiercommande(ActionEvent actionEvent) {
        // Contrôle de saisie des champs de texte
        if (id_prodMo.getText().isEmpty() || id_userMo.getText().isEmpty() || montanttotalMo.getText().isEmpty() || nom_userMo.getText().isEmpty() || prenom_userMo.getText().isEmpty() || quantiteMo.getText().isEmpty() || idcmdMo.getText().isEmpty() || datecomd.getValue().toString() == null) {
            afficherErreur("Erreur", "Veuillez remplir tous les champs");
            return;
        }

        // Contrôle de saisie pour l'existence de l'ID de commande dans la base de données
        try {
            int idCommande = Integer.parseInt(idcmdMo.getText());
            CommandeService cs = new CommandeService();
            boolean commandeExiste = cs.verifierExistenceCommande(idCommande);

            if (!commandeExiste) {
                afficherErreur("Erreur", "La commande avec l'ID spécifié n'existe pas dans la base de données.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID de commande valide (nombre entier uniquement).");
            return;
        } catch (Exception e) {
            afficherErreur("Erreur", "Erreur lors de la vérification de l'existence de la commande dans la base de données.");
            e.printStackTrace();
            return;
        }

        // Contrôle de saisie pour id valide
        try {
            int id = Integer.parseInt(idcmdMo.getText());
            if (id <= 0) {
                afficherErreur("Erreur", "Veuillez saisir un ID de commande valide (nombre entier positif uniquement).");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID de commande valide (nombre entier uniquement).");
            return;
        }

        // Contrôle de saisie pour le nom valide
        if (!isString(nom_userMo.getText())) {
            afficherErreur("Erreur", "Le nom doit être une chaîne de caractères valide.");
            return;
        }

        // Contrôle de saisie pour quantité valide
        try {
            int quantite = Integer.parseInt(quantiteMo.getText());
            if (quantite <= 0) {
                afficherErreur("Erreur", "Veuillez saisir une quantité valide (nombre entier positif uniquement).");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir une quantité valide (nombre entier uniquement).");
            return;
        }

        // Contrôle de saisie pour le montant total valide
        try {
            float montantTotal = Float.parseFloat(montanttotalMo.getText());
            if (montantTotal <= 0) {
                afficherErreur("Erreur", "Veuillez saisir un montant total valide (nombre décimal positif uniquement).");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un montant total valide (nombre décimal uniquement).");
            return;
        }

        // Si toutes les validations sont passées avec succès, modifier la commande
        try {
            CommandeService cs = new CommandeService();
            Commande c = new Commande();
            c.setId(Integer.parseInt(idcmdMo.getText()));
            c.setPro_id(Integer.parseInt(id_prodMo.getText()));
            c.setUser_id(Integer.parseInt(id_userMo.getText()));
            c.setDate_comd(Date.valueOf(datecomd.getValue()));
            c.setMontantTotal(Float.parseFloat(montanttotalMo.getText()));
            c.setNom_user(nom_userMo.getText());
            c.setPrenom_user(prenom_userMo.getText());
            c.setQauntite(Integer.parseInt(quantiteMo.getText()));

            cs.modifier(c);

            // Afficher un message de confirmation
            afficherMessage("Succès", "La commande a été modifiée avec succès.");
        } catch (Exception e) {
            // Gérer toute autre exception imprévue
            afficherErreur("Erreur", "Une erreur s'est produite lors de la modification de la commande.");
            e.printStackTrace();
        }
    }

    private boolean isString(String text) {
        return text.matches("[a-zA-Z\\s]+");
    }


    private void afficherErreur(String erreur, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(erreur);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessage(String succès, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(succès);
        alert.setContentText(message);
        alert.showAndWait();
    }
}