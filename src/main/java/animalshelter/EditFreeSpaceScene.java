package animalshelter;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;




class EditFreeSpaceScene {
    static int places = 20;
    private ImportExport importExport = new ImportExport();

    void sendMail() throws javax.mail.MessagingException {
        if (places < 5) {
            EmailScene emailScene = new EmailScene();
            emailScene.setEmailProperties();
        }
    }

    Scene getEditFreeSpaceScene() {
        VBox editspaceVbox = new VBox(10);
        editspaceVbox.setPadding(new Insets(5));
        Scene editspaceScene = new Scene(editspaceVbox, 400, 300);

        Button backToMainSceneButton = new Button("Return");
        MainScene glownastage = new MainScene();
        backToMainSceneButton.setOnAction(event -> {
            MainScene.getPrimaryStage().setScene(glownastage.getMainScene());
            importExport.saveList();
        });

        Label wolneMiejscaLabel = new Label();
        wolneMiejscaLabel.setText("Aktualna ilość miejsc w schronisku, to: " + places);
        Label zajeteMiejscaLabel = new Label();
        zajeteMiejscaLabel.setText("Zajęte miejsca, to: " + String.valueOf(Database.databazeSize()));

        HBox przyciskiHbox = new HBox();
        przyciskiHbox.setSpacing(10);
        Label pluslubminusLabel = new Label();
        pluslubminusLabel.setText(" Dodaj lub usuń miejsca: ");

        Button dodaj = new Button("+1");
        dodaj.setPrefWidth(50);
        Button odejmij = new Button("-1");
        odejmij.setPrefWidth(50);

        Button domyslne = new Button("Przywróć domyślne");
        domyslne.setPrefWidth(150);

        dodaj.setOnAction(event -> {
            places++;
            wolneMiejscaLabel.setText("Aktualna ilość miejsc w schronisku, to: " + places);
        });

        odejmij.setOnAction(event -> {
            if (places > Database.databazeSize()) {
                places--;
                wolneMiejscaLabel.setText("Aktualna ilość miejsc w schronisku, to: " + places);
            }
        });

        domyslne.setOnAction(event -> {
            places = 20;
            wolneMiejscaLabel.setText("Aktualna ilość miejsc w schronisku, to: " + places);
        });
        przyciskiHbox.getChildren().addAll(dodaj, odejmij, domyslne);

        editspaceVbox.getChildren().addAll(backToMainSceneButton, wolneMiejscaLabel, zajeteMiejscaLabel, pluslubminusLabel, przyciskiHbox);

        return editspaceScene;

    }
}
