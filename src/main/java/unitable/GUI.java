package unitable;

import unitable.dataelements.*;
import unitable.filters.CatalogFilter;
import unitable.filters.ClientFilter;
import unitable.filters.OperationFilter;
import unitable.generators.CatalogElementGenerator;
import unitable.generators.ClientElementGenerator;
import unitable.generators.OperationElementGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private JFrame frm;

    private JPanel cardPane;
    private CardLayout cardLayout;

    private JButton showCatalogBtn;
    private JButton showOperationsBtn;
    private JButton showClientsBtn;

    private UniTable catalogTable;
    private UniTable operationTable;
    private UniTable clientsTable;

    public GUI() {
        frm = new JFrame("UniTable");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH, HEIGHT);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2;
        frm.setLocation(xPos, yPos);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel btnPane = new JPanel();
        btnPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        catalogTable = new UniTable(CatalogElement.class, new CatalogFilter());
        operationTable = new UniTable(OperationElement.class, new OperationFilter());
        clientsTable = new UniTable(ClientElement.class, new ClientFilter());

        catalogTable.setContent(CatalogElementGenerator.getCatalogElementList());

        showCatalogBtn = new JButton("Каталог");
        showOperationsBtn = new JButton("Операции");
        showClientsBtn = new JButton("Клиенты");

        btnPane.add(showCatalogBtn);
        btnPane.add(showOperationsBtn);
        btnPane.add(showClientsBtn);

        contentPane.add(btnPane, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPane = new JPanel(cardLayout);
        contentPane.add(cardPane, BorderLayout.CENTER);

        cardPane.add(catalogTable.getVisualComponent(), "CATALOG");
        cardPane.add(operationTable.getVisualComponent(), "OPERATIONS");
        cardPane.add(clientsTable.getVisualComponent(), "CLIENTS");

        cardLayout.show(cardPane, "CATALOG");

        showCatalogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CatalogElement> list = CatalogElementGenerator.getCatalogElementList();
                catalogTable.setContent(list);
                cardLayout.show(cardPane, "CATALOG");
            }
        });

        showOperationsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<OperationElement> list = OperationElementGenerator.getOperationElementList();
                operationTable.setContent(list);
                cardLayout.show(cardPane, "OPERATIONS");
            }
        });

        showClientsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<ClientElement> list = ClientElementGenerator.getClientElementList();
                clientsTable.setContent(list);
                cardLayout.show(cardPane, "CLIENTS");
            }
        });

        frm.setContentPane(contentPane);
    }

    public void showGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frm.setVisible(true);
            }
        });
    }

}
