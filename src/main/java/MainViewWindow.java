import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainViewWindow extends  JFrame {
    JFrame frame;
    private  JPanel adminPanel;
    private  JPanel hallsPanel;
    private JPanel moviewPanel;
    private JPanel mainPanel;
    private JButton hallsButton;
    private JButton moviesButton;
    private JButton sessionsButton;
    private JPanel sessionsPanel;
    private JButton backToMainButton1;
    private JButton changeCurrentHallButton;
    private JButton addHallButton;
    private JPanel addHallPanel;
    private JPanel editHallsPanel;
    private JTextField seatsTextFieldInAddMode;
    private JTextField titleTextFieldInAddMode;
    private JButton saveNewHallButton;
    private JButton backToHallsButton1;
    private JPanel moviesPanel;
    private JComboBox chooseHallcomboBox;
    private JButton addNewHallButton;
    private JButton backToHallsButton2;
    private JLabel seats1;
    private JLabel title1;
    private JLabel title2;
    private JLabel seats2;
    private JTextField titleTextFieldInEditMode;
    private JTextField seatsTextFieldInEditMode;
    private JButton deleteHall;
    private HallsRequestHandler hallsHandler;
    private  MessageHandler messageHandler;
    private  Statement statement;
    private  Connection connection;


    public MainViewWindow(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
        initialize();
        buttonsProcessing();

    }

    private void initialize(){
        hallsHandler = new HallsRequestHandler(statement, connection);
        messageHandler = new MessageHandler();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0,0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height *= 0.8;
        screenSize.width *= 0.8;
        frame.setSize(screenSize);
        changePanels();
        addPanelsToTheFrame();

/*        ArrayList<String> movies = new ArrayList<String>();
        movies.add("Select a movie");
        for (int i = 0; i < 5; i++) {
            movies.add("Movie " + (i + 1));
        }

        DefaultComboBoxModel model1 = new DefaultComboBoxModel(movies.toArray());
        chooseHallcomboBox = new JComboBox(model1);
        chooseHallcomboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                System.out.println(chooseHallcomboBox.getSelectedIndex());
                if (chooseHallcomboBox.getSelectedIndex() == 0) {
                   // timesCombo.setEnabled(false);
                } else {
                }
            }

        });*/


       // petList.addActionListener(this);

    }

    private void addPanelsToTheFrame() {
        frame.getContentPane().add(mainPanel, "name_1");
        frame.getContentPane().add(hallsPanel, "name_2");
        frame.getContentPane().add(moviesPanel, "name_3");
        frame.getContentPane().add(sessionsPanel, "name_4");
        addHallPanel.setBackground(new Color(40,111,129));
        frame.getContentPane().add(addHallPanel, "name_5");
        frame.getContentPane().add(editHallsPanel, "name_6");
    }

    private void buttonsProcessing() {
        saveNewHallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String seats = seatsTextFieldInAddMode.getText();
                String title = titleTextFieldInAddMode.getText();
                int numOfSeats = HallsRequestHandler.checkInt(seats);
                seatsTextFieldInAddMode.setText(Integer.toString(numOfSeats));;
                if (!HallsRequestHandler.checkNulls(seats,title)) {
                    messageHandler.nullValuesError();
                } else if (numOfSeats == -1) {
                    messageHandler.invalidIntValue("Кількість місць");
                } else if (hallsHandler.checkTitleForDuplicate(title)){
                    messageHandler.duplicateError("Зала");
                } else{
                    try {
                        hallsHandler.addNewHall(numOfSeats,title);
                        seatsTextFieldInAddMode.setText("");
                        titleTextFieldInAddMode.setText("");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        addNewHallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
    }




    private void changePanels() {
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
        backToHallsButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeActivePanel(hallsPanel, editHallsPanel);
            }
        });
        addHallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeActivePanel(addHallPanel, hallsPanel);

            }
        });
        changeCurrentHallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeActivePanel(editHallsPanel, hallsPanel);

            }
        });

    }

    private void changeActivePanel(JPanel newPanel, JPanel previousPanel){
        newPanel.setVisible(true);
        previousPanel.setVisible(false);
    }


}

