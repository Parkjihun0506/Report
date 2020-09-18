import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person2 {
	public StringProperty idColumn2;
	public StringProperty nameColumn2;
	public StringProperty editColumn;
	public StringProperty dateColumn2;

	public Person2(StringProperty idColumn, StringProperty nameColumn, StringProperty editColumn,
			StringProperty dateColumn) {
		super();
		this.idColumn2 = idColumn;
		this.nameColumn2 = nameColumn;
		this.editColumn = editColumn;
		this.dateColumn2 = dateColumn;

	}

	public StringProperty getidColumn() {
		return idColumn2;
	}

	public StringProperty getnameColumn() {
		return nameColumn2;
	}

	public StringProperty geteditColumn() {
		return editColumn;
	}

	public StringProperty getdateColumn() {
		return dateColumn2;
	}

}