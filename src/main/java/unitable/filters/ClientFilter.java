package unitable.filters;

import unitable.UniTable;
import unitable.dataelements.DataElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFilter implements Filter {

    private static final Font mainFont = new Font("Arial", Font.PLAIN, 16);

    private UniTable table;

    private JPanel contentPane;
    private JTextField idField;
    private JTextField nameField;
    private JTextField countField;
    private JButton startBtn;
    private JButton clearBtn;

    public ClientFilter() {
        contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        idField = new JTextField(5);
        idField.setFont(mainFont);

        nameField = new JTextField(20);
        nameField.setFont(mainFont);

        countField = new JTextField(5);
        countField.setFont(mainFont);

        startBtn = new JButton(">");
        clearBtn = new JButton("X");

        contentPane.add(new JLabel("Номер:"));
        contentPane.add(idField);
        contentPane.add(new JLabel("Имя клиента:"));
        contentPane.add(nameField);
        contentPane.add(new JLabel("Сумма покупок:"));
        contentPane.add(countField);
        contentPane.add(startBtn);
        contentPane.add(clearBtn);

        ActionListener commonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBtn.doClick();
            }
        };

        idField.addActionListener(commonListener);
        nameField.addActionListener(commonListener);
        countField.addActionListener(commonListener);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Проверка корректности количества
                if (!checkCountField()) {
                    JOptionPane.showMessageDialog(null, "Введите корректную сумму покупок", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                table.refresh();
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
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
        String nameText = nameField.getText().toLowerCase();
        String countText = countField.getText().toLowerCase();

        String idElement = (element.getField(0) + "").toLowerCase();
        String nameElement = ((String) element.getField(1)).toLowerCase();
        Integer countElement = (Integer) element.getField(2);

        boolean idCheck = true;
        boolean nameCheck = true;
        boolean countCheck = true;

        if (!idText.equals("")) {
            idCheck = (idElement.indexOf(idText) != (-1));
        }
        if (!nameText.equals("")) {
            nameCheck = (nameElement.indexOf(nameText) != (-1));
        }
        if (!countText.equals("")) {
            Integer countValue = Integer.parseInt(countField.getText());
            countCheck = (countElement.compareTo(countValue) <= 0);
        }

        return idCheck && nameCheck && countCheck;
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
