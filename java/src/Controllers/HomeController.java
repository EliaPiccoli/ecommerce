package Controllers;

import DB_Handler.DBProduct;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import obj.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class HomeController {
    @FXML TableView<Product> productTable = new TableView<>();
    @FXML Button userLogged;
    @FXML TextField searchParameter;

    public void initialize() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            List<Product> products = dbProductController.getProducts();
            ObservableList<Product> data = productTable.getItems();
            data.removeAll(data);
            products.forEach(p -> data.add(p));
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    private void newScene(ActionEvent event, String path) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource(path));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.out.println("/ff");
        }
    }

    public void editProfile(ActionEvent event) {
        newScene(event, "/ClientProfileModifier.fxml");
    }

    public void seeOrders(ActionEvent event) {
        newScene(event, "/ClientOrderHistory.fxml");
    }

    public void seeBasket(ActionEvent event) {
        // TODO
        newScene(event, "/Basket.fxml");
    }

    public void seePoints(ActionEvent event) {
        newScene(event, "/FidelityPoints.fxml");
    }

    public void search() {

    }

    /*
    search -> f()
    doubleclick -> event -> popup -> cart
    */

}
