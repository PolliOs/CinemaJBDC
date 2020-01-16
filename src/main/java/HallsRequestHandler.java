import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HallsRequestHandler {
    private static Connection connection;
    private  static  Statement statement;
     //private static final String hallsTitleQuery =;
    private static final String INSERT_QUERY =
            "INSERT INTO halls VALUES (?, ?, ?)";


    HallsRequestHandler(Statement statement, Connection connection){
        HallsRequestHandler.connection = connection;
        HallsRequestHandler.statement = statement;
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
            String hallsTitleQuery = "SELECT * FROM halls WHERE title = \"" + title + "\"";
            ResultSet resultSet = statement.executeQuery(hallsTitleQuery);
            return resultSet.next();
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
            Set<Integer> used = new HashSet<Integer>();
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

    public ArrayList<String> updateChoseHallComboBox() {
        ArrayList<String> halls = new ArrayList<String>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT title FROM halls");
            while(resultSet.next()){
                halls.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return halls;

    }

    public int getSeatsByTitle(String title) {
        int numOfSeats = -1;
        try {
            String sqlSelectQuery =
                    "SELECT seats FROM halls WHERE  title = \"" + title + "\"";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(sqlSelectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               numOfSeats = resultSet.getInt("seats");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return numOfSeats;
    }

    public int deleteHall(int id) {
        try {
            String deleteConnectionsQuery = "DELETE FROM hall_of_session WHERE hall_id = \"" + id + "\"";
            String deleteHallQuery =
                    "DELETE FROM halls WHERE  hall_id = \"" + id + "\"";
            return statement.executeUpdate(deleteConnectionsQuery) * statement.executeUpdate(deleteHallQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;

        }
    }

    public int getIdByTitle(String title) {
        int id = -1;
        try {
            String getIdQuery =
                    "SELECT hall_id FROM halls WHERE  title = \"" + title + "\"";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(getIdQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt("hall_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    public void updateHall(String prevHall, String newTitle, String newNumOfSeats) {
        try {
            int id = getIdByTitle(prevHall);
            String updateHallQuery =
                    "UPDATE halls SET `title`=\"" + newTitle + "\", `seats`= \"" + newNumOfSeats + "\" WHERE `hall_id`=\"" + id + "\"";
            System.out.println(statement.executeUpdate(updateHallQuery));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
