import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JavaTCPRcv extends Thread {

	private InputStream inS;
	private TextField tf0;
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	private TextField tf5;
	private TextArea ta1;
	private LineChart<String, Number> lineChart;

	String rcvBuff;

	public JavaTCPRcv(InputStream inS, TextField tf0, TextField tf1, TextField tf2, TextField tf3, TextField tf4,
			TextField tf5, TextArea ta1, LineChart<String, Number> lineChart) {
		this.inS = inS;
		this.tf0 = tf0;
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.tf3 = tf3;
		this.tf4 = tf4;
		this.tf5 = tf5;
		this.ta1 = ta1;
		this.lineChart = lineChart;

		lineChart.setAnimated(false);
	}

	@Override
	public void run() {

		String[] i = new String[50];
		for (int resetPoint = 0; resetPoint < 50; resetPoint++) {
			i[resetPoint] = "-1";
		}
		try {

			byte[] byteRBuff = new byte[512]; // 수신 버퍼

			while (true) {

				int nRcvLen = inS.read(byteRBuff);

				if (nRcvLen < 0 || nRcvLen == 0) {
					continue;
				}

				rcvBuff = new String(byteRBuff, 0, nRcvLen);

				serialMsg(rcvBuff);

				if ((rcvBuff.indexOf("A") >= 0 && rcvBuff.indexOf("a") > rcvBuff.indexOf("A"))
						&& (rcvBuff.indexOf("B") >= rcvBuff.indexOf("A") && rcvBuff.indexOf("b") > rcvBuff.indexOf("B"))
						&& (rcvBuff.indexOf("C") >= rcvBuff.indexOf("B") && rcvBuff.indexOf("c") > rcvBuff.indexOf("C"))
						&& (rcvBuff.indexOf("D") >= rcvBuff.indexOf("C") && rcvBuff.indexOf("d") > rcvBuff.indexOf("D"))
						&& (rcvBuff.indexOf("E") >= rcvBuff.indexOf("D") && rcvBuff.indexOf("e") > rcvBuff.indexOf("E"))
						&& (rcvBuff.indexOf("F") >= rcvBuff.indexOf("E")
								&& rcvBuff.indexOf("f") > rcvBuff.indexOf("F"))) {

					String A0 = rcvBuff.substring(rcvBuff.indexOf("A") + 1, rcvBuff.indexOf("a"));
					String A1 = rcvBuff.substring(rcvBuff.indexOf("B") + 1, rcvBuff.indexOf("b"));
					String A2 = rcvBuff.substring(rcvBuff.indexOf("C") + 1, rcvBuff.indexOf("c"));
					String A3 = rcvBuff.substring(rcvBuff.indexOf("D") + 1, rcvBuff.indexOf("d"));
					String A4 = rcvBuff.substring(rcvBuff.indexOf("E") + 1, rcvBuff.indexOf("e"));
					String A5 = rcvBuff.substring(rcvBuff.indexOf("F") + 1, rcvBuff.indexOf("f"));

					for (int movePoint = 0; movePoint < 49; movePoint++) {
						i[movePoint] = i[movePoint + 1];
					}

					if (FXMLMain.choiceFlag > 0) {
						for (int choiceReset = 0; choiceReset < 50; choiceReset++) {
							i[choiceReset] = "-1";

						}
						FXMLMain.choiceFlag *= -1;
					}

					if (FXMLMain.A0 > 0) {
						i[49] = A0;
					}
					if (FXMLMain.A1 > 0) {
						i[49] = A1;
					}
					if (FXMLMain.A2 > 0) {
						i[49] = A2;
					}
					if (FXMLMain.A3 > 0) {
						i[49] = A3;
					}
					if (FXMLMain.A4 > 0) {
						i[49] = A4;
					}
					if (FXMLMain.A5 > 0) {
						i[49] = A5;
					}

					Platform.runLater(() -> {

						tf0.setText(A0);
						tf1.setText(A1);
						tf2.setText(A2);
						tf3.setText(A3);
						tf4.setText(A4);
						tf5.setText(A5);

						lineChart.getData().clear();
						XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
						series.setName("차트");
						int startLabel = 1;
						for (int namePoint = 0; namePoint < 50; namePoint++) {
							if (i[namePoint] != "-1") {
								String nameLabel = Integer.toString(startLabel);
								series.getData().add(
										new XYChart.Data<String, Number>(nameLabel, Integer.parseInt(i[namePoint])));
								startLabel++;
							}
						}

						lineChart.getData().add(series);
					});
				}
			}
		} catch (

		Exception exp) {
			System.out.println("[EXP]" + exp.getLocalizedMessage());
			return;
		}

	}

	void serialMsg(String rcvdata) {
		if (rcvdata.contains("Server")) {
			ta1.setText(ta1.getText() + rcvdata);
		}

		if (rcvdata.contains("PinNum")) {
			ta1.setText(ta1.getText() + rcvdata);
		}
	}
}
