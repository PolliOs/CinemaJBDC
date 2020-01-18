import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Handler {
    String panel;
    Statement statement;
    Connection connection;
    Handler(String panel, Statement statement, Connection connection){
        this.panel = panel;
        this.statement = statement;
        this.connection = connection;
    }
    public boolean findDuplicate(String title) {
        try {
            String hallsTitleQuery = "SELECT * FROM " + panel + " WHERE title = \"" + title + "\"";
            ResultSet resultSet = statement.executeQuery(hallsTitleQuery);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    int findUnusedId() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM " + panel);
            Set<Integer> used = new HashSet<>();
            while(resultSet.next()){
                used.add(resultSet.getInt("id"));
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
    public int getIntValueByTitle(String title, String value) {
        int ans = -1;
        try {
            String sqlSelectQuery =
                    "SELECT " + value + " FROM " + panel + " WHERE  title = \"" + title + "\"";
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sqlSelectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ans = resultSet.getInt(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return ans;
    }

    public ArrayList<String> getListOfTitles() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT title FROM " + panel);
            while(resultSet.next()){
                list.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
