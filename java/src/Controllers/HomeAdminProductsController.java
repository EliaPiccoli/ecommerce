package Controllers;

import DB_Handler.DBProduct;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import obj.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import System.State;

public class HomeAdminProductsController {

    @FXML private TextField user;
    @FXML private ComboBox<String> typeSearch;
    @FXML private TextField searchParameter;
    @FXML private TableView<Product> productTable = new TableView<>();

    State state = State.getInstance();

    public void initialize() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBProduct dbProductController = new DBProduct(con);
            List<Product> products = dbProductController.getProducts();
            if (products != null) {
                ObservableList<Product> data = productTable.getItems();
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
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AdminProfileModifier.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void seeOrders(ActionEvent event) {
        newScene(event, "/HomeAdminOrders.fxml");
    }

    public void addProduct(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/PopupAdminProductAdd.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage pointsStage = new Stage();
            pointsStage.setScene(tableViewScene);
            pointsStage.setTitle("Verdo's Shop");
            pointsStage.getIcons().add(new Image("/logo.jpg"));
            pointsStage.setOnHidden(window -> {
                initialize();
            });
            pointsStage.show();
        } catch (IOException e) {
            System.out.println("Error loading fidelity card");
        }
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

            ObservableList<Product> data = productTable.getItems();
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

    public void click(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopupAdminProductSetup.fxml"));
                Parent root = loader.load();

                //The following both lines are the only addition we need to pass the arguments
                PopupAdminProductSetupController controller2 = loader.getController();
                Product myProduct = productTable.getSelectionModel().getSelectedItem();
                controller2.setProduct(myProduct);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Product modify");
                stage.setOnHidden(windowEvent -> initialize());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
