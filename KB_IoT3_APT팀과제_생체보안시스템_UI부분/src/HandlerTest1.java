import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class HandlerTest1 implements Initializable {

	JavaFXTCPSnd javafxTcpSnd;
	JavaFXTCPRcv javafxTcpRcv;
	@FXML
	Button btnLogin, btnLogout, btnpop, btncan, btnsrch1, btnnew, btnchnH, btnchnF, btncall;
	@FXML
	TextField tf1, tf2, tf7, tf8;
	@FXML
	Label lbl1;
	@FXML
	Tab tab1, tab2, tab3, tab4;
	@FXML
	DatePicker dp1;
	@FXML
	AnchorPane anchor1;
	@FXML
	ImageView ib2;

	Label lbl3;

	HandlerTest2 han2;

	Stage newc;

	String rcvBuff1;

	public static String tcpRcv1;
	public Stage primaryStage;
	String rcvOut;
	String path = System.getProperty("user.dir");

	private final static int dataSize = 005;
	private final static int rowsPerPage = 10;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		try {
//			FileInputStream fis = new FileInputStream("C:\\IoTLab\\Java\\JavaFXBio2\\APTLogo.JPG");
//
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			// 이미지 생성하기
//			Image img = new Image(bis);
//			// 이미지 띄우기
//
//			ib2.setImage(img);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		tf1.setText("a0004");
		tf2.setText("0004");

		javafxTcpSnd = new JavaFXTCPSnd();
		javafxTcpSnd.start();

		logIn();

//		javafxTcpSnd.rcvMsg = "13";

		// String abc =
		// "993456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

		// javafxTcpSnd.rcvMsg = abc;

//		System.out.print("111111");
	}

	public void btnCancle(ActionEvent event) throws Exception {

		// System.out.print(rcvBuff1);

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("종료");
		alert.setHeaderText("");
		alert.setContentText("종료하시겠습니까?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			try {
				JavaFXTCPSnd.cltSocket.shutdownInput();
				JavaFXTCPSnd.cltSocket.shutdownOutput();
				JavaFXTCPSnd.cltSocket.close();

			} catch (Exception e) {
				System.out.print(e.getLocalizedMessage());
			}

			Platform.exit();
			System.exit(0);

		} else {

			alert.close();
		}
	}

	public void btnLogin(ActionEvent event) throws Exception {

		int i = tf1.getText().length();
		int z = tf2.getText().length();

		if (i <= 5 && z <= 12) {

			String textField5 = String.format("%5s", tf1.getText());
			String textField6 = String.format("%16s", tf2.getText());
			String cmd1 = "10";

			String sndMsg = cmd1 + textField5 + textField6;
			if (sndMsg != null) {
				javafxTcpSnd.rcvMsg = sndMsg;
			}
			// System.out.print(tcpRcv1);

			Thread.sleep(1000);

//		rcvBuff1 = javafxTcpSnd.jaInputT.rcvBuff;
//		System.out.print(rcvBuff1);

		} else {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("[ERR3] 아이디 또는 비밀번호를 확인하세요.");
			alert.showAndWait();
			tf1.setText("");
			tf2.setText("");
		}

	}

	public void logIn() {

		Thread logInT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						if (tcpRcv1 != null) {

							if (!tcpRcv1.equals("T")) {

								tcpRcv1 = null;
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("에러");
								alert.setHeaderText("");
								alert.setContentText("[ERR1] 등록되지 않은 정보입니다.");
								alert.showAndWait();

//								lbl1.setText("로그인 실패");
								tf1.setText("");
								tf2.setText("");

							} else {

								FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLlogin.fxml"));

								try {
									Parent login = (Parent) loader.load();
									han2 = loader.getController();
									han2.javafxTcpSnd1 = javafxTcpSnd;
									han2.javafxTcpRcv1 = javafxTcpRcv;

//								han2.tf1 = tf1;
//								Parent login = FXMLLoader.load(getClass().getResource("FXMLlogin.fxml"));					

									Scene scene = new Scene(login);

									primaryStage = (Stage) btnLogin.getScene().getWindow();
									primaryStage.setScene(scene);

									tcpRcv1 = null;
									rcvOut = "stop";
								} catch (IOException e) {

									e.printStackTrace();
								}
//							System.out.print(loader.getController().toString());

							}
							tcpRcv1 = null;
						}

//						 else {
//
//							Alert alert = new Alert(AlertType.ERROR);
//							alert.setTitle("에러");
//							alert.setHeaderText("");
//							alert.setContentText("[ERR2] 서버연결을 확인하세요.");
//							alert.showAndWait();
//							tf1.setText("");
//							tf2.setText("");
//
//						}
					}

				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(100);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);
				}
			}
		});
		logInT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		logInT.start();
	}
}
