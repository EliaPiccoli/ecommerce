package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import System.State;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientBasketController {
    @FXML private TextField user;

    private final State state = State.getInstance();

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
        System.out.println("Search");
    }

    public void products(ActionEvent e) {
        newScene(e, "/Home.fxml");
    }

    public void buy() {
        System.out.println("Buy");
    }
}
