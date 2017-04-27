package animalshelter;


import java.util.Random;

class RandomNameGenerator {

    String generatorKlasa() {
        String[] samogloski = {"a", "e", "i", "u", "y"};

        String[] spolgloski = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n",
                "p", "r", "s", "t", "w", "z", "sz", "cz", "ch", "ż"};
        String[] koncowki = {"sia", "sik", "cia", "cik", "teł", "nia", "tek", "wik", "pka", "pik"};

        int iloscZnakow = new Random().nextInt(1) + 2;
        String finalName = "";

        for (int i = 0; i < iloscZnakow; i++) {

            int indexTablicySamoglosek = new Random().nextInt(samogloski.length);
            int indexTablicySpolglosek = new Random().nextInt(spolgloski.length);
            int indexTabKoncowek = new Random().nextInt(koncowki.length);

            String spolgloska = (spolgloski[indexTablicySpolglosek]);
            String samogloska = (samogloski[indexTablicySamoglosek]);
            String koncowke = (koncowki[indexTabKoncowek]);

            String sylaba = spolgloska + samogloska;
            String firstLeter = sylaba.substring(0, 1).toUpperCase() + sylaba.substring(1);

            if (i == 0) {
                finalName = firstLeter;
            } else if (i == iloscZnakow - 1) {
                finalName += sylaba + koncowke;
            } else
                finalName += sylaba;
        }

        return finalName;
    }
}