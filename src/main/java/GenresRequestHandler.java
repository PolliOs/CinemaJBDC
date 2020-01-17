import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenresRequestHandler {
    private  Statement statement;
    private  Connection connection;
    public GenresRequestHandler(Statement statement, Connection connection) {
        this.connection = connection;
        this.statement = statement;
    }

    public String[] getGenresList() {
        ArrayList<String> genres = new ArrayList<String>();
        try {
            String genresTitleQuery = "SELECT genre FROM genres";
            ResultSet resultSet = statement.executeQuery(genresTitleQuery);
            while (resultSet.next()){
                genres.add(resultSet.getString("genre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres.toArray(new String[genres.size()]);
    }
    private int findUnusedId() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM genres");
            Set<Integer> used = new HashSet<Integer>();
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
    public void addNewGenre(String newGenre) {
        try {
            String insertQuery = "INSERT INTO genres VALUES (?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(insertQuery);
            int id = findUnusedId();
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, newGenre);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean findDuplicate(String newGenre) {
        try {
            String hallsTitleQuery = "SELECT * FROM genres WHERE genre = \"" + newGenre + "\"";
            ResultSet resultSet = statement.executeQuery(hallsTitleQuery);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getIdByGenre(String genre) {
        int id = -1;
        try {
            String getIdQuery =
                    "SELECT id FROM genres WHERE  genre = \"" + genre + "\"";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(getIdQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    public int deleteGenre(String genre) {
        try {
            int id = getIdByGenre(genre);
            String deleteConnectionsQuery = "DELETE FROM genres_of_movie WHERE genre_id = \"" + id + "\"";
            String deleteGenreQuery =
                    "DELETE FROM genres WHERE  id = \"" + id + "\"";
            statement.executeUpdate(deleteConnectionsQuery);
            return  statement.executeUpdate(deleteGenreQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;

        }
    }
}
