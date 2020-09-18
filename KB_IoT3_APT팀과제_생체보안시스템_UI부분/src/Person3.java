import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Person3 {

	public StringProperty idColumn3;
	public StringProperty nameColumn3;
	public StringProperty callColumn3;
	public StringProperty depColumn;
	public StringProperty faceColumn;
	public StringProperty fingerColumn;
	public StringProperty dateColumn3;
	public BooleanProperty chkColumn;

	public Person3(//BooleanProperty chkColumn, 
			StringProperty idColumn, StringProperty nameColumn,
			StringProperty callColumn, StringProperty depColumn, StringProperty faceColumn, StringProperty fingerColumn,
			StringProperty dateColumn) {
		super();
		this.chkColumn = chkColumn;
		this.idColumn3 = idColumn;
		this.nameColumn3 = nameColumn;
		this.callColumn3 = callColumn;
		this.dateColumn3 = dateColumn;
		this.depColumn = depColumn;
		this.faceColumn = faceColumn;
		this.fingerColumn = fingerColumn;
	}

	public BooleanProperty getchkColumn() {
		return chkColumn;
	}

	public StringProperty getidColumn() {
		return idColumn3;
	}

	public StringProperty getnameColumn() {
		return nameColumn3;
	}

	public StringProperty getdateColumn() {
		return dateColumn3;
	}

	public StringProperty getcallColumn() {
		return callColumn3;
	}

	public StringProperty getdepColumn() {
		return depColumn;
	}

	public StringProperty getfaceColumn() {
		return faceColumn;
	}

	public StringProperty getfingerColumn() {
		return fingerColumn;
	}

}