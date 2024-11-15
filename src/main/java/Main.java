import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            AnchorPane root = loader.load();


            Scene scene = new Scene(root);


            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());


            primaryStage.setTitle("To-Do Notepad");

            Image icon = new Image(getClass().getResourceAsStream("clipboard2.png"));
            primaryStage.getIcons().add(icon);

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
