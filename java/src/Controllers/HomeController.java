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
import javafx.scene.image.Image;
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
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ClientOrderHistory.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void seeBasket(ActionEvent event) {
        // cart in State
        newScene(event, "/Basket.fxml");
    }

    public void seePoints(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FidelityPoints.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage pointsStage = new Stage();
            pointsStage.setScene(tableViewScene);
            pointsStage.setTitle("Verdo's Shop");
            pointsStage.getIcons().add(new Image("/logo.jpg"));
            pointsStage.show();
        } catch (IOException e) {
            System.out.println("Error loading fidelity card");
        }
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
            state.addProduct(productTable.getSelectionModel().getSelectedItem());
            AlertBox.display("Cart", "Product added to cart!", true);
        }
    }

    public void logOutButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException {
        state.reset();
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
