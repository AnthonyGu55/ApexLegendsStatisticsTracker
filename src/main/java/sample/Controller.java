package sample;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller implements EventHandler <ActionEvent>, Initializable {



    @FXML
    private ChoiceBox <String> hero_name_box;

    @FXML
    private TextField damage_field;

    @FXML
    private TextField kills_field;

    @FXML
    private TextField position_field;

    @FXML
    private Button add_button;

    @FXML
    private Button remove_button;

    @FXML
    private TableView <Game_Entry> table;

    @FXML
    private TableColumn <Game_Entry, String> hero_name_column;

    @FXML
    private TableColumn <Game_Entry, Integer> damage_column;

    @FXML
    private TableColumn <Game_Entry, Integer> kills_column;

    @FXML
    private TableColumn <Game_Entry, Integer> position_column;

    @FXML
    private LineChart <Number, Number> line_chart;

    @FXML
    private NumberAxis x_Axis;

    @FXML
    private NumberAxis y_Axis;

    @FXML
    private BorderPane main_pane;

    CSVHandler csvHandler;

    public ArrayList <Game_Entry> main_game_entries;

    public ObservableList <Game_Entry> observable_game_entries;

    public String[] heroes = new String[]{"Bangalore", "Bloodhound", "Caustic", "Crypto", "Gibraltar", "Lifeline", "Loba", "Mirage", "Octane", "Pathfinder", "Revenant", "Watson", "Wraith"};

    public static final String CSV_FILE_PATH = "C:\\Users\\Antoni\\Programming\\IdeaProjects\\ApexStatsJavaFX\\game_entries.csv";

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        csvHandler        = new CSVHandler();
        main_game_entries = csvHandler.readFromCSV(CSV_FILE_PATH);

        observable_game_entries = FXCollections.observableArrayList(main_game_entries);

        //Table setup
        setUpTable(observable_game_entries);

        //Save Button
        add_button.setOnAction(this);

        //Remove button
        remove_button.setOnAction(this);

        hero_name_box.getItems().addAll(heroes);

        hero_name_box.setValue((observable_game_entries.get(observable_game_entries.size() - 1)).getHero_name());

        XYChart.Series <Number, Number> data = new XYChart.Series <>();
        data.setName("Damage");
        int i = 0;

        for (Game_Entry g : observable_game_entries) {
            data.getData().add(new XYChart.Data <Number, Number>(i, g.getDamage()));
            i++;
        }
        line_chart.getData().add(data);


    }


    //Handles button events
    @Override
    public void handle (ActionEvent actionEvent) {

        if (actionEvent.getSource() == add_button) {
            saveToArrayAddtoTable(actionEvent);
        }

        if (actionEvent.getSource() == remove_button) {
            try {
                removeSelectedItem();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void removeSelectedItem () throws IOException {

        Game_Entry tmpgame_Entry = (Game_Entry) table.getSelectionModel().getSelectedItem();
        int index = observable_game_entries.indexOf(tmpgame_Entry) + 1;

        boolean answer = ConfirmBox.display("Remove last entry", "Are you sure you want to remove this entry?\n\tHero: " + tmpgame_Entry.getHero_name() + "\n\tDamage: " + tmpgame_Entry.getDamage() + "\n\tKills: " + tmpgame_Entry.getKills() + "\n\tPosition: " + tmpgame_Entry.getPosition());

        if (answer) {
            observable_game_entries.remove(tmpgame_Entry);
            table.getItems().remove(tmpgame_Entry);
            System.out.println(index);
            csvHandler.eraseLast(index);
        }
    }

    private void saveToArrayAddtoTable (ActionEvent actionEvent) {
        String hero_name = hero_name_box.getValue();
        try {
            int damage = Integer.parseInt(damage_field.getText());
            int kills = Integer.parseInt(kills_field.getText());
            int position = Integer.parseInt(position_field.getText());

            boolean answer = ConfirmBox.display("New Entry", "Are you sure you want to add this entry?\n\tHero: " + hero_name + "\n\tDamage: " + damage + "\n\tKills: " + kills + "\n\tPosition: " + position);
            if (answer) {
                Game_Entry tmpGame_Entry = new Game_Entry(hero_name, damage, kills, position);
                observable_game_entries.add(tmpGame_Entry);

                damage_field.setText("");
                kills_field.setText("");
                position_field.setText("");

                csvHandler.writeToCSV(tmpGame_Entry, CSV_FILE_PATH);
                table.getItems().add(tmpGame_Entry);
            }
            actionEvent.consume();
        }
        catch (NumberFormatException | IOException exception) {
            AlertBox.display("Wrong input", "Error: You entered a wrong value, which wasn't a number");
            actionEvent.consume();
        }
    }

    private void closeProgram () {
        Stage stage = (Stage) hero_name_box.getScene().getWindow();
        stage.close();
    }

    public void addArray (ChoiceBox <String> hero_name_box, String[] heroes) {
        for (String s : heroes) {
            hero_name_box.getItems().add(s);
        }
    }


    public void setUpTable (ObservableList <Game_Entry> game_entries) {

        //Hero name column
        hero_name_column.setCellValueFactory(new PropertyValueFactory <>("hero_name"));

        //Damage column
        damage_column.setCellValueFactory(new PropertyValueFactory <>("damage"));

        //Hero name column
        kills_column.setCellValueFactory(new PropertyValueFactory <>("kills"));

        //Hero name column
        position_column.setCellValueFactory(new PropertyValueFactory <>("position"));

        //TableView
        table.setItems(game_entries);
        /*table.getColumns().addAll(hero_name_column, damage_column, kills_column, position_column);*/
    }

    public static ObservableList <Game_Entry> getGame_entries (ArrayList <Game_Entry> game_entries) {
        ObservableList <Game_Entry> game_entryObservableList = FXCollections.observableArrayList(game_entries);
        game_entryObservableList.addAll();

        return game_entryObservableList;
    }
}