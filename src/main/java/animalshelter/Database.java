package animalshelter;

// DP browser for SQLite oraz sqlite-jdbc-3.16.1.jar

import animalshelter.animals.Animal;

import java.io.File;
import java.sql.*;

class Database {

    static String tableOfAnimals = "TableOfAnimals";
    static String tableOfEmails = "TableOfEmails";
    static File temp = new File("resources/DatabaseOfAnimals.db");

    private static String sender = "default";
    private static String password = "default";
    private static String recipient = "default";
    private static String buttonOnOff = "Włącz";


    static Connection connectDatabase(String database) {
        Connection connection;


        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + temp);

        } catch (Exception e) {
            System.err.println("Database connection error " + e.getMessage());
            return null;
        }
        return connection;
    }

    static void createTableOfAnimals(Connection connection, String nameOfTable) {
        Statement statement;

        try {
            statement = connection.createStatement();
            String sqlTab = "CREATE TABLE " + nameOfTable
                    + "(NAME CHAR(225),"
                    + "TYPE CHAR(225),"
                    + "MASS INT,"
                    + "HEALTH CHAR(225))";
            statement.executeUpdate(sqlTab);
            connection.close();
            System.out.println("Table Animals created correctly");

        } catch (Exception e) {
            System.err.println("Cannot create table" + e.getMessage());
        }
    }

    static void createTableOfEmails(Connection connection, String nameOfTable) {
        Statement statement;

        try {
            statement = connection.createStatement();
            String sqlTab = "CREATE TABLE " + nameOfTable
                    + "(ID CHAR (225), "
                    + "EMAIL CHAR(225), "
                    + "PASSWORD CHAR(225),"
                    + "RECIPIENT CHAR(225),"
                    + "BUTTON CHAR(225))";
            statement.executeUpdate(sqlTab);

            String sqlDefault = "INSERT INTO " + tableOfEmails + "(ID, EMAIL, PASSWORD, RECIPIENT, BUTTON )" + "VALUES (" +
                    "'MAIN'" + "," +
                    "'" + sender + "'" + "," +
                    "'" + password + "'" + "," +
                    "'" + recipient + "'" + "," +
                    "'" + buttonOnOff + "'" + ")";
            statement.executeUpdate(sqlDefault);
            connection.close();
            System.out.println("Table Email created correctly");

        } catch (Exception e) {
            System.err.println("Cannot create table" + e.getMessage());
        }
    }


    static void addValue(Animal animal, String tableOfAnimals) {
        Connection connection;
        Statement statement;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + temp);
            statement = connection.createStatement();
            String sqlAddValue = "INSERT INTO " + tableOfAnimals + "(NAME, TYPE, MASS, HEALTH)"
                    + "VALUES ("
                    + "'" + AddAnimalScene.getAnimal().getName() + "'" + ", "
                    + "'" + AddAnimalScene.getAnimal().getType() + "'" + ", "
                    + AddAnimalScene.getAnimal().getMass() + ", "
                    + "'" + AddAnimalScene.getAnimal().getHealth() + "'"
                    + " );";
            statement.executeUpdate(sqlAddValue);
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addEmail(String tableOfEmails) {

        Connection connection;
        Statement statement;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + temp);
            statement = connection.createStatement();
            String sqlAddValue = "UPDATE " + tableOfEmails
                    + " SET EMAIL = '" + EmailScene.nadawcaTxtField.getText()
                    + "', PASSWORD='" + EmailScene.paswdField.getText()
                    + "', RECIPIENT= '" + EmailScene.odbiorcaTxtField.getText()
                    + "', BUTTON= '" + EmailScene.switchButton.getText()
                    + "' WHERE ID= 'MAIN';";
            statement.executeUpdate(sqlAddValue);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static int databazeSize() {
        Connection connection;
        int count = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + temp);

            String sqlAddValue = "SELECT COUNT(*) AS name FROM " + tableOfAnimals;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlAddValue);
            rs.next();
            count = rs.getInt("name");
            rs.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    static String getSender() {
        Connection connection;
        String email = sender;
        try {
            connection = Database.connectDatabase(Database.tableOfEmails);
            String SQL = "SELECT EMAIL from " + Database.tableOfEmails;
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            email = resultSet.getString(1);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    static String getPassword() {
        Connection connection;
        String passwd = password;
        try {
            connection = Database.connectDatabase(Database.tableOfEmails);
            String SQL = "SELECT PASSWORD from " + Database.tableOfEmails;
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            passwd = resultSet.getString(1);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return passwd;
    }

    static String getRecipient() {
        Connection connection;
        String recip = recipient;
        try {
            connection = Database.connectDatabase(Database.tableOfEmails);
            String SQL = "SELECT RECIPIENT from " + Database.tableOfEmails;
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            recip = resultSet.getString(1);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recip;
    }

    static String getOnOffButton() {
        Connection connection;
        String button = buttonOnOff;
        try {
            connection = Database.connectDatabase(Database.tableOfEmails);
            String SQL = "SELECT BUTTON from " + Database.tableOfEmails;
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);

            button = resultSet.getString(1);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }
}






