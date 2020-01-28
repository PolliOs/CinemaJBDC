import javax.swing.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class SessionsRequestHandler extends Handler {
    private static int idIndex;
    private static int hallIndex;
    private static int movieIndex;
    private static int dayIndex;
    private static int timeIndex;
    private static int deleteIndex;
    private static int priceIndex;
    private static final String defaultPrice = "50";
    private static final String defaultTime = "10:00";
    SessionsRequestHandler(Statement statement, Connection connection, JTable table) {
        super("sessions", statement, connection);
        idIndex = table.getColumn("ID").getModelIndex();
        hallIndex = table.getColumn("Зал").getModelIndex();
        movieIndex = table.getColumn("Фільм").getModelIndex();
        dayIndex = table.getColumn("День").getModelIndex();
        timeIndex = table.getColumn("Час").getModelIndex();
        deleteIndex = table.getColumn("Видалити").getModelIndex();
        priceIndex = table.getColumn("Ціна").getModelIndex();
    }

    public void deleteRows(JTable table) {
        int numOfRows = table.getRowCount();
        for(int i = 0; i < numOfRows; i++){
            if(table.getValueAt(i,deleteIndex) == Boolean.TRUE){
                deleteSessions((String) table.getValueAt(i,idIndex));
            }
        }
    }

    private void deleteSessions(String id) {
        try {
            String deleteGenreQuery =
                    "DELETE FROM sessions WHERE  id = \"" + id + "\"";
            statement.executeUpdate(deleteGenreQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void applyChanges(JTable sessionsTable, Set<Integer> changedRows) {
        Set<String> used = new HashSet<>();
        updateExistedSessions(sessionsTable, changedRows, used);
        addNewSessions(sessionsTable,used);
    }

    private void addNewSessions(JTable table, Set<String> used) {
        int numOfRows = table.getRowCount();
        String time;
        String day;
        String movie;
        String hall;
        for(int i = 0; i < numOfRows; i++){
            if(table.getValueAt(i,idIndex).toString().isEmpty()){
                time = validateTime(table.getValueAt(i, timeIndex));
                day = (String) table.getValueAt(i,dayIndex);
                movie = (String) table.getValueAt(i,movieIndex);
                hall = (String) table.getValueAt(i,hallIndex);
                if(!checkNulls(new String[]{day,movie,hall}) && !used.contains(day+time+movie+hall)){
                    addNewSession(day,time,movie,hall,table.getValueAt(i,priceIndex).toString());
                    used.add(day+time+movie+hall);
                }

            }
        }
    }

    private void addNewSession(String day, String time, String movie, String hall, String price) {
        try {
            int id = findUnusedId();
            String movie_id = getValueByTitle(movie,"id", "movies");
            String hall_id = getValueByTitle(hall,"id","halls");
            String insertQuery = "INSERT INTO sessions VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, day);
            preparedStatement.setString(4, price);
            preparedStatement.setString(5, movie_id);
            preparedStatement.setString(6, hall_id);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateExistedSessions(JTable table, Set<Integer> changedRows, Set<String> used) {
        int numOfRows = table.getRowCount();
        String time;
        String day;
        String movie;
        String hall;
        for(int i = 0; i < numOfRows; i++){
            if(!table.getValueAt(i,idIndex).toString().isEmpty() && !changedRows.contains(i)){
                time = validateTime(table.getValueAt(i, timeIndex));
                day = (String) table.getValueAt(i,dayIndex);
                movie = (String) table.getValueAt(i,movieIndex);
                hall = (String) table.getValueAt(i,hallIndex);
                used.add(day+time+movie+hall);
            }
        }
        for(int i = 0; i < numOfRows; i++){
            if(!table.getValueAt(i,idIndex).toString().isEmpty() && changedRows.contains(i)){
                time = validateTime(table.getValueAt(i, timeIndex));
                day = (String) table.getValueAt(i,dayIndex);
                movie = (String) table.getValueAt(i,movieIndex);
                hall = (String) table.getValueAt(i,hallIndex);
                if(!used.contains(day+time+movie+hall)){
                    updateSession(String.valueOf(table.getValueAt(i,idIndex)), day,time,movie,hall, String.valueOf(table.getValueAt(i,priceIndex)));
                    used.add(day+time+movie+hall);
                }

            }
        }

    }
    private  void updateSession(String id, String day, String time, String movie, String hall, String price){
        price = validatePrice(price);
        try {
            String movie_id = getValueByTitle(movie, "id", "movies");
            String hall_id = getValueByTitle(hall, "id", "halls");
            String updateMovieQuery =
                    "UPDATE sessions SET `time`=\"" + time + "\", `day`= \"" + day + "\", `price`= \"" + price + "\", `movie_id`= \"" + movie_id + "\", `hall_id` = \"" + hall_id + "\"   WHERE `id`=\"" + id + "\"";
            statement.executeUpdate(updateMovieQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String validatePrice(String price) {
        if(checkInt(price) != -1){
            return String.valueOf(checkInt(price));
        }
        return defaultPrice;
    }

    private String validateTime(Object time) {
        String sessionTime = time.toString();
        try {
            LocalTime.parse(sessionTime);
            return sessionTime;
        } catch (DateTimeParseException | NullPointerException e) {
            System.out.println("Invalid time string: " + sessionTime);
            return defaultTime;
        }
    }
}
