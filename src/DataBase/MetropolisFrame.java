package DataBase;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetropolisFrame extends JFrame {
    private static final int  MAX_INP_SIZE = 10;
    private JPanel headerPanel;
    private JTextField metropolisesFld;
    private JTextField continetsFld;
    private JTextField populFld;
    private JButton addBtn;
    private JButton searchBtn;
    private JComboBox populationValJCb;
    private JComboBox matchJCB;
    private Metropolis mtPolis;
    private JTable dataTable;
    private String DEF_POPJCB_VAL = "Population Larger Than";
    private String DEF_MATHCJCB_VAl = "Exact Match";
    public MetropolisFrame(){
        super("Metropolis Viewer");

        setupHeadSection();
        setupTable();
        setupRightSection();
        addListeners();
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void addListeners() {
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metroInp = metropolisesFld.getText();
                String contInp = continetsFld.getText();
                String populInp = populFld.getText();
                mtPolis.add(metroInp,contInp,populInp);
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metroInp = metropolisesFld.getText();
                String contInp = continetsFld.getText();
                String populInp = populFld.getText();
                Boolean popLargerThan=populationValJCb.getSelectedItem().equals(DEF_POPJCB_VAL);
                Boolean exactMatch=matchJCB.getSelectedItem().equals(DEF_MATHCJCB_VAl);
                mtPolis.search(metroInp,contInp,populInp,popLargerThan,exactMatch);
            }
        });
    }

    private void setupRightSection() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

        JPanel Btns_wrapper = new JPanel();
        GridLayout rightLayout = new GridLayout(4,1);
        rightLayout.setVgap(5);
        rightPanel.setLayout(rightLayout);
        GridLayout handler = new GridLayout(2,1);
        handler.setVgap(5);
        Btns_wrapper.setLayout(handler);
        addBtn = new JButton("ADD");
        Btns_wrapper.add(addBtn);
        searchBtn = new JButton("SEARCH");
        Btns_wrapper.add(searchBtn);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(handler);
        searchPanel.setBorder(new TitledBorder("Search Options"));
        populationValJCb= new JComboBox();
        matchJCB = new JComboBox();
        populationValJCb.addItem(DEF_POPJCB_VAL);
        populationValJCb.addItem("Population Smaller Than or equal to");
        searchPanel.add(populationValJCb);
        matchJCB.addItem(DEF_MATHCJCB_VAl);
        matchJCB.addItem("Partial Match");
        searchPanel.add(matchJCB);
        rightPanel.add(Btns_wrapper);

        rightPanel.add(searchPanel);
        add(rightPanel,BorderLayout.EAST);

    }

    private void setupTable() {
        mtPolis = new Metropolis();
        JPanel tablePnl = new JPanel();
        GridLayout tblGrid = new GridLayout(1,3);
        tablePnl.setLayout(tblGrid);
        dataTable = new JTable(mtPolis);
        JScrollPane scrollable = new JScrollPane(dataTable);
        scrollable.setPreferredSize(new Dimension(300,400));
        tablePnl.add(scrollable);
        add(tablePnl,BorderLayout.CENTER);
    }

    private void setupHeadSection() {
        headerPanel = new JPanel();
        metropolisesFld = new JTextField(MAX_INP_SIZE);
        continetsFld = new JTextField(MAX_INP_SIZE);
        populFld = new JTextField(MAX_INP_SIZE);
        headerPanel.add(new JLabel("Metropolis:"));
        headerPanel.add(metropolisesFld);
        headerPanel.add(new JLabel("Continent:"));
        headerPanel.add(continetsFld);
        headerPanel.add(new JLabel("Population:"));
        headerPanel.add(populFld);
        add(headerPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {

        }
        MetropolisFrame frame = new MetropolisFrame();
    }


}
