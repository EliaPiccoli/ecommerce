package Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import System.State;
import obj.Order;

public class ClientOrderHistoryController {
    @FXML Button log;
    @FXML TextField user;
    @FXML ComboBox<String> typeSearch;
    @FXML TableView<Order> ordersTable;

    State state = State.getInstance();

    public void initialize() {
        log.setDisable(true);
        user.setText(state.getCurrentUser().getEmail());

        ObservableList<String> data2 = typeSearch.getItems();
        data2.removeAll(data2);
        data2.addAll("Show All", "ID");
        typeSearch.getSelectionModel().selectFirst();


    }

    public void home() {
        System.out.println("Home");
    }

    public void search() {
        System.out.println("Search");
    }

    public void logout() {
        System.out.println("Logout");
    }
}
