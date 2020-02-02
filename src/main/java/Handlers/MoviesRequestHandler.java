package Handlers;

import Handlers.Handler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MoviesRequestHandler extends Handler {
    public Set<Integer> currentSelectedGenresForMovie;
    public ArrayList<Integer> genresOfCurrentMovie;
    public Set<Integer> currentSelectedGenresId;

    public MoviesRequestHandler(Statement statement, Connection connection){
        super("movies", statement,connection);
    }

    public void setGenresOfMovie(String title){
        ArrayList<Integer> selectedGenres = new ArrayList<>();
        currentSelectedGenresForMovie = new HashSet<>();
        currentSelectedGenresId = new HashSet<>();
        String selectedGenresStr = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT movies.title, group_concat(genres.id) as genres FROM movies INNER JOIN genres_of_movie ON genres_of_movie.movie_id = movies.id INNER JOIN genres"+
                    " ON genres_of_movie.genre_id = genres.id WHERE movies.title = \"" + title + "\"");
            while(resultSet.next()){
                selectedGenresStr = (resultSet.getString("genres"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (selectedGenresStr != null) {
            for (String genre : selectedGenresStr.split(",", 0)) {
                selectedGenres.add(Integer.parseInt(genre));
                currentSelectedGenresForMovie.add(Integer.parseInt(genre));
            }
        }

        this.genresOfCurrentMovie = selectedGenres;
    }

    public void currentSelectedGenresId(Integer[] genres) {
        currentSelectedGenresId = new HashSet<>();
        for(int i = 0; i < genres.length; i++){
            if(currentSelectedGenresForMovie.contains(genres[i])){
                currentSelectedGenresId.add(i);
            }
        }
    }

    public boolean findChanges(Object currentTitle, String newTitle, String newYear, String newDuration) {
       if(!newTitle.equals(currentTitle)){
            return true;
        }
        if(!newYear.equals(getValueByTitle((String) currentTitle, "year"))){
            return true;
        }
        if(!newDuration.equals(getValueByTitle((String) currentTitle, "duration"))){
            return true;
        }
        return !(genresOfCurrentMovie.containsAll(currentSelectedGenresForMovie) &&
                currentSelectedGenresForMovie.containsAll(genresOfCurrentMovie));
    }

    public void changeMovie(String movie, String newTitle, String year, String duration) {
        try {
            String id = getValueByTitle(movie, "id");
            String updateMovieQuery =
                    "UPDATE movies SET `title`=\"" + newTitle + "\", `year`= \"" + year + "\", `duration`= \"" + duration + "\"  WHERE `id`=\"" + id + "\"";
            statement.executeUpdate(updateMovieQuery);
            genresOfCurrentMovie = new ArrayList<>();
            genresOfCurrentMovie.addAll(currentSelectedGenresForMovie);
            updateGenresOfMovie(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGenresOfMovie(String movie_id) {
        try {
            String insertQuery = "INSERT INTO genres_of_movie (genre_id, movie_id) VALUES (?, ?);";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(insertQuery);
            String deletePreviousGenresQuery =
                    "DELETE FROM genres_of_movie WHERE  movie_id = \"" + movie_id + "\"";
            statement.executeUpdate(deletePreviousGenresQuery);
            for(Integer genre:genresOfCurrentMovie) {
                preparedStatement.setString(2,movie_id);
                preparedStatement.setString(1, String.valueOf(genre));
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addNewMovie(String newTitle, String year, String duration) {
        try {
            int id = findUnusedId();
            String insertQuery = "INSERT INTO movies VALUES (?,?,?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.setString(2, newTitle);
            preparedStatement.setString(3, year);
            preparedStatement.setString(4, duration);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(String movie) {
        try {
            String id = getValueByTitle(movie, "id");
            String deleteConnectionsQuery = "DELETE FROM genres_of_movie WHERE movie_id = \"" + id + "\"";
            String deleteSessionsQuery = "DELETE FROM sessions WHERE movie_id = \"" + id + "\"";
            String deleteMovieQuery =
                    "DELETE FROM movies WHERE  id = \"" + id + "\"";
            statement.executeUpdate(deleteConnectionsQuery);
            statement.executeUpdate(deleteSessionsQuery);
            statement.executeUpdate(deleteMovieQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
