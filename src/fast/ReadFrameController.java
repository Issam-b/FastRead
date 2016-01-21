package fast;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Issam
 */
public class ReadFrameController implements Initializable {

    @FXML private Label readLabel;
    @FXML private Label wordsLabel,paragLabel,timeLabel;
    
    Stage stage;
    
    public ReadFrameController() {
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readLabel.setAlignment(Pos.CENTER);
    }
    
    @FXML
    public void doPause(ActionEvent event) {   
        
        MainFrameController.th.stop();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    public void setReadLabel(String word) {
        this.readLabel.setText(word);
    }
    
    public Label getReadLabel() {
        return this.readLabel;
    }
    
    public Label getWordsLabel() {
        return wordsLabel;
    }

    public Label getParagLabel() {
        return paragLabel;
    }

    public Label getTimeRemain() {
        return timeLabel;
    }
}
