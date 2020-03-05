import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Screen extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene menuScene = null;
        try {
            menuScene = FXMLLoader.load(getClass().getResource("menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Deadwood");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void switchScene() {
        Scene gameScene = null;
        try {
            gameScene = FXMLLoader.load(getClass().getResource("game.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
    }
}
