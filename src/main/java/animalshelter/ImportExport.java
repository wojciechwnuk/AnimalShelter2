package animalshelter;

import animalshelter.animals.Animal;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;


class ImportExport {

    private String path = "resources/listOfPlaces.txt";


    void saveList() {
        try {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(EditFreeSpaceScene.places);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadList() {
        if (path!=null){

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            Integer PersonListFromFile = (Integer) in.readObject();
            in.close();
            EditFreeSpaceScene.places = PersonListFromFile;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }}
}
    String path1 = "lista.ser";

 }
