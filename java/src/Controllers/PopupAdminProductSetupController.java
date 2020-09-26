package Controllers;

import DB_Handler.DBProduct;
import DB_Handler.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import obj.Product;
import obj.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PopupAdminProductSetupController {

    /*
    String tipo;-
    String nome;-
    String marca;-
    String descrizione;-
    Integer quantita;-
    Integer quantita_conf;-
    BigDecimal prezzo;-
    */

    private Product product;

    @FXML private TextField tipo;
    @FXML private TextField nome;
    @FXML private TextField marca;
    @FXML private TextArea descrizione;
    @FXML private TextField quantita;
    @FXML private TextField quantita_conf;
    @FXML private TextField prezzo;

    public void initialize() {
        /*
        this.tipo.setText(product.getTipo());
        this.nome.setText(product.getNome());
        this.marca.setText(product.getMarca());
        this.descrizione.setText(product.getDescrizione());
        this.quantita.setText(String.valueOf(product.getQuantita()));
        this.quantita_conf.setText(String.valueOf(product.getQuantita_conf()));
        this.prezzo.setText(String.valueOf(product.getPrezzo()));
        */
    }

    public void setProduct(Product product) {
        this.product = product;
        this.tipo.setText(product.getTipo());
        this.nome.setText(product.getNome());
        this.marca.setText(product.getMarca());
        this.descrizione.setText(product.getDescrizione());
        this.quantita.setText(String.valueOf(product.getQuantita()));
        this.quantita_conf.setText(String.valueOf(product.getQuantita_conf()));
        this.prezzo.setText(String.valueOf(product.getPrezzo()));
    }

    public void cancelButtonPressed(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void saveButtonPressed(ActionEvent event) throws IOException {

        /*if (!vefifyTextField()) {
            AlertBox.display("Error", "All field must be filled", false);
            return;
        }
        if (!checkRightFormat()) return;
        */

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            Product newProduct = new Product(this.product.getId(), tipo.getText(), nome.getText(), marca.getText(), descrizione.getText(), Integer.parseInt(quantita.getText()), Integer.parseInt(quantita_conf.getText()), new BigDecimal(11.0)); //BigDecimal.valueOf(Double.valueOf(prezzo.getText()))
            //Product newProduct = new Product(this.product.getId(), "myTipo", "myNome", "myMarca", "myDescrizione", 1, 5, new BigDecimal(11.0));
            Product updatedProduct = dbProductController.updateProduct(newProduct);
            if(updatedProduct != null) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.close();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to db");
        }
    }
}
