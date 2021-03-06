package animalshelter;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private ImportExport importExport = new ImportExport();


    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        if (!Database.temp.exists()) {
            Database.createTableOfAnimals(Database.connectDatabase(Database.tableOfAnimals), Database.tableOfAnimals);
            Database.createTableOfEmails(Database.connectDatabase(Database.tableOfAnimals), Database.tableOfEmails);
        }
        MainScene mainScene = new MainScene();
        EmailScene emailScene = new EmailScene();
        emailScene.getEmailScene();
        AddAnimalScene addAnimalScene = new AddAnimalScene();
        addAnimalScene.getAddAnimalScene();

        mainScene.setPrimaryStage(primaryStage);


        importExport.loadList();

        primaryStage.setTitle("Animal Shelter Manager");
        primaryStage.setScene(mainScene.getMainScene());
        primaryStage.show();

    }
}
