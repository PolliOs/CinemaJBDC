



import java.awt.*;
import java.sql.*;

import static java.lang.String.format;

public class JDBC {
    static final String DATABASE_URL = "jdbc:mysql://localhost/cinema?useSSL=false&characterEncoding=Cp1251";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String USER = "root";
    static final String PASSWORD = "password";
    private static Connection connection;
    private static Statement statement;
 //

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();
       /* EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    MainViewWindow window = new MainViewWindow(statement);
                    window.frame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });*/


    }

    private static void connect() throws ClassNotFoundException, SQLException {
         connection = null;
         statement = null;
        try {
            System.out.println("Registering JDBC driver...");
            //  Class.forName(JDBC_DRIVER);

            System.out.println("Creating connection to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Getting records...");
            statement = connection.createStatement();
            MainViewWindow window = new MainViewWindow(statement, connection);
            window.frame.setVisible(true);

            // String movieQuery = "SELECT * FROM movies";
           /* String genresOfMovieQuery = "SELECT movies.year, movies.title, movies.duration group_concat(genres.genre) as genres\n" +
                    " FROM movies INNER JOIN genres_of_movie ON genres_of_movie.movie_id = movies.movie_id\n" +
                    " INNER JOIN genres ON genres_of_movie.genre_id = genres.id\n" +
                    " GROUP BY movies.title, movies.year, movies.duration;";
            // String genres;

            //ResultSet resultSet = statement.executeQuery(movieQuery);

            ResultSet resultSetOfGenres = statement.executeQuery(genresOfMovieQuery);
            while (resultSetOfGenres.next()){
                //  int id = resultSet.getInt(1);

                String  title = resultSetOfGenres.getString("title");
                int  year = resultSetOfGenres.getInt("year");
                String genres = resultSetOfGenres.getString("genres");
                int duration = resultSetOfGenres.getInt("duration");

                //  System.out.println("id: " + id);
                System.out.println("title: " + title);
                System.out.println("year: " + year);
                System.out.println("Genres" + " ");
                System.out.println(genres);
                System.out.println("Duration: " + duration);
                System.out.println("===================\n");
            }*/
        }finally {
           /* if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }*/
           System.out.println("successful job");
        }
    }


}