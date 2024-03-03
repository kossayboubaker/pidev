package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.pidev_desktop.services.PanierService;

import java.sql.SQLException;

public class AfficherPanier {

    ObservableList<ListProduits> list = FXCollections.observableArrayList();
    private final PanierService ps = new PanierService();
    private PanierService css = new PanierService();
    @FXML
    private TextField prixNN;

    @FXML
    private TextField stockNN;
    @FXML
    private ListView<?> listview;

    public int idp;
    public String nomp;
    public float prixp;
    public int stockp;
    public String imagep;
    public String descriptionp;
    public int prod_id;

    public String getImagep() {
        return imagep;
    }
public void setImagep(String imagep) {
        this.imagep = imagep;
}
public String getDescriptionp() {
        return descriptionp;
}
public void setDescriptionp(String descriptionp) {
        this.descriptionp = descriptionp;
}
public int getProd_id() {
        return prod_id;
}
public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
}
public String getNomp() {
        return nomp;
}
public void setNomp(String nomp) {
        this.nomp = nomp;
}

public float getPrixp() {
        return prixp;
}
public void setPrixp(float prixp) {
        this.prixp = prixp;
}

public int getStockp() {
        return stockp;
}
public void setStockp(int stockp) {
        this.stockp = stockp;
}

public void SetValue(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
    ListProduits selected = (ListProduits) listview.getSelectionModel().getSelectedItem();

    if (selected != null) {


    }
}
    public void modifierpanier(ActionEvent actionEvent) {
        int stock = Integer.parseInt(stockNN.getText());
        int prixx = Integer.parseInt(prixNN.getText());

        ListProduits p = new ListProduits();
    }

    public void supppanier(ActionEvent actionEvent) {
    }

    public void triasc(ActionEvent actionEvent) {
    }

    public void tridesc(ActionEvent actionEvent) {
    }
}
