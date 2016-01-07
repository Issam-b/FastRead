package fast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Issam
 */
public class MainFrameController implements Initializable {
    
    @FXML public TextArea textArea;
    @FXML private Button doneBtn;
    @FXML private Slider slider;    
    
    Stage stage;
    Parent root;   
    
    ReadFrameController rc;
    static int i;
    static boolean once = false;
    public static Thread th;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    private void doStart(ActionEvent event) throws IOException {
        buildReadScene("ReadFrame.fxml");
        ReadThread(getText(), getSpeed());
    }
   
    @FXML
    private void doResume(ActionEvent event) throws IOException {
        //TODO
        System.out.println("sdf");
        if(rc.getPause()){
            buildReadScene("ReadFrame.fxml");
            
            String[] temp = getText();
            int length = temp.length - MainFrameController.i;
//            length -= MainFrameController.i;
            String[] words_resume = new String[length];
            for (int j = 0; j < length; j++) {
                words_resume[j] = temp[j + MainFrameController.i];
            }
            ReadThread(words_resume, getSpeed());
//            MainFrameController.i = 0;
        }
        
    }
    
    @FXML
    private void doExit(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    private void doClear(ActionEvent event) {
        textArea.setText("");
    }
    @FXML
    private void doAbout(ActionEvent event) throws IOException {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
        Parent roott = (Parent)loader.load();

        stage.setScene(new Scene(roott));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(textArea.getScene().getWindow());
        stage.show();
    }
    @FXML
    private void doDone(ActionEvent event) {
        stage = (Stage) doneBtn.getScene().getWindow();
        stage.close();
    }
    
    private void buildReadScene(String fxmlFile) throws IOException {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent roott = (Parent)loader.load();        
        rc = loader.<ReadFrameController>getController();

        stage.setScene(new Scene(roott));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(textArea.getScene().getWindow());
        stage.show();
    }
    
//    static int J = 0;
    private void ReadThread(String[] words, long speed) {
        th = new Thread(() -> {
            for (int j = 0; j < words.length; j++) {
                MainFrameController.i = j;
                Platform.runLater(() -> {
                    rc.getReadLabel().setText(words[i]);
                        

                });
                
                try {
                   Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        th.start();
    }
    
    public String[] getText() {
        String text = textArea.getText();
        return text.split(" ");
    }
    
    public long getSpeed() {
        return 60000 / (long) slider.getValue();
    }
    
    public static void setOnce(boolean once) {
        MainFrameController.once = once;
    }
    
}