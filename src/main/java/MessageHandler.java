import javax.swing.*;

public class MessageHandler {

    public void nullValuesError() {
        JOptionPane.showMessageDialog(null, "Будь ласка, заповніть усі поля коректними значеннями.", "InfoBox: " + "Failure", JOptionPane.INFORMATION_MESSAGE);
    }

    public void invalidIntValue(String value) {
        JOptionPane.showMessageDialog(null, "Поле '" + value + "\" має бути цілим додатнім числом, меншим за 10^5." , "InfoBox: " + "Failure", JOptionPane.INFORMATION_MESSAGE);
    }

    public void duplicateError(String value) {
        JOptionPane.showMessageDialog(null,  value + " з таким ім'ям вже існує." , "InfoBox: " + "Duplicate error", JOptionPane.INFORMATION_MESSAGE);
    }

    public int confirmDeleteHall() {
        return JOptionPane.showConfirmDialog(null, "Ви впевнені що хочете видалити цей зал? Усі сеанси, які проводяться у цій залі також втратяться");
    }

    public void unselectedError() {
        JOptionPane.showMessageDialog(null,  "Ви не обрали значення для видалення" , "InfoBox: " + "Unselected error", JOptionPane.INFORMATION_MESSAGE);
    }

    public void emptyToAddError() {
        JOptionPane.showMessageDialog(null,  "Значення не має бути порожнім рядком" , "InfoBox: " + "Empty value error", JOptionPane.INFORMATION_MESSAGE);
    }
    public int confirmDeleteGenre() {
        return JOptionPane.showConfirmDialog(null, "Ви впевнені що хочете видалити цей жанр? ");
    }

}
