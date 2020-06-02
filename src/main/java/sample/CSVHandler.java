package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CSVHandler {


    public void writeToCSV (Game_Entry game_entry, String path) throws IOException {

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
        pw.println(game_entry.getHero_name() + "," + game_entry.getDamage() + "," + game_entry.getKills() + "," + game_entry.getPosition());
        pw.flush();
        pw.close();
    }

    public ArrayList <Game_Entry> readFromCSV (String path) {

        ArrayList <Game_Entry> game_entries = new ArrayList <>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                //Read Values From The Line
                String[] values = line.split(",");

                //Specify the game entry parameters
                String name = values[0];
                int damage = Integer.parseInt(values[1]);
                int kills = Integer.parseInt(values[2]);
                int position = Integer.parseInt(values[3]);

                //Create a new Game_entry
                Game_Entry game_entry = new Game_Entry(name, damage, kills, position);

                //Add the game entry to the List
                game_entries.add(game_entry);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("reading");
        for (Game_Entry g : game_entries)
            System.out.println(g);
        return game_entries;
    }

    public void eraseLast (int index) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(Controller.CSV_FILE_PATH));
        index++;

        StringBuilder sb = new StringBuilder();
        int lineNumber = 1;
        String line;

        while ((line = br.readLine()) != null) {
            if (lineNumber != index) {
                if (lineNumber != index) {
                    sb.append(line).append("\n");
                }
                lineNumber++;
            }

            br.close();

            FileWriter fw = new FileWriter(new File(Controller.CSV_FILE_PATH));
            fw.write(sb.toString());
            fw.close();

        }
    }
}
