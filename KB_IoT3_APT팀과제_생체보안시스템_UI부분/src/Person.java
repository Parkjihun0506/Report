import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	public StringProperty idColumn;
	public StringProperty nameColumn;
	public StringProperty callColumn;
	public StringProperty dateColumn;
	public StringProperty acesColumn;
	public StringProperty gateColumn;

	public Person(StringProperty idColumn, StringProperty nameColumn, StringProperty callColumn, StringProperty dateColumn, StringProperty acesColumn,
			StringProperty gateColumn) {
		super();
		this.idColumn = idColumn;
		this.nameColumn = nameColumn;
		this.callColumn = callColumn;
		this.dateColumn = dateColumn;
		this.acesColumn = acesColumn;
		this.gateColumn = gateColumn;
	}

	public StringProperty getidColumn() {
		return idColumn;
	}

	public StringProperty getnameColumn() {
		return nameColumn;
	}

	public StringProperty getdateColumn() {
		return dateColumn;
	}

	public StringProperty getcallColumn() {
		return callColumn;
	}

	public StringProperty getacesColumn() {
		return acesColumn;
	}

	public StringProperty getgateColumn() {
		return gateColumn;
	}

}