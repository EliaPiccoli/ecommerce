package Controllers;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AlertBox {
    private static Button okButton = null;

    public static void display(String title, String message, boolean popup) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(120);
        window.getIcons().add(new Image("/logo.jpg"));

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText(message);
        if(!popup) {
            okButton = new Button("Ok");
            okButton.setMinWidth(80);
            okButton.setOnAction(e -> window.close());
        }
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        if(!popup)
            layout.getChildren().add(okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        if (!popup)
            window.showAndWait();
        else {
            PauseTransition wait = new PauseTransition(Duration.seconds(1));
            wait.setOnFinished((e) -> {
                /*YOUR METHOD*/
                window.close();
            });
            wait.play();
            window.show();
        }
    }
}