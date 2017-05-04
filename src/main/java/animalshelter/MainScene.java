package animalshelter;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


class MainScene {

    private static Stage pStage;
    private AddAnimalScene addAnimalScene = new AddAnimalScene();
    private TableOfAnimalsSQLScene tableOfAnimalsSQLScene = new TableOfAnimalsSQLScene();
    private EditFreeSpaceScene editFreeSpaceScene = new EditFreeSpaceScene();
    private AddParametersScene addParametersScene = new AddParametersScene();
    private EmailScene emailScene = new EmailScene();


    Scene getMainScene() {
        VBox verticalCage = new VBox(10);
        verticalCage.setPadding(new Insets(5, 5, 5, 5));
        verticalCage.setSpacing(10);
        Scene scene = new Scene(verticalCage, 600, 450, Color.BLACK);

        //SCENA GŁÓWNA
        //MENU
        MenuBar menuBar = new MenuBar();
        //menu plik
        Menu menuFile = new Menu("Plik");
        AddAnimalScene addAnimalScene = new AddAnimalScene();

        MenuItem saveFile = new MenuItem("Zapisz bazę do .txt");
        saveFile.setOnAction(event ->{addAnimalScene.importList();
            Alert alertAddedAnimal = new Alert(Alert.AlertType.INFORMATION);
            alertAddedAnimal.setTitle("Information Dialog");
            alertAddedAnimal.setHeaderText(null);
            alertAddedAnimal.setContentText("Lista poprawnie zapisana do pliku .txt (resources/output.txt)");
            alertAddedAnimal.showAndWait();
        });

        menuFile.getItems().addAll(saveFile);

        // menu baza zwierzat
        Menu menuBaza = new Menu("Baza zwierząt");
        MenuItem dodajZwierzeMenu = new MenuItem("Dodaj zwierzę");
        dodajZwierzeMenu.setOnAction(event -> getPrimaryStage().setScene(addAnimalScene.getAddAnimalScene()));


        MenuItem sprawadzListeZwierzatMenu = new MenuItem("Lista zwierząt");
        sprawadzListeZwierzatMenu.setOnAction(event -> getPrimaryStage().setScene(tableOfAnimalsSQLScene.getTableOfAnimalsSQLScene()));

        menuBaza.getItems().addAll(dodajZwierzeMenu, sprawadzListeZwierzatMenu);

        //menu opcje
        Menu menuOpieka = new Menu("Opcje");
        MenuItem zmienLiczbeMiejscMenu = new MenuItem("Edytuj liczbę miejsc");
        zmienLiczbeMiejscMenu.setOnAction(event -> getPrimaryStage().setScene(editFreeSpaceScene.getEditFreeSpaceScene()));
        MenuItem dodajCechyMenu = new MenuItem("Dodaj parametry zwierząt");
        dodajCechyMenu.setOnAction(event -> getPrimaryStage().setScene(addParametersScene.getAddParametersScene()));
        MenuItem emailMenu = new MenuItem("Ustawienia e-mail");
        emailMenu.setOnAction(event -> getPrimaryStage().setScene(emailScene.getEmailScene()));

        menuOpieka.getItems().addAll(zmienLiczbeMiejscMenu, dodajCechyMenu, emailMenu);

        menuBar.getMenus().addAll(menuFile);
        menuBar.getMenus().addAll(menuBaza);
        menuBar.getMenus().addAll(menuOpieka);

        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);

        Label szukajLabel = new Label("Szukaj zwierzę po imieniu:");
        TextField szukajTextField = new TextField();
        szukajTextField.setMinWidth(135);

        Label zajeteMiejscaLabel = new Label();
        zajeteMiejscaLabel.setText("Zajęte miejsca: " + String.valueOf(Database.databazeSize()) + " z " + EditFreeSpaceScene.places + " dostępnych.");


        Image kosze = new Image("file:resources\\kosze.png");

        ImageView iv1 = new ImageView();
        iv1.setFitWidth(400);
        iv1.setImage(kosze);


        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));

        pane.setPrefSize(500, 300);
        pane.setCenter(iv1);


        verticalCage.getChildren().addAll(szukajLabel, szukajTextField, zajeteMiejscaLabel, pane);

        return scene;
    }

    static Stage getPrimaryStage() {
        return pStage;
    }

    void setPrimaryStage(Stage pStage) {
        MainScene.pStage = pStage;
    }

}
