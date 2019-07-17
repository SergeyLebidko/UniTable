package unitable.filters;

import unitable.UniTable;
import unitable.dataelements.DataElement;

import javax.swing.*;

public interface Filter {

    void setTable(UniTable table);

    JPanel getVisualComponent();

    boolean check(DataElement element);

}
