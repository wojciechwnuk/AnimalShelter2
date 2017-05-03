//package animalshelter;
///*
//-działa autoemail, do zrobienia: odawania zwierzat o tym samym imieniu, sciagnąc do listy, przemapowac liste, gdy znajdzie imie
//=nazwazwierzeciatextfield, to powiadoka
// */
//
//import animalshelter.animals.Animal;
//import animalshelter.animals.Cat;
//import animalshelter.animals.Dog;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class TestsAndNotesClass {
//
//
//
//    Animal michal2 = new Dog("1", "1", "1", "1");
//
//
//    public static void main(String[] args) throws SQLException {
//        TestsAndNotesClass tak = new TestsAndNotesClass();
//
//        for (int i = 0; i <tak.rzecz().size() ; i++) {
//            System.out.println(tak.rzecz().get(i).getHealth());
//        }
//
//        TestsAndNotesClass testsAndNotesClass = new TestsAndNotesClass();
//
//        System.out.println(testsAndNotesClass.michal2.equals(Cat.michal2));
//        System.out.println(testsAndNotesClass.michal2.hashCode());
//        System.out.println(Cat.michal2.hashCode());
//        if(testsAndNotesClass.michal2.hashCode()!= testsAndNotesClass.michal2.hashCode()){
//            System.out.println(true);
//        }
//
//
//    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        TestsAndNotesClass that = (TestsAndNotesClass) o;
//
//        return michal2.equals(that.michal2);
//    }
//
//    @Override
//    public int hashCode() {
//        return michal2.hashCode();
//    }
//
//    void porownaj() {
//
//
//        System.out.println(equals(michal2));
//        System.out.println(hashCode());
//    }
//
////
////    List<Animal> rzecz() throws SQLException {
////        String sender = null;
////        Connection connection;
////        String email = sender;
////
////        connection = Database.connectDatabase(Database.tableOfAnimals);
////        String SQL = "SELECT NAME, HEALTH from " + Database.tableOfAnimals;
////        assert connection != null;
////        ResultSet resultSet = connection.createStatement().executeQuery(SQL);
////
////        List<Animal> animals = new ArrayList<>();
////        while (resultSet.next()) {
////            animals.add(mapToAnimal(resultSet));
////        }
////
////        connection.close();
////
////
////        return animals;
////    }
//
//
////    Animal mapToAnimal(ResultSet resultSet) throws SQLException {
////        Animal dog = new Dog();
////        Animal michal = new Dog("1", "1", "1", "1");
////        Animal michal1 = new Dog("1", "1", "1", "1");
////
////
////        dog.setName(resultSet.getString("NAME"));
////        dog.setHealth(resultSet.getString("HEALTH"));
////        return dog;
////    }
//}
//
