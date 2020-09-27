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
import javafx.scene.control.*;

import System.State;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import obj.Order;
import obj.Product;
import obj.ProductInOrder;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientBasketController {
    @FXML private TextField user;
    @FXML private ComboBox<String> typeSearch;
    @FXML private TextField searchParameter;
    @FXML private TableView<ProductInOrder> productsTable;
    @FXML private TextField points;
    @FXML private TextField total;
    @FXML private DatePicker date;
    @FXML private ComboBox<String> hourDelivery;
    @FXML private ComboBox<String> payment;

    private final State state = State.getInstance();

    public void initialize() {
        user.setText(state.getCurrentUser().getEmail());

        ObservableList<ProductInOrder> data = productsTable.getItems();
        data.removeAll(data);
        data.addAll(state.getCurrentOrder().getProdottiOrdine());

        points.setText(String.valueOf(state.getCurrentOrder().getTotalOfOrder().toBigInteger()));
        points.setDisable(true);
        total.setText(state.getCurrentOrder().getTotalOfOrder() + " €");
        total.setDisable(true);

        ObservableList<String> data2 = typeSearch.getItems();
        data2.removeAll(data2);
        data2.addAll("Show All", "Nome", "Marca");
        typeSearch.getSelectionModel().selectFirst();

        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        ObservableList<String> data3 = hourDelivery.getItems();
        data3.removeAll(data2);
        data3.addAll("9:00:00", "10:00:00", "11:00:00", "12:00:00");
        hourDelivery.getSelectionModel().selectFirst();

        ObservableList<String> data4 = payment.getItems();
        data4.removeAll(data2);
        data4.addAll("Carta di Credito", "PayPal", "Consegna");
        payment.getSelectionModel().selectFirst();
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
            case "Show All" : {
                data.addAll(state.getCurrentOrder().getProdottiOrdine());
                break;
            }
            case "Nome" : {
                state.getCurrentOrder().getProdottiOrdine().forEach(p -> {
                    if (p.getNome().toLowerCase().equals(searchParameter.getText().toLowerCase()))
                        data.add(p);
                });
                break;
            }
            case "Marca" : {
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

    public void buy(ActionEvent e) {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            System.out.println("Buy");
            Order current = state.getCurrentOrder();
            current.setDataConsegna(Date.valueOf(date.getValue()));
            current.setOraConsegna(Time.valueOf(hourDelivery.getSelectionModel().getSelectedItem()));
            current.setPagamento(payment.getSelectionModel().getSelectedItem());
            DBOrder dbOrderController = new DBOrder(con);
            if(dbOrderController.insertOrder(current)) {
                try (PreparedStatement s = con.prepareStatement(String.format("UPDATE cartafed SET saldo = saldo + %d WHERE id = ?", Integer.valueOf(points.getText())))) {
                    s.setInt(1, state.getCurrentUser().getCartaFed().getId());
                    if(s.executeUpdate() == 1) {
                        System.out.println("SUCCESS");
                        state.resetOrder();
                        newScene(e, "/Home.fxml");
                    } else {
                        System.out.println("/ff");
                    }
                }
            } else {
                System.out.println("/ff");
            }
        } catch (SQLException ex) {
            System.out.println("Error connecting to db");
        }
    }

    public void click(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProducts.fxml"));
                Parent root = loader.load();

                //The following both lines are the only addition we need to pass the arguments
                AddProductsController controller2 = loader.getController();
                ProductInOrder myProduct = productsTable.getSelectionModel().getSelectedItem();
                controller2.changeProduct(myProduct);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Product modify");
                stage.setOnHidden(windowEvent -> { initialize(); });
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
