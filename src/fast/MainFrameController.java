package fast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Issam
 */
public class MainFrameController implements Initializable {
    
    // TODO include statistics about text like word & paragraph count
    // and remaing count.
    // time reamained to finish the text
    // Adding some CSS styles to make it look fancy and easy for eyes to read.
    
    @FXML public TextArea textArea;
    @FXML private Button doneBtn;
    @FXML private Slider slider;    
    
    Stage stage;
    Parent root;
    
    ReadFrameController rc;
    static boolean once = false;
    public static Thread th;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    static private int readPos;
    @FXML
    private void doStart(ActionEvent event) throws IOException {
        buildReadScene("ReadFrame.fxml");       
        ReadThread(getText(), getSpeed(), 0);
    }
   
    @FXML
    private void doResume(ActionEvent event) throws IOException {
        String[] words = getText();
        
        // TODO: unexpectedly the read window shows after two consecutive 
        // clicks even the below condition is not true
        
        if(getReadPos() != words.length - 1)
            buildReadScene("ReadFrame.fxml");
        
        if(getReadPos() <= 2)
            ReadThread(words, getSpeed(), 0);
        else
            ReadThread(words, getSpeed(), getReadPos() - 2);
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
        stage.setTitle("About FastRead");
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
        stage.setTitle("FastRead: Reading now ...");
        stage.show();
    }
    
    private void ReadThread(String[] words, long speed, int readPos) {
        int temp = readPos;
        th = new Thread(() -> {
            for (int j = temp; j < words.length; j++) {
                setReadPos(j);
                int i = j;
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
        th.setDaemon(true);
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
    
    public static int getReadPos() {
        return readPos;
    }

    public static void setReadPos(int readPos) {
        MainFrameController.readPos = readPos;
    }
    
}