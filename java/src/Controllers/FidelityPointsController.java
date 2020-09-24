package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import System.State;
import javafx.stage.Stage;
import obj.FidelityCard;
import obj.User;

public class FidelityPointsController {
    @FXML Button close;
    @FXML TextField mail;
    @FXML TextField points_tot;
    @FXML TextField IDcardtxt;
    @FXML TextField DateCardtxt;
    @FXML Button log;

    State state = State.getInstance();

    public void initialize() {
        log.setDisable(true);
        User currentUser = state.getCurrentUser();
        mail.setText(currentUser.getEmail());
        mail.setDisable(true);
        FidelityCard card = currentUser.getCartaFed();
        points_tot.setText(String.valueOf(card.getSaldo()));
        points_tot.setDisable(true);
        IDcardtxt.setText(String.valueOf(card.getId()));
        IDcardtxt.setDisable(true);
        DateCardtxt.setText(String.valueOf(card.getDataEmissione()));
        DateCardtxt.setDisable(true);
    }

    public void close(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
