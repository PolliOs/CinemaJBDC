
import java.sql.*;

public class MoviesRequestHandler extends Handler {
    public MoviesRequestHandler(Statement statement, Connection connection){
        super("movies", statement,connection);
    }


}
