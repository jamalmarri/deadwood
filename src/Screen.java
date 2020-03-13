import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JavaFX Application which displays the current state of the game. */
public class Screen extends Application {

    /** Whether or not the Screen object has been initialized. */
    private static boolean objectExists = false;

    /** The one Screen object for this Singleton class. */
    private static Screen screen;

    /** The primary Stage for this Screen. */
    private Stage primaryStage;

    /** Class constructor. */
    private Screen() {}

    /**
     * Ensures exactly one Screen exists and returns it.
     *
     * @return the Screen object
     */
    public static Screen getInstance() {
        // Check if a Screen object already exists
        if (!objectExists) {
            // Create a new Screen object if one doesn't already exist
            screen = new Screen();
            objectExists = true;
        }
        return screen;
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize local primaryStage field
        this.primaryStage = primaryStage;
        // Attempt to load menu FXML file
        Scene menuScene = null;
        try {
            menuScene = FXMLLoader.load(getClass().getResource("menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set the stage's title, and make it not resizable
        primaryStage.setTitle("Deadwood");
        primaryStage.setResizable(false);
        // Set the stage's scene to the menu scene
        primaryStage.setScene(menuScene);
        primaryStage.centerOnScreen();
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /** Switches the primary Stage's scene to the game scene. */
    public void switchScene() {
        // Attempt to load game FXML file
        Scene gameScene = null;
        try {
            gameScene = FXMLLoader.load(getClass().getResource("game.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Hide the stage while setting the scene
        primaryStage.hide();
        // Set the stage's scene to the game scene
        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
