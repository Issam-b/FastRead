package fast;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Issam
 */
public class Fast extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainFrame.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
<<<<<<< HEAD
        stage.setTitle("FastRead");
        stage.setResizable(false);
=======
>>>>>>> b9da71c0586412a33cb811e29821da7baba98829
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
