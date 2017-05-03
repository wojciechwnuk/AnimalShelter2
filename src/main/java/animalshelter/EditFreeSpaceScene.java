package animalshelter;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.xml.crypto.Data;


class EditFreeSpaceScene {
    static int places = 20;
    static int freePlaces;

    private ImportExport importExport = new ImportExport();


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
        freePlaces = places - Database.databazeSize();


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
        System.out.println(Database.getOnOffButton());
        return editspaceScene;
    }

    void sendAlertEmail() {
        if (Database.getOnOffButton().equals(EmailScene.autoEmailButtonOff)){
            System.out.println("daje");

            EmailScene email = new EmailScene();

            try {
                email.sendEmail();
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }
        }
      }
}
