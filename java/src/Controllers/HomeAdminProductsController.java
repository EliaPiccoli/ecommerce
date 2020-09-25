package Controllers;

import DB_Handler.DBOrder;
import DB_Handler.DBProduct;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import obj.Order;
import obj.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import System.State;

public class HomeAdminProductsController {

    @FXML TextField user;
    @FXML ComboBox<String> typeSearch;
    @FXML TextField searchParameter;
    @FXML TableView<Product> productsTable = new TableView<>();

    State state = State.getInstance();

    public void initialize() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            List<Product> products = dbProductController.getProducts();
            if (products != null) {
                ObservableList<Product> data = productsTable.getItems();
                data.removeAll(data);
                data.addAll(products);
            } else {
                System.out.println("Lista prodotti vuota");
            }

            ObservableList<String> data2 = typeSearch.getItems();
            data2.removeAll(data2);
            data2.addAll("Show All", "Tipo", "Nome", "Marca");
            typeSearch.getSelectionModel().selectFirst();

            user.setText(state.getCurrentUser().getEmail());
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    public void editProfile(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AdminProfileModifier.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e);
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
            System.out.println(e);
        }
    }

    public void seeOrders(ActionEvent event) {
        newScene(event, "/HomeAdminProducts.fxml");
    }

    public void addProduct(ActionEvent event) {
        // TODO
    }

    public void search(ActionEvent event) {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            String col = typeSearch.getSelectionModel().getSelectedItem();
            List<Product> filteredProducts = new ArrayList<>();
            if(col.equals("Show All"))
                filteredProducts = dbProductController.getProducts();
            else
                filteredProducts = dbProductController.searchProducts(col, searchParameter.getText());

            ObservableList<Product> data = productsTable.getItems();
            data.removeAll(data);
            data.addAll(filteredProducts);

            searchParameter.clear();
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    public void logOut(ActionEvent event) throws IOException, ClassNotFoundException {
        state.reset();
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
