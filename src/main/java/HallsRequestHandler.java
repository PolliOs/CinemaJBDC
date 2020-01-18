import java.sql.Connection;
import java.sql.*;

public class HallsRequestHandler extends Handler {
    private static final String INSERT_QUERY =
            "INSERT INTO halls VALUES (?, ?, ?)";


    public HallsRequestHandler(Statement statement, Connection connection){
        super("halls", statement,connection);
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
    public void addNewHall(int numOfseats, String title) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(INSERT_QUERY);
        int id = findUnusedId();
        preparedStatement.setString(1, Integer.toString(id));
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, Integer.toString(numOfseats));
        preparedStatement.addBatch();
        preparedStatement.executeBatch();
    }
    public int deleteHall(int id) {
        try {
            String deleteConnectionsQuery = "DELETE FROM hall_of_session WHERE hall_id = \"" + id + "\"";
            String deleteHallQuery =
                    "DELETE FROM halls WHERE  id = \"" + id + "\"";
            statement.executeUpdate(deleteConnectionsQuery);
            return statement.executeUpdate(deleteHallQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;

        }
    }
    public void updateHall(String prevHall, String newTitle, String newNumOfSeats) {
        try {
            int id = getIntValueByTitle(prevHall, "id");
            String updateHallQuery =
                    "UPDATE halls SET `title`=\"" + newTitle + "\", `seats`= \"" + newNumOfSeats + "\" WHERE `id`=\"" + id + "\"";
            System.out.println(statement.executeUpdate(updateHallQuery));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
