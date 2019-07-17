package unitable;

import unitable.dataelements.DataElement;
import unitable.dataelements.Description;
import unitable.filters.Filter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UniTable {

    private static final int rowHeight = 40;
    private static final Color gridColor = Color.LIGHT_GRAY;
    private static final Color headerColor = new Color(230, 230, 230);
    private static final Color evenCellsColor = new Color(240, 240, 240);
    private static final Color notEvenCellsColor = new Color(255, 255, 255);
    private static final Font mainFont = new Font("Arial", Font.PLAIN, 16);

    private static final int TO_UP = 1;
    private static final int TO_DOWN = -1;

    private JPanel contentPane;

    private JTable table;
    private Model model;
    private CellRenderer cellRenderer;
    private HeaderRenderer headerRenderer;
    private ElementsComparator elementsComparator;
    private Filter elementsFilter;

    private ArrayList<DataElement> content;
    private ArrayList<DataElement> checkedContent;

    private int sortOrder;
    private int sortedColumn;

    private class ElementsComparator implements Comparator<DataElement> {

        @Override
        public int compare(DataElement o1, DataElement o2) {
            Comparable c1 = (Comparable) o1.getField(sortedColumn);
            Comparable c2 = (Comparable) o2.getField(sortedColumn);
            return sortOrder * c1.compareTo(c2);
        }

    }

    private class Model extends AbstractTableModel {

        private String[] columnNames;

        private int rowCount;
        private int columnCount;

        public Model(String[] columnNames) {
            this.columnNames = columnNames;
            this.columnCount = columnNames.length;
            rowCount = 0;
        }

        public void refresh() {
            content.sort(elementsComparator);
            createCheckedContentList();
            rowCount = checkedContent.size();
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return checkedContent.get(rowIndex).getField(columnIndex);
        }

    }

    private class CellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lab = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            lab.setFont(mainFont);
            if (!isSelected) {
                if ((row % 2) == 0) {
                    lab.setBackground(evenCellsColor);
                }
                if ((row % 2) != 0) {
                    lab.setBackground(notEvenCellsColor);
                }
            }
            return lab;
        }

    }

    private class HeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lab = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == sortedColumn) {
                String text = lab.getText();
                if (sortOrder == TO_UP) {
                    text = (char) 708 + " " + text;
                }
                if (sortOrder == TO_DOWN) {
                    text = (char) 709 + " " + text;
                }
                lab.setText(text);
            }

            lab.setFont(mainFont);
            lab.setBackground(headerColor);
            lab.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            lab.setHorizontalAlignment(SwingConstants.CENTER);
            return lab;
        }

    }

    public UniTable(Class<? extends DataElement> dataClass, Filter filter) {
        content = new ArrayList<>();
        checkedContent = new ArrayList<>();
        elementsComparator = new ElementsComparator();
        sortedColumn = 0;
        sortOrder = TO_UP;
        elementsFilter = null;

        //Создаем таблицу и панель, в которую она будет помещена
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        Description descriptionAnno = dataClass.getAnnotation(Description.class);
        String[] columnNames = descriptionAnno.columnNames();

        model = new Model(columnNames);
        cellRenderer = new CellRenderer();
        headerRenderer = new HeaderRenderer();
        table = new JTable(model);
        table.setDefaultRenderer(Object.class, cellRenderer);
        table.getTableHeader().setDefaultRenderer(headerRenderer);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setGridColor(gridColor);
        table.setRowHeight(rowHeight);
        table.setShowVerticalLines(false);

        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        //Если передан фильтр, то выполняем дополнительные действия по его настройке
        if (filter != null) {
            elementsFilter = filter;
            elementsFilter.setTable(this);
            contentPane.add(elementsFilter.getVisualComponent(), BorderLayout.NORTH);
        }

        //Создаем обработчик щелчка по заголовку столбца
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 & e.getButton() == MouseEvent.BUTTON1) {
                    int nextCortedColumn = table.getTableHeader().columnAtPoint(e.getPoint());
                    int nextSortOrder = sortOrder;
                    if (nextCortedColumn == sortedColumn) {
                        if (sortOrder == TO_UP) {
                            nextSortOrder = TO_DOWN;
                        }
                        if (sortOrder == TO_DOWN) {
                            nextSortOrder = TO_UP;
                        }
                    }
                    sortedColumn = nextCortedColumn;
                    sortOrder = nextSortOrder;
                    table.getTableHeader().repaint();
                    model.refresh();
                }
            }
        });
    }

    public JPanel getVisualComponent() {
        return contentPane;
    }

    public void setContent(List<? extends DataElement> list) {
        content.clear();
        content.addAll(list);
        refresh();
    }

    public void refresh() {
        model.refresh();
    }

    public DataElement getSelectedItem() {
        int selectedIndex = table.getSelectedRow();
        if (selectedIndex == (-1)) return null;
        return checkedContent.get(selectedIndex);
    }

    private void createCheckedContentList() {
        checkedContent.clear();
        if (elementsFilter == null) {
            checkedContent.addAll(content);
            return;
        }
        if (elementsFilter != null) {
            for (DataElement element : content) {
                if (elementsFilter.check(element)) {
                    checkedContent.add(element);
                }
            }
        }
    }

}
