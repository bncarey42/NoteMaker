import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main application class for the Dwarf Planets JavaFX application.
 */
public class TextEditor extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent parent = FXMLLoader.load(getClass().getResource("TextEditor.fxml"));

        Scene scene = new Scene(parent);

        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
