import javax.swing.*;

public class MessageHandler {

    public void nullValuesError() {
        JOptionPane.showMessageDialog(null, "Будь ласка, заповніть усі поля коректними значеннями.", "InfoBox: " + "Failure", JOptionPane.INFORMATION_MESSAGE);
    }

    public void invalidIntValue(String value) {
        JOptionPane.showMessageDialog(null, "Поле '" + value + "\" має бути цілим додатнім числом, який відповідає життєвим величинам :)" , "InfoBox: " + "Failure", JOptionPane.INFORMATION_MESSAGE);
    }

    public void duplicateError(String value) {
        JOptionPane.showMessageDialog(null,  value + " з таким ім'ям вже існує." , "InfoBox: " + "Duplicate error", JOptionPane.INFORMATION_MESSAGE);
    }

    public int confirmDeleteHall() {
        return JOptionPane.showConfirmDialog(null, "Ви впевнені що хочете видалити цей зал? Усі сеанси, які проводяться у цій залі також втратяться");
    }
    public int confirmDeleteMovie() {
        return JOptionPane.showConfirmDialog(null, "Ви впевнені що хочете видалити цей фільм? Усі сеанси, які транслюють даний фільм також втратяться");
    }

    public void unselectedError() {
        JOptionPane.showMessageDialog(null,  "Ви не обрали значення для видалення" , "InfoBox: " + "Unselected error", JOptionPane.INFORMATION_MESSAGE);
    }

    public void emptyToAddError() {
        JOptionPane.showMessageDialog(null,  "Значення не має бути порожнім рядком" , "InfoBox: " + "Empty value error", JOptionPane.INFORMATION_MESSAGE);
    }
    public int confirmDeleteValue(String value) {
        return JOptionPane.showConfirmDialog(null, "Ви впевнені що хочете видалити цей " + value + " ? ");
    }

    public int confirmChanges() {
        return JOptionPane.showConfirmDialog(null, "Ви впевнені що хочете зберегти ці зміни? ");

    }
}
