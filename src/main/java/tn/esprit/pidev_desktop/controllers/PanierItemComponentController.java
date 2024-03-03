package tn.esprit.pidev_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import tn.esprit.pidev_desktop.models.PanierItem;

public class PanierItemComponentController {

    @FXML
    private ImageView productImage;

    @FXML
    private Text productName;

    @FXML
    private Text productPrice;

    @FXML
    private TextField quantityTextField;

    private PanierItem panierItem;

    public void initialize(PanierItem panierItem) {
        this.panierItem = panierItem;
        productName.setText(panierItem.getProduit().getNom());
        productPrice.setText(String.valueOf(panierItem.getProduit().getPrix()));
        quantityTextField.setText(String.valueOf(panierItem.getQuantite()));
    }



    @FXML
    void deleteItem(ActionEvent event) {

    }

    @FXML
    void updateQuantityButtonClicked(ActionEvent event) {
        int newQuantity = Integer.parseInt(quantityTextField.getText());
        panierItem.setQuantite(newQuantity);
    }
}
