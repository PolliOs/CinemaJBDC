import javax.swing.*;
import java.awt.*;
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
    private JComboBox<String> chooseHallcomboBox;
    private JButton addNewHallButton;
    private JButton backToHallsButton2;
    private JLabel seats1;
    private JLabel title1;
    private JLabel title2;
    private JLabel seats2;
    private JTextField titleTextFieldInEditMode;
    private JTextField seatsTextFieldInEditMode;
    private JButton deleteHall;
    private JButton genresEditButton;
    private JPanel moviesEditPanel;
    private JButton moviesEditButton;
    private JPanel genresEditPanel;
    private JButton backToMainFromMovies;
    private JList<String> genresList;
    private JTextField genresToAddTextField;
    private JButton addGenreButton;
    private JButton backToMoviesFromGenresEdit;
    private JLabel addNewGenreLabel;
    private JLabel existedGenres;
    private JButton deleteGenreButton;
    private JButton changeMovieButton;
    private JComboBox<String> selectMovieComboBox;
    private JList<String> genresOfMovieList;
    private JTextField movieTitleTextField;
    private JTextField yearTextField;
    private JTextField durationTextField;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton addMovieButton;
    private JLabel movieSelectedLabel;
    private JLabel movieTitleLabel;
    private JLabel yearLabel;
    private JLabel durationLabel;
    private JLabel additionLAbel;
    private JButton backToMoviesFromMoviesEdit;
    private JButton deleteMovieButton;
    private HallsRequestHandler hallsHandler;
    private  MessageHandler messageHandler;
    private  Statement statement;
    private  Connection connection;
    private GenresRequestHandler genresHandler;
    private MoviesRequestHandler moviesHandler;
    private  int[] select;
    private int ind = 0;



    public MainViewWindow(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
        initialize();
        buttonsProcessing();
        //TODO(change this, when the movies selected will be ready)
        genresOfMovieList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int minSelectionIndex = genresOfMovieList.getMinSelectionIndex();
                int maxSelectionIndex = genresOfMovieList.getMaxSelectionIndex();

                // int ind = 0;
                ArrayList<Integer> newSelectedItems;
                newSelectedItems = new ArrayList<>();
                newSelectedItems.add(1);
                newSelectedItems.add(3);
                newSelectedItems.add(5);
                // System.out.println("Converting ArrayList to Array" );
                for (int i = minSelectionIndex; i <= maxSelectionIndex; i++) {
                    if (genresOfMovieList.isSelectedIndex(i)) {
                       // Object elementAt = model.getElementAt(i);

                        if( i == 1){
                            newSelectedItems.remove(0);
                        }else{
                            newSelectedItems.add(i);
                        }
                        //System.out.print(elementAt);
                        // select[ind++] = i;
                    }
                }
                // Integer[] selected = newSelectedItems.toArray(new Integer[newSelectedItems.size()]);
                select = newSelectedItems.stream().mapToInt(i -> i).toArray();
                genresOfMovieList.setSelectedIndices(select);
            }
        });
    }

    private void initialize(){
        hallsHandler = new HallsRequestHandler(statement, connection);
        genresHandler = new GenresRequestHandler(statement, connection);
        messageHandler = new MessageHandler();
        moviesHandler = new MoviesRequestHandler(statement,connection);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new CardLayout(0,0));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height *= 0.8;
        screenSize.width *= 0.8;
        frame.setSize(screenSize);
        changePanels();
        addPanelsToTheFrame();
        updateGenresList();
        updateGenresOfMovieList();
    }


    private void updateGenresList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        genresList.setModel(model);
        ArrayList<String> genresArrayList = genresHandler.getListOfTitles();
        String[] genres = genresArrayList.toArray(new String[0]);
        for (int i = 0; i < genres.length; i++) {
            model.add(i, genres[i]);
        }
    }
    private void updateGenresOfMovieList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        genresOfMovieList.setCellRenderer(new CheckboxListCellRenderer());
        ArrayList<String> genresArrayList = genresHandler.getListOfTitles();
        String[] genres = genresArrayList.toArray(new String[0]);
        for (int i = 0; i < genres.length; i++) {
            model.add(i, genres[i]);
        }
        genresOfMovieList.setModel(model);
        genresOfMovieList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        select = new int[]{1,2,3};
      //  genresOfMovieList.addListSelectionListener(new SharedListSelectionHandler());
        genresOfMovieList.setSelectedIndices(select);

       // ListSelectionDocument listSelectionDocument = new ListSelectionDocument();
      //  genresOfMovieList.addListSelectionListener(listSelectionDocument);

    }

    private void buttonsProcessing() {
        saveNewHallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String seats = seatsTextFieldInAddMode.getText();
                String title = titleTextFieldInAddMode.getText();
                int numOfSeats = HallsRequestHandler.checkInt(seats);
                seatsTextFieldInAddMode.setText(Integer.toString(numOfSeats));
                if (!HallsRequestHandler.checkNulls(seats,title)) {
                    messageHandler.nullValuesError();
                } else if (numOfSeats == -1) {
                    messageHandler.invalidIntValue("Кількість місць");
                } else if (hallsHandler.findDuplicate(title)){
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

        chooseHallcomboBox.addActionListener(event -> {
            String selectedHallTitle = (String) chooseHallcomboBox.getSelectedItem();
            updateTitleAndSeatsFields(selectedHallTitle);
        });
        selectMovieComboBox.addActionListener(event -> {
            String selectedHallTitle = (String) selectMovieComboBox.getSelectedItem();
            updateYearAndDurationFields(selectedHallTitle);
        });
        addNewHallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String prevHall = (String) chooseHallcomboBox.getSelectedItem();
                String newTitle = titleTextFieldInEditMode.getText();
                String newNumOfSeats = seatsTextFieldInEditMode.getText();
                if(!hallsHandler.findDuplicate(newTitle) || newTitle.equals(prevHall)){
                    hallsHandler.updateHall(prevHall, newTitle, newNumOfSeats);
                    updateSelectedHallComboBox();
                }else{
                    messageHandler.duplicateError("Зала");
                }
            }
        });
        deleteHall.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String title = titleTextFieldInEditMode.getText();
                if(messageHandler.confirmDeleteHall() == 0) {
                    if (hallsHandler.deleteHall(hallsHandler.getIntValueByTitle(title, "id")) == 1) {
                        updateSelectedHallComboBox();
                    }
                }

            }
        });
        addGenreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String newGenre = genresToAddTextField.getText();
                if(newGenre.isEmpty()){
                    messageHandler.emptyToAddError();
                }else if(genresHandler.findDuplicate(newGenre)) {
                    messageHandler.duplicateError("Жанр");
                }else{
                    genresHandler.addNewGenre(newGenre);
                    updateGenresList();
                    genresToAddTextField.setText("");
                }
            }
        });

        deleteGenreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String selectedGenre =genresList.getSelectedValue();
                if(selectedGenre==null) {
                    messageHandler.unselectedError();
                }else{
                    if(messageHandler.confirmDeleteGenre()==0){
                        if(genresHandler.deleteGenre(selectedGenre)==1) {
                            updateGenresList();
                        }
                    }
                }
            }
        });
    }




    private void changePanels() {
        hallsButton.addActionListener(e -> changeActivePanel(hallsPanel, mainPanel));
        moviesButton.addActionListener(e -> changeActivePanel(moviesPanel, mainPanel));
        sessionsButton.addActionListener(e -> changeActivePanel(sessionsPanel, mainPanel));
        backToMainButton1.addActionListener(e -> changeActivePanel(mainPanel, hallsPanel));
        backToHallsButton1.addActionListener(e -> changeActivePanel(hallsPanel, addHallPanel));
        backToHallsButton2.addActionListener(e -> changeActivePanel(hallsPanel, editHallsPanel));
        addHallButton.addActionListener(e -> changeActivePanel(addHallPanel, hallsPanel));
        changeCurrentHallButton.addActionListener(e -> {
            changeActivePanel(editHallsPanel, hallsPanel);
            updateSelectedHallComboBox();

        });
        moviesButton.addActionListener(e -> changeActivePanel(moviesPanel, mainPanel));
        moviesEditButton.addActionListener(e -> {
            changeActivePanel(moviesEditPanel, moviesPanel);
            updateMoviesTitleComboBox();
        });
        genresEditButton.addActionListener(e -> changeActivePanel(genresEditPanel, moviesPanel));
        backToMainFromMovies.addActionListener(e -> changeActivePanel(mainPanel, moviesPanel));
        backToMoviesFromGenresEdit.addActionListener(e -> changeActivePanel(moviesPanel, genresEditPanel));
        backToMoviesFromMoviesEdit.addActionListener(e -> changeActivePanel(moviesPanel, moviesEditPanel));
    }
    private void updateSelectedHallComboBox() {
        ArrayList<String> halls =
                hallsHandler.getListOfTitles();
        chooseHallcomboBox.removeAllItems();
        for(String hallTitle: halls){
            chooseHallcomboBox.addItem(hallTitle);
        }
        String selected = (String) chooseHallcomboBox.getSelectedItem();
        updateTitleAndSeatsFields(selected);
    }
    private void updateMoviesTitleComboBox() {
        ArrayList<String> movies =
                moviesHandler.getListOfTitles();
        selectMovieComboBox.removeAllItems();
        for(String movieTitle: movies){
            selectMovieComboBox.addItem(movieTitle);
        }
        String selected = (String) selectMovieComboBox.getSelectedItem();
        updateYearAndDurationFields(selected);
    }

    private void updateYearAndDurationFields(String movie) {
        int year = moviesHandler.getIntValueByTitle(movie, "year");
        int duration = moviesHandler.getIntValueByTitle(movie, "duration");
        yearTextField.setText(String.valueOf(year));
        durationTextField.setText(String.valueOf(duration));
        movieTitleTextField.setText(movie);
    }

    private void changeActivePanel(JPanel newPanel, JPanel previousPanel){
        newPanel.setVisible(true);
        previousPanel.setVisible(false);
    }
    private void updateTitleAndSeatsFields(String title) {
        int numOfSeats = hallsHandler.getIntValueByTitle(title, "seats");
        titleTextFieldInEditMode.setText(title);
        seatsTextFieldInEditMode.setText(Integer.toString(numOfSeats));
    }
    private void addPanelsToTheFrame() {
        frame.getContentPane().add(mainPanel, "name_1");
        frame.getContentPane().add(hallsPanel, "name_2");
        frame.getContentPane().add(moviesPanel, "name_3");
        frame.getContentPane().add(sessionsPanel, "name_4");
        addHallPanel.setBackground(new Color(40,111,129));
        frame.getContentPane().add(addHallPanel, "name_5");
        frame.getContentPane().add(editHallsPanel, "name_6");
        frame.getContentPane().add(moviesEditPanel, "name_7");
        frame.getContentPane().add(genresEditPanel, "name_8");
    }




}



