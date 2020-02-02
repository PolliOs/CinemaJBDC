package ViewWindow;

import Handlers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MainViewWindow extends  JFrame {
    public JFrame frame;
    private  JPanel adminPanel;
    private  JPanel hallsPanel;
    private JPanel mainPanel;
    private JButton hallsButton;
    private JButton moviesButton;
    private JButton sessionsButton;
    private JPanel sessionsPanel;
    private JButton backToMainButtonFromHallsButton;
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
    private JButton changeHallButton;
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
    private JButton backToMainFromMoviesButton;
    private JList<String> genresList;
    private JTextField genresToAddTextField;
    private JButton addGenreButton;
    private JButton backToMoviesFromGenresEditButton;
    private JLabel addNewGenreLabel;
    private JLabel existedGenres;
    private JButton deleteGenreButton;
    private JButton changeMovieButton;
    private JComboBox<String> selectMovieComboBox;
    private JList<String> genresOfMovieList;
    private JTextField movieTitleTextField;
    private JTextField yearTextField;
    private JTextField durationTextField;
    private JTextField addNewMovieTitleTextField;
    private JTextField addYearMovieTextField;
    private JTextField addDurationTextTittle;
    private JButton addMovieButton;
    private JLabel movieSelectedLabel;
    private JLabel movieTitleLabel;
    private JLabel yearLabel;
    private JLabel durationLabel;
    private JLabel additionLAbel;
    private JButton backToMoviesFromMoviesEditButton;
    private JButton deleteMovieButton;
    private JTable sessionsTable;
    private JButton saveChangesInTableButton;
    private JButton backToMainFromSessionsButton;
    private JButton addRowButton;
    private JButton helpButton;
    private HallsRequestHandler hallsHandler;
    private MessageHandler messageHandler;
    private  Statement statement;
    private  Connection connection;
    private GenresRequestHandler genresHandler;
    private MoviesRequestHandler moviesHandler;
    private  int[] select;
    private TableCreationHandler tableCreationHandler;
    private SessionsRequestHandler sessionsHandler;



    public MainViewWindow(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
        initialize();
        buttonsProcessing();
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                messageHandler.helpMessage();
            }
        });
    }

    private void initialize(){
        hallsHandler = new HallsRequestHandler(statement, connection);
        genresHandler = new GenresRequestHandler(statement, connection);
        messageHandler = new MessageHandler();
        moviesHandler = new MoviesRequestHandler(statement,connection);
        tableCreationHandler = new TableCreationHandler(connection);
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
        setGenresOfMovieList();
        setTableModel();
    }

    private void setTableModel() {
        sessionsTable.setModel(tableCreationHandler.table);
        tableCreationHandler.initColumnSizes(sessionsTable);
        updateSessionTableView();
        sessionsTable.setFillsViewportHeight(true);
        mainPanel.setOpaque(true);
        sessionsTable.setVisible(true);
        sessionsHandler = new SessionsRequestHandler(statement,connection,sessionsTable);
        sessionsTable.setBorder(BorderFactory.createEmptyBorder());
    }


    private void updateGenresList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        genresList.setModel(model);
        ArrayList<String> genresArrayList = genresHandler.getListOf("title");
        String[] genres = genresArrayList.toArray(new String[0]);
        for (int i = 0; i < genres.length; i++) {
            model.add(i, genres[i]);
        }
    }
    private void setGenresOfMovieList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        genresOfMovieList.setCellRenderer(new CheckboxListCellRenderer());
        ArrayList<String> genresArrayList = genresHandler.getListOf("title");
        String[] genres = genresArrayList.toArray(new String[0]);
        for (int i = 0; i < genres.length; i++) {
            model.add(i, genres[i]);
        }
        genresOfMovieList.setModel(model);
        genresOfMovieList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    private void updateSelectedGenresOfMovieList() {
        updateSelectedGenresOfMovieId();
        select = moviesHandler.currentSelectedGenresId.stream().mapToInt(i -> i).toArray();
        genresOfMovieList.setSelectedIndices(select);
    }

    private void buttonsProcessing() {
        saveNewHallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String seats = seatsTextFieldInAddMode.getText();
                String title = titleTextFieldInAddMode.getText();
                int numOfSeats = hallsHandler.checkInt(seats);
                seatsTextFieldInAddMode.setText(Integer.toString(numOfSeats));
                if (hallsHandler.checkNulls(new String[]{seats, title})) {
                    messageHandler.nullValuesError();
                    seatsTextFieldInAddMode.setText("");
                } else if (numOfSeats == -1) {
                    messageHandler.invalidIntValue("Кількість місць");
                    seatsTextFieldInAddMode.setText("");
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
            String selectedMovieTitle = (String) selectMovieComboBox.getSelectedItem();
            updateYearAndDurationFields(selectedMovieTitle);
            moviesHandler.setGenresOfMovie(selectedMovieTitle);
            updateSelectedGenresOfMovieId();
            updateSelectedGenresOfMovieList();
        });
        changeHallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String prevHall = (String) chooseHallcomboBox.getSelectedItem();
                String newTitle = titleTextFieldInEditMode.getText();
                String newNumOfSeats = seatsTextFieldInEditMode.getText();
                if(hallsHandler.checkNulls(new String[]{newTitle, newNumOfSeats})){
                    messageHandler.emptyToAddError();
                }else if(!hallsHandler.findDuplicate(newTitle) || newTitle.equals(prevHall)){
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
                    if (hallsHandler.deleteHall(hallsHandler.getValueByTitle(title, "id")) == 1) {
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
                    if(messageHandler.confirmDeleteValue("жанр")==0){
                        if(genresHandler.deleteGenre(selectedGenre)==1) {
                            updateGenresList();
                        }
                    }
                }
            }
        });

        genresOfMovieList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int minSelectionIndex = genresOfMovieList.getMinSelectionIndex();
                int maxSelectionIndex = genresOfMovieList.getMaxSelectionIndex();
                for (int i = minSelectionIndex; i <= maxSelectionIndex; i++) {
                    if (genresOfMovieList.isSelectedIndex(i)) {
                        if(moviesHandler.currentSelectedGenresId.contains(i)){
                            moviesHandler.currentSelectedGenresId.remove(i);
                            String id = genresHandler.getValueByTitle(genresOfMovieList.getSelectedValue(), "id");
                            moviesHandler.currentSelectedGenresForMovie.remove(Integer.parseInt(id));
                        }else{
                            String id = genresHandler.getValueByTitle(genresOfMovieList.getSelectedValue(), "id");
                            moviesHandler.currentSelectedGenresForMovie.add(Integer.valueOf(id));
                            moviesHandler.currentSelectedGenresId.add(i);
                        }
                    }
                }
                select = moviesHandler.currentSelectedGenresId.stream().mapToInt(i -> i).toArray();
                genresOfMovieList.setSelectedIndices(select);
            }
        });

        changeMovieButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String currMovieTitle = (String) selectMovieComboBox.getSelectedItem();
                String newTitle = movieTitleTextField.getText();
                String year = yearTextField.getText();
                String duration = durationTextField.getText();
                if(moviesHandler.findChanges(currMovieTitle, newTitle,year, duration )){
                    if(messageHandler.confirmChanges()==0){
                        if(moviesHandler.checkNulls(new String[]{newTitle, year, duration})){
                            messageHandler.emptyToAddError();
                        }
                        else {
                            moviesHandler.changeMovie(currMovieTitle, newTitle, year, duration);
                            updateMoviesTitleComboBox();
                            updateSelectedGenresOfMovieList();
                        }
                    }
                }
            }
        });

        addMovieButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String newTitle = addNewMovieTitleTextField.getText();
                String year = addYearMovieTextField.getText();
                String duration = addDurationTextTittle.getText();

                if (moviesHandler.checkNulls(new String[]{newTitle, year, duration})) {
                    messageHandler.nullValuesError();
                } else {
                    if(moviesHandler.findDuplicate(newTitle)) {
                        messageHandler.duplicateError("Назва");
                    }else{
                        tryToChangeAddMovieFields(year, duration, newTitle);
                    }
                }
            }

            private void tryToChangeAddMovieFields(String year, String duration, String newTitle) {
                int yearInt = moviesHandler.checkInt(year);
                if(yearInt < 1900 || yearInt > 2020){
                    messageHandler.invalidIntValue("Рік");
                    return;
                }
                int durationInt = moviesHandler.checkInt(duration);
                addDurationTextTittle.setText(String.valueOf(durationInt));
                if(durationInt < 0 || durationInt > 1000){
                    messageHandler.invalidIntValue("Тривалість");
                    return;
                }
                moviesHandler.addNewMovie(newTitle, year,duration);
                updateSelectedGenresOfMovieList();
                updateMoviesTitleComboBox();
                addNewMovieTitleTextField.setText("");
                addYearMovieTextField.setText("");
                addDurationTextTittle.setText("");
            }
        });
        deleteMovieButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(messageHandler.confirmDeleteMovie()==0){
                    moviesHandler.deleteMovie((String) selectMovieComboBox.getSelectedItem());
                    updateMoviesTitleComboBox();
                }

            }
        });
        addRowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tableCreationHandler.insertRow();
                updateSessionTableView();
            }
        });
        saveChangesInTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sessionsHandler.deleteRows(sessionsTable);
                sessionsHandler.applyChanges(sessionsTable,tableCreationHandler.table.changedRows);
                tableCreationHandler.initializeTable();
                updateSessionTableView();
            }
        });
    }


    private void updateSelectedGenresOfMovieId() {
        String[] genres = genresHandler.getListOf("title").toArray(new String[0]);
        Integer[] genresId = new Integer[genres.length];
        for(int i = 0; i < genres.length; i++){
            genresId[i] = Integer.valueOf(genresHandler.getValueByTitle(genres[i], "id"));
        }
        moviesHandler.currentSelectedGenresId(genresId);
    }

    private void changePanels() {
        PanelsProcessing(hallsButton, hallsPanel, mainPanel, moviesButton, moviesPanel, backToMainButtonFromHallsButton, backToHallsButton1, hallsPanel, addHallPanel);
        backToHallsButton2.addActionListener(e -> changeActivePanel(hallsPanel, editHallsPanel));
        addHallButton.addActionListener(e -> changeActivePanel(addHallPanel, hallsPanel));
        changeCurrentHallButton.addActionListener(e -> {
            changeActivePanel(editHallsPanel, hallsPanel);
            updateSelectedHallComboBox();

        });
        moviesButton.addActionListener(e -> changeActivePanel(moviesPanel, mainPanel));
        moviesEditButton.addActionListener(e -> {
            changeActivePanel(moviesEditPanel, moviesPanel);
            setGenresOfMovieList();
            updateMoviesTitleComboBox();
            updateSelectedGenresOfMovieList();
        });
        sessionsButton.addActionListener(e->{
            changeActivePanel(sessionsPanel,mainPanel);
            tableCreationHandler.initializeTable();
            updateSessionTableView();
        });
        backToMainFromSessionsButton.addActionListener(e-> changeActivePanel(mainPanel,sessionsPanel));
        PanelsProcessing(genresEditButton, genresEditPanel, moviesPanel, backToMainFromMoviesButton, mainPanel, backToMoviesFromGenresEditButton, backToMoviesFromMoviesEditButton, moviesPanel, moviesEditPanel);
    }
    private void updateSessionTableView(){
        tableCreationHandler.setUpMovieColumn(sessionsTable.getColumnModel().getColumn(sessionsTable.getColumn("Фільм").getModelIndex()), moviesHandler.getListOf("title"));
        tableCreationHandler.setUpHallsColumn(sessionsTable.getColumnModel().getColumn(sessionsTable.getColumn("Зал").getModelIndex()), hallsHandler.getListOf("title"));
        tableCreationHandler.setUpDaysColumn(sessionsTable.getColumnModel().getColumn(sessionsTable.getColumn("День").getModelIndex()));
    }

    private void PanelsProcessing(JButton hallsButton, JPanel hallsPanel, JPanel mainPanel, JButton moviesButton, JPanel moviesPanel, JButton backToMainButtonFromHallsButton, JButton backToHallsButton1, JPanel hallsPanel2, JPanel addHallPanel) {
        hallsButton.addActionListener(e -> changeActivePanel(hallsPanel, mainPanel));
        moviesButton.addActionListener(e -> changeActivePanel(moviesPanel, mainPanel));
        backToMainButtonFromHallsButton.addActionListener(e -> changeActivePanel(mainPanel, hallsPanel));
        backToHallsButton1.addActionListener(e -> changeActivePanel(hallsPanel2, addHallPanel));
    }

    private void updateSelectedHallComboBox() {
        ArrayList<String> halls =
                hallsHandler.getListOf("title");
        chooseHallcomboBox.removeAllItems();
        for(String hallTitle: halls){
            chooseHallcomboBox.addItem(hallTitle);
        }
        String selected = (String) chooseHallcomboBox.getSelectedItem();
        updateTitleAndSeatsFields(selected);
    }
    private void updateMoviesTitleComboBox() {
        ArrayList<String> movies =
                moviesHandler.getListOf("title");
        selectMovieComboBox.removeAllItems();
        for(String movieTitle: movies){
            selectMovieComboBox.addItem(movieTitle);
        }
        String selected = (String) selectMovieComboBox.getSelectedItem();
        updateYearAndDurationFields(selected);
    }

    private void updateYearAndDurationFields(String movie) {
        String year = moviesHandler.getValueByTitle(movie, "year");
        String duration = moviesHandler.getValueByTitle(movie, "duration");
        yearTextField.setText(year);
        durationTextField.setText(duration);
        movieTitleTextField.setText(movie);
    }

    private void changeActivePanel(JPanel newPanel, JPanel previousPanel){
        newPanel.setVisible(true);
        previousPanel.setVisible(false);
    }
    private void updateTitleAndSeatsFields(String title) {
        String numOfSeats = hallsHandler.getValueByTitle(title, "seats");
        titleTextFieldInEditMode.setText(title);
        seatsTextFieldInEditMode.setText(numOfSeats);
    }
    private void addPanelsToTheFrame() {
        frame.getContentPane().add(mainPanel, "name_1");
        frame.getContentPane().add(hallsPanel, "name_2");
        frame.getContentPane().add(moviesPanel, "name_3");
        frame.getContentPane().add(sessionsPanel, "name_4");
        frame.getContentPane().add(addHallPanel, "name_5");
        frame.getContentPane().add(editHallsPanel, "name_6");
        frame.getContentPane().add(moviesEditPanel, "name_7");
        frame.getContentPane().add(genresEditPanel, "name_8");
    }




}



