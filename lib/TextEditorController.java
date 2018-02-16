import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

public class TextEditorController {

    @FXML
    private MenuItem newFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem saveFileAs;

    @FXML
    private MenuItem exitApp;

    @FXML
    private RadioMenuItem monoButton;

    @FXML
    private ToggleGroup fontSwitcherToggleGroup;

    @FXML
    private RadioMenuItem serifButton;

    @FXML
    private RadioMenuItem sansSerifButton;

    @FXML
    private CheckMenuItem italicFormatCheckBox;

    @FXML
    private CheckMenuItem boldFormatCheckBox;

    @FXML
    private TextArea workSpace;

    private String fileName;

    @FXML
    public void initialize() {
        workSpace.setWrapText(true);
        workSpace.setFont(new Font("Monospaced", 12));
        sansSerifButton.setSelected(true);
        italicFormatCheckBox.setSelected(false);
        boldFormatCheckBox.setSelected(false);
    }

    /**
     *
     * @param ae
     */
    public void openMenuListener(ActionEvent ae) {

        String content = "";

        JFileChooser fileChooser = new JFileChooser();

        int status = fileChooser.showOpenDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();

                fileName = selectedFile.getPath();

                try (Scanner inputFile = new Scanner(selectedFile)) {
                    do {

                        content += inputFile.nextLine();

                    } while (inputFile.hasNext());

                    workSpace.setText(content);

                }
            } catch (FileNotFoundException ex) {

                JOptionPane.showMessageDialog(fileChooser, "There was an error opening the file", "Error", status);
            }
        }
    }

    /**
     *
     * @param ae
     */
    public void saveMenuListener(ActionEvent ae) {
        if (fileName != null) {
            saveFile();
        } else {
            saveNewFile();
        }
    }

    /**
     *
     * @param ae
     */
    public void saveAsMenuListener(ActionEvent ae) {
        saveNewFile();
    }

    /**
     * saveNewFile() offers the user the option to name a new file before saving
     * that file to the chosen directory
     */
    public void saveNewFile() {
        JFileChooser fileChooser = new JFileChooser();

        int status = fileChooser.showSaveDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {

            fileName = fileChooser.getSelectedFile().toString();

            saveFile();
        }
    }

    /**
     * saveFile() creates the file passing the text saved in the textArea
     */
    public void saveFile() {
        File savedFile = new File(fileName);

        try (FileWriter fw = new FileWriter(savedFile)) {
            fw.write(workSpace.getText());

            fw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error saving the file");
        }
    }

    /*
        File Menu Event Listeners
     */
    public void newFileButtonListener() {
        workSpace.setText(null);
        fileName = null;
        sansSerifButton.setSelected(true);
        italicFormatCheckBox.setSelected(false);
        boldFormatCheckBox.setSelected(false);
    }

    /**
     *
     */
    public void exitButtonListener() {
        Platform.exit();
    }

    /*
        Font menu action listener
     */
    public void menuRadioButtonListener(ActionEvent ae) {
        // Get the current font.
        Font textFont = workSpace.getFont();
        // Retrieve the font name and size.
        String fontName = textFont.getName();

        if (monoButton.isSelected()) {
            fontName = "Monospaced";
        } else if (serifButton.isSelected()) {
            fontName = "Serif";
        } else if (sansSerifButton.isSelected()) {
            fontName = "SansSerif";
        }

        workSpace.setFont(Font.font(fontName));
    }

    /**
     *
     * @param ae
     */
    public void italicFormatCheckBoxListener(ActionEvent ae) {

        Font font = workSpace.getFont();

        FontPosture posture = italicFormatCheckBox.isSelected()
                ? FontPosture.ITALIC : FontPosture.REGULAR;

        workSpace.setFont(Font.font(font.getFamily(), posture, font.getSize()));

    }

    /**
     *
     * @param ae
     */
    public void boldFormatCheckBoxListener(ActionEvent ae) {

        Font font = workSpace.getFont();

        workSpace.setFont(Font.font(font.getFamily(),
                (boldFormatCheckBox.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL),
                font.getSize()));

    }
}
