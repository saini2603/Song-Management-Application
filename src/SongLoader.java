//Name: Rohit Saini
//ID: 2122294

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.*;
import java.util.*;

public class SongLoader {


    public static List<Song> loadCSV(String filename) {
        LinkedList<Song> listSongs=new LinkedList<>();
        Song objectSong;
        try {

            InputStreamReader fileread=new FileReader("Data/" +filename);
            BufferedReader buffReader = new BufferedReader(fileread);
            String lineRead = buffReader.readLine();

            while (lineRead != null) {

                String[] values = lineRead.split(",");
                objectSong = new Song(Integer.parseInt(values[0]), values[1], values[2], Integer.parseInt(values[3]));
                listSongs.add(objectSong);
                lineRead = buffReader.readLine();
            }
        }
        catch(Exception e) {

            System.out.println("Error in finding the file");
            e.fillInStackTrace();
            return null;
        }
        return listSongs;
    }

    public  static  void saveCSV(LinkedList<Song> list,String fname) {

        Song objectSong = new Song();
        File log = new File("Data/"+fname);
        try{
            if(!log.exists()){
                System.out.println("We had to make a new file.");
                log.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(log, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Song sng :list) {
                // bufferedWriter.write("\n");
                bufferedWriter.write(String.valueOf(sng));
            }
            bufferedWriter.close();

            System.out.println("Done");
        } catch(IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }
}




