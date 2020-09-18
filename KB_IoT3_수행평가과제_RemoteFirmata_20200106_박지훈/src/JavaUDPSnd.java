import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import javafx.scene.chart.LineChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JavaUDPSnd extends Thread {
	private TextField tf14;
	private TextArea ta2;
	public String rcvMsgUdp;

	public JavaUDPSnd(TextField tf14, TextArea ta2) {
		this.tf14 = tf14;
		this.ta2 = ta2;
	}

	@Override
	public void run() {

		DatagramSocket udpSvrSocket = null;

		try {

			int nSvrPort = 7777;

			udpSvrSocket = new DatagramSocket(nSvrPort);
			byte[] byteBuff = null;

			while (true) {

				byteBuff = new byte[256];
				DatagramPacket udpPacket = new DatagramPacket(byteBuff, byteBuff.length);
				udpSvrSocket.receive(udpPacket);
				String strRcvMsg = new String(byteBuff, 0, udpPacket.getLength(), "utf-8");
				InetAddress iaClt = udpPacket.getAddress();
				String strRcvMsg1 = strRcvMsg + "\n";
				ta2.setText(ta2.getText() + strRcvMsg1);
				System.out.printf("클라이언트 [%s:%d] - %s\n", iaClt.getHostName(), udpPacket.getPort(), strRcvMsg);

			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

	}
}
