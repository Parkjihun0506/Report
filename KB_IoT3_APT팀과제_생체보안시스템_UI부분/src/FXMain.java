import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class FXMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("출입시스템관리시스템v1.2");
		primaryStage.setResizable(false);
		// primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {

				try {
					JavaFXTCPSnd.cltSocket.shutdownInput();
					JavaFXTCPSnd.cltSocket.shutdownOutput();
					JavaFXTCPSnd.cltSocket.close();

				} catch (Exception e) {
					System.out.print(e.getLocalizedMessage());
				}

				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {

		launch(args);

	}

}
