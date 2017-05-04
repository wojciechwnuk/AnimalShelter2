package animalshelter;
/*
-działa autoemail, do zrobienia: odawania zwierzat o tym samym imieniu, sciagnąc do listy, przemapowac liste, gdy znajdzie imie
=nazwazwierzeciatextfield, to powiadoka
 */


import animalshelter.animals.Animal;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class TestsAndNotesClass {
    String s = "141";
    String path1 = "lista.ser";
    AddAnimalScene addAnimalScene = new AddAnimalScene();

    public static void main(String[] args) throws SQLException {
        TestsAndNotesClass testsAndNotesClass = new TestsAndNotesClass();
        testsAndNotesClass.importList();
    }


    void importList() {
        try {
            FileOutputStream fout = new FileOutputStream(path1);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(addAnimalScene.databaseNamesToList());
            oos.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
