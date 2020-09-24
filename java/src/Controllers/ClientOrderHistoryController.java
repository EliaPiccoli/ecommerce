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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import System.State;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import obj.Order;
import obj.ProductInOrder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientOrderHistoryController {
    @FXML Button log;
    @FXML TextField user;
    @FXML ComboBox<String> typeSearch;
    @FXML TableView<Order> ordersTable;
    @FXML TableView<ProductInOrder> productsInOrder;
    @FXML TextField searchParameter;

    State state = State.getInstance();

    public void initialize() {
        log.setDisable(true);
        user.setText(state.getCurrentUser().getEmail());

        ObservableList<String> data2 = typeSearch.getItems();
        data2.removeAll(data2);
        data2.addAll("Show All", "ID");
        typeSearch.getSelectionModel().selectFirst();

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBOrder dbOrderController = new DBOrder(con);
            List<Order> orders = dbOrderController.getOrdersOfUser(state.getCurrentUser().getEmail());
            if(orders != null) {
                ObservableList<Order> data = ordersTable.getItems();
                data.removeAll(data);
                data.addAll(orders);
            } else {
                System.out.println("/ff");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    public void home(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void search() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBOrder dbOrderController = new DBOrder(con);
            String col = typeSearch.getSelectionModel().getSelectedItem();
            List<Order> filteredOrders = new ArrayList<>();
            if(col.equals("Show All"))
                filteredOrders = dbOrderController.getOrdersOfUser(state.getCurrentUser().getEmail());
            else
                filteredOrders.add(dbOrderController.getOrder(Integer.parseInt(searchParameter.getText())));
            ObservableList<Order> data = ordersTable.getItems();
            data.removeAll(data);
            data.addAll(filteredOrders);
            searchParameter.clear();
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    public void logout(ActionEvent event) throws IOException {
        state.reset();
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void click(MouseEvent event) {
        if(event.getClickCount() >= 2) {
            Order o = ordersTable.getSelectionModel().getSelectedItem();
            ObservableList<ProductInOrder> data = productsInOrder.getItems();
            data.removeAll(data);
            data.addAll(o.getProdottiOrdine());
        }
    }
}
