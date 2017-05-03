package animalshelter;
/*
-działa autoemail, do zrobienia: odawania zwierzat o tym samym imieniu, sciagnąc do listy, przemapowac liste, gdy znajdzie imie
=nazwazwierzeciatextfield, to powiadoka
 */

import animalshelter.animals.Animal;
import animalshelter.animals.Dog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestsAndNotesClass {


    public static void main(String[] args) throws SQLException {
        TestsAndNotesClass tak = new TestsAndNotesClass();

        for (int i = 0; i <tak.rzecz().size() ; i++) {
            System.out.println(tak.rzecz().get(i).getHealth());
        }

    }
    List<Animal> rzecz() throws SQLException {
        String sender = null;
        Connection connection;
        String email = sender;

            connection = Database.connectDatabase(Database.tableOfAnimals);
            String SQL = "SELECT NAME, HEALTH from " + Database.tableOfAnimals;
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            List<Animal> animals = new ArrayList<>();
            while (resultSet.next()) {
                animals.add(mapToAnimal(resultSet));
            }

            connection.close();


            return  animals;
        }


     Animal mapToAnimal (ResultSet resultSet) throws SQLException {
       Animal dog = new Dog();
       dog.setName(resultSet.getString("NAME"));
       dog.setHealth(resultSet.getString("HEALTH"));
        return  dog;
    }
}

