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
<<<<<<< HEAD
    @FXML private Label wordsLabel,paragLabel,timeLabel;
    
=======
    private static boolean pause = false;
>>>>>>> b9da71c0586412a33cb811e29821da7baba98829
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
<<<<<<< HEAD
    public void doPause(ActionEvent event) {   
        
=======
    private void doPause(ActionEvent event) {
        pause = true;
//        MainFrameController.setOnce(false);
>>>>>>> b9da71c0586412a33cb811e29821da7baba98829
        MainFrameController.th.stop();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    public void setReadLabel(String word) {
        this.readLabel.setText(word);
    }
    
    public Label getReadLabel() {
        return this.readLabel;
    }
    
<<<<<<< HEAD
    public Label getWordsLabel() {
        return wordsLabel;
    }

    public Label getParagLabel() {
        return paragLabel;
    }

    public Label getTimeRemain() {
        return timeLabel;
    }
=======
    public void setPause(boolean pause) {
        ReadFrameController.pause = pause;
    }
    
    public boolean getPause() {
        return ReadFrameController.pause;
    }
    
>>>>>>> b9da71c0586412a33cb811e29821da7baba98829
}
