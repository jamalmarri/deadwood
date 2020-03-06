import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/** A controller class for the JavaFX menu scene. */
public class Menu {

    /** The MenuButton used to choose the amount of Players in the game. */
    @FXML private MenuButton playerCountButton;

    /** Initializes the MenuButton's MenuItems, each representing a different number of Players. */
    @FXML
    public void initialize() {
        // For every possible number of players
        for (int i = 2; i < 9; i++) {
            // Declare and initialize MenuItem using number of players as its text
            MenuItem playerCountItem = new MenuItem(String.valueOf(i));
            // Set the MenuItem's ActionEvent to execute Deadwood's initPlayers and pass its text as
            // a parameter
            playerCountItem.setOnAction(
                    actionEvent ->
                            Deadwood.initPlayers(Integer.parseInt(playerCountItem.getText())));
            // Add MenuItem to the MenuButton with the indexes beginning at 0
            playerCountButton.getItems().add(i - 2, playerCountItem);
        }
    }
}
