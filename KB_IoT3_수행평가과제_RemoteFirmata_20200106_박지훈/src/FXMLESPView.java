import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLESPView extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXMLESPMain.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Remote");
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {

				try {
					JavaTCPSnd.cltSocket.shutdownInput();
					JavaTCPSnd.cltSocket.shutdownOutput();
					JavaTCPSnd.cltSocket.close();
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
