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

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JavaFXTCPSnd extends Thread {

	public static String rcvMsg;

	private boolean stop;
	JavaFXTCPRcv jaInputT;

	public static Socket cltSocket = null; // 클라이언트 소켓

	public JavaFXTCPSnd() {
		this.stop = false;

	}

	@Override
	public void run() {
		String strSvrIP = "192.168.34.38";
		int nSvrPort = 7777;
		JavaFXTCPSnd im = new JavaFXTCPSnd();
		String rcvIP = strSvrIP;
		int rcvPort = nSvrPort;
		InputStream inS = null;
		OutputStream os1 = null;

		InetSocketAddress isaSvrAddr = null;
		InetSocketAddress isaCltAddr = null;

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

			inS = cltSocket.getInputStream();

			byte[] byteBuff = new byte[1024];

			jaInputT = new JavaFXTCPRcv(inS);
			jaInputT.start();

			os1 = cltSocket.getOutputStream();

			while (!stop) {

				Thread.sleep(1);

				if (rcvMsg != null) {
					// System.out.println(rcvMsg);
					os1.write(rcvMsg.getBytes());
					os1.flush();
//					System.out.print(rcvMsg);
					rcvMsg = null;
//					int rcvLen = inS.read(byteBuff);
//					String strRcvMsg = new String(byteBuff, 0, rcvLen, "EUC-KR");
//					System.out.print(strRcvMsg);

				} else {
					continue;
				}
			}
		} catch (

		Exception e) {
			System.out.println("[EXP] " + e.getLocalizedMessage());
		} finally {
			try {
				jaInputT.threadStop(true);
				inS.close();
				os1.close();
				cltSocket.shutdownInput();
				cltSocket.shutdownOutput();
				cltSocket.close();

			} catch (Exception e) {
			}
		}
	}

	public void threadStop(boolean stop) {
		this.stop = stop;
	}
}