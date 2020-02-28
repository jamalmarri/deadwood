import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Screen extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = FXMLLoader.load(getClass().getResource("deadwood.fxml"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Deadwood");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
