package animalshelter;
/*
-działa autoemail, do zrobienia: odawania zwierzat o tym samym imieniu, sciagnąc do listy, przemapowac liste, gdy znajdzie imie
=nazwazwierzeciatextfield, to powiadoka
 */


import animalshelter.animals.Animal;
import animalshelter.animals.Degu;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class TestsAndNotesClass {
    String s = "141";
    String path1 = "lista.txt";
    ArrayList<Animal> lista = new ArrayList<>();


//
//    void importList() {
//        try {
//
//
//            String sep = ":";
//            FileWriter writer = new FileWriter("output.txt");
//            for(Animal str: databaseNamesToList() ) {
//                writer.write(String.valueOf(str.getMass()+sep+str.getName()+sep+str.getHealth()+sep+
//                        str.getType()+System.lineSeparator()));
//
//            }
//            writer.close();
//
//        } catch (IOException e){
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
