import java.sql.Connection;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class HallsRequestHandler {
    private static Connection connection;
    private  static  Statement statement;
     private static final String hallsTitleQuery = "SELECT title FROM halls";
    private static final String INSERT_QUERY =
            "INSERT INTO halls VALUES (?, ?, ?)";


    HallsRequestHandler(Statement statement, Connection connection){
        this.connection = connection;
        this.statement = statement;
    }
    static boolean checkNulls(String seats, String title){
        return(!(seats.isEmpty() || title.isEmpty()));
    }

    public static int checkInt(String value) {
        try
        {
            int number = Integer.parseInt(value);
            if(Math.abs(number) < 10e5)
                 return Math.abs(number);
            else return -1;
        }
        catch(NumberFormatException er)
        {
            return -1;
        }
    }

    public static boolean checkTitle(String title) {
        return title.length() <= 45;
    }

    public boolean checkTitleForDuplicate(String title) {
        try {
            ResultSet resultSet = statement.executeQuery(hallsTitleQuery);
            String existingHall;
            while(resultSet.next()){
                existingHall = resultSet.getString("title");
                if(title.equals(existingHall)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void addNewHall(int numOfseats, String title) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(INSERT_QUERY);
        int id = findUnusedId();
        preparedStatement.setString(1, Integer.toString(id));
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, Integer.toString(numOfseats));
        System.out.println(preparedStatement.toString());
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
    }

    private int findUnusedId() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT hall_id FROM halls");
            Set<Integer> used = new HashSet<Integer>();;
            while(resultSet.next()){
                used.add(resultSet.getInt("hall_id"));
            }
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                if (!used.contains(i)) {
                    return i;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;

    }
}
