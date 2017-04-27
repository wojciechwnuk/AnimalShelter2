package sample;

public class Takson {
    private int id;
    private String rodzaj;
    private String gatunek;
    private int n2;
    private int x;
    private String uwagi;

    public Takson(int id, String rodzaj, String gatunek, int n2, int x) {
        this.id = id;
        this.rodzaj = rodzaj;
        this.gatunek = gatunek;
        this.n2 = n2;
        this.x = x;
    }

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

     String getGatunek() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

     int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

     int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

     String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

}