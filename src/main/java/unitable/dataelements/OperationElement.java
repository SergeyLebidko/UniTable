package unitable.dataelements;

import java.time.LocalDate;

@Description(columnNames = {"Номер", "Дата", "Наименование", "Количество"})
public class OperationElement implements DataElement {

    private int id;
    private LocalDate date;
    private String name;
    private int count;

    public OperationElement(int id, LocalDate date, String name, int count) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.count = count;
    }

    @Override
    public Object getField(int fieldIndex) {
        if (fieldIndex == 0) return id;
        if (fieldIndex == 1) return date;
        if (fieldIndex == 2) return name;
        if (fieldIndex == 3) return count;
        return null;
    }

}
