import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class JavaTimer extends Thread {

	public static String rcvGate;
	public static String rcvEnt;
//	public static String rcvIP;
//	public static String rcvMsg1;
	public static String strMsg;
	public static List<String> strMyIP = new ArrayList<String>();
	private boolean stop;
	private boolean aaa;
	JavaFXTCPRcv javafxTcpRcv;
	public int nFlag = 0;

	TextField tf8;	
	Button btncall;
	Stage stage;
	String rcvIP;
	String rcvMsg1;

	public JavaTimer(Stage stage, TextField tf8, Button btncall, String rcvIP, String rcvMsg1) {

		this.tf8 = tf8;

		this.btncall = btncall;
		this.stage = stage;
		this.stop = false;
		this.rcvMsg1 = rcvMsg1;
		this.rcvIP = rcvIP;
	}

	@Override
	public void run() {

		int count = 15;

//		System.out.print(count);

//		System.out.println("사이즈" + strMyIP.size());

//		for (int i = 0; i < strMyIP.size(); i++) {
//			System.out.println("타이머쪽 : " + strMyIP.get(i));
		//
//		}

		btncall.setOnAction((ActionEvent) -> {
			btnclose();
		});

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {

				try {
					btnclose();

				} catch (Exception e) {
					System.out.print(e.getLocalizedMessage());
				}

			}
		});

		strMyIP.add(rcvIP);

		while (stop == false) {

//				tf8.setText(i + "");
//				System.out.println(i);				
			tf8.setText(count + "");

			if (strMsg != null) {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						stage.close();

					}
				});

//				System.out.println("카운트 1 :" + strMyIP);
//				strMyIP.remove(rcvIP);
//				System.out.println("카운트 12 :" + strMyIP);

				stop = true;
				strMsg = null;
			}

			if (count == 0) {

				String cmd1 = "52";
				String szMsg = "관리자가 자리에 없습니다.";
				String sndMsg = cmd1 + rcvGate + rcvEnt + String.format("%15s", rcvIP) + String.format("%30s", szMsg);

				if (sndMsg != null) {
//								System.out.print(sndMsg);
					JavaFXTCPSnd.rcvMsg = sndMsg;
				}
//				System.out.print(strMyIP);

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						stage.close();

					}
				});
//						stop = true;
//				System.out.println("카운트 전 :" + strMyIP);
				strMyIP.remove(rcvIP);
//				System.out.println("카운트 후 :" + strMyIP);
//						strMyIP = null;
//						stage = null;

//						btncall();
//						stop = true;

//				System.out.println("카운트 전밖 :" + strMyIP);

				stop = true;

//				nFlag = 0;
//				strMyIP.remove(rcvIP);
//				stage=null;
//				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count--;

		}

//		stage.close();

//		System.out.println("카운트밖 전 :" + strMyIP);
//		strMyIP.remove(rcvIP);
//		System.out.println("카운트밖 후 :" + strMyIP);
//		strMsg = null;
//		stop = true;
//		strMyIP = null;
	}

	public void threadStop(boolean stop) {
		this.stop = stop;
	}

	public void btnclose() {

		String cmd1 = "52";
		String szMsg = "5분내로 가겠습니다.";

		String sndMsg = cmd1 + rcvGate + rcvEnt + String.format("%15s", rcvIP) + String.format("%30s", szMsg);
		if (sndMsg != null) { // System.out.print(sndMsg);
			JavaFXTCPSnd.rcvMsg = sndMsg;
		}

//		System.out.print(strMyIP);
//		strMsg = "1";		

//		Stage stage1 = (Stage) btncall.getScene().getWindow(); // stage1.close();
		stage.close();
		stop = true;
//		strMyIP = null;		
//		strMsg = "stop";
//		nFlag = 0;
//		System.out.println("확인 전 :" + strMyIP);
		strMyIP.remove(rcvIP);
//		System.out.println("확인 후 :" + strMyIP);

//		stage = null;

//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		stage = null;

	}
}
