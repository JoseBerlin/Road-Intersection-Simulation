import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*; //used for user interface
import javax.swing.text.SimpleAttributeSet; //used for changing fonts
import javax.swing.text.StyleConstants; //text styles
import javax.swing.text.StyledDocument; //text styles
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener; //listens for mouse clicks on buttons
import java.awt.event.ActionEvent; //handles button press events

public class GUI {
    JFrame mainframe;
    JPanel mainpanel;
    private JTable veh_tbl, ps_tbl, stat_tbl, add_veh_tbl;
    private JButton add, cancel, exit, add_pedestrian;

    vehicleManger mg = new vehicleManger();

    public void Invoke() {
        mainframe = new JFrame();
        mainpanel = new JPanel();
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // program to be exited if window is closed
        mainframe.setTitle("Road Intersection Simulation"); // title of main window
        mainframe.setSize(1200, 700); // seting the width and height of the main window
        mainframe.setLocationRelativeTo(null); // position the nframe in the middle of the screen
        mainframe.setLayout(new BorderLayout()); // initiate layout of nframe

        // Creating Tables
        String[] veh_tbl_head = { "Type", "no", "segment", "Cross time", "Direction", "Status", "Length" };
        Object[][] veh_data = { { "h", "a", "l", "l", "o", "o", "a" }, { "n", "a", "l", "l", "o", "o", "o" } };
        veh_tbl = new JTable(veh_data, veh_tbl_head);
        veh_tbl.getTableHeader().setReorderingAllowed(false); // setting coumn reordering order as false
        veh_tbl.setAutoCreateRowSorter(true); // setting row sorting order as true for the table
        veh_tbl.setEnabled(false); // Setting the table's data non editable
        JScrollPane veh_scroll = new JScrollPane(veh_tbl); // creating the scrollable pane for table
        veh_scroll.setPreferredSize(new Dimension(500, 300));
        veh_scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Vehicles", TitledBorder.CENTER,
                TitledBorder.CENTER));

        // A panel to contain North segment
        JPanel North_panel = new JPanel();
        North_panel.setPreferredSize(new Dimension(0, 450));
        North_panel.add(veh_scroll, BorderLayout.WEST);

        // 2.Phases table
        String[] ps_tbl_head = { "Phase", "Duration" };
        Object[][] ps_data = { { "h", "w" }, { "h", "e" } };
        ps_tbl = new JTable(ps_data, ps_tbl_head);
        ps_tbl.getTableHeader().setReorderingAllowed(false);
        ps_tbl.setAutoCreateRowSorter(true);
        ps_tbl.setEnabled(false);
        JScrollPane ps_scroll = new JScrollPane(ps_tbl);
        ps_scroll.setPreferredSize(new Dimension(200, 140));
        ps_scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Phases", TitledBorder.CENTER,
                TitledBorder.CENTER));
        North_panel.add(ps_scroll, BorderLayout.CENTER);

        // 3.Statistics table
        String[] stat_tbl_head = { "Segment", "Waiting time", "Waiting Length", "Cross time" };
        Object[][] stat_data = { { "S", "W", "WL", "C" } };
        stat_tbl = new JTable(stat_data, stat_tbl_head);
        stat_tbl.getTableHeader().setReorderingAllowed(false);
        stat_tbl.setEnabled(false);
        stat_tbl.setAutoCreateRowSorter(true); // enables sorting
        JScrollPane stat_scroll = new JScrollPane(stat_tbl);
        stat_scroll.setPreferredSize(new Dimension(450, 150));
        stat_scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Statistics", TitledBorder.CENTER,
                TitledBorder.CENTER));

        // Co2 label & Textboxes
        JLabel co2 = new JLabel("CO2 Emission : ");
        co2.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel kg = new JLabel("kg");
        JTextField val = new JTextField();
        JPanel co2_panel = new JPanel();
        co2_panel.add(co2, BorderLayout.WEST);
        co2_panel.add(val, BorderLayout.CENTER);
        co2_panel.add(kg, BorderLayout.EAST);

        // Adding stat panel and co2 panel
        JPanel stat_panel = new JPanel();
        stat_panel.add(stat_scroll, BorderLayout.NORTH);

        North_panel.add(stat_panel, BorderLayout.EAST);
        North_panel.add(co2_panel, BorderLayout.SOUTH);

        // 4.Add vehicle table
        String[] add_tbl_head = { "Type", "No.", "Segment", "Crossing_time", "Direction", "Length", "Co2" };
        Object[][] add_data = { { "V", "T", "C", "D", "L", "E", "Q" } };
        add_veh_tbl = new JTable(add_data, add_tbl_head);
        JScrollPane add_veh_scroll = new JScrollPane(add_veh_tbl);
        add_veh_scroll.setPreferredSize(new Dimension(700, 39));
        JPanel Centre_panel = new JPanel();
        Centre_panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), "Add Vehicle", TitledBorder.CENTER,
                TitledBorder.CENTER));
        Centre_panel.add(add_veh_scroll, BorderLayout.SOUTH);

        // Creating Buttons
        add = new JButton("Add");
        cancel = new JButton("Cancel");
        exit = new JButton("Exit");
        add_pedestrian = new JButton("Add Pedestrians");

        // Seperate panel for Buttons
        JPanel South_panel = new JPanel(new FlowLayout());
        South_panel.setPreferredSize(new Dimension(5000, 50));

        // Adding buttons to the buttons panel
        South_panel.add(add, BorderLayout.WEST);
        South_panel.add(cancel, BorderLayout.CENTER);
        South_panel.add(exit, BorderLayout.EAST);

        // Adding panels to mainframe
        mainframe.add(North_panel, BorderLayout.NORTH);
        mainframe.add(Centre_panel, BorderLayout.CENTER);
        mainframe.add(South_panel, BorderLayout.SOUTH);

        // Adding vehicles from gui
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String table_data_0 = GetData(add_veh_tbl, 0, 0);
                String table_data_1 = GetData(add_veh_tbl, 0, 1);
                String table_data_2 = GetData(add_veh_tbl, 0, 2);
                String table_data_3 = GetData(add_veh_tbl, 0, 3);
                String table_data_4 = GetData(add_veh_tbl, 0, 4);
                String table_data_5 = GetData(add_veh_tbl, 0, 5);
                String table_data_6 = GetData(add_veh_tbl, 0, 6);

                mg.add_Vehicle_gui(table_data_0, Integer.parseInt(table_data_1), table_data_2.charAt(0),
                        Double.parseDouble(table_data_3), table_data_4.charAt(0), Double.parseDouble(table_data_5),
                        Double.parseDouble(table_data_6));

            }

            // Function that returns data from specified position
            private String GetData(JTable add_veh_tbl, int i, int j) {
                return String.valueOf(add_veh_tbl.getModel().getValueAt(i, j));
            }
        });

        mainframe.setVisible(true);
    }
}