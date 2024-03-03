package tn.esprit.pidev_desktop.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ModifierProduit {

    @FXML
    private ListView<String> ListProduitN;

    @FXML
    private ListView<String> ListProduit;
    @FXML
    private TextField DescriptionN;
    @FXML
    private AnchorPane main_for;

    @FXML
    private TextField IdN;

@FXML
    private TextField NomN;

    @FXML
    private TextField PrixN;

    @FXML
    private TextField StockN;

    @FXML
    private ImageView imageN;


    public void getid(String id){
        IdN.setText(id);
    }

    public void setProduitId(String id){
        IdN.setText(id);
    }
    public void getnom(String nom){
        NomN.setText(nom);
    }
    public void getprix(String prix){
        PrixN.setText(prix);
    }
    public void getstock(String stock){
        StockN.setText(stock);
    }
    public void getdescription(String description){
        DescriptionN.setText(description);
    }

    public void getimage(String image){
        imageN.setImage(new Image(image));
    }
    @FXML
        void importimagebtn(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        File file = openFile.showOpenDialog(main_for.getScene().getWindow());
        if (file != null) {
            Data.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 120, 127, false, true);
            imageN.setImage(image);
        }

        }







    @FXML
    void initialize() {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.recuperer();

            // Créer une ObservableList pour stocker les données des commandes
            ObservableList<String> produitDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-20s %-20s %-20s %-20s %-20s %-20s ", "ID", "nom",  "description", "prix", "stock", "image");
            produitDataList.add(columnTitles);

            // Itérer à travers la liste des commandes et ajouter leurs détails à la commandeDataList
            for (Produit produit : produits) {
                String produitData = String.format("%-20s %-20s %-20s %-20s %-20s %-20s",
                        produit.getId(),
                        produit.getNom(),
                        produit.getDescription(),
                        produit.getPrix(),
                        produit.getStock(),
                        produit.getImage());
                produitDataList.add(produitData);
            }




            // Définir les éléments pour la ListView
            ListProduit.setItems(produitDataList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Add an event handler to the ListView to detect selection changes
        ListProduit.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.startsWith("ID")) {
                // Parse the selected item to extract repas information
                String[] repasInfo = newValue.split("\\s+");
                String id = repasInfo[0];
                String nom = repasInfo[1];
                String description = repasInfo[2];
                String prix = repasInfo[3];
                String stock = repasInfo[4];
                String image = repasInfo[5];
                // Populate the text fields with the repas information
                IdN.setText(id);
             NomN.setText(nom);
                            DescriptionN.setText(description);
                            PrixN.setText(prix);
                            StockN.setText(stock);
                            imageN.getImage();

            }
        });
    }

    public void gesmarketplace(MouseEvent mouseEvent) {
    }

    public void modifierproduitN(ActionEvent actionEvent) {


            if (ListProduit.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun régime sélectionné");
                alert.setContentText("Veuillez sélectionner un produit à modifier.");
                alert.showAndWait();
                return;
            }

            // Récupérer la chaîne sélectionnée dans la ListView
            String selectedProduit = ListProduit.getSelectionModel().getSelectedItem();

            // Vérifier si la chaîne sélectionnée est vide ou si elle contient les titres des colonnes
            if (selectedProduit== null || selectedProduit.trim().isEmpty() || selectedProduit.startsWith("ID")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sélection invalide");
                alert.setContentText("Veuillez sélectionner un produit valide à modifier.");
                alert.showAndWait();
                return;
            }

            // Extraire l'ID du régime à partir de la chaîne sélectionnée
            int produitId = Integer.parseInt(selectedProduit.trim().split("\\s+")[0]);

            // Demander confirmation à l'utilisateur
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmer la modification");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce produit ?");

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé la modification, ouvrir la fenêtre de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyProduit.fxml"));
                try {

                    Parent root = loader.load();

                    // Passer l'ID du régime à modifier au contrôleur de la fenêtre de modification
                    ModifierProduit controller = loader.getController();


                    // Créer une nouvelle scène pour la fenêtre de modification
                    Scene scene = new Scene(root);

                    // Récupérer la scène actuelle à partir de l'événement source
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                    // Afficher la scène de modification dans la fenêtre principale
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    private int produitId;
    public void initData(int produitId) {
        this.produitId = produitId;
        // Utilisez cet ID pour afficher les informations du produit dans votre fenêtre de modification
        // Par exemple, vous pouvez remplir les champs de texte avec les détails du produit à modifier
    }
    }


