package sample;


 class Finder {
     static String szukaj(String sekwencja, String szukana) {

        String[] pomiedzy = sekwencja.split(szukana);

        String rezultat = "";

        if (pomiedzy.length > 1) {

            for (int i = 0; i < pomiedzy.length - 1; i++) {

                if (rezultat.length() == 0)
                    rezultat = rezultat + pomiedzy[i] + "<" + szukana + ">" + pomiedzy[i + 1];
                else
                    rezultat = rezultat + "<" + szukana + ">" + pomiedzy[i + 1];

            }
            if (sekwencja.indexOf(szukana, sekwencja.length() - szukana.length()) > 0) {
                rezultat = rezultat + "<" + szukana + ">";
            }

        } else {
            rezultat = sekwencja;
        }
        return rezultat;
    }
}
