package Controllers;

import DB_Handler.DBProduct;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import System.State;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import obj.Product;
import obj.ProductInOrder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientBasketController {
    @FXML private TextField user;
    @FXML private ComboBox<String> typeSearch;
    @FXML private TextField searchParameter;
    @FXML private TableView<ProductInOrder> productsTable;
    @FXML private TextField points;
    @FXML private TextField total;

    private final State state = State.getInstance();

    public void initialize() {
        user.setText(state.getCurrentUser().getEmail());

        ObservableList<ProductInOrder> data = productsTable.getItems();
        data.removeAll(data);
        data.addAll(state.getCurrentOrder().getProdottiOrdine());

        System.out.print(String.valueOf(state.getCurrentOrder().getTotalOfOrder().toBigInteger()));
        System.out.print(state.getCurrentOrder().getTotalOfOrder() + " €");

        points.setText(String.valueOf(state.getCurrentOrder().getTotalOfOrder().toBigInteger()));
        points.setDisable(true);
        total.setText(state.getCurrentOrder().getTotalOfOrder() + " €");
        total.setDisable(true);

        ObservableList<String> data2 = typeSearch.getItems();
        data2.removeAll(data2);
        data2.addAll("Show All", "Nome", "Marca");
        typeSearch.getSelectionModel().selectFirst();
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

    public void orders(ActionEvent e) {
        newScene(e, "/ClientOrderHistory.fxml");
    }

    public void points() {
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
        ObservableList<ProductInOrder> data = productsTable.getItems();
        data.removeAll(data);
        switch (typeSearch.getSelectionModel().getSelectedItem()) {
            case "Show All" -> {
                data.addAll(state.getCurrentOrder().getProdottiOrdine());
                break;
            }
            case "Nome" -> {
                state.getCurrentOrder().getProdottiOrdine().forEach(p -> {
                    if (p.getNome().toLowerCase().equals(searchParameter.getText().toLowerCase()))
                        data.add(p);
                });
                break;
            }
            case "Marca" -> {
                state.getCurrentOrder().getProdottiOrdine().forEach(p -> {
                    if (p.getMarca().toLowerCase().equals(searchParameter.getText().toLowerCase()))
                        data.add(p);
                });
                break;
            }
        }
    }

    public void products(ActionEvent e) {
        newScene(e, "/Home.fxml");
    }

    public void buy() {
        System.out.println("Buy");
    }
}
