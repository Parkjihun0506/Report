import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class HandlerTest2 implements Initializable {

	JavaFXTCPSnd javafxTcpSnd1;
	JavaFXTCPRcv javafxTcpRcv1;
//	HandlerTest1 handlerTest1;
	public static String tcpRcv;
	public static List<String> rcvList1 = new ArrayList<String>();
	public static List<String> rcvList2 = new ArrayList<String>();
	public static List<String> rcvList3 = new ArrayList<String>();
	public static List<String> rcvSys = new ArrayList<String>();
	public static List<String> rcvIP = new ArrayList<String>();
	public static String strName;
	public static String strEmpID;
	public static String strCnt1;
	public static String strCnt2;
	public static String strCnt3;
	public static String strTF;

	public static String strSys;

	public static String id;
	public static String name;
	public static String call;
	public static String date;
	public static String aces;
	public static String gate;
	public static String flag;
	private Pagination pagination = new Pagination(5, 2);
	public static Stage newc;
	public static Stage popF;
	public static Stage popH;
	String rcvOut;
	String gateIP;

	ObservableList<Person> list = null;
	ObservableList<Person2> list2 = null;
	ObservableList<Person3> list3 = null;
	ObservableList<Person4> list4 = null;

	private ObservableList<String> cbList = FXCollections.observableArrayList("조회", "수정", "삭제", "등록");

	@FXML
	TableColumn<Person, String> idColumn, nameColumn, callColumn, dateColumn, acesColumn, gateColumn;
	@FXML
	TableColumn<Person2, String> idColumn2, nameColumn2, dateColumn2, editColumn;
	@FXML
	TableColumn<Person3, String> idColumn3, nameColumn3, callColumn3, dateColumn3, depColumn, faceColumn, fingerColumn;
	@FXML
	TableColumn<Person3, Boolean> chkColumn;
	@FXML
	TableColumn<Person4, String> entColumn, exitColumn;
	@FXML
	TableColumn<Person4, Integer> gateColumn2;
	@FXML
	TableView<Person> tableroom;
	@FXML
	TableView<Person2> tableroom2;
	@FXML
	TableView<Person3> tableroom3;
	@FXML
	TableView<Person4> tableroom4;

	@FXML
	Button btnLogin, btnLogout, btnpop, btncan, btnsrch1, btnsrch3, btnnew, btnchnH, btnchnF, btncan2, btnfnd1, btnfnd2,
			btndel, btnall, btnref;
	@FXML
	TextField tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10;
	@FXML
	Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
	@FXML
	Tab tab1, tab2, tab3, tab4;
	@FXML
	DatePicker dp1, dp2, dp3, dp4;
	@FXML
	AnchorPane anchor1;
	@FXML
	ComboBox cb1;
	@FXML
	TextArea ta1, ta2;

	private final static int rowsPerPage = 10;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		JavaFXTCPSnd.rcvMsg = "13";

		// System.out.print(strMsg);

//		System.out.print(tcpRcv);		
		clock();

		btnref.setDisable(true);

		ta1.setText(ta1.getText() + "환영합니다." + "\n");

		if (tcpRcv.trim().substring(1, 2).equals("0")) {
			tab2.setDisable(true);
		}

		if (tcpRcv.trim().substring(2, 3).equals("0")) {
			tab3.setDisable(true);
		}

		if (tcpRcv.trim().substring(3, 4).equals("0")) {
			tab4.setDisable(true);

		} else if (tcpRcv.trim().substring(3, 4).equals("1")) {

			btnnew.setDisable(true);
			btnchnH.setDisable(true);
			btnchnF.setDisable(true);
			btndel.setDisable(true);
		}

		tcpRcv = null;

//		else if (tcpRcv.trim().equals("2")) {
//			tab4.setDisable(true);
//			tcpRcv = null;
//		}
//		System.out.print(strName);

		if (strEmpID != null) {

			lbl3.setText(strEmpID + "님 환영합니다.");
		}

		if (strName != null) {

			if (rcvIP.size() != 0) {

				for (int i = 0; i < rcvIP.size(); i++) {

					gateIP = rcvIP.get(i) + " ";
					gateIP += gateIP;
				}

				String strArea = strName.trim() + " 님의 관리 게이트는" + "\n" + gateIP + "입니다." + "\n";
//				ta1.setText(ta1.getText() + strArea);

//				ta1.setText(ta1.getText() + gateIP + "\n");

			}
		}

		dp1.setValue(LocalDate.now());
		dp3.setValue(LocalDate.now());

		// 끝일자를 시작일자보다 이전으로 선택하지 못하게 하는 함수
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (dp1.getValue() != null) {
							if (item.isBefore(dp1.getValue().plusDays(1))) {
								setDisable(true);
								setStyle("-fx-background-color: #ffc0cb;");
							}
						}
					}
				};
			}
		};

		final Callback<DatePicker, DateCell> dayCellFactory1 = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (dp3.getValue() != null) {
							if (item.isBefore(dp3.getValue().plusDays(1))) {
								setDisable(true);
								setStyle("-fx-background-color: #ffc0cb;");
							}
						}
					}
				};
			}
		};

		dp2.setDayCellFactory(dayCellFactory);
		dp2.setValue(dp1.getValue().plusDays(1));
		dp4.setDayCellFactory(dayCellFactory1);
		dp4.setValue(dp3.getValue().plusDays(1));

		cb1.setValue("");
		cb1.setItems(cbList);

		sysCheck();
		acesCheck();
		editCheck();
		baseCheck();
		delCheck();
		sysRefresh();

//		dp2.setValue(LocalDate.now());
//		dp1.setValue(dp2.getValue().minusDays(1));

//		dp1.setValue(LocalDate.now());

//		if (tableroom4.getColumns() == null) {
//			btnref.setDisable(false);
//
//		}

	}

	public void btnCancle2(ActionEvent event) throws Exception {

		tf5.setText("");
		tf6.setText("");
		lbl2.setText("");
		dp1.setValue(LocalDate.now());
		dp2.setValue(dp1.getValue().plusDays(1));
		tableroom.getItems().clear();
	}

	public void btnRefresh(ActionEvent event) throws Exception {

		javafxTcpSnd1.rcvMsg = "13";

	}

	public void btnCancle3(ActionEvent event) throws Exception {

		tf9.setText("");
		tf10.setText("");
		lbl5.setText("");
		dp3.setValue(LocalDate.now());
		dp4.setValue(dp3.getValue().plusDays(1));
		cb1.setValue("");
		tableroom2.getItems().clear();
	}

	public void btnCancle4(ActionEvent event) throws Exception {

		tf7.setText("");
		tf8.setText("");
		lbl4.setText("");

		tableroom3.getItems().clear();
	}

	// 로그아웃
	public void btnLogout(ActionEvent event) throws Exception {

//		StackPane root = (StackPane)btnLogout.getScene().getRoot();
//		root.getChildren().remove(login);

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그아웃");
		alert.setHeaderText("");
		alert.setContentText("로그아웃 하시겠습니까?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			try {
				javafxTcpSnd1.threadStop(true);
				rcvOut = "stop";

				Parent login = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));

				Scene scene = new Scene(login);
				Stage primaryStage = (Stage) btnLogout.getScene().getWindow();
				primaryStage.setScene(scene);

			} catch (Exception e) {
				System.out.print(e.getLocalizedMessage());
			}

		} else {

			alert.close();
		}

//		System.out.print(tf1.getText());

	}

	// 출입로그조회
	public void btnSerch1(ActionEvent event) throws Exception {

		if (tf5.getText().length() <= 5 && tf6.getText().length() <= 8) {

			String cmd1 = "11";
			String datePicker1 = String.format("%10s", dp1.getValue().toString());
			String textField5 = String.format("%5s", tf5.getText().toUpperCase());
			String textField6 = String.format("%8s", tf6.getText().toUpperCase());
			String datePicker2 = String.format("%10s", dp2.getValue().toString());

			String sndMsg = cmd1 + textField5 + textField6 + datePicker1 + datePicker2;

			if (sndMsg != null) {
//				System.out.print(sndMsg);
				javafxTcpSnd1.rcvMsg = sndMsg;
			}

//			Thread.sleep(1500);

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("[ERR3] 사번 또는 사원명을 확인하세요.");
			alert.showAndWait();
		}
	}

	// 편집로그조회
	public void btnSerch2(ActionEvent event) throws Exception {

		if (tf9.getText().length() <= 5 && tf10.getText().length() <= 8) {

			String cmd1 = "12";
			String datePicker3 = String.format("%10s", dp3.getValue().toString());
			String textField9 = String.format("%5s", tf9.getText().toUpperCase());
			String textField10 = String.format("%8s", tf10.getText().toUpperCase());
			String datePicker4 = String.format("%10s", dp4.getValue().toString());
			String comboBox1 = String.format("%2s", cb1.getValue().toString());

			String sndMsg = cmd1 + textField9 + textField10 + datePicker3 + datePicker4 + comboBox1;

			if (sndMsg != null) {
//				System.out.print(sndMsg);
				javafxTcpSnd1.rcvMsg = sndMsg;
			}

//			Thread.sleep(3000);

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("[ERR3] 사번 또는 사원명을 확인하세요.");
			alert.showAndWait();
		}
	}

	private Node createPage(int pageIndex) {

		int fromIndex = pageIndex * rowsPerPage;
		int toIndex = Math.min(fromIndex + rowsPerPage, list3.size());
		tableroom3.setItems(FXCollections.observableArrayList(list3.subList(fromIndex, toIndex)));

		return new BorderPane(tableroom3);
	}

	// 기준정보조회
	public void btnSerch3(ActionEvent event) throws Exception {

		if (tf7.getText().equals("") && tf8.getText().equals("")) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("사번 또는 사원명을 입력하시오.");
			alert.showAndWait();

		} else {
			if (tf7.getText().length() > 5 || tf8.getText().length() > 8) {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("에러");
				alert.setHeaderText("");
				alert.setContentText("[ERR3]사번 또는 사원명을 확인하세요.");
				alert.showAndWait();

			} else {

				String cmd1 = "16";
				String textField5 = String.format("%5s", tf7.getText().toUpperCase());
				String textField6 = String.format("%8s", tf8.getText().toUpperCase());
				String sndName = String.format("%8s", strName);

				String sndMsg = cmd1 + textField5 + textField6 + strEmpID + sndName;

				if (sndMsg != null) {
//					System.out.print(sndMsg);
					javafxTcpSnd1.rcvMsg = sndMsg;
				}
//				Thread.sleep(3000);
			}

//			Thread.sleep(1500);

		}
	}

	// 신규 등록 팝업
	public void btnnew(ActionEvent event) {

		newc = new Stage(StageStyle.DECORATED);
		Stage stage = (Stage) btnnew.getScene().getWindow();

		newc.initModality(Modality.WINDOW_MODAL);
		newc.initOwner(stage);
		newc.setTitle("신규 기준정보 등록");

		try {
			Parent root = FXMLLoader.load(getClass().getResource("FXMLNew.fxml"));
			Scene sc = new Scene(root);
			newc.setResizable(false);
			newc.setScene(sc);
			newc.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 안면 수정 팝업
	public void btnchnF(ActionEvent event) throws Exception {

		if (!tableroom3.getSelectionModel().isEmpty()) {

			Person3 mVo = tableroom3.getSelectionModel().getSelectedItem();

			HandlerTest4.rcvEmpID = mVo.getidColumn().getValue().toString();
			HandlerTest4.rcvName = mVo.getnameColumn().getValue().toString();
			HandlerTest4.rcvCall = mVo.getcallColumn().getValue().toString();
			HandlerTest4.rcvDep = mVo.getdepColumn().getValue().toString();
			HandlerTest4.rcvFinger = mVo.getfingerColumn().getValue().toString();
			HandlerTest4.rcvDate = mVo.getdateColumn().getValue().toString();

			popF = new Stage(StageStyle.DECORATED);
			Stage stage = (Stage) btnchnF.getScene().getWindow();

			popF.initModality(Modality.WINDOW_MODAL);
			popF.initOwner(stage);
			popF.setTitle("안면이미지 수정");

			Parent root = FXMLLoader.load(getClass().getResource("FXMLChangeF.fxml"));
			Scene sc = new Scene(root);
			popF.setResizable(false);
			popF.setScene(sc);
			popF.show();

//			Thread.sleep(1000);

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("수정할 데이터를 선택하세요.");
			alert.showAndWait();
		}

	}

	// 지문 수정 팝업
	public void btnchnH(ActionEvent event) throws Exception {

		if (!tableroom3.getSelectionModel().isEmpty()) {
			Person3 mVo = tableroom3.getSelectionModel().getSelectedItem();

			HandlerTest5.rcvEmpID = mVo.getidColumn().getValue().toString();
			HandlerTest5.rcvName = mVo.getnameColumn().getValue().toString();
			HandlerTest5.rcvCall = mVo.getcallColumn().getValue().toString();
			HandlerTest5.rcvDep = mVo.getdepColumn().getValue().toString();
			HandlerTest5.rcvFace = mVo.getfaceColumn().getValue().toString();
			HandlerTest5.rcvDate = mVo.getdateColumn().getValue().toString();

			popH = new Stage(StageStyle.DECORATED);
			Stage stage = (Stage) btnchnH.getScene().getWindow();

			popH.initModality(Modality.WINDOW_MODAL);
			popH.initOwner(stage);
			popH.setTitle("지문이미지 수정");

			Parent root = FXMLLoader.load(getClass().getResource("FXMLChangeH.fxml"));
			Scene sc = new Scene(root);
			popH.setResizable(false);
			popH.setScene(sc);
			popH.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("수정할 데이터를 선택하세요.");
			alert.showAndWait();
		}
	}

	public void btndel(ActionEvent event) throws Exception {

		if (!tableroom3.getSelectionModel().isEmpty()) {
			Person3 mVo = tableroom3.getSelectionModel().getSelectedItem();

			String cmd1 = "17";
			String gId = String.format("%5s", mVo.getidColumn().getValue().toString());
			String gName = String.format("%8s", mVo.getnameColumn().getValue().toString());
			String gCall = String.format("%14s", mVo.getcallColumn().getValue().toString());
			String gDep = String.format("%10s", mVo.getdepColumn().getValue().toString());
			String gFace = String.format("%100s", mVo.getfaceColumn().getValue().toString());
			String gFinger = String.format("%100s", mVo.getfingerColumn().getValue().toString());
			String gDate = String.format("%10s", mVo.getdateColumn().getValue().toString());
			String sndName = String.format("%8s", strName);

			String sndMsg = cmd1 + gId + gName + gCall + gDep + gFace + gFinger + gDate + strEmpID + sndName;

			if (sndMsg != null) {
//				System.out.print(sndMsg);
				JavaFXTCPSnd.rcvMsg = sndMsg;
				sndMsg = null;
			}
//			Thread.sleep(2000);			

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("에러");
			alert.setHeaderText("");
			alert.setContentText("삭제할 데이터를 선택하세요.");
			alert.showAndWait();
		}

	}

	// 현재시간
	public void clock() {

		Thread clockT = new Thread(new Runnable() {
			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				while (true) {
					String strDate = sdf.format(new Date());

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							lbl6.setText(strDate);
						}
					});

					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		clockT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		clockT.start();
	}

	// 게이트 연결상태
	public void sysCheck() {

		Thread sysCheckT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						if (rcvSys.size() != 0) {

							list4 = FXCollections.observableArrayList();

							for (int i = 0; i < rcvSys.size(); i++) {

								list4.add(new Person4(new SimpleIntegerProperty(i + 1),
										new SimpleStringProperty(rcvSys.get(i).substring(1, 2).trim()),
										new SimpleStringProperty(rcvSys.get(i).substring(2, 3).trim())));

								gateColumn2.setCellValueFactory(
										cellData -> cellData.getValue().getgateColumn2().asObject());
								entColumn.setCellValueFactory(cellData -> cellData.getValue().getentColumn());
								exitColumn.setCellValueFactory(cellData -> cellData.getValue().getexitColumn());
							}
							tableroom4.setItems(list4);

							if (tableroom4.getItems().isEmpty() != true) {
								rcvSys.removeAll(rcvSys);
								System.out.printf("[DBG] List remove " + rcvSys.isEmpty() + "\n");
							}
						}
					}

				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(100);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);
				}
			}
		});
		sysCheckT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		sysCheckT.start();
	}

	// 게이트 연결상태
	public void sysRefresh() {

		Thread sysRefreshT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {
//						System.out.print(tableroom4.getItems());

						if (tableroom4.getItems().isEmpty() == true) {

							btnref.setDisable(false);
						} else {
							btnref.setDisable(true);
						}
					}
				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(500);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);
				}
			}
		});
		sysRefreshT.setDaemon(true);
		sysRefreshT.start();
	}

	// 출입로그
	public void acesCheck() {

		Thread acesCheckT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						if (strCnt1 != null) {

//							System.out.println(id);

							lbl2.setText(strCnt1);

							int dataSize = Integer.parseInt(strCnt1.trim());

							if (rcvList1.size() != 0) {
//							System.out.print(rcvList.get(0));
								list = FXCollections.observableArrayList();

								for (int i = 0; i < dataSize; i++) {
									// System.out.print("[DBG]랄라라랄");

									list.add(new Person(new SimpleStringProperty(rcvList1.get(i).substring(0, 5)),
											new SimpleStringProperty(rcvList1.get(i).substring(5, 13).trim()),
											new SimpleStringProperty(rcvList1.get(i).substring(13, 27).trim()),
											new SimpleStringProperty(rcvList1.get(i).substring(27, 51).trim()),
											new SimpleStringProperty(rcvList1.get(i).substring(51, 54).trim()),
											new SimpleStringProperty(rcvList1.get(i).substring(54, 60).trim())));

									idColumn.setCellValueFactory(cellData -> cellData.getValue().getidColumn());
									nameColumn.setCellValueFactory(cellData -> cellData.getValue().getnameColumn());
									callColumn.setCellValueFactory(cellData -> cellData.getValue().getcallColumn());
									dateColumn.setCellValueFactory(cellData -> cellData.getValue().getdateColumn());
									acesColumn.setCellValueFactory(cellData -> cellData.getValue().getacesColumn());
									gateColumn.setCellValueFactory(cellData -> cellData.getValue().getgateColumn());
								}

								tableroom.setItems(list);

								if (tableroom.getColumns().isEmpty() != true) {
									rcvList1.removeAll(rcvList1);
									System.out.printf("[DBG] List remove " + rcvList1.isEmpty() + "\n");
								}
							} else {
								strCnt1 = null;
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("에러");
								alert.setHeaderText("");
								alert.setContentText("[ERR4] ???");
								alert.showAndWait();
							}
							strCnt1 = null;
						}

					}

				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);

				}

			}

		});
		acesCheckT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		acesCheckT.start();
	}

	// 편집로그
	public void editCheck() {

		Thread editCheckT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						if (strCnt2 != null) {

							lbl5.setText(strCnt2);

							int dataSize = Integer.parseInt(strCnt2.trim());
//							System.out.print(rcvList.get(0));
							list2 = FXCollections.observableArrayList();
							if (rcvList2.size() != 0) {
								for (int i = 0; i < dataSize; i++) {
									// System.out.print("[DBG]랄라라랄");

									list2.add(new Person2(new SimpleStringProperty(rcvList2.get(i).substring(0, 5)),
											new SimpleStringProperty(rcvList2.get(i).substring(5, 13).trim()),
											new SimpleStringProperty("사번" + rcvList2.get(i).substring(13, 18).trim()
													+ "을(를) " + rcvList2.get(i).substring(18, 20).trim() + " 했습니다."),
											new SimpleStringProperty(rcvList2.get(i).substring(20, 44).trim())));

									idColumn2.setCellValueFactory(cellData -> cellData.getValue().getidColumn());
									nameColumn2.setCellValueFactory(cellData -> cellData.getValue().getnameColumn());
									editColumn.setCellValueFactory(cellData -> cellData.getValue().geteditColumn());
									dateColumn2.setCellValueFactory(cellData -> cellData.getValue().getdateColumn());

								}
								tableroom2.setItems(list2);
								if (tableroom2.getColumns().isEmpty() != true) {
									rcvList2.removeAll(rcvList2);
									System.out.printf("[DBG] List remove " + rcvList2.isEmpty() + "\n");
								}

							} else {
								strCnt2 = null;
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("에러");
								alert.setHeaderText("");
								alert.setContentText("[ERR4] ???");
								alert.showAndWait();
							}

						}
						strCnt2 = null;
					}

				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(500);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);

				}

			}

		});
		editCheckT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		editCheckT.start();
	}

	// 기준정보
	public void baseCheck() {

		Thread baseCheckT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						if (strCnt3 != null) {

							lbl4.setText(strCnt3);

							int dataSize = Integer.parseInt(strCnt3.trim());
//							System.out.print(rcvList.get(0));
							list3 = FXCollections.observableArrayList();
							if (rcvList3.size() != 0) {
								for (int i = 0; i < dataSize; i++) {
									// System.out.print("[DBG]랄라라랄");

									list3.add(new Person3(

											new SimpleStringProperty(rcvList3.get(i).substring(0, 5)),
											new SimpleStringProperty(rcvList3.get(i).substring(5, 13).trim()),
											new SimpleStringProperty(rcvList3.get(i).substring(13, 27).trim()),
											new SimpleStringProperty(rcvList3.get(i).substring(27, 37).trim()),
											new SimpleStringProperty(rcvList3.get(i).substring(37, 137).trim()),
											new SimpleStringProperty(rcvList3.get(i).substring(137, 237).trim()),
											new SimpleStringProperty(rcvList3.get(i).substring(237, 247).trim())));

									idColumn3.setCellValueFactory(cellData -> cellData.getValue().getidColumn());
									nameColumn3.setCellValueFactory(cellData -> cellData.getValue().getnameColumn());
									callColumn3.setCellValueFactory(cellData -> cellData.getValue().getcallColumn());
									depColumn.setCellValueFactory(cellData -> cellData.getValue().getdepColumn());
									faceColumn.setCellValueFactory(cellData -> cellData.getValue().getfaceColumn());
									fingerColumn.setCellValueFactory(cellData -> cellData.getValue().getfingerColumn());
									dateColumn3.setCellValueFactory(cellData -> cellData.getValue().getdateColumn());
								}
								tableroom3.setItems(list3);

//								tableroom3.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
								if (tableroom3.getItems().isEmpty() != true) {
									rcvList3.removeAll(rcvList3);
									System.out.printf("[DBG] List remove " + rcvList3.isEmpty() + "\n");
								}

							} else {
								strCnt3 = null;
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("에러");
								alert.setHeaderText("");
								alert.setContentText("[ERR4] ???");
								alert.showAndWait();
							}

						}
						strCnt3 = null;
					}

				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(500);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);

				}

			}

		});
		baseCheckT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		baseCheckT.start();
	}

	public void delCheck() {

		Thread delCheckT = new Thread(new Runnable() {

			@Override
			public void run() {
				Runnable updater = new Runnable() {
					@Override
					public void run() {

						if (strTF != null) {

							if (strTF.equals("T")) {
								strTF = null;
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("완료");
								alert.setHeaderText("");
								alert.setContentText("삭제를 완료하였습니다.");
								alert.showAndWait();

//							tableroom3.refresh();

							} else {
								strTF = null;
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("에러");
								alert.setHeaderText("");
								alert.setContentText("삭제를 실패했습니다. 삭제할 정보를 다시 한번 확인하세요.");
								alert.showAndWait();
							}
						}
						strTF = null;
					}
				};

				while (true) {

					if (rcvOut != null) {
						break;
					}

					try {
						Thread.sleep(500);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(updater);

				}

			}

		});
		delCheckT.setDaemon(true); // 메인 스레드보다 먼저 시작되어야 함, 메인 스레드가 종료되면 자동으로 종료됨
		delCheckT.start();
	}

}