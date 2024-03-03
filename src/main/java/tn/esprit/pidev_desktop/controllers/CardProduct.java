package tn.esprit.pidev_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tn.esprit.pidev_desktop.models.PanierItem;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.utils.MyDatabase;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;



public class CardProduct implements Initializable {

    private Alert alert;

    @FXML
    private Button btnadd;

    @FXML
    private AnchorPane cardform;

    @FXML
    private Label description_product;

    @FXML
    private Label prix_product;

    @FXML
    private ImageView product_image;

    @FXML
    private Label product_name;

    @FXML
    private Spinner<Integer> stock_productspinner;

    private SpinnerValueFactory<Integer> spin;

    private Produit produitcard;

    private int produitt_id;

private Connection connection;

public CardProduct() {
    connection = MyDatabase.getInstance().getConnection();
}

public void setData(Produit produitcard) {
    this.produitcard = produitcard;
    System.out.println(produitcard);

    produitt_id= produitcard.getId();
    System.out.println(produitcard.getNom());
    product_name.setText(produitcard.getNom());
   prix_product.setText("DT"+String.valueOf(produitcard.getPrix()));
    description_product.setText(produitcard.getDescription());
     pr= produitcard.getPrix();
    byte[] decodedBytes = Base64.getDecoder().decode(produitcard.getImage());
    String decodedString = new String(decodedBytes);
    InputStream inputstream = new ByteArrayInputStream(decodedBytes);
    Image image = new Image(inputstream, 173, 86, false, false);
    product_image.setImage(image);


     // je veux afficher l'image de produit de la base de donne


/*
    Image image = new Image(produitcard.getImage(), 173, 86, false, false);
    product_image.setImage(image);

 */

}

    private int stk;

    public void setStock(){
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0);
        stock_productspinner.setValueFactory(spin);
    }

    private double total;
    private float pr;

    public void add(ActionEvent actionEvent) {
        PanierItem panierItem = new PanierItem();
        panierItem.setProduit(produitcard);
        panierItem.setQuantite(stock_productspinner.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStock();

    }


}
