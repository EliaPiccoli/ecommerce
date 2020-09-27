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
    private Product product;

    @FXML private TextField tipo;
    @FXML private TextField nome;
    @FXML private TextField marca;
    @FXML private TextArea descrizione;
    @FXML private TextField quantita;
    @FXML private TextField quantita_conf;
    @FXML private TextField prezzo;

    public void initialize() {
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
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            Product newProduct = new Product(this.product.getId(), tipo.getText(), nome.getText(), marca.getText(), descrizione.getText(), Integer.parseInt(quantita.getText()), Integer.parseInt(quantita_conf.getText()), BigDecimal.valueOf(Double.parseDouble(prezzo.getText().replace(",", "."))));
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
