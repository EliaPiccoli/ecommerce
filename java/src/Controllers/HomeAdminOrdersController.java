package Controllers;

import DB_Handler.DBOrder;
import DB_Handler.DBProduct;
import DB_Handler.DBUser;
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
import obj.Order;

import System.State;
import obj.Product;
import obj.ProductInOrder;
import obj.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeAdminOrdersController {
    @FXML TableView<Order> ordersTable = new TableView<>();
    @FXML TableView<ProductInOrder> ordersTable1 = new TableView<>();
    @FXML Button userLogged;
    @FXML TextField user;
    @FXML ComboBox<String> typeSearch;
    @FXML TextField searchParameter;
    @FXML Button logOutButton;
    @FXML TextField pointsBalanceText;
    @FXML TextField nameText;
    @FXML TextField phoneText;
    @FXML TextField addressText;
    @FXML TextField paymentText;
    @FXML TextField surnameText;
    @FXML TextField emailText;

    State state = State.getInstance();

    //List<Order> orders = new ArrayList<>();

    public void initialize() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBOrder dbOrderController = new DBOrder(con);
            List<Order> orders = dbOrderController.getOrders();
            if (orders != null) {
                ObservableList<Order> data = ordersTable.getItems();
                data.removeAll(data);
                data.addAll(orders);
            } else {
                System.out.println("Lista ordini vuota");
            }

            ObservableList<String> data2 = typeSearch.getItems();
            data2.removeAll(data2);
            data2.addAll("Show All", "ID", "Email");
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
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AdminProfileModifier.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void seeProducts(ActionEvent event) {
        newScene(event, "/HomeAdminProducts.fxml");
    }

    public void search() {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBOrder dbOrderController = new DBOrder(con);
            String col = typeSearch.getSelectionModel().getSelectedItem();
            List<Order> filteredOrders = new ArrayList<>();
            if(col.equals("Show All"))
                filteredOrders = dbOrderController.getOrders();
            else if (col.equals("Email"))
                filteredOrders = dbOrderController.getOrdersOfUser(searchParameter.getText());
            else
                filteredOrders.add(dbOrderController.getOrder(Integer.parseInt(searchParameter.getText())));

            if (filteredOrders != null) {
                ObservableList<Order> data = ordersTable.getItems();
                data.removeAll(data);
                data.addAll(filteredOrders);
            } else {
                System.out.println("Lista ordini vuota nella ricerca");
            }
            searchParameter.clear();
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }

    public void click(MouseEvent event) {
        if(event.getClickCount() >= 2) {
            try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
                Order orderSelected = ordersTable.getSelectionModel().getSelectedItem();
                DBUser DBUserController = new DBUser(con);
                User userSelected = DBUserController.getUser(orderSelected.getEmailCliente());

                List<ProductInOrder> productInOrders = orderSelected.getProdottiOrdine();
                ObservableList<ProductInOrder> data = ordersTable1.getItems();
                data.removeAll(data);
                data.addAll(productInOrders);

                pointsBalanceText.setText(userSelected.getCartaFed().getSaldo().toString());
                nameText.setText(userSelected.getNome());
                phoneText.setText(userSelected.getTelefono());
                addressText.setText(userSelected.getIndirizzo());
                paymentText.setText(orderSelected.getPagamento());
                surnameText.setText(userSelected.getCognome());
                emailText.setText(userSelected.getEmail());

            }  catch (SQLException e) {
                System.out.println("Error connecting with db");
            }
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
