package ViewWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;


public class MainWindow extends JFrame{
    private JPanel mainPanel;
    private JLabel adminMode;
    // private JPanel MainPanel;
    private JList list;

    public MainWindow(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height *= 0.8;
        screenSize.width *= 0.8;
        setSize(screenSize);
        setContentPane(mainPanel);
        setVisible(true);

        //list = new JList(new String[]{"Зали", "Фільми", "Сеанси"}); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        addWindowListener(new WindowConfirmedCloseAdapter());
        adminMode.setFont(new Font("Serif", Font.BOLD, 24));


        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()){
                    JList source = (JList)event.getSource();
                    String selected = source.getSelectedValue().toString();
                    if(selected.equals("Зали")){
                        //HallsEditWindow hallWindow = new HallsEditWindow();
                    }else if(selected.equals("Сеанси")){
                        System.out.println("Sessions");
                    }else if(selected.equals("Фільми")){
                        System.out.println("Movies");
                    }
                }
            }
        });
    }

}
