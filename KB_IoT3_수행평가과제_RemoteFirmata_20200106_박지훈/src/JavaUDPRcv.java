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

public class JavaUDPRcv extends Thread {
	private TextField tf14;
	private TextArea ta2;
	public String rcvMsgUdp;

	public JavaUDPRcv(TextField tf14, TextArea ta2) {
		this.tf14 = tf14;
		this.ta2 = ta2;
	}

	@Override
	public void run() {
		DatagramSocket udpCltSocket = null;		

		JavaUDPRcv im = new JavaUDPRcv(tf14, ta2);
		try {
			String strSvrIP = "192.168.34.99";
			int nSvrPort = 7777;

			udpCltSocket = new DatagramSocket();
			byte[] byteBuff = null;

			while (true) {

				im.rcvMsgUdp = null;
				if (rcvMsgUdp != null) {
					byteBuff = rcvMsgUdp.getBytes("utf-8");
					System.out.print(rcvMsgUdp);
					DatagramPacket udpPacket = new DatagramPacket(byteBuff, byteBuff.length,
							new InetSocketAddress(strSvrIP, nSvrPort));
					udpCltSocket.send(udpPacket);
					rcvMsgUdp = null;
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

	}
}
