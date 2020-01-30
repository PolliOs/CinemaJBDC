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

    public void helpMessage() {
        String message = "Раді вітати Вас у режимі адміністратора! \n";
        message += "В цьому режимі ви можете додавати та редагувати фільми,жанри фільмів, зали та сеанси.\n";
        message += "Треба зауважити наступне:\n";
        message += "Неможна додавати зал,фільм або жанр з пустими полями(програма повідомить вам про це)\n";
        message += "При додаванні/редагуванні даних будьте уважними і вводьте коректні значення, які відповідають дійсному світу.\n";
        message += "Зокрема неможливо додати фільм, рік випуску якого пізніше за 2020(на жаль, машини часу ще не вигадали) фбо раніше за 1900(інакше наші прабатьки жили б веселіше:).\n";
        message += "Також ціна не може бути більшою за число 10^5, а при спробі поставити від'ємну ціну, програма автоматично встановить значення по модулю.\n";
        message += "Слід зауважити, що при редагуванні сеансів за змовчування час сеансі встановлюється 10:00 і його ціна 50.\n";
        message += "Якщо якесь із цих полів буде заповнено некоректними даними, під час редагування, то програма автоматично заповнить поля даними за змовчуванням.\n";

        message += "При редагуванні сеансів система слідкує за тим, шоб не виникали колізії, і адміністратор випадково не встановив два різні фільми у залі в той час, як там транслюватиметься інший фільм\n";
        message += "Тож, якщо при редагуванні фільму ваші зміни не збереглися, то ви намагаєтесь створити сеанс у зайнятому в цей час залі, будь ласка уважно перевірте свої зміни\n";
        message += "Приємного користування цією системою!\n";
        JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);

    }
}
