import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;

import javax.swing.*; //used for user interface
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener; //listens for mouse clicks on buttons
import java.awt.event.ActionEvent; //handles button press events

public class GUI implements Observer {
        JFrame mainframe;
        JPanel mainpanel;
        private DefaultTableModel tableModel, statModel, psModel;
        private JTable veh_tbl, ps_tbl, stat_tbl;
        JTable add_veh_tbl;
        private JButton add, exit, add_pedestrian, delete;
        Collection<Vehicle> vehicleList;
        JLabel val;

        public void invoke() {

                mainframe = new JFrame();
                mainpanel = new JPanel();
                mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // program to be exited if window is
                                                                                   // closed
                mainframe.setTitle("Road Intersection Simulation"); // title of main window
                mainframe.setSize(1200, 700); // seting the width and height of the main window
                mainframe.setLocationRelativeTo(null); // position the nframe in the middle of the screen
                mainframe.setLayout(new BorderLayout()); // initiate layout of nframe

                // Creating Tables
                String[] veh_tbl_head = { "Type", "no", "segment", "Cross time", "Direction", "Status", "Length" };

                tableModel = new DefaultTableModel();
                tableModel.setColumnIdentifiers(veh_tbl_head);

                veh_tbl = new JTable();
                veh_tbl.setModel(tableModel);

                veh_tbl.getTableHeader().setReorderingAllowed(false); // setting coumn reordering order as false
                veh_tbl.setAutoCreateRowSorter(true); // setting row sorting order as true for the table
                veh_tbl.setEnabled(false); // Setting the table's data non-editable
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
                psModel = new DefaultTableModel();
                psModel.setColumnIdentifiers(ps_tbl_head);
                ps_tbl = new JTable();
                ps_tbl.setModel(psModel);
                ps_tbl.getTableHeader().setReorderingAllowed(false);
                ps_tbl.setEnabled(false);
                JScrollPane ps_scroll = new JScrollPane(ps_tbl);
                ps_scroll.setPreferredSize(new Dimension(200, 140));
                ps_scroll.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createEtchedBorder(), "Phases", TitledBorder.CENTER,
                                TitledBorder.CENTER));
                North_panel.add(ps_scroll, BorderLayout.CENTER);

                // 3.Statistics table
                String[] stat_tbl_head = { "Segment", "Waiting time", "Waiting Length",
                                "Cross time" };
                statModel = new DefaultTableModel();
                statModel.setColumnIdentifiers(stat_tbl_head);
                stat_tbl = new JTable();
                stat_tbl.setModel(statModel);

                stat_tbl.getTableHeader().setReorderingAllowed(false);
                stat_tbl.setEnabled(false);
                stat_tbl.setAutoCreateRowSorter(true); // enables sorting
                JScrollPane stat_scroll = new JScrollPane(stat_tbl);
                stat_scroll.setPreferredSize(new Dimension(450, 150));
                stat_scroll.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createEtchedBorder(), "Statistics", TitledBorder.CENTER,
                                TitledBorder.CENTER));

                // Co2 label & Text boxes
                JLabel co2 = new JLabel("CO2 Emission : ");
                co2.setFont(new Font("Arial", Font.BOLD, 16));
                JLabel kg = new JLabel("kg");
                val = new JLabel();
                String tco2 = "0";
                val.setText(tco2);
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
                Object[][] add_data = { { "car", "23232", "E", "32.9", "N", "12.0", "32.0" } };
                JComboBox<String> optbox = new JComboBox<String>();
                optbox.addItem("car");
                optbox.addItem("bike");
                optbox.addItem("bus");
                optbox.addItem("truck");
                add_veh_tbl = new JTable(add_data, add_tbl_head);
                TableColumn fcol = add_veh_tbl.getColumnModel().getColumn(0);
                fcol.setCellEditor(new DefaultCellEditor(optbox));
                JScrollPane add_veh_scroll = new JScrollPane(add_veh_tbl);
                add_veh_scroll.setPreferredSize(new Dimension(700, 39));
                JPanel Centre_panel = new JPanel();
                Centre_panel.setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(), "Add Vehicle", TitledBorder.CENTER,
                                TitledBorder.CENTER));
                Centre_panel.add(add_veh_scroll, BorderLayout.SOUTH);

                // Creating Buttons
                add = new JButton("Add");
                exit = new JButton("Exit");
                add_pedestrian = new JButton("Add Pedestrians");
                delete = new JButton("Delete");

                // Separate panel for Buttons
                JPanel South_panel = new JPanel(new FlowLayout());
                South_panel.setPreferredSize(new Dimension(5000, 50));

                // Adding buttons to the buttons panel
                South_panel.add(add, BorderLayout.WEST);
                South_panel.add(delete, BorderLayout.CENTER);
                South_panel.add(exit, BorderLayout.EAST);
                South_panel.add(add_pedestrian, BorderLayout.EAST);

                // Adding panels to mainframe
                mainframe.add(North_panel, BorderLayout.NORTH);
                mainframe.add(Centre_panel, BorderLayout.CENTER);
                mainframe.add(South_panel, BorderLayout.SOUTH);

                // Adding vehicles from gui

                // Handling JFrame close Button
                exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                int confriming = JOptionPane.showConfirmDialog(null,
                                                "Are you sure you want to exit the program?", "Exit message",
                                                JOptionPane.YES_NO_OPTION);

                                if (confriming == JOptionPane.YES_OPTION) {

                                        try {
                                                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                                                        Logger.getInstance().writeLogsToFile("log.txt");
                                                }));
                                                FileWriter writer = new FileWriter("report.txt");
                                                writer.write(getContent(veh_tbl.getModel(), ps_tbl.getModel(),
                                                                stat_tbl.getModel(), val.getText()));
                                                writer.close();
                                                JOptionPane.showMessageDialog(null,
                                                                "The simulation report has been generated",
                                                                "Report",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                System.exit(0);
                                        } catch (IOException ex) {
                                                ex.printStackTrace();
                                        }
                                }
                        }
                });

        }

        // value error exception
        public void wrongValue(Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Wrong Value", 0);
        }

        /**
         * Function to get Vehicle Data from vehicle.csv
         */
        public void update(Observable obs, Object arg) {

                Queue<Vehicle> upvehicle;
                Double dumv;
                if (obs instanceof VehicleModal) {
                        if (arg instanceof Map) {

                                Map<?, ?> allvehicle = (Map<?, ?>) arg;

                                if (allvehicle.values().iterator().next() instanceof Queue<?>) {
                                        tableModel.setRowCount(0);
                                        for (Entry<?, ?> veh : allvehicle.entrySet()) {

                                                upvehicle = (Queue<Vehicle>) veh.getValue();

                                                for (Vehicle vehicle : upvehicle) {

                                                        Object[] rowData = { vehicle.getType(), vehicle.getPlate_no(),
                                                                        vehicle.getIn_segment(),
                                                                        vehicle.getCrossing_time(),
                                                                        vehicle.getDirection_to(),
                                                                        vehicle.isCrossed(),
                                                                        vehicle.getLength(),
                                                                        vehicle.getCo2_emission() };
                                                        tableModel.addRow(rowData);

                                                }
                                        }
                                } else if (allvehicle.values().iterator().next() instanceof List<?>) {

                                        statModel.setRowCount(0);
                                        for (Entry<?, ?> stat : allvehicle.entrySet()) {
                                                List<Double[]> sval = (List<Double[]>) stat.getValue();
                                                Double[] dd = sval.get(0);

                                                Object[] rowData = { stat.getKey(), dd[0] + " s", dd[1],
                                                                dd[2] + " s" };
                                                statModel.addRow(rowData);

                                        }
                                }

                        }

                        if (arg instanceof ArrayList) {
                                dumv = 0.0;
                                ArrayList<Double[]> inter = (ArrayList<Double[]>) arg;
                                psModel.setRowCount(0);
                                for (Double[] intersection : inter) {
                                        double dvl = intersection[1] - dumv;
                                        Object[] rowData = { intersection[0].intValue(),
                                                        Double.parseDouble(String.format("%.2f", dvl)) };
                                        dumv = intersection[1];
                                        psModel.addRow(rowData);
                                }
                        }

                        if (arg instanceof Double) {

                                Double inter = (Double) arg;
                                inter = inter / 1000;
                                String dd = inter.toString();

                                val.setText(String.format("%.2f", inter));
                        }

                }

        }

        public void show() {
                mainframe.setVisible(true);
        }

        /**
         * Function to get Content for the Report.
         * 
         * @param vehicles
         * @param phases
         * @param statistics
         * @param co2
         * @return String
         */
        public String getContent(TableModel vehicles, TableModel phases, TableModel statistics, String co2) {
                String content = "\t\t\tROAD SIMULATION REPORT\n\t\t\t~~~~ ~~~~~~~~~~ ~~~~~~\n\n";

                content += "Number of Vehicles Crossed during Simulation" + "\t:\t" +
                                get_veh_count(vehicles) + " Vehicles\n\n";
                content += "\t\t\tStatistics of each Segment\n\t\t\t---------- -- ---- -------\n";
                content += String.format("\n%s\n", getstat(statistics));

                content += String.format("Average Waiting Time to cross\t:\t%s Seconds\n\n", getavg_wait(statistics));
                content += String.format("Total CO2 Emmision during the simulation \t\t:\t%s KG\n", co2.toString());
                return content;

        }

        /**
         * Function to get vehicle count.
         * 
         * @param mytbl
         * @return String
         */
        public String get_veh_count(TableModel mytbl) {
                int c_veh_count = 0; // Crossed vehicle count
                for (int i = 0; i < mytbl.getRowCount(); i++) {
                        if (mytbl.getValueAt(i, 5).toString().equals("crossed")) // Checking whether the vehicle has
                                                                                 // been crossed
                        {
                                c_veh_count += 1;
                        }
                }
                return Integer.toString(c_veh_count);
        }

        private String getstat(TableModel statistics) {
                String Content = "Segment\t\t" + "Total Waiting Time\t"
                                + "Total Waiting length(vehicles)\t"
                                + "Total Crosstime";
                Content += "\n*******\t\t" + "******************\t"
                                + "******************************\t"
                                + "***************";
                String seg = null;
                if (statistics.getRowCount() > 0) {
                        for (int i = 0; i < statistics.getRowCount(); i++) {
                                if (statistics.getValueAt(i, 0).equals('S'))
                                        seg = "South";
                                else if (statistics.getValueAt(i, 0).equals('E'))
                                        seg = "East";
                                else if (statistics.getValueAt(i, 0).equals('N'))
                                        seg = "North";
                                else if (statistics.getValueAt(i, 0).equals('W'))
                                        seg = "West";
                                Content += String.format("\n%-7s %18s %20s %24s\n", seg,
                                                statistics.getValueAt(i, 1).toString(),
                                                statistics.getValueAt(i, 2).toString(),
                                                statistics.getValueAt(i, 3).toString());
                        }
                } else
                        Content = "\tSimulation did not run long enough to produce statistics\n";
                return Content;
        }

        private Object getavg_wait(TableModel statistics) {
                double val = 0.0;
                for (int i = 0; i < statistics.getRowCount(); i++) {
                        val += Double.parseDouble(statistics.getValueAt(i, 1).toString().split(" ")[0]);
                }
                val = val / statistics.getRowCount();
                return val;
        }

        public JButton getAddButton() {
                return add;
        }

        public JButton getPedestrian(){
                return add_pedestrian;
        }

        public String[] getTableinfo() throws noSegmentException {

                String table_data_0 = GetData(add_veh_tbl, 0, 0);
                String table_data_1 = GetData(add_veh_tbl, 0, 1);
                String table_data_2 = GetData(add_veh_tbl, 0, 2);
                String table_data_3 = GetData(add_veh_tbl, 0, 3);
                String table_data_4 = GetData(add_veh_tbl, 0, 4);
                String table_data_5 = GetData(add_veh_tbl, 0, 5);
                String table_data_6 = GetData(add_veh_tbl, 0, 6);

                table_data_0.getClass().equals(String.class);

                String[] sp = new String[] { table_data_0, table_data_1, table_data_2, table_data_3, table_data_4,
                                table_data_5, table_data_6 };
                return sp;

        }

        // Function that returns data from specified position
        public String GetData(JTable add_veh_tbl, int i, int j) {
                return String.valueOf(add_veh_tbl.getModel().getValueAt(i, j));
        }

}