package animalshelter;

import animalshelter.animals.Animal;
import animalshelter.animals.Cat;
import animalshelter.animals.Degu;
import animalshelter.animals.Dog;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class AddAnimalScene implements Serializable {

    static List<Animal> listOfAnimals = new ArrayList<>();
    private static ChoiceBox<String> animalChoiceBox = new ChoiceBox<>();
    private static TextField nameTextField = new TextField();
    private static TextField massTextField = new TextField();
    private static ChoiceBox<String> healthChoiceBox = new ChoiceBox<>();
    private EditFreeSpaceScene editFreeSpaceScene = new EditFreeSpaceScene();

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


            if (massTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || healthChoiceBox.getValue() == null || animalChoiceBox.getValue() == null) {
                Alert alertAddingError = new Alert(Alert.AlertType.ERROR);
                alertAddingError.setTitle("Error Dialog");
                alertAddingError.setHeaderText("Błąd w wypełnieniu pól!");
                alertAddingError.setContentText("Proszę wypełnić wszystkie pola cech!");
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
                try {
                    editFreeSpaceScene.sendMail();
               } catch (javax.mail.MessagingException e) {
                    e.printStackTrace();
                }

                Alert alertAddedAnimal = new Alert(Alert.AlertType.INFORMATION);
                alertAddedAnimal.setTitle("Information Dialog");
                alertAddedAnimal.setHeaderText(null);
                alertAddedAnimal.setContentText("Zwierzę poprawnie dodano do bazy danych :) ");

                alertAddedAnimal.showAndWait();


                try {
                    editFreeSpaceScene.sendMail();

                } catch (javax.mail.MessagingException e) {
                    e.printStackTrace();
                }

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

}
