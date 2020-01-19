import java.sql.*;

public class GenresRequestHandler extends Handler {

    public GenresRequestHandler(Statement statement, Connection connection) {
        super("genres", statement, connection);
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
    public int deleteGenre(String genre) {
        try {
            String id = getValueByTitle(genre, "id");
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
