import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class FXMLMain implements Initializable {

	JavaTCPSnd jaUARTT;
	JavaTCPRcv jaInputT;
	JavaUDPRcv jaUDPT;
	JavaUDPSnd jaUDPT1;
	public String rcvAnal;
	@FXML
	public Label lb1;
	@FXML
	public TextArea ta1, ta2;
	@FXML
	public TextField tf0, tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11, tf12, tf13, tf14, tf16;
	@FXML
	public LineChart<String, Number> lineChart;
	@FXML
	public CategoryAxis xAxis;
	@FXML
	public NumberAxis yAxis;

	public static int choiceFlag = -1;
	public static int clton = -1;
	public static int A0 = 0;
	public static int A1 = 0;
	public static int A2 = 0;
	public static int A3 = 0;
	public static int A4 = 0;
	public static int A5 = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void hglwHandler(ActionEvent event) throws Exception {

		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		if (selected == true) {
			String tgId = tgBtn.getId();
			int ntgId = tgId.indexOf("x");
			String strtgId = tgId.substring(0, ntgId);
			jaUARTT.rcvMsg = strtgId;
		} else if (selected == false) {
			String tgId = tgBtn.getId();
			int ntgId = tgId.indexOf("x");
			String strtgId = tgId.substring(ntgId + 1);
			jaUARTT.rcvMsg = strtgId;
		}
	}

	public void ipHandler(ActionEvent event) throws Exception {

		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();
		if (selected == true) {
			jaUARTT.rcvMsg = tgId;
		}
	}

	public void opHandler(ActionEvent event) throws Exception {

		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();
		if (selected == true) {
			jaUARTT.rcvMsg = tgId;
		}
	}

	public void comHandler1(ActionEvent event) throws Exception {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf6.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = ta1.getText() + "PinNum : 3의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler2(ActionEvent event) throws Exception {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf7.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = ta1.getText() + "PinNum : 5의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler3(ActionEvent event) throws Exception {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf8.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = ta1.getText() + "PinNum : 6의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler4(ActionEvent event) throws Exception {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf9.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = ta1.getText() + "PinNum : 9의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler5(ActionEvent event) throws Exception {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf10.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = ta1.getText() + "PinNum : 10의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler6(ActionEvent event) throws Exception {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf11.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = ta1.getText() + "PinNum : 11의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void sendHandler(ActionEvent event) throws Exception {
		String tf = tf14.getText();
		jaUDPT.rcvMsgUdp = tf;
	}

	public void PWHandler(ActionEvent event) throws Exception {
		String tf = tf16.getText();
		jaUARTT.rcvMsg = tf;
	}

	public void A0Click(ActionEvent event) {

		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();

		if (selected == true) {
			lineChart.setTitle(tgId);
			choiceFlag *= -1;
			A0 = 1;
			A1 = 0;
			A2 = 0;
			A3 = 0;
			A4 = 0;
			A5 = 0;
		}
	}

	public void A1Click(ActionEvent event) {
		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();

		if (selected == true) {
			lineChart.setTitle(tgId);
			choiceFlag *= -1;
			A0 = 0;
			A1 = 1;
			A2 = 0;
			A3 = 0;
			A4 = 0;
			A5 = 0;
		} else {
			lineChart.getData().clear();
		}
	}

	public void A2Click(ActionEvent event) {
		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();

		if (selected == true) {
			lineChart.setTitle(tgId);
			choiceFlag *= -1;
			A0 = 0;
			A1 = 0;
			A2 = 1;
			A3 = 0;
			A4 = 0;
			A5 = 0;
		} else {
			lineChart.getData().clear();
		}
	}

	public void A3Click(ActionEvent event) {
		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();

		if (selected == true) {
			lineChart.setTitle(tgId);
			choiceFlag *= -1;
			A0 = 0;
			A1 = 0;
			A2 = 0;
			A3 = 1;
			A4 = 0;
			A5 = 0;
		} else {
			lineChart.getData().clear();
		}
	}

	public void A4Click(ActionEvent event) {
		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();

		if (selected == true) {
			lineChart.setTitle(tgId);
			choiceFlag *= -1;
			A0 = 0;
			A1 = 0;
			A2 = 0;
			A3 = 0;
			A4 = 1;
			A5 = 0;
		} else {
			lineChart.getData().clear();
		}
	}

	public void A5Click(ActionEvent event) {
		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();

		if (selected == true) {
			lineChart.setTitle(tgId);
			choiceFlag *= -1;
			A0 = 0;
			A1 = 0;
			A2 = 0;
			A3 = 0;
			A4 = 0;
			A5 = 1;
		} else {
			lineChart.getData().clear();
		}
	}

	public void connHandler(ActionEvent event) throws Exception {

		jaUARTT = new JavaTCPSnd(tf0, tf1, tf2, tf3, tf4, tf5, ta1, tf12, tf13, lineChart);
		jaUARTT.start();
		jaUDPT = new JavaUDPRcv(tf14, ta2);
		jaUDPT.start();
		jaUDPT1 = new JavaUDPSnd(tf14, ta2);
		jaUDPT1.start();
		ta1.setText("");
		ta2.setText("");
	}

}