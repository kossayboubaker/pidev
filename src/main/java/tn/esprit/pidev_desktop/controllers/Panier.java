package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.utils.MyDatabase;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Panier implements Initializable {


    @FXML
    private Button btncon;

    @FXML
    private AnchorPane menuform;

    @FXML
    private VBox menugrid;



    @FXML
    private ScrollPane menuscroll;

    @FXML
    private Label menutotal;
    @FXML
    private ListView<Panier> menulistview;

private ObservableList<Produit> po = FXCollections.observableArrayList();
private Connection connection;
public Panier() {
    connection = MyDatabase.getInstance().getConnection();
}
private ObservableList<Panier> menuorder;

public ObservableList<Produit> menugetdata() {

    String sql = "SELECT * FROM produit";
    ObservableList<Produit> listdata = FXCollections.observableArrayList();

    try {
        PreparedStatement prepare = connection.prepareStatement(sql);
       ResultSet rs = prepare.executeQuery();
       Produit proo;
       while (rs.next()) {
proo = new Produit(  rs.getString("nom"), rs.getString("description"), rs.getFloat("prix") , rs.getInt("stock") , rs.getString("image"));

listdata.add(proo);
       }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return listdata;

}

public void menudisplay() {
    po.clear();
    po.addAll(menugetdata());
    int row = 0;
    int colum = 0;
   // menugrid.getRowConstraints().clear();
   // menugrid.getColumnConstraints().clear();
    ProduitService po = new ProduitService();
    List<Produit> listproduit;
    try {
        listproduit = po.recuperer();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    for (Produit p : listproduit) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/CardProduct.fxml"));
            AnchorPane pane = loader.load();
            CardProduct cor = loader.getController();
            cor.setData(p);
            if (colum == 4) {
                colum = 0;
                row++;
            }
           menugrid.getChildren().add(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    menudisplay();

    }


}
