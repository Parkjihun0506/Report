import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JavaTCPSnd extends Thread {

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
	private LineChart<String, Number> lineChart;
	public static Socket cltSocket = null; // 클라이언트 소켓

	public JavaTCPSnd(TextField tf0, TextField tf1, TextField tf2, TextField tf3, TextField tf4, TextField tf5,
			TextArea ta1, TextField tf12, TextField tf13, LineChart<String, Number> lineChart) {
		this.tf0 = tf0;
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.tf4 = tf4;
		this.tf5 = tf5;
		this.tf12 = tf12;
		this.tf13 = tf13;
		this.ta1 = ta1;
		this.lineChart = lineChart;
	}

	@Override
	public void run() {
		// String strSvrIP = "192.168.34.99";
		// int nSvrPort = 7777;
		JavaTCPSnd im = new JavaTCPSnd(tf0, tf1, tf2, tf3, tf4, tf5, ta1, tf12, tf13, lineChart);
		String rcvIP = tf12.getText();
		int rcvPort = Integer.parseInt(tf13.getText());
		InputStream inS = null;
		OutputStream os1 = null;

		InetSocketAddress isaSvrAddr = null;

		try {
			// 클라이언트 소켓 생성
			cltSocket = new Socket();

			// 서버 주소 객체 생성
			isaSvrAddr = new InetSocketAddress(rcvIP, rcvPort);

			// String strSysMsg1 = "서버로 접속 요청...";
			// String strSysMsg2 = "서버로 접속 완료...";

			System.out.printf("서버[%s:%d]로 접속 요청...\n", rcvIP, rcvPort);
			// ta1.setText(strSysMsg1);
			cltSocket.connect(isaSvrAddr, 1000);
			System.out.printf("서버[%s:%d]로 접속 완료...\n", rcvIP, rcvPort);
			// ta1.setText(strSysMsg2);

			ta1.setText("");
			// tf12.setText("");
			// tf13.setText("");
			String strta1Msg = ta1.getText() + "비밀번호를 입력하시오." + "\n";

			ta1.setText(strta1Msg);

			// ta1.setText(strta1Msg2);

			inS = cltSocket.getInputStream();
			JavaTCPRcv jaInputT = new JavaTCPRcv(inS, tf0, tf1, tf2, tf3, tf4, tf5, ta1, lineChart);
			jaInputT.start();

			while (true) {

				os1 = cltSocket.getOutputStream();
				im.rcvMsg = null;

				if (rcvMsg != null) {
					os1.write(rcvMsg.getBytes());
					os1.flush();
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
				os1.close();
				cltSocket.shutdownInput();
				cltSocket.shutdownOutput();
				cltSocket.close();

			} catch (Exception e) {
			}
		}
	}
}