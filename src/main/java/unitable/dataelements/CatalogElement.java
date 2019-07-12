package unitable.dataelements;

@Description(columnNames = {"Номер", "Наименование"})
public class CatalogElement implements DataElement {

    private int id;
    private String name;

    public CatalogElement(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Object getField(int fieldIndex) {
        if (fieldIndex == 0) return id;
        if (fieldIndex == 1) return name;
        return null;
    }

}
