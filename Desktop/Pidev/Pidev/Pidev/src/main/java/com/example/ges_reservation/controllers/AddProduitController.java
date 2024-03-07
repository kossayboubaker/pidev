package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import com.example.ges_reservation.models.Categorie;
import com.example.ges_reservation.models.Produit;
import com.example.ges_reservation.services.CategorieService;
import com.example.ges_reservation.services.ProduitService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProduitController implements Initializable {




    @FXML
    private ChoiceBox<String> cat;

    @FXML
    private TextField des;

    @FXML
    private ImageView imv;
    @FXML
    private TextField nom;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;

    @FXML
    private TextField cert ;

    int c;
    int file;
    File pDir;
    File pfile;
    String lien;

    CategorieService cs = new CategorieService  ();
    ProduitService p = new ProduitService ();
    @FXML
    void Ajouter(ActionEvent event) throws SQLException {

        if (des.getText().equals("") || nom.getText().equals("")|| cert.getText().equals("") || cat.getValue().equals("")|| file==0 || Integer.parseInt(quantite.getText())==0|| prix.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "vérifier votre champs", ButtonType.OK);
            alert.showAndWait();
        }
       else if (!isValidFloat(prix.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "vérifier Le prix", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            Produit o = new Produit();
            o.setId_cat(cs.getCategorieByDescription(cat.getValue()).getId_categori());
            o.setNom(nom.getText());
            o.setPrix(Float.parseFloat(prix.getText()));
            o.setDescription(des.getText());
            o.setQuantite(Integer.parseInt(quantite.getText()));
            o.setImage(lien);
            o.setCertif(cert.getText());
            copier(pfile, pDir);
            p.addProdui(o);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ajout Avec success", ButtonType.OK);
            alert.showAndWait();

            Parent root;
            try {  //redirection pour la page FXML:Afficher oeuvre
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ShowProduit.fxml")));
                Stage myWindow = (Stage) nom.getScene().getWindow();
                Scene sc = new Scene(root);
                myWindow.setScene(sc);
                myWindow.setTitle("Login");
                //myWindow.setFullScreen(true);
                myWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(AddProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

    }

    @FXML
    void upload(ActionEvent event) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser(); //package pour upload image
        fileChooser.setTitle("Choisir une image: ");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        Window stage = null;
        pfile = fileChooser.showOpenDialog(stage);

        /* - draw image */
        if (pfile != null) { //si le fichier non null-->path de l'image
            file=1;
            Image image = new Image(pfile.toURI().toURL().toExternalForm());
            imv.setImage(image);
        }
    }


    public static void copier(File source, File dest) {
        try (InputStream sourceFile = new FileInputStream(source);
             OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo
            byte[] buffer = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//méthode app lors de l'initialisation
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;  // Filtre pour accepter seulement des entiers dans le champ quantite
            }
            return null;
        };  // Applique le filtre au champ de texte quantite
        quantite.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));

        try {
            List<Categorie> l =cs.getAllCategories();
            for (Categorie c : l) {
                cat.getItems().add(c.getDescription());
            }
        } catch (SQLDataException e) {
            throw new RuntimeException(e);  //en cas d'erreur sql
        }

        c = (int) (Math.random() * (300000 - 2 + 1)) + 2;
        lien = "oeuvre" + c + ".jpg";
        pDir = new File("src/main/resources/img/"+lien);
     //random id pour le nom du fichier image
    }


    public  boolean isValidFloat(String input) {
        // Expression régulière pour vérifier un nombre flottant
        String floatRegex = "^[-+]?[0-9]*\\.?[0-9]+$";

        // Créer un pattern et un matcher pour l'expression régulière
        Pattern pattern = Pattern.compile(floatRegex);
        Matcher matcher = pattern.matcher(input);

        // Vérifier si la saisie correspond à un nombre flottant valide
        return matcher.matches();
    }
}
