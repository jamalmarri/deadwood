import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class Menu {

    @FXML
    private MenuButton playerCountButton;

    @FXML
    public void initialize() {
        for (int i = 1; i < 8; i++) {
            MenuItem playerCountItem = new MenuItem(String.valueOf(i + 1));
            playerCountItem.setOnAction(actionEvent -> Deadwood.initPlayers(Integer.parseInt(playerCountItem.getText())));
            playerCountButton.getItems().add(i - 1, playerCountItem);
        }
    }
}
