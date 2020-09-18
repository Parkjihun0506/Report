import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JavaInputT1 extends Thread {

	private InputStream inS;
	private TextField tf0;
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	private TextField tf5;	
	private TextArea ta1;
	private String strRes1;

	public JavaInputT1(InputStream inS, TextField tf0, TextField tf1, TextField tf2, TextField tf3, TextField tf4,
			TextField tf5, TextArea ta1) {
		this.inS = inS;
		this.tf0 = tf0;
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.tf4 = tf4;
		this.tf5 = tf5;
		this.ta1 = ta1;
	}

	@Override
	public void run() {

		try {

			byte[] byteRBuff = new byte[5]; // 수신 버퍼
			int strResult;
			String rcvBuff;

			while (true) {

				int nRcvLen = inS.read(byteRBuff);

				rcvBuff = new String(byteRBuff, 0, nRcvLen);

				strResult = rcvBuff.indexOf("/");

				if (strResult > 0) {
					if (strRes1 == null) {
						strRes1 = rcvBuff.substring(strResult + 1);
						continue;
					}
					strRes1 += rcvBuff.substring(0, strResult);
					strTemp();
					strMsg();
					strRes1 = rcvBuff.substring(strResult + 1);
				}

				if (strResult == 0) {
					if (strRes1 == null) {
						strRes1 = rcvBuff.substring(1);
						continue;
					}
					strTemp();
					strMsg();
					strRes1 = rcvBuff.substring(1);
				}
				if (strResult < 0) {

					if (strRes1 == null) {
						continue;
					}
					strRes1 += rcvBuff;
				}
			}

		} catch (

		Exception exp) {
			System.out.println("[EXP]" + exp.getLocalizedMessage());
			return;
		}
	}

	void strTemp() {

		int strTemp1 = strRes1.indexOf("A0");
		int strTemp2 = strRes1.indexOf("A1");
		int strTemp3 = strRes1.indexOf("A2");
		int strTemp4 = strRes1.indexOf("A3");
		int strTemp5 = strRes1.indexOf("A4");
		int strTemp6 = strRes1.indexOf("A5");

		if (strTemp1 != -1) {
			tf0.setText(strRes1.substring(strTemp1));
		}
		if (strTemp2 != -1) {
			tf1.setText(strRes1.substring(strTemp2));
		}
		if (strTemp3 != -1) {
			tf2.setText(strRes1.substring(strTemp3));
		}
		if (strTemp4 != -1) {
			tf3.setText(strRes1.substring(strTemp4));
		}
		if (strTemp5 != -1) {
			tf4.setText(strRes1.substring(strTemp5));
		}
		if (strTemp6 != -1) {
			tf5.setText(strRes1.substring(strTemp6));
		}
	}

	void strMsg() {
		int strMsg1 = strRes1.indexOf(".");
		if (strMsg1 != -1) {
			ta1.setText(strRes1.substring(0, strMsg1));
		}
	}
}
