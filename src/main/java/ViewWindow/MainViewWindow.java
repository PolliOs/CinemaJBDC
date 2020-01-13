package ViewWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainViewWindow extends  JFrame {
    private JFrame frame;
    private  JPanel adminPanel;
    private  JPanel hallsPanel;
    private JPanel moviewPanel;
    private JPanel mainPanel;
  //  private JPanel Halls;
    private JButton hallsButton;
    private JButton moviesButton;
    private JButton sessionsButton;
    private JButton editCurrentHallsButton;
    private JPanel sessionsPanel;
    private JButton backToMainButton1;
    private JButton changeCurrentHallButton;
    private JButton addHallButton;
    private JPanel addHallPanel;
    private JPanel editHallsPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton saveNewHallButton;
    private JButton backToHallsButton1;
    private JPanel moviesPanel;
    private JComboBox comboBox1;
    private JButton button1;
    private JButton button2;
    private JLabel seats1;
    private JLabel price1;
    private JLabel title1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    MainViewWindow window = new MainViewWindow();
                    window.frame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public MainViewWindow() {
        initialize();
    }
   private void initialize(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0,0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height *= 0.8;
        screenSize.width *= 0.8;
        //frame.setBounds(100,100,628,381);
        frame.setSize(screenSize);
      //  mainPanel = new JPanel();;
     //   mainPanel.setBackground(new Color(25,25,112));
        frame.getContentPane().add(mainPanel, "name_1");

       hallsButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changeActivePanel(hallsPanel, mainPanel);
           }
       });
       moviesButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changeActivePanel(moviesPanel,mainPanel);

           }
       });
       sessionsButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changeActivePanel(sessionsPanel,mainPanel);

           }
       });
       backToMainButton1.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changeActivePanel(mainPanel, hallsPanel);

           }
       });
       backToHallsButton1.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changeActivePanel(hallsPanel, addHallPanel);
           }
       });
       addHallButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               changeActivePanel(addHallPanel, hallsPanel);

           }
       });



      // mainPanel.add(hallsButton);




       // Halls.setBackground(new Color(46,139,87));
       frame.getContentPane().add(hallsPanel, "name_2");
       frame.getContentPane().add(moviesPanel, "name_3");
       frame.getContentPane().add(sessionsPanel, "name_4");
       frame.getContentPane().add(addHallPanel, "name_5");
       frame.getContentPane().add(editHallsPanel, "name_6");

    }
    private void changeActivePanel(JPanel newPanel, JPanel previousPanel){
        newPanel.setVisible(true);
        previousPanel.setVisible(false);
    }


  /* private  void initialize(){
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       screenSize.height *= 0.8;
       screenSize.width *= 0.8;
       setSize(screenSize);
       setContentPane(mainPanel);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Halls.setVisible(false);

       hallsButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               Halls.setVisible(true);
               mainPanel.setVisible(false);

           }
       });
       Halls.add(editCurrentHallsButton);
   }*/

}

