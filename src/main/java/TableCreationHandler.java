import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class TableCreationHandler {
    public  SessionsTableModel table;
    private static int numOfColumns = 6;
    private Connection connection;

    TableCreationHandler(Connection connection){
        this.connection = connection;
        table = new SessionsTableModel();
        table.setData(getData());
    }
    private Object[][] getData() {
        ArrayList<Object[]> dataArrayList = new ArrayList<>();
        try {
            String sqlSelectQuery =
                    "SELECT * FROM session";
            String sqlGetMovieTitleQuery = "SELECT title FROM movies WHERE id = (?)";
            String sqlGetHallTitleQuery = "SELECT title FROM halls WHERE id = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectQuery);
            PreparedStatement prepStatForMovieQuery = connection.prepareStatement(sqlGetMovieTitleQuery);
            PreparedStatement prepStatForHallQuery = connection.prepareStatement(sqlGetHallTitleQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            dataArrayList = getDataArrayList(resultSet, prepStatForHallQuery, prepStatForMovieQuery);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        Object[][] data = new Object[dataArrayList.size()][numOfColumns];
        for (int i = 0; i < dataArrayList.size(); i++) {
            data[i] = dataArrayList.get(i);
        }
        return data;
    }

    private ArrayList<Object[]> getDataArrayList(ResultSet resultSet, PreparedStatement prepStatForHallQuery, PreparedStatement prepStatForMovieQuery) throws SQLException {
        ArrayList<Object[]> dataArrayList = new ArrayList<>();
        String time;
        String day;
        String price;
        String movie = "";
        String hall = "";
        while (resultSet.next()) {
            time = resultSet.getString("time");
            day = resultSet.getString("day");
            price = resultSet.getString("price");
            prepStatForMovieQuery.setString(1, resultSet.getString("movie_id"));
            prepStatForHallQuery.setString(1, resultSet.getString("hall_id"));
            ResultSet resultSet1 = prepStatForMovieQuery.executeQuery();
            while (resultSet1.next()) {
                movie = resultSet1.getString("title");
            }
            resultSet1 = prepStatForHallQuery.executeQuery();
            while (resultSet1.next()) {
                hall = resultSet1.getString("title");
            }
            dataArrayList.add(new Object[]{day, time, movie, hall, price, Boolean.FALSE});
        }
        return  dataArrayList;
    }

    /*{"Monday", LocalTime.parse("12:22",
            DateTimeFormatter.ISO_TIME).toString(),
            "Дулітл", "purpl20e", "50", FALSE},
    {"Monday", LocalTime.parse("22:10",
            DateTimeFormatter.ISO_TIME).toString(),
            "Дулітл", "pink", "50", FALSE},
    {"Wednesday", LocalTime.parse("13:20",
            DateTimeFormatter.ISO_TIME).toString(),
            "Дулітл", "purple", "50", FALSE}
};*/
    public void initColumnSizes(JTable table) {
        SessionsTableModel model = (SessionsTableModel) table.getModel();
        TableColumn column;
        Component comp;
        int headerWidth;
        int cellWidth;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
                table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < numOfColumns; i++) {
            column = table.getColumnModel().getColumn(i);
            comp = headerRenderer.getTableCellRendererComponent(
                    null, column.getHeaderValue(),
                    false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                    getTableCellRendererComponent(
                            table, longValues[i],
                            false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            /*if (DEBUG) {
                System.out.println("Initializing width of column "
                        + i + ". "
                        + "headerWidth = " + headerWidth
                        + "; cellWidth = " + cellWidth);
            }*/

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

    public void setUpMovieColumn(TableColumn movieColumn, ArrayList<String> movies) {
        JComboBox<String> comboBox = new JComboBox<>();
        for(String movieTitle:movies){
            comboBox.addItem(movieTitle);
        }
        movieColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        movieColumn.setCellRenderer(renderer);
    }

    public void setUpDaysColumn(TableColumn daysColumn) {
        //Set up the editor for the sport cells.
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Понеділок");
        comboBox.addItem("Вівторок");
        comboBox.addItem("Середа");
        comboBox.addItem("Четвер");
        comboBox.addItem("П'ятниця");
        comboBox.addItem("Субота");
        comboBox.addItem("Неділя");
        daysColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        daysColumn.setCellRenderer(renderer);
    }

    void setUpHallsColumn(TableColumn hallsColumn, ArrayList<String> halls) {
        //Set up the editor for the sport cells.
        JComboBox<String> comboBox = new JComboBox<>();
        for(String hall:halls) {
            comboBox.addItem(hall);
        }
        hallsColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        hallsColumn.setCellRenderer(renderer);
    }
}
//TODO remove all debug here