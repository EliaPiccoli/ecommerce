import Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws ClassNotFoundException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Class.forName("org.postgresql.Driver");

        try{
            FXMLLoader login = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Pane root = login.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Online Shop");
            primaryStage.getIcons().add(new Image("/logo.jpg"));
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
