import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class FXMLMain implements Initializable {

	JavaUART3 jaUARTT;
	JavaInputT1 jaInputT;
	@FXML
	public TextArea ta1;
	@FXML
	public TextField tf0, tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11, tf12, tf13;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void hglwHandler(ActionEvent event) {

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

	public void ipHandler(ActionEvent event) {

		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();
		if (selected == true) {
			jaUARTT.rcvMsg = tgId;
		}
	}

	public void opHandler(ActionEvent event) {

		ToggleButton tgBtn = (ToggleButton) event.getSource();
		boolean selected = tgBtn.isSelected();
		String tgId = tgBtn.getId();
		if (selected == true) {
			jaUARTT.rcvMsg = tgId;
		}
	}

	public void comHandler1(ActionEvent event) {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf6.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = "PinNum : 3의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler2(ActionEvent event) {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf7.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = "PinNum : 5의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler3(ActionEvent event) {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf8.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = "PinNum : 6의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler4(ActionEvent event) {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf9.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = "PinNum : 9의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler5(ActionEvent event) {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf10.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = "PinNum : 10의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void comHandler6(ActionEvent event) {

		Button tgBtn = (Button) event.getSource();
		String tgId = tgBtn.getId();
		String tf = tf11.getText();
		int ntf = Integer.parseInt(tf);
		if (0 <= ntf && ntf <= 255) {
			jaUARTT.rcvMsg = tgId + tf;
		} else {
			String strMsg = "PinNum : 11의 PWM이 0~255를 초과하였습니다.";
			ta1.setText(strMsg);
		}
	}

	public void connHandler(ActionEvent event) {

		jaUARTT = new JavaUART3(tf0, tf1, tf2, tf3, tf4, tf5, ta1, tf12, tf13);
		jaUARTT.start();
	}
}
