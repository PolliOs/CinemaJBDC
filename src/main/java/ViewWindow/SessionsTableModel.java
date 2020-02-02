package ViewWindow;

import javax.swing.table.AbstractTableModel;
import java.util.HashSet;
import java.util.Set;

public class SessionsTableModel extends AbstractTableModel {
    public static String defaultTime = "10:00";
    public static String defaultPrice = "50";
    public Set<Integer> changedRows = new HashSet<>();
    private String[] columnNames = {"ID",
            "День",
            "Час",
            "Фільм",
            "Зал",
            "Ціна",
            "Видалити"};

    private Object[][] data;
    public  void setData(Object[][] data){
        this.data = data;
        fireTableStructureChanged();
    }

    public final Object[] longValues = {"", "",  defaultTime,
            "", "", defaultPrice, Boolean.FALSE};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
        changedRows.add(row);
    }

}
