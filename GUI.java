import java.io.*;
import java.util.*;
import java.util.Queue;
import javax.swing.*; //used for user interface
import javax.swing.text.SimpleAttributeSet; //used for changing fonts
import javax.swing.text.StyleConstants; //text styles
import javax.swing.text.StyledDocument; //text styles
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener; //listens for mouse clicks on buttons
import java.awt.event.ActionEvent; //handles button press events

public class GUI {
        JFrame mainframe;
        JPanel mainpanel;
        private DefaultTableModel tableModel, statModel;
        private JTable veh_tbl, ps_tbl, stat_tbl, add_veh_tbl;
        private JButton add, cancel, exit, add_pedestrian;
        Collection<Vehicle> vehicleList;

        vehicleManger mg;

        public void invoke(vehicleManger vehicle) {
                mg = vehicle;

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
                getVehicleData();
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
                Object[][] ps_data = getPhaseData();
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
                statModel = new DefaultTableModel();
                statModel.setColumnIdentifiers(stat_tbl_head);
                stat_tbl = new JTable();
                stat_tbl.setModel(statModel);
                setStatData();
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

                JLabel val = new JLabel();

                String tco2 = mg.calCo2() + "";
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
                JComboBox optbox = new JComboBox<>();
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
                cancel = new JButton("Cancel");
                exit = new JButton("Exit");
                add_pedestrian = new JButton("Add Pedestrians");

                // Separate panel for Buttons
                JPanel South_panel = new JPanel(new FlowLayout());
                South_panel.setPreferredSize(new Dimension(5000, 50));

                // Adding buttons to the buttons panel
                South_panel.add(add, BorderLayout.WEST);
                South_panel.add(cancel, BorderLayout.CENTER);
                South_panel.add(exit, BorderLayout.EAST);
                South_panel.add(add_pedestrian, BorderLayout.EAST);

                // Adding panels to mainframe
                mainframe.add(North_panel, BorderLayout.NORTH);
                mainframe.add(Centre_panel, BorderLayout.CENTER);
                mainframe.add(South_panel, BorderLayout.SOUTH);

                // Adding vehicles from gui
                add.addActionListener(new ActionListener() {
                        Scanner testtype = new Scanner(System.in);

                        public void actionPerformed(ActionEvent e) {

                                try {
                                        String table_data_0 = GetData(add_veh_tbl, 0, 0);
                                        String table_data_1 = GetData(add_veh_tbl, 0, 1);
                                        String table_data_2 = GetData(add_veh_tbl, 0, 2);
                                        String table_data_3 = GetData(add_veh_tbl, 0, 3);
                                        String table_data_4 = GetData(add_veh_tbl, 0, 4);
                                        String table_data_5 = GetData(add_veh_tbl, 0, 5);
                                        String table_data_6 = GetData(add_veh_tbl, 0, 6);

                                        table_data_0.getClass().equals(String.class);
                                        mg.add_Vehicle_gui(table_data_0, Integer.parseInt(table_data_1),
                                                        table_data_2.charAt(0),
                                                        Double.parseDouble(table_data_3), table_data_4.charAt(0),
                                                        Double.parseDouble(table_data_5),
                                                        Double.parseDouble(table_data_6));
                                } catch (NumberFormatException | noSegmentException e1) {
                                        // TODO Auto-generated catch block
                                        wrongValue(e1);
                                }

                                for (Vehicle fv : mg.vehicleWest) {
                                        System.out.println(fv.getPlate_no());

                                }
                                System.out.println("----------------------");
                                getVehicleData();
                                setStatData();

                        }

                        // Function that returns data from specified position
                        private String GetData(JTable add_veh_tbl, int i, int j) {
                                return String.valueOf(add_veh_tbl.getModel().getValueAt(i, j));
                        }
                });

                // Handling JFrame close Button
                exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        FileWriter writer = new FileWriter("report.txt");
                                        writer.write(getContent());
                                        writer.close();
                                        JOptionPane.showMessageDialog(null, "The simulation report has been generated",
                                                        "Report",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                        System.exit(0);
                                } catch (IOException ex) {
                                        ex.printStackTrace();
                                }
                        }
                });

                mainframe.setVisible(true);

        }

        // value error exception
        public void wrongValue(Exception ex) {
                JOptionPane.showMessageDialog(null, ex, "Wrong Value", 0);
        }

        /**
         * Function to get Vehicle Data from vehicle.csv
         */
        public void getVehicleData() {
                int rocount = tableModel.getRowCount();
                for (int i = rocount - 1; i >= 0; i--) {
                        tableModel.removeRow(i);
                }

                for (Vehicle vh : mg.vehicleEast) {
                        String[] abc = { String.valueOf(vh.getType()), String.valueOf(vh.getPlate_no()),
                                        String.valueOf(vh.getIn_segment()), String.valueOf(vh.getCrossing_time()),
                                        String.valueOf(vh.getDirection_to()), String.valueOf(vh.isCrossed()),
                                        String.valueOf(vh.getLength()) };
                        tableModel.addRow(abc);
                }
                for (Vehicle vh : mg.vehicleNorth) {
                        String[] abc = { String.valueOf(vh.getType()), String.valueOf(vh.getPlate_no()),
                                        String.valueOf(vh.getIn_segment()), String.valueOf(vh.getCrossing_time()),
                                        String.valueOf(vh.getDirection_to()), String.valueOf(vh.isCrossed()),
                                        String.valueOf(vh.getLength()) };
                        tableModel.addRow(abc);
                }
                for (Vehicle vh : mg.vehicleSouth) {
                        String[] abc = { String.valueOf(vh.getType()), String.valueOf(vh.getPlate_no()),
                                        String.valueOf(vh.getIn_segment()), String.valueOf(vh.getCrossing_time()),
                                        String.valueOf(vh.getDirection_to()), String.valueOf(vh.isCrossed()),
                                        String.valueOf(vh.getLength()) };
                        tableModel.addRow(abc);
                }
                for (Vehicle vh : mg.vehicleWest) {
                        String[] abc = { String.valueOf(vh.getType()), String.valueOf(vh.getPlate_no()),
                                        String.valueOf(vh.getIn_segment()), String.valueOf(vh.getCrossing_time()),
                                        String.valueOf(vh.getDirection_to()), String.valueOf(vh.isCrossed()),
                                        String.valueOf(vh.getLength()) };
                        tableModel.addRow(abc);
                }

        }

        /**
         * Function to assign stat data in table.
         */
        public void setStatData() {
                int rocount = statModel.getRowCount();

                for (int i = rocount - 1; i >= 0; i--) {
                        statModel.removeRow(i);
                }

                Double[] fg = null;
                HashMap<Character, Double[]> stat = mg.calSegment();
                for (char i : stat.keySet()) {
                        fg = stat.get(i);
                        Object[] df = new Object[] { i, fg[0], fg[1], fg[2] };
                        statModel.addRow(df);
                }
        }

        /**
         * Function to get Phase Table Data.
         *
         * @return Object[][]
         */
        public Object[][] getPhaseData() {
                Collection<Intersection> intersection = mg.intersection;
                Object[][] abc = intersection
                                .stream()
                                .map(inter -> new String[] { String.valueOf(inter.getPhases()),
                                                String.valueOf(inter.getDuration()) })
                                .toArray(String[][]::new);
                return abc;
        }

        /**
         * Function to get Content for the Report.
         *
         * @return String
         */
        public String getContent() {
                String content = "\t\t\tROAD SIMULATION REPORT\n\t\t\t~~~~ ~~~~~~~~~~ ~~~~~~\n\nNumber of Vehicles crossed\n------ -- -------- -------\n";

                content += "Phase " + getPhaseData()[0][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleEast)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[1][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleNorth)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[2][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleSouth)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[3][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleWest)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[4][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleEast)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[5][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleNorth)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[6][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleSouth)) + " Vehicles\n";
                content += "Phase " + getPhaseData()[7][0].toString() + "\t:\t" +
                                Integer.toString(get_veh_count(mg.vehicleWest)) + " Vehicles\n\n";

                content += "Average Waiting Time to cross\t:\t" + "00:00 minutes" + "\n\n";
                content += "Total CO2 Emmision \t\t:\t" + Double.toString(mg.calCo2());
                return content;

        }

        /**
         * Function to get vehicle count.
         * 
         * @param myQueue
         * @return int
         */
        public int get_veh_count(Queue<Vehicle> myQueue) {
                int c_veh_count = 0; // Crossed vehicle count
                for (Vehicle vh : myQueue) {
                        if (vh.isCrossed().equals(true)) // Checking whether the vehicle has been crossed
                        {
                                c_veh_count += 1;
                        }
                }
                return c_veh_count;
        }

}