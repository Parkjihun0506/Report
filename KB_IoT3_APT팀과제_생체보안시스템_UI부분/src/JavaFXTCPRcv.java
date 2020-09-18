import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class JavaFXTCPRcv extends Thread {

	public List<String> listData = new ArrayList<String>();
	public List<String> listIP = new ArrayList<String>();
	public List<String> listGate = new ArrayList<String>();
	public List<HandlerTest6> listCon = new ArrayList<HandlerTest6>();
	public List<FXMLLoader> listfxml = new ArrayList<FXMLLoader>();
	Stage stage;
	JavaTimer javaTimer;
//	public static int nFlag = 0;
	boolean aaa = false;

	ObservableList<Person4> list4 = null;

	private InputStream inS;
	private boolean stop;
	HandlerTest6 handlerTest6;
	HandlerTest5 handlerTest5;
	HandlerTest4 handlerTest4;
	HandlerTest3 handlerTest3;
	HandlerTest2 handlerTest2;
	HandlerTest1 handlerTest1;
	String rcvBuff;

	@FXML
	TableColumn<Person4, String> entColumn, exitColumn;
	@FXML
	TableColumn<Person4, Integer> gateColumn2;

	public JavaFXTCPRcv(InputStream inS) {
		this.inS = inS;
		this.stop = false;
	}

	@Override
	public void run() {

		try {

			byte[] byteRBuff = new byte[20000]; // 수신 버퍼
			Thread.sleep(1);

			while (!stop) {

				int nRcvLen = inS.read(byteRBuff);

				if (nRcvLen < 0 || nRcvLen == 0) {
					continue;
				}

				rcvBuff = new String(byteRBuff, 0, nRcvLen);
//				String buffsub = rcvBuff.substring(0, 2);

//				System.out.println("-------------");
//				System.out.println(buffsub);
//				System.out.println("-------------");

				System.out.println(rcvBuff);

				String rcvCmd = rcvBuff.substring(0, 2);

				Thread.sleep(1000);

				if (rcvCmd.equals("10")) {
					login(rcvBuff);
				}

				if (rcvCmd.equals("11")) {
					acesLog(rcvBuff);
				}

				if (rcvCmd.equals("12")) {
					editLog(rcvBuff);
				}

				if (rcvCmd.equals("13")) {
					sysCheck(rcvBuff);
				}

				if (rcvCmd.equals("14")) {
					regist(rcvBuff);
				}

				if (rcvCmd.equals("15")) {
					newCheck(rcvBuff);
				}

				if (rcvCmd.equals("16")) {
					baseLog(rcvBuff);
				}

				if (rcvCmd.equals("17")) {
					logDel(rcvBuff);
				}

				if (rcvCmd.equals("18")) {
					logAdjustF(rcvBuff);
				}

				if (rcvCmd.equals("19")) {
					logAdjustH(rcvBuff);
				}

				if (rcvCmd.equals("52")) {
					callpop(rcvBuff);
				}
				rcvBuff = null;
			}
		} catch (

		Exception exp) {
			System.out.println("[EXP]" + exp.getLocalizedMessage());
			return;
		}

	}

	public void threadStop(boolean stop) {
		this.stop = stop;
	}

	public void login(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvTF = rcvMsg.substring(2, 3);
		String rcvEmpID = rcvMsg.substring(3, 8);
		String rcvName = rcvMsg.substring(8, 16);
		String rcvLev = rcvMsg.substring(16, 20);
		String rcvData = rcvMsg.substring(0, rcvLen);

		handlerTest1.tcpRcv1 = rcvTF;
		handlerTest2.tcpRcv = rcvLev;
		handlerTest2.strName = rcvName;
		handlerTest2.strEmpID = rcvEmpID;
		handlerTest3.strName = rcvName;
		handlerTest3.strEmpID = rcvEmpID;
		handlerTest4.strEmpID = rcvEmpID;
		handlerTest4.strName = rcvName;

		for (int i = 20; i < rcvLen; i += 15) {

			String rcvIP = rcvMsg.substring(i, i + 15).trim();
			listIP.add(rcvIP);
			handlerTest2.rcvIP = listIP;
//			System.out.println(rcvIP);
		}
		rcvMsg = null;

	}

	public void acesLog(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvCnt = rcvMsg.substring(2, 7);

		handlerTest2.strCnt1 = rcvCnt;

		for (int i = 7; i < rcvLen; i += 60) {

			String rcvData = rcvMsg.substring(i, i + 60);
//			System.out.println(rcvData);
			listData.add(rcvData);
			handlerTest2.rcvList1 = listData;
		}
		rcvCnt = null;
		rcvMsg = null;
//		System.out.print(listData.isEmpty());
	}

	public void editLog(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvCnt = rcvMsg.substring(2, 7);
//		String rcvData = rcvMsg.substring(0, rcvLen);
		handlerTest2.strCnt2 = rcvCnt;
//		System.out.print(rcvData);

		for (int i = 7; i < rcvLen; i += 44) {

			String rcvData = rcvMsg.substring(i, i + 44);
//			System.out.println(rcvData);
			listData.add(rcvData);
			handlerTest2.rcvList2 = listData;
		}
//		System.out.print(rcvData);
		rcvCnt = null;
		rcvMsg = null;
//		System.out.print(listData.isEmpty());
	}

	public void sysCheck(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;
//		String rcvData = rcvMsg.substring(7, 67);
//		String rcvCnt = rcvMsg.substring(2, 7);

//		String rcvData = rcvMsg.substring(0, rcvLen);
//		System.out.println(rcvLen);
//		System.out.println(rcvCnt);

//		handlerTest2.strCnt = rcvCnt;
//		System.out.print(rcvData);	

		for (int i = 2; i < rcvLen; i += 3) {

			String rcvData = rcvMsg.substring(i, i + 3);
//			System.out.println(rcvData);

			listGate.add(rcvData);

			handlerTest2.rcvSys = listGate;

//			handlerTest2.strSys = rcvData;
//			handlerTest2.flag = "go";
		}

		rcvMsg = null;
	}

	public void baseLog(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvCnt = rcvMsg.substring(2, 7);

		handlerTest2.strCnt3 = rcvCnt;
//		System.out.println(rcvData);

		for (int i = 7; i < rcvLen; i += 247) {

			String rcvData = rcvMsg.substring(i, i + 247);
			// System.out.println(rcvData);
			listData.add(rcvData);
			handlerTest2.rcvList3 = listData;
			// handlerTest2.tcpRcv = "5";
//			System.out.println(rcvData);

		}
		rcvCnt = null;
		rcvMsg = null;

//		System.out.println(rcvData);
	}

	public void newCheck(String rcvMsg) {
		int rcvLen = rcvMsg.length() - 1;
//		String rcvCnt = rcvMsg.substring(2, 7);
//		String rcvData = rcvMsg.substring(53, 99);		
		String rcvEmpID = rcvMsg.substring(2, 7);
		String rcvTF = rcvMsg.substring(7, 8);
		String rcvName = rcvMsg.substring(8, 16);
		String rcvCall = rcvMsg.substring(16, 30);
		String rcvDep = rcvMsg.substring(30, 40);
		String rcvData = rcvMsg.substring(0, rcvLen);

		handlerTest3.strTF = rcvTF;
		handlerTest3.rcvEmpID = rcvEmpID;
		handlerTest3.rcvName = rcvName;
		handlerTest3.strCall = rcvCall;
		handlerTest3.strDep = rcvDep;

//		System.out.println(rcvLen);
//		System.out.println(rcvData);

		rcvMsg = null;

	}

	public void regist(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

//		String rcvData = rcvMsg.substring(0, rcvLen);
		String rcvTF = rcvMsg.substring(2, 3);
		handlerTest3.rcvTF = rcvTF;
//		System.out.println(rcvEmpID);
//		System.out.println(rcvData);

		rcvMsg = null;
	}

	public void logDel(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

//		String rcvData = rcvMsg.substring(0, rcvLen);
		String rcvTF = rcvMsg.substring(2, 3);
		handlerTest2.strTF = rcvTF;
//		System.out.println(rcvEmpID);
//		System.out.println(rcvData);

		rcvMsg = null;
	}

	public void logAdjustF(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvData = rcvMsg.substring(0, rcvLen);
		String rcvTF = rcvMsg.substring(2, 3);

//		System.out.println(rcvEmpID);
//		System.out.println(rcvData);
		handlerTest4.rcvTF = rcvTF;
		rcvMsg = null;
	}

	public void logAdjustH(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvData = rcvMsg.substring(0, rcvLen);
		String rcvTF = rcvMsg.substring(2, 3);

//		System.out.println(rcvEmpID);
//		System.out.println(rcvData);
		handlerTest5.rcvTF = rcvTF;
		rcvMsg = null;
	}

	public void callpop(String rcvMsg) {

		int rcvLen = rcvMsg.length() - 1;

		String rcvData = rcvMsg.substring(0, rcvLen);
		String rcvGate = rcvMsg.substring(2, 8);
		String rcvEnt = rcvMsg.substring(8, 11);
		String rcvIP = rcvMsg.substring(11, 26).trim();
		String rcvMsg1 = rcvMsg.substring(26, 56);

//		System.out.println(rcvMsg1);

		handlerTest6.rcvGate = rcvGate;
		handlerTest6.rcvEnt = rcvEnt;
		handlerTest6.rcvIP = rcvIP;
		handlerTest6.rcvMsg1 = rcvMsg1;
		javaTimer.rcvGate = rcvGate;
		javaTimer.rcvEnt = rcvEnt;
//		JavaTimer.rcvIP = rcvIP;
//		JavaTimer.rcvMsg1 = rcvMsg1;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// 다른 창 띄우는 작업 .... 1
				for (int i = 0; i < listIP.size(); i++) {

					if (rcvIP.equals(listIP.get(i)) && rcvMsg1.trim().equals("")) {
						if (JavaTimer.strMyIP.isEmpty() == false) {
							for (int j = 0; j < JavaTimer.strMyIP.size(); j++) {
//								System.out.println(rcvIP);
//								System.out.println("RCV 사이즈 : " + JavaTimer.strMyIP.size());
								if (JavaTimer.strMyIP.get(j).equals(rcvIP)) {
//									System.out.print(Integer.toString(j) + "::" + JavaTimer.strMyIP.get(j));
//									System.out.println("2345");
									aaa = true;
									break;
								}
							}
							if (aaa == true) {
								aaa = false;
//								System.out.println("1234");
								break;
							}
						}

//						if (stage != null) {
//							break;
//						}

						// if (rcvIP.trim().equals("192.168.34.51") && rcvMsg1.trim().equals("")) {

//					52     6  입  192.168.34.106                              
//					52     6  입  192.168.34.106                   5분내로 가겠습니다.

//						System.out.println("팝업창 뜸");

						FXMLLoader another = new FXMLLoader(JavaFXTCPRcv.class.getResource("FXMLpopup.fxml"));

						HandlerTest6 con = new HandlerTest6();

						another.setController(con);

						try {
							AnchorPane anotherPage = (AnchorPane) another.load();

							// 다른창 띄우는 작업 .... 2
							Scene anotherScene = new Scene(anotherPage);

							stage = new Stage(StageStyle.UTILITY);
							stage.setResizable(false);
							stage.setScene(anotherScene);

							javaTimer = new JavaTimer(stage, con.tf8, con.btncall, rcvIP, rcvMsg1);
//							javaTimer.setDaemon(true);
							javaTimer.start();

							double x = stage.getX();
							double y = stage.getY();

							stage.setX(x + 10);
							stage.setY(y + 10);
							stage.setAlwaysOnTop(true);

//							System.out.println("5555");
//							System.out.println(rcvMsg1);
//							if (rcvMsg1.trim().equals("")) {

							stage.show();
//							}
//							if (!rcvMsg1.trim().equals("") && stage != null) {
//								System.out.println("1111");
//								stage.close();
//								javaTimer.threadStop(true);
//							}
//							nFlag = 1;

//							handlerTest2.ta1.setText(
//									handlerTest2.ta1.getText() + rcvGate + "번 게이트 " + rcvEnt + "구에서 호출이 왔습니다." + "\n");

//						System.out.println(stage.isShowing());

//					Popup popup = new Popup();
//					Parent parent= FXMLLoader.load(getClass().getResource("FXMLpopup.fxml"));			
//					popup.getContent().add(parent);
//					popup.show();						

							// 다른창 띄우는 작업 .... 2 끝.
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e);
						}

					}
					if (rcvIP.equals(listIP.get(i)) && !rcvMsg1.trim().equals("") && stage != null) {

						try {
//							System.out.println("날아오는거 전 : " + javaTimer.strMyIP);
							javaTimer.strMyIP.remove(rcvIP);
//							Platform.runLater(new Runnable() {
//								@Override
//								public void run() {
//									stage.close();
//								}
//							});
							javaTimer.strMsg = "stop";
//							javaTimer.threadStop(true);
//							System.out.println("날아오는거 후 : " + javaTimer.strMyIP);
//							nFlag = 0;
//							if (stage.isShowing() == false) {
//								System.out.println("팝업창 꺼짐");
//							}
//							System.out.println("팝업창 꺼짐2");

//							handlerTest2.ta1.setText(handlerTest2.ta1.getText() + "다른관리자가 " + rcvGate + "번 게이트 "
//									+ rcvEnt + "구의 호출을 확인했습니다.");
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e);
						}
					}

				}

			}

		});
		// 다른 창 띄우는 작업 .... 1 끝.
//		stage = null;
		rcvMsg = null;

//		}

	}

}
