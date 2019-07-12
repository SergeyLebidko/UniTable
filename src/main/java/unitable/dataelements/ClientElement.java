package unitable.dataelements;

@Description(columnNames = {"Номер", "ФИО", "Сумма покупок"})
public class ClientElement implements DataElement {

    private int id;
    private String name;
    private int amount;

    public ClientElement(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    @Override
    public Object getField(int fieldIndex) {
        if (fieldIndex == 0) return id;
        if (fieldIndex == 1) return name;
        if (fieldIndex == 2) return amount;
        return null;
    }

}
