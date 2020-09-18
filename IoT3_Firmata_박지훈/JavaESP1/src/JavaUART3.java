import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JavaUART3 extends Thread {

	public String rcvMsg;
	private TextField tf0;
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	private TextField tf5;
	private TextField tf12;
	private TextField tf13;
	private TextArea ta1;

	public JavaUART3(TextField tf0, TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5,
			TextArea ta1, TextField tf12, TextField tf13) {
		this.tf0 = tf0;
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.tf4 = tf4;
		this.tf5 = tf5;
		this.tf12 = tf12;
		this.tf13 = tf13;
		this.ta1 = ta1;
	}

	@Override
	public void run() {

		JavaUART3 im = new JavaUART3(tf0, tf1, tf2, tf3, tf4, tf5, ta1, tf12, tf13);
		String rcvComm = tf12.getText();
		int rcvSpd = Integer.parseInt(tf13.getText());
		String rcvBuff = null;
		String strComm = rcvComm;
		InputStream inS = null;
		OutputStream outS = null;
		OutputStream os1 = null;

		try {

			CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier(strComm);

			// COMM Port 사용여부 확인
			if (portID.isCurrentlyOwned()) {
				// 다른 App 사용중
				System.out.printf("[ERR] 포트 %s는 이미 사용 중...", strComm);
				rcvBuff = strComm + "는 이미 사용 중";
				ta1.setText(rcvBuff);
				return;
			}

			// COMM 포터 열기
			CommPort commPort = portID.open(im.getClass().getName(), 2000);
			if (!(commPort instanceof SerialPort))
				return;

			SerialPort serialPort = (SerialPort) commPort;

			// COMM 환경설정
			serialPort.setSerialPortParams(rcvSpd // 통신 속도
					, SerialPort.DATABITS_8 // 데이터 비트
					, SerialPort.STOPBITS_1 // 스톱 비트
					, SerialPort.PARITY_NONE // 패리티 정보
			);
			
			ta1.setText("");
			tf12.setText("");
			tf13.setText("");
			ta1.setText("Server Connect!");

			// 입력 스트림 획득
			inS = serialPort.getInputStream();

			JavaInputT1 jaInputT = new JavaInputT1(inS, tf0, tf1, tf2, tf3, tf4, tf5, ta1);
			jaInputT.start();

			while (true) {
				outS = serialPort.getOutputStream();
				im.rcvMsg = null;

				if (rcvMsg != null) {
					outS.write(rcvMsg.getBytes());
					rcvMsg = null;
				} else {
					continue;
				}
			}
		} catch (

		Exception e) {
			System.out.println("[EXP] " + e.getLocalizedMessage());
		} finally {
			try {
				inS.close();
				outS.close();
				os1.close();
			} catch (Exception e) {
			}
		}
	}
}