import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VehicleView implements Observer {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public VehicleView() {
        frame = new JFrame("Vehicle Data");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        
         tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        // tableModel = new DefaultTableModel();

        tableModel.addColumn("Type");
        tableModel.addColumn("Plate No.");
        tableModel.addColumn("In Segment");
        tableModel.addColumn("Crossing Time");
        tableModel.addColumn("Direction To");
        tableModel.addColumn("Crossed");
        tableModel.addColumn("Length");
        tableModel.addColumn("CO2 Emission");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void update(Observable obs, Object arg) {
        if (obs instanceof VehicleModel) {
            LinkedList<Vehicle> vehicleQueue = (LinkedList<Vehicle>) arg;
            tableModel.setRowCount(0);
            for (Vehicle vehicle : vehicleQueue) {
                Object[] rowData = { vehicle.getType(), vehicle.getPlate_no(), vehicle.getIn_segment(),
                        vehicle.getCrossing_time(), vehicle.getDirection_to(), vehicle.isCrossed(),
                        vehicle.getLength(), vehicle.getCo2_emission() };
                tableModel.addRow(rowData);
            }
        }
    }
}
