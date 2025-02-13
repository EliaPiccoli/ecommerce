package Controllers;

import DB_Handler.DBProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import obj.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PopupAdminProductAddController {

    @FXML private TextField tipo;
    @FXML private TextField nome;
    @FXML private TextField marca;
    @FXML private TextArea descrizione;
    @FXML private TextField quantita;
    @FXML private TextField quantita_conf;
    @FXML private TextField prezzo;

    public void initialize() { }

    // TODO price error
    public void addButtonPressed(ActionEvent event) {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            boolean insertProduct = dbProductController.insertProduct(tipo.getText(), nome.getText(), marca.getText(), descrizione.getText(), Integer.parseInt(quantita.getText()), Integer.parseInt(quantita_conf.getText()), BigDecimal.valueOf(Double.parseDouble(prezzo.getText())));
            if(insertProduct) {
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.close();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to db");
        }
    }

    public void cancelButtonPressed(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
