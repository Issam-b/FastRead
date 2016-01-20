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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Issam
 */
public class MainFrameController implements Initializable {
    
    // TODO include statistics about text like paragraph count
    // Adding some CSS styles to make it look fancy and easy for eyes to read.
    
    @FXML public TextArea textArea;
    @FXML private Button doneBtn;
    @FXML private Slider slider;
    
    Stage stage;
    Stage dialog;
    Parent root;

    static private int readPos;
    static private int wordsCount;
    static private long timeRemained;
    
    ReadFrameController rc;
    static boolean once = false;
    public static Thread th;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    private void doStart(ActionEvent event) throws IOException {
        if(textArea.getText().equalsIgnoreCase("")) {
            showDialog("There is no valid text in the text field\n"
                            + "Please enter some text !");
        }
        else {
            buildReadScene("ReadFrame.fxml");
            ReadThread(getText(), getSpeed(), 0);
        }
    }
   
    @FXML
    private void doResume(ActionEvent event) throws IOException {
        String[] words = getText();

        if(textArea.getText().equalsIgnoreCase("") || getReadPos() != (words.length - 1)) {
            showDialog("There is no previous paused reading to resume on !");
        }
        else {
        
            // TODO: unexpectedly the read window shows after two consecutive 
            // clicks even the below condition is not true
        
            buildReadScene("ReadFrame.fxml");
            
            if(getReadPos() <= 2)
                ReadThread(words, getSpeed(), 0);
            else
                ReadThread(words, getSpeed(), getReadPos() - 2);
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
        setWordsCount(words.length);
        th = new Thread(() -> {
            for (int j = temp; j < words.length; j++) {
                setReadPos(j);
                int i = j;
                Platform.runLater(() -> {
                    rc.getReadLabel().setText(words[i]);
                    rc.getWordsLabel().setText((getReadPos() + 1) + " / " + getWordsCount());
                    rc.getParagLabel().setText("on work ...");
                    rc.getTimeRemain().setText(displayTime());
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
    
    private void showDialog(String msg) {
        dialog = new Dialog(stage, true, "No input !", msg);
        dialog.sizeToScene();
        dialog.show();
    }
    
    private String displayTime(){
        long remain = (getWordsCount() - getReadPos()) * getSpeed() / 1000;
        long total = getWordsCount() * getSpeed()/1000;
        return remain + " s / " + total + " s";
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

    public static int getWordsCount() {
        return wordsCount;
    }

    public static void setWordsCount(int wordsCount) {
        MainFrameController.wordsCount = wordsCount;
    }

    public static long getTimeRemained() {
        return timeRemained;
    }

    public static void setTimeRemained(long timeRemained) {
        MainFrameController.timeRemained = timeRemained;
    }
    
}

class Dialog extends Stage {
    // TODO add a gray layer
    public Dialog(Stage owner, boolean modality, String title, String msg) {
        super();
        initOwner(owner);
        Modality m = modality ? Modality.APPLICATION_MODAL : Modality.NONE;
        initModality(m);
        setTitle(title);
        Group root = new Group();
        Scene scene = new Scene(root, 265, 150, Color.WHITE);
        setScene(scene);
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        Label msgLabel = new Label(msg);
        msgLabel.setTextAlignment(TextAlignment.CENTER);
        gridpane.add(msgLabel, 0, 3);
        GridPane.setHalignment(msgLabel, HPos.CENTER);
        
        Button OkButton = new Button("OK !");
        OkButton.setOnAction((ActionEvent event) -> {
            close();
        });
        
        gridpane.add(OkButton, 0, 10);
        GridPane.setHalignment(OkButton, HPos.CENTER);
        root.getChildren().add(gridpane);
    }
}