import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
}
