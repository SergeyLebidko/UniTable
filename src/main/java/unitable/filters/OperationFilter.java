package unitable.filters;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import unitable.UniTable;
import unitable.dataelements.DataElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class OperationFilter implements Filter {

    private static final Font mainFont = new Font("Arial", Font.PLAIN, 16);

    private UniTable table;

    private JPanel contentPane;
    private JTextField idField;
    private DatePicker dateField;
    private JTextField nameField;
    private JTextField countField;
    private JButton startBtn;
    private JButton clearBtn;

    public OperationFilter() {
        contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        idField = new JTextField(5);
        idField.setFont(mainFont);

        dateField = new DatePicker();
        dateField.setDateToToday();
        dateField.getComponentDateTextField().setEditable(false);

        nameField = new JTextField(20);
        nameField.setFont(mainFont);

        countField = new JTextField(5);
        countField.setFont(mainFont);

        startBtn = new JButton(">");
        clearBtn = new JButton("X");

        contentPane.add(new JLabel("Номер:"));
        contentPane.add(idField);
        contentPane.add(new JLabel("Дата:"));
        contentPane.add(dateField);
        contentPane.add(new JLabel("Наименование:"));
        contentPane.add(nameField);
        contentPane.add(new JLabel("Количество:"));
        contentPane.add(countField);
        contentPane.add(startBtn);
        contentPane.add(clearBtn);

        ActionListener commonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBtn.doClick();
            }
        };

        dateField.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                startBtn.doClick();
            }
        });

        idField.addActionListener(commonListener);
        nameField.addActionListener(commonListener);
        countField.addActionListener(commonListener);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Проверка корректности количества
                if (!checkCountField()) {
                    JOptionPane.showMessageDialog(null, "Введите корректное значение количества", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                table.refresh();
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                dateField.setDateToToday();
                nameField.setText("");
                countField.setText("");
                table.refresh();
            }
        });
    }

    public void setTable(UniTable table) {
        this.table = table;
    }

    @Override
    public JPanel getVisualComponent() {
        return contentPane;
    }

    @Override
    public boolean check(DataElement element) {
        String idText = idField.getText().toLowerCase();
        LocalDate date = dateField.getDate();
        String nameText = nameField.getText().toLowerCase();
        String countText = countField.getText().toLowerCase();

        String idElement = (element.getField(0) + "").toLowerCase();
        LocalDate dateElement = (LocalDate) element.getField(1);
        String nameElement = ((String) element.getField(2)).toLowerCase();
        Integer countElement = (Integer) element.getField(3);

        boolean idCheck = true;
        boolean dateCheck = true;
        boolean nameCheck = true;
        boolean countCheck = true;

        if (!idText.equals("")) {
            idCheck = (idElement.indexOf(idText) != (-1));
        }
        if (date != null) {
            dateCheck = (dateElement.compareTo(date) <= 0);
        }
        if (!nameText.equals("")) {
            nameCheck = (nameElement.indexOf(nameText) != (-1));
        }
        if (!countText.equals("")) {
            Integer countValue = Integer.parseInt(countField.getText());
            countCheck = (countElement.compareTo(countValue) <= 0);
        }

        return idCheck && dateCheck && nameCheck && countCheck;
    }

    private boolean checkCountField() {
        if (countField.getText().equals("")) return true;
        try {
            Integer.parseInt(countField.getText());
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

}
