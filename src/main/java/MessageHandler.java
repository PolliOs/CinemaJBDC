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
}
