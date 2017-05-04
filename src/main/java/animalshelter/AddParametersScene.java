package animalshelter;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

class AddParametersScene {

    Scene getAddParametersScene() {

        VBox addParameteresVbox = new VBox(10);
        addParameteresVbox.setPadding(new Insets(5));
        Scene addParameteresScene = new Scene(addParameteresVbox, 400, 300);

        Button backToMainSceneButton = new Button("Return");
        MainScene glownastage = new MainScene();
        backToMainSceneButton.setOnAction(event -> {MainScene.getPrimaryStage().setScene(glownastage.getMainScene());
          AddAnimalScene addAnimalScene = new AddAnimalScene();
          addAnimalScene.importList();
        });

        addParameteresVbox.getChildren().addAll(backToMainSceneButton);


        return addParameteresScene;
    }
}
