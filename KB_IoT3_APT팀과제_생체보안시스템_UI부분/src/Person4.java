import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Person4 {

	public IntegerProperty gateColumn2;
	public StringProperty entColumn;
	public StringProperty exitColumn;

	public Person4(IntegerProperty gateColumn2, StringProperty entColumn, StringProperty exitColumn) {
		super();
		this.gateColumn2 = gateColumn2;
		this.entColumn = entColumn;
		this.exitColumn = exitColumn;

	}

	public IntegerProperty getgateColumn2() {
		return gateColumn2;
	}

	public StringProperty getentColumn() {
		return entColumn;
	}

	public StringProperty getexitColumn() {
		return exitColumn;
	}

}