import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Deadwood {

    private static Screen screen = new Screen();
    @FXML
    private Label playerLabel;
    @FXML
    private Label dollarsLabel;
    @FXML
    private Label creditsLabel;
    @FXML
    private MenuButton moveMenu;
    @FXML
    private MenuButton takeMenu;
    @FXML
    private Button rehearseButton;
    @FXML
    private Button actButton;
    @FXML
    private MenuButton upgradeMenu;
    @FXML
    private Button endTurnButton;
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;
    @FXML
    private ImageView dice3;
    @FXML
    private ImageView dice4;
    @FXML
    private ImageView dice5;
    @FXML
    private ImageView dice6;
    @FXML
    private Label dayLabel;

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        new JFXPanel();
        Platform.runLater(() -> {
            try {
                screen.start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 3; i++) {
            MenuItem menuItem = new MenuItem("Room " + i);
            menuItem.setOnAction(actionEvent -> move(menuItem.getText()));
            moveMenu.getItems().addAll(menuItem);
        }
    }

    public void move(String text) {
        System.out.println("Move to " + text);
    }

    public void take() {
    }

    public void rehearse() {
    }

    public void act() {
    }

    public void upgrade() {
    }

    public void endTurn() {
        Platform.exit();
    }
}
