import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*; //used for user interface
import javax.swing.text.SimpleAttributeSet; //used for changing fonts
import javax.swing.text.StyleConstants; //text styles
import javax.swing.text.StyledDocument; //text styles
import java.awt.*;
import java.awt.event.ActionListener; //listens for mouse clicks on buttons
import java.awt.event.ActionEvent; //handles button press events

public class GUI {
    JFrame mainframe;
    JPanel mainpanel;
    private JTable veh_tbl, ps_tbl, stat_tbl, add_veh_tbl;
    private JButton add, cancel, exit;

    vehicleManger mg=new vehicleManger();
    public void Invoke() {
        mainframe = new JFrame();
        mainpanel = new JPanel();
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // program to be exited if window is closed
        mainframe.setTitle("Road Intersection Simulation"); // title of main window
        mainframe.setSize(1200, 700); // seting the width and height of the main window
        mainframe.setLocationRelativeTo(null); // position the nframe in the middle of the screen
        mainframe.setLayout(new BorderLayout()); // initiate layout of nframe

        // Creating Tables
        // 1. Vehicle table
        JLabel veh_title = new JLabel("Vehicles");
        veh_title.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel veh_ttl_panel = new JPanel();
        veh_ttl_panel.add(veh_title);
        String[] veh_tbl_head = { "Type", "no", "segment", "Cross time", "Direction", "Status", "Length" };
        Object[][] veh_data = { { "h", "a", "l", "l", "o", "o", "o" }, { "h", "a", "l", "l", "o", "o", "o" } };
        veh_tbl = new JTable(veh_data, veh_tbl_head);
        JScrollPane veh_scroll = new JScrollPane(veh_tbl);
        veh_scroll.setPreferredSize(new Dimension(500, 300));
        JPanel veh_panel = new JPanel(new BorderLayout());
        veh_panel.add(veh_ttl_panel, BorderLayout.NORTH);
        veh_panel.add(veh_scroll, BorderLayout.SOUTH);

        // 2.Phases table
        JLabel ps_title = new JLabel("Phases");
        ps_title.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel ps_ttl_panel = new JPanel();
        ps_ttl_panel.add(ps_title);
        String[] ps_tbl_head = { "Phase", "Duration" };
        Object[][] ps_data = { { "h", "w" }, { "h", "e" } };
        ps_tbl = new JTable(ps_data, ps_tbl_head);
        JScrollPane ps_scroll = new JScrollPane(ps_tbl);
        ps_scroll.setPreferredSize(new Dimension(150, 300));
        JPanel ps_panel = new JPanel(new BorderLayout());
        ps_panel.add(ps_ttl_panel, BorderLayout.NORTH);
        ps_panel.add(ps_scroll, BorderLayout.SOUTH);

        // 3.Statistics table
        JLabel stat_title = new JLabel("Statictics");
        stat_title.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel stat_ttl_panel = new JPanel();
        stat_ttl_panel.add(stat_title);
        String[] stat_tbl_head = { "Segment", "Waiting time", "Waiting Length", "Cross time" };
        Object[][] stat_data = { { "S", "W", "WL", "C" } };
        stat_tbl = new JTable(stat_data, stat_tbl_head);
        JScrollPane stat_scroll = new JScrollPane(stat_tbl);
        stat_scroll.setPreferredSize(new Dimension(420, 150));
        JPanel stat_panel = new JPanel(new BorderLayout());
        stat_panel.add(stat_ttl_panel, BorderLayout.NORTH);
        stat_panel.add(stat_scroll);

        // 4.Add vehicle table
        JLabel add_title = new JLabel("Add Vehicle");
        stat_title.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel add_ttl_panel = new JPanel();
        add_ttl_panel.add(add_title);
        String[] add_tbl_head = { "Type", "No.", "Segment", "Crossing_time", "Direction", "Length","Co2" };
        Object[][] add_data = { { "V", "T", "C", "D", "L", "E","Q" } };
        add_veh_tbl = new JTable(add_data, add_tbl_head);
        JScrollPane add_veh_scroll = new JScrollPane(add_veh_tbl);
        add_veh_scroll.setPreferredSize(new Dimension(400, 300));
        JPanel add_veh_panel = new JPanel(new BorderLayout());
        add_veh_panel.add(add_ttl_panel, BorderLayout.NORTH);
        add_veh_panel.add(add_veh_scroll, BorderLayout.SOUTH);

        // Co2 label & Textboxes
        JLabel co2 = new JLabel("CO2 Emission : ");
        co2.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel kg = new JLabel("kg");
        JTextField val = new JTextField();
        JPanel co2_ttl_panel = new JPanel();
        co2_ttl_panel.add(co2, BorderLayout.NORTH);
        co2_ttl_panel.add(val);
        co2_ttl_panel.add(kg, BorderLayout.SOUTH);
        stat_panel.add(co2_ttl_panel, BorderLayout.SOUTH);

        // Adding all panels to the mainpanel
        JPanel content = new JPanel();
        content.add(veh_panel, BorderLayout.NORTH);
        content.add(ps_panel, BorderLayout.NORTH);
        content.add(stat_panel, BorderLayout.NORTH);

        JPanel add_veh = new JPanel();
        add_veh.add(add_veh_panel);
        JPanel all_content = new JPanel();
        all_content.add(content);
        all_content.add(add_veh, BorderLayout.SOUTH);

        mainpanel.add(all_content, BorderLayout.NORTH);

        // Creating Buttons
        add = new JButton("Add");
        cancel = new JButton("Cancel");
        exit = new JButton("Exit");

        // Seperate panel for Buttons
        JPanel buttons_panel = new JPanel(new FlowLayout());

        // Adding buttons to the buttons panel
        buttons_panel.add(add, BorderLayout.WEST);
        buttons_panel.add(cancel, BorderLayout.CENTER);
        buttons_panel.add(exit, BorderLayout.EAST);

        mainframe.add(buttons_panel, BorderLayout.SOUTH);

        mainframe.add(new JScrollPane(mainpanel));

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

                mg.add_Vehicle_gui(table_data_0, Integer.parseInt(table_data_1), table_data_2.charAt(0),Double.parseDouble(table_data_3), table_data_4.charAt(0), Double.parseDouble(table_data_5),Double.parseDouble(table_data_6));

                

            }

            // Function that returns data from specified position
            private String GetData(JTable add_veh_tbl, int i, int j) {
                return String.valueOf(add_veh_tbl.getModel().getValueAt(i, j));
            }
        });

        mainframe.setVisible(true);
    }
}