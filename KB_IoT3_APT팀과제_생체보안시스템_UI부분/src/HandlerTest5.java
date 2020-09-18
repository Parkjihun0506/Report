import java.io.BufferedInputStream;
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
import javafx.util.Callback;

public class HandlerTest5 implements Initializable {

	JavaFXTCPSnd javafxTcpSnd1;
	JavaFXTCPRcv javafxTcpRcv1;
//	HandlerTest1 handlerTest1;
	public static String tcpRcv;

	@FXML
	Button btnreg3;
	@FXML
	TextField tf19, tf20, tf21, tf22, tf23;

	Stage newc;

	public static String strEmpInfo;
	public static String strEmpID;
	public static String strName;
	public static String rcvEmpID;
	public static String rcvName;
	public static String rcvCall;
	public static String rcvDep;
	public static String rcvFace;
	public static String rcvDate;
	public static String rcvTF;

	String path = System.getProperty("user.dir");

	HandlerTest2 han2;

	String ftpIP = "192.168.34.38";
	String ftpID = "kb304-01";
	String ftpPW = "iot3*";

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		System.out.print(rcvEmpID);

		tf19.setText(rcvEmpID);
		tf20.setText(rcvName);
		tf21.setText(rcvCall);
		tf22.setText(rcvDep);

	}

	public void registHandle(ActionEvent event) throws Exception {

		FTPClient ftp = new FTPClient();
		FileInputStream fis1 = null;

		try {

			if (tf23.getText().equals("")) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("[ERR]����");
				alert.setHeaderText("");
				alert.setContentText("������ ������ �������ּ���.");
				alert.showAndWait();

			} else {

				int pathGet = tf23.getText().lastIndexOf("\\");

				String cmd = "19";
				String textField19 = tf19.getText();
				String textField20 = String.format("%8s", tf20.getText());
				String textField21 = String.format("%14s", tf21.getText());
				String textField22 = String.format("%10s", tf22.getText());
				String textFace = String.format("%100s", rcvFace);
				String textField23 = String.format("%100s", tf23.getText().substring(pathGet + 1));
				String textDate = String.format("%10s", rcvDate);
				String sndName = String.format("%8s", strName);

				String sndMsg = cmd + textField19 + textField20 + textField21 + textField22 + textFace + textField23
						+ textDate + strEmpID + sndName;

//				System.out.print(sndMsg);
				// ���Ͻô� ���ڵ� Ÿ��
				ftp.setControlEncoding("EUC-KR");
				ftp.connect(ftpIP);
				ftp.login(ftpID, ftpPW);

				// ����� ������ �ȵ��� ��� ftp������ �����ϴ�.
				if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
					ftp.disconnect();
				}
				// ������ ���� ���丮�� �������ݴϴ�.
				// makeDirectory�� directory ������ �ʿ��� ���� ���ֽø� �˴ϴ�.
//				ftp.makeDirectory("/DataPic");
				ftp.changeWorkingDirectory("/");
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

				// System.out.print(tf3.getText());

//			C:\\Test1\\APTLogo.JPG

				String strPath1 = tf23.getText().replace("\\", "\\\\");

//				System.out.printf("[0]" + strPath1);

				int nNum1 = tf23.getText().lastIndexOf("\\");
				int nLen1 = tf23.getText().length();

				String strSub1 = tf23.getText().substring(nNum1 + 1, nLen1);

//				System.out.printf("[DBG]" + strSub1);
//				System.out.print(tf3.getText());

				fis1 = new FileInputStream(strPath1); // ���� ���ϸ�
				boolean isSuccess1 = ftp.storeFile(strSub1, fis1);// �������� �����̸�

				// storeFile Method�� ���� �۽Ű���� boolean������ �����մϴ�
				if (isSuccess1) {
					System.out.println("���ε� ����");
					if (sndMsg != null) {
						JavaFXTCPSnd.rcvMsg = sndMsg;
					}

				} else {
					System.out.println("���ε� ����");
				}

				Thread.sleep(2000);

				if (rcvTF != null) {
					if (rcvTF.equals("T")) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("���� �Ϸ�");
						alert.setHeaderText("");
						alert.setContentText("������ �Ϸ�Ǿ����ϴ�.");
						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == ButtonType.OK) {
							HandlerTest2.popH.close();
						}
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("���� ����");
						alert.setHeaderText("");
						alert.setContentText("������ �����߽��ϴ�.");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							HandlerTest2.popH.close();
						}
					}
					rcvTF = null;
				}

			}
		} catch (Exception e) {
			System.out.print(e);
		} finally {
			if (fis1 != null) {
				try {
					fis1.close();

				} catch (Exception e) {
					System.out.print(e);
				}
			}
		}

	}

	public void fingerChooser(ActionEvent event) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File(path));

		File selectedFile = fileChooser.showOpenDialog(newc);

		// ���� �о����
//		FileInputStream fis = new FileInputStream(selectedFile);

//		BufferedInputStream bis = new BufferedInputStream(fis);
		// �̹��� �����ϱ�
//		Image img = new Image(bis);
		// �̹��� ����
//		ib2.setImage(img);

		if (selectedFile != null) {
			System.out.print(selectedFile.getPath());

			tf23.setText(selectedFile.getPath());

		}
	}

	public void cancleHandle2(ActionEvent event) throws Exception {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("����");
		alert.setHeaderText("");
		alert.setContentText("�����Ͻðڽ��ϱ�?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			try {
				HandlerTest2.popH.close();
			} catch (Exception e) {
				System.out.print(e.getLocalizedMessage());
			}

		} else {

			alert.close();
		}

	}

}