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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import obj.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import System.State;

public class HomeController {
    @FXML TableView<Product> productTable = new TableView<>();
    @FXML Button userLogged;
    @FXML TextField searchParameter;
    @FXML ComboBox<String> typeSearch;
    @FXML TextField user;
    State state = State.getInstance();

    List<Product> cart = new ArrayList<>();

    public void initialize() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            List<Product> products = dbProductController.getProducts();
            ObservableList<Product> data = productTable.getItems();
            data.removeAll(data);
            data.addAll(products);

            ObservableList<String> data2 = typeSearch.getItems();
            data2.removeAll(data2);
            data2.addAll("Show All", "Tipo", "Nome", "Marca");
            typeSearch.getSelectionModel().selectFirst();

            user.setText(state.getCurrentUser().getEmail());
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    private void newScene(ActionEvent event, String path) {
        try {
            System.out.println(path);

            Parent tableViewParent = FXMLLoader.load(getClass().getResource(path));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void editProfile(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ClientProfileModifier.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void seeOrders(ActionEvent event) {
        newScene(event, "/ClientOrderHistory.fxml");
    }

    public void seeBasket(ActionEvent event) {
        // TODO: pass info of the elements in the cart
        newScene(event, "/Basket.fxml");
    }

    public void seePoints(ActionEvent event) {
        newScene(event, "/FidelityPoints.fxml");
    }

    public void search() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            String col = typeSearch.getSelectionModel().getSelectedItem();
            List<Product> filteredProducts = new ArrayList<>();
            if(col.equals("Show All"))
                filteredProducts = dbProductController.getProducts();
            else
                filteredProducts = dbProductController.searchProducts(col, searchParameter.getText());

            ObservableList<Product> data = productTable.getItems();
            data.removeAll(data);
            data.addAll(filteredProducts);
            searchParameter.clear();
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    public void click(MouseEvent event) {
        if(event.getClickCount() >= 2) {
            cart.add(productTable.getSelectionModel().getSelectedItem());
            System.out.println(cart);
            AlertBox.display("Cart", "Product added to cart!", true);
        }
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException {
        state.setCurrentUser(null);
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
