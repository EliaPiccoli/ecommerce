package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import System.State;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientBasketController {
    @FXML private TextField user;

    private State state = State.getInstance();

    public void initialize() {
        user.setText(state.getCurrentUser().getEmail());
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
        System.out.println("Orders");
    }

    public void points() {
        System.out.println("Points");
    }

    public void search() {
        System.out.println("Search");
    }

    public void products() {
        System.out.println("Products");
    }

    public void buy() {
        System.out.println("Buy");
    }
}
