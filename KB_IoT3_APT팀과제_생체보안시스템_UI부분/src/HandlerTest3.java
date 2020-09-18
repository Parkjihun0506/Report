import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class HandlerTest3 implements Initializable {

	JavaFXTCPSnd javafxTcpSnd1;
	JavaFXTCPRcv javafxTcpRcv1;
//	HandlerTest1 handlerTest1;
	public static String tcpRcv;
	private boolean szTF = false;

	@FXML
	Button btnfnd1, btnfnd2, btnreg1, btncan2;
	@FXML
	TextField tf3, tf4, tf9, tf10, tf11, tf12, tf13;
	@FXML
	Label lbl1;
	@FXML
	Tab tab1, tab2, tab3, tab4;

	Stage newc;

	private final static int dataSize = 005;
	private final static int rowsPerPage = 10;
	public static String strEmpInfo;
	public static String strTF;
	public static String rcvTF;
	public static String strEmpID;
	public static String strName;
	public static String strCall;
	public static String strDep;
	public static String rcvEmpID;
	public static String rcvName;
	String rcvOut;

	HandlerTest2 han2;
	String path = System.getProperty("user.dir");
	String cmd = System.getProperty("user.dir") + "//Finger.exe";

	String ftpIP = "192.168.34.38";
	String ftpID = "kb304-01";
	String ftpPW = "iot3*";

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void newCheck(ActionEvent event) throws Exception {

		if (tf9.getText().length() <= 5) {

			if (tf9.getText().equals("")) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("경고");
				alert.setHeaderText("");
				alert.setContentText("사번을 입력하시오.");
				alert.showAndWait();

			} else {

				String cmd = "15";
				String empID = String.format("%5s", tf9.getText());

				String sndMsg = cmd + empID;
//				System.out.print(sndMsg);

				if (sndMsg != null)
					JavaFXTCPSnd.rcvMsg = sndMsg;

//				System.out.print(strEmpInfo);
				Thread.sleep(1500);

				if (strTF == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("경고");
					alert.setHeaderText("결과값이 없습니다.");
					alert.setContentText("사번을 확인하시오.");
					alert.showAndWait();

				} else {
					if (!strTF.equals("F")) {

						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("중복");
						alert.setHeaderText("등록된 정보입니다.");
						alert.setContentText("사번을 확인하시오");
						alert.showAndWait();
						strTF = null;

					} else {

						tf10.setText(rcvEmpID.trim());
						tf11.setText(rcvName.trim());
						tf12.setText(strCall.trim());
						tf13.setText(strDep.trim());
						strTF = null;
					}
				}
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("경고");
			alert.setHeaderText("");
			alert.setContentText("사번을 확인하세요.");
			alert.showAndWait();
		}
	}

	public void registHandle(ActionEvent event) throws Exception {

		FTPClient ftp = new FTPClient();
		FileInputStream fis1 = null;
		FileInputStream fis2 = null;
		try {

			if (tf3.getText().equals("") || tf4.getText().equals("") || tf10.getText().equals("")) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("에러");
				alert.setHeaderText("");
				alert.setContentText("사번을 조회하거나 이미지 경로를 설정해주세요.");
				alert.showAndWait();

			} else {

				// 원하시는 인코딩 타입
				ftp.setControlEncoding("EUC-KR");
				ftp.connect(ftpIP);
				ftp.login(ftpID, ftpPW);

				// 제대로 연결이 안됐을 경우 ftp접속을 끊습니다.
				if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
					ftp.disconnect();
				}
				// 파일을 넣을 디렉토리를 설정해줍니다.
				// makeDirectory는 directory 생성이 필요할 때만 해주시면 됩니다.
//				ftp.makeDirectory("/DataPic");
				ftp.changeWorkingDirectory("/");
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				// System.out.print(tf3.getText());

//			C:\\Test1\\APTLogo.JPG 

				String strPath1 = tf3.getText().replace("\\", "\\\\");
				String strPath2 = tf4.getText().replace("\\", "\\\\");
//				System.out.printf("[0]" + strPath1 + strPath2);

				int nNum1 = tf3.getText().lastIndexOf("\\");
				int nLen1 = tf3.getText().length();
				int nNum2 = tf4.getText().lastIndexOf("\\");
				int nLen2 = tf4.getText().length();

				String strSub1 = tf3.getText().substring(nNum1 + 1, nLen1);
				String strSub2 = tf4.getText().substring(nNum2 + 1, nLen2);
//				System.out.printf("[DBG]" + strSub1);
//				System.out.print(tf3.getText());

				fis1 = new FileInputStream(strPath1); // 보낼 파일명
				boolean isSuccess1 = ftp.storeFile(strSub1, fis1);// 내가정할 파일이름
				fis2 = new FileInputStream(strPath2); // 보낼 파일명
				boolean isSuccess2 = ftp.storeFile(strSub2, fis2);// 내가정할 파일이름

//				System.out.println(rcvTF);
				Thread.sleep(1000);
				// storeFile Method는 파일 송신결과를 boolean값으로 리턴합니다
				if (isSuccess1 && isSuccess2) {
					System.out.println("업로드 성공");
//					Alert alert = new Alert(AlertType.INFORMATION);
//					alert.setTitle("종료");
//					alert.setHeaderText("");
//					alert.setContentText("등록을 완료하였습니다.");
//					HandlerTest2.newc.close();

				} else {
					System.out.println("업로드 실패");
				}

				int pathGet = tf3.getText().lastIndexOf("\\");
				int pathGet1 = tf4.getText().lastIndexOf("\\");

				String cmd = "14";
				String textField10 = tf10.getText();
				String textField11 = String.format("%8s", tf11.getText());
				String textField12 = String.format("%14s", tf12.getText());
				String textField13 = String.format("%10s", tf13.getText());
				String textField3 = String.format("%100s", tf3.getText().substring(pathGet + 1));
				String textField4 = String.format("%100s", tf4.getText().substring(pathGet1 + 1));
				String sndName = String.format("%8s", strName);

				String sndMsg = cmd + textField10 + textField11 + textField12 + textField13 + textField3 + textField4
						+ strEmpID + sndName;

				if (sndMsg != null) {
					JavaFXTCPSnd.rcvMsg = sndMsg;
				}

//				szTF = true;
//				System.out.print(sndMsg);
//				System.out.println(rcvTF);

				Thread.sleep(2000);
				if (rcvTF != null) {
					if (rcvTF.equals("T")) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("완료");
						alert.setHeaderText("등록을 완료하였습니다.");
						alert.setContentText("종료하시겠습니까?");

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {

							try {
								HandlerTest2.newc.close();
								System.out.println("성공");

							} catch (Exception e) {
								System.out.print(e.getLocalizedMessage());
							}

						} else {
							alert.close();
							tf3.setText("");
							tf4.setText("");
							tf9.setText("");
							tf10.setText("");
							tf11.setText("");
							tf12.setText("");
							tf13.setText("");
						}

//					szTF = false;
//					rcvOut = "stop";
//					rcvTF = null;

					} else {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("실패");
						alert.setHeaderText("[ERR] 등록을 실패하였습니다.");
						alert.setContentText("종료하시겠습니까?");

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {

							try {
								HandlerTest2.newc.close();
								System.out.println("실패");

							} catch (Exception e) {
								System.out.print(e.getLocalizedMessage());
							}

						} else {
							alert.close();
							tf3.setText("");
							tf4.setText("");
							tf9.setText("");
							tf10.setText("");
							tf11.setText("");
							tf12.setText("");
							tf13.setText("");
						}

//					szTF = false;
//					rcvOut = "stop";
//					rcvTF = null;
					}
					rcvTF=null;
				}
			}

		} catch (

		Exception e) {
			System.out.print(e);
		} finally {
			if (fis1 != null && fis2 != null) {
				try {
					fis1.close();
					fis2.close();
				} catch (Exception e) {
					System.out.print(e);
				}
			}
		}

	}

	public void exeFinger(ActionEvent event) {

		// cmd[0] = "sh";

		try {
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void faceChooser(ActionEvent event) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File(path));

		File selectedFile = fileChooser.showOpenDialog(newc);

		if (selectedFile != null) {
//			System.out.print(selectedFile.getPath());

			tf3.setText(selectedFile.getPath());
		}
	}

	public void fingerChooser(ActionEvent event) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File(path));
		File selectedFile = fileChooser.showOpenDialog(newc);

		if (selectedFile != null) {
//			System.out.print(selectedFile.getPath());

			tf4.setText(selectedFile.getPath());
		}

	}

	public void cancleHandle1(ActionEvent event) throws Exception {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("종료");
		alert.setHeaderText("");
		alert.setContentText("종료하시겠습니까?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			try {
				HandlerTest2.newc.close();

			} catch (Exception e) {
				System.out.print(e.getLocalizedMessage());
			}

		} else {

			alert.close();

		}

	}

}