import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CSVTableModel extends AbstractTableModel {

    private List<String[]> data;
    private String[] columnNames = { "Temp Column" };

    public void setData(List<String[]> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
