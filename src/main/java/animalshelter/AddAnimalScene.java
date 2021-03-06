package animalshelter;

import animalshelter.animals.Animal;
import animalshelter.animals.Cat;
import animalshelter.animals.Degu;
import animalshelter.animals.Dog;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class AddAnimalScene implements Serializable {

    private ChoiceBox<String> animalChoiceBox = new ChoiceBox<>();
    private TextField nameTextField = new TextField();
    private TextField massTextField = new TextField();
    private ChoiceBox<String> healthChoiceBox = new ChoiceBox<>();
    private static Animal animal;

    static Animal getAnimal() {
        return animal;
    }

    Scene getAddAnimalScene() {

        VBox addAnimalVbox = new VBox(20);
        addAnimalVbox.setPadding(new Insets(5));
        Scene addAnimalScene = new Scene(addAnimalVbox, 600, 450, Color.BLACK);

        Button backToMainSceneButton = new Button("Return");
        MainScene glownastage = new MainScene();
        backToMainSceneButton.setOnAction(event -> MainScene.getPrimaryStage().setScene(glownastage.getMainScene()));

        Label dodajZwierzeLabel = new Label("Choose type of animal:");

        animalChoiceBox.getItems().addAll("Dog", "Cat", "Degu");
        HBox nameHbox = new HBox(10);
        HBox massHbox = new HBox(10);
        HBox healthHbox = new HBox(10);

        Label nameLabel = new Label("Name: ");

        nameTextField.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> nameTextField.setText(""));
        Button generateNameButton = new Button("Generuj losowe imie");

        generateNameButton.setOnAction(event ->
        {
            RandomNameGenerator randomNameGenerator = new RandomNameGenerator();
            nameTextField.setText(randomNameGenerator.generatorKlasa());
        });

        nameHbox.getChildren().addAll(nameLabel, nameTextField, generateNameButton);


        Label massLabel = new Label("Mass[kg]: ");

        massTextField.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> massTextField.setText(""));
        massHbox.getChildren().addAll(massLabel, massTextField);

        Label healthLabel = new Label("Health state ");

        healthChoiceBox.getItems().addAll("Bardzo dobry", "Poprawny", "Zły");
        healthHbox.getChildren().addAll(healthLabel, healthChoiceBox);

        //Dodaje obiekt zwierzęcia do bazy
        Button addAnimalButton = new Button("Dodaj!");

        addAnimalButton.setOnAction(event -> {
            try {
                if (compareIfNameExists() != null) {
                    Alert alertAddingError = new Alert(Alert.AlertType.ERROR);
                    alertAddingError.setTitle("Error Dialog");
                    alertAddingError.setHeaderText("Zwierzę o takim imieniu jest już w schronisku!");
                    alertAddingError.setContentText("Proszę nadać inne imię!");
                    alertAddingError.showAndWait();
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            EditFreeSpaceScene editFreeSpaceScene = new EditFreeSpaceScene();
            editFreeSpaceScene.sendAlertEmail();

            if (massTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || healthChoiceBox.getValue() == null || animalChoiceBox.getValue() == null) {
                Alert alertAddingError = new Alert(Alert.AlertType.ERROR);
                alertAddingError.setTitle("Error Dialog");
                alertAddingError.setHeaderText("Błąd w wypełnieniu pól!");
                alertAddingError.setContentText("Proszę wypełnić wszystkie pola cech!");
                alertAddingError.showAndWait();

            } else if (checkIfDouble() == null) {
                Alert alertAddingError = new Alert(Alert.AlertType.ERROR);
                alertAddingError.setTitle("Error Dialog");
                alertAddingError.setHeaderText("Błędne dane w polu: masa!");
                alertAddingError.setContentText("Proszę wpisać cyfrę całkowitą, lub zmiennoprzecinkową rozdzieloną kropką!");
                alertAddingError.showAndWait();


            } else if (EditFreeSpaceScene.places > Database.databazeSize()) {


                if (Objects.equals(animalChoiceBox.getValue(), "Dog")) {
                    animal = new Dog(animalChoiceBox.getValue(), massTextField.getText(), nameTextField.getText(), healthChoiceBox.getValue());
                    Database.connectDatabase(Database.tableOfAnimals);
                    Database.addValue(animal, Database.tableOfAnimals);

                } else if (Objects.equals(animalChoiceBox.getValue(), "Cat")) {
                    animal = new Cat(animalChoiceBox.getValue(), massTextField.getText(), nameTextField.getText(), healthChoiceBox.getValue());
                    Database.connectDatabase(Database.tableOfAnimals);
                    Database.addValue(animal, Database.tableOfAnimals);

                } else if (Objects.equals(animalChoiceBox.getValue(), "Degu")) {
                    animal = new Degu(animalChoiceBox.getValue(), massTextField.getText(), nameTextField.getText(), healthChoiceBox.getValue());
                    Database.connectDatabase(Database.tableOfAnimals);
                    Database.addValue(animal, Database.tableOfAnimals);
                }

                Alert alertAddedAnimal = new Alert(Alert.AlertType.INFORMATION);
                alertAddedAnimal.setTitle("Information Dialog");
                alertAddedAnimal.setHeaderText(null);
                alertAddedAnimal.setContentText("Zwierzę poprawnie dodano do bazy danych :) ");
                alertAddedAnimal.showAndWait();

                System.out.println(massTextField.getText() + " " + nameTextField.getText() + " " + healthChoiceBox.getValue());
            } else {
                Alert alertAddingError = new Alert(Alert.AlertType.ERROR);
                alertAddingError.setTitle("Error Dialog");
                alertAddingError.setHeaderText("Schronisko jest pełne!");
                alertAddingError.setContentText("Proszę zwiększyć ilość dostępnych miejsc, lub usunąć zwierzę.");
                alertAddingError.showAndWait();
            }
        });

        addAnimalVbox.getChildren()
                .addAll(backToMainSceneButton, dodajZwierzeLabel, animalChoiceBox, nameHbox, massHbox, healthHbox, addAnimalButton);
        return addAnimalScene;
    }

    private String compareIfNameExists() throws SQLException {

        return databaseNamesToList().stream()
                .filter(x -> nameTextField.getText().equals(x.getName()))
                .map(Animal::getName)
                .findAny()
                .orElse(null);
    }

    private List<Animal> databaseNamesToList() throws SQLException {
        Connection connection;
        connection = Database.connectDatabase(Database.tableOfAnimals);
        String SQL = "SELECT NAME, MASS, HEALTH, TYPE from " + Database.tableOfAnimals;
        assert connection != null;
        ResultSet resultSet = connection.createStatement().executeQuery(SQL);
        List<Animal> animals = new ArrayList<>();
        while (resultSet.next()) {
            animals.add(mapToAnimal(resultSet));
        }
        connection.close();

        return animals;
    }

    private Animal mapToAnimal(ResultSet resultSet) throws SQLException {
        Animal animal2 = null;
        if (Database.getType().equals("Dog")) {
            animal2 = new Dog();
        } else if (Database.getType().equals("Cat")) {
            animal2 = new Cat();
        } else if (Database.getType().equals("Degu")) {
            animal2 = new Degu();
        }

        animal2.setName(resultSet.getString("NAME"));
        animal2.setMass(resultSet.getString("MASS"));
        animal2.setHealth(resultSet.getString("HEALTH"));
        animal2.setType(resultSet.getString("TYPE"));

        return animal2;
    }

    private Double checkIfDouble() {
        Double mass;

        try {
            mass = Double.parseDouble(massTextField.getText());

        } catch (NumberFormatException e) {
            return null;
        }
        return mass;
    }

    void importList() {
        try {
            String sep = ":";
            FileWriter writer = new FileWriter("resources/output.txt");
            for (Animal str : databaseNamesToList()) {
                writer.write(String.valueOf(str.getType() + sep + str.getName() + sep + str.getMass() + sep +
                        str.getHealth() + System.lineSeparator()));
            }
            writer.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}


