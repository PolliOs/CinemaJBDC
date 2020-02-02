package Handlers;

import Handlers.Handler;

import java.sql.Connection;
import java.sql.*;

public class HallsRequestHandler extends Handler {
    private static final String INSERT_QUERY =
            "INSERT INTO halls VALUES (?, ?, ?)";


    public HallsRequestHandler(Statement statement, Connection connection){
        super("halls", statement,connection);
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
    public int deleteHall(String id) {
        try {
            String deleteConnectionsQuery = "DELETE FROM sessions WHERE hall_id = \"" + id + "\"";
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
            String id = getValueByTitle(prevHall, "id");
            String updateHallQuery =
                    "UPDATE halls SET `title`=\"" + newTitle + "\", `seats`= \"" + newNumOfSeats + "\" WHERE `id`=\"" + id + "\"";
            statement.executeUpdate(updateHallQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
