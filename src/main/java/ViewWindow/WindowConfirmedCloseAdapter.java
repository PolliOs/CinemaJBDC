package ViewWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowConfirmedCloseAdapter extends WindowAdapter {

    public void windowClosing(WindowEvent e) {

        Object[] options = {"Yes", "No"};

        int close = JOptionPane.showOptionDialog(e.getComponent(),
                "Do you really want to close this application?\n", "Attention",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        //TODO uncomment
        if(close == JOptionPane.YES_OPTION) {
             /*  if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }*/
            ((JFrame)e.getSource()).setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE);
        } else {
            ((JFrame)e.getSource()).setDefaultCloseOperation(
                    JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
}