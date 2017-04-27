package sample;

import java.sql.*;


public class SQLitePrzyklad {

    public static void main(String[] args) {
        // Nazwa bazy
        String baza = "organizmy";
        // Wywołanie metody polacz, która zwraca obiekt typu Connection
        Connection polaczenie = polacz(baza);

    }

    /**
     * Metoda odpowiedzialna za połączenie z bazą
     * jeśli bazy nie ma to zostaje utworzona
     */
     private static Connection polacz(String baza) {
        Connection polaczenie;
        try {
            // Wskazanie jaki rodzaj bazy danych będzie wykorzystany, tu sqlite
            Class.forName("org.sqlite.JDBC");
            // Połączenie, wskazujemy rodzaj bazy i jej nazwę
            polaczenie = DriverManager.getConnection("jdbc:sqlite:"+baza+".db");
            System.out.println("Połączyłem się z bazą "+baza);
        } catch (Exception e) {
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
    }
    public static void stworzTabele(Connection polaczenie, String tabela) {
        // Obiekt odpowiadający za wykonanie instrukcji
        Statement stat = null;
        try {
            stat = polaczenie.createStatement();
            // polecenie SQL tworzące tablicę
            String tabelaSQL = "CREATE TABLE " + tabela
                    + " (ID INT PRIMARY KEY     NOT NULL,"
                    + " RODZAJ         CHAR(50)    NOT NULL, "
                    + " GATUNEK        CHAR(50)     NOT NULL, "
                    + " N2             INT, "
                    + " X              INT, "
                    + " UWAGI          TEXT)";
            // wywołanie polecenia
            stat.executeUpdate(tabelaSQL);
            // zamykanie wywołania i połączenia
            stat.close();
            polaczenie.close();
        } catch (SQLException e) {
            System.out.println("Nie mogę stworzyć tabeli" + e.getMessage());
        }
    }

    public static void dodajDane(Takson takson, String baza) {
        Connection polaczenie;
        Statement stat;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");

            stat = polaczenie.createStatement();

            String dodajSQL = "INSERT INTO " + baza + " (ID, RODZAJ, GATUNEK, N2, X, UWAGI) "
                    + "VALUES ("
                    + takson.getId() + ","
                    + "'" + takson.getRodzaj() + "',"
                    + "'" + takson.getGatunek() + "',"
                    + takson.getN2() + ","
                    + takson.getX() + ","
                    + "'" + takson.getUwagi() + "'"
                    + "  );";
            stat.executeUpdate(dodajSQL);
            stat.close();
            polaczenie.close();
            // Komunikat i wydrukowanie końcowej formy polecenia SQL
            System.out.println("Polecenie: \n" + dodajSQL + "\n wykonane.");
        } catch (Exception e) {
            System.out.println("Nie mogę dodać danych " + e.getMessage());
        }
    }
    public static void szukaj(String baza, String pole, String wartosc) {
        Connection polaczenie;
        Statement stat;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            // Polecenie wyszukania
            String szukajSQL = "SELECT * FROM " + baza
                    + " WHERE " + pole + "=='" + wartosc + "';";

            ResultSet wynik = stat.executeQuery(szukajSQL);

            System.out.println("Wynik polecenia:\n" + szukajSQL);

            while (wynik.next()) {
                int id = wynik.getInt("id");
                System.out.println("ID:       " + id);
                System.out.println("Rodzaj:   " + wynik.getString("rodzaj"));
                System.out.println("Gatunek:  " + wynik.getString("gatunek"));
                System.out.println("2n:       " + wynik.getString("N2"));
                System.out.println("x:        " + wynik.getString("X"));
                System.out.println("Uwagi:    " + wynik.getString("UWAGI"));
                System.out.println(" ---------------------- ");
            }
            wynik.close();
            stat.close();
            polaczenie.close();
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }

    }

    public static void zmien(String baza, String poleSzukane, String wartoscSzukana,
                             String poleZmieniane, String nowaWartosc) {
        Connection polaczenie;
        Statement stat;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            // Polecenie zmiany
            String zmienSQL = "UPDATE " + baza + " SET "
                    + poleZmieniane + " = '" + nowaWartosc
                    + "' WHERE " + poleSzukane + "='" + wartoscSzukana + "';";
            System.out.println("Polecenie zmiany:\n" + zmienSQL);
            // Uwaga: wywołujemy metodę executeUpdate
            stat.executeUpdate(zmienSQL);

            stat.close();
            polaczenie.close();
        } catch (Exception e) {
            System.out.println("Nie mogę poprawić danych " + e.getMessage());
        }
    }
    private static void usun(String baza, String pole, String wartosc) {
        Connection polaczenie;
        Statement stat  ;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            // Polecenie usunięcia
            String usunSQL = "DELETE FROM "+ baza + " WHERE " + pole +
                    "='" + wartosc + "';";
            System.out.println("Polecenie:\n" + usunSQL);
            stat.executeUpdate(usunSQL);

            stat.close();
            polaczenie.close();
        } catch (Exception e) {
            System.out.println("Nie mogę usunąć danych " + e.getMessage());
        }

    }

}