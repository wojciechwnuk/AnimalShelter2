package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox verticalCage = new VBox(5);
        verticalCage.setPadding(new Insets(5));

        Label sekwencjaLabel = new Label();
        sekwencjaLabel.setText("Sekwencja");

        verticalCage.getChildren().add(sekwencjaLabel);

        TextArea sekwencjaTextArea = new TextArea();
        sekwencjaTextArea.setWrapText(true);

        verticalCage.getChildren().add(sekwencjaTextArea);

        HBox szukajBox = new HBox(5);
        Label szukajLabel = new Label("Szukaj:");
        TextField szukajTextField = new TextField();
        szukajTextField.setMinWidth(235);

        szukajBox.getChildren().addAll(szukajLabel,szukajTextField);

        verticalCage.getChildren().add(szukajBox);

        Label wynikLabel = new Label("Wynik:");
        verticalCage.getChildren().add(wynikLabel);

        TextArea wynikTextArea = new TextArea();
        wynikTextArea.setEditable(false);
        wynikTextArea.setWrapText(true);
        verticalCage.getChildren().add(wynikTextArea);

        HBox buttonsHbox = new HBox(5);
        Button szukajButton = new Button("Szukaj");
        szukajButton.setOnAction(event -> wynikTextArea.setText(
               Finder.szukaj(sekwencjaTextArea.getText(), szukajTextField.getText())
        ));
        Button zakonczButton = new Button("Zakończ");
        zakonczButton.setOnAction(event -> Platform.exit());
        buttonsHbox.getChildren().addAll(szukajButton, zakonczButton);
        verticalCage.getChildren().add(buttonsHbox);

        Scene scene = new Scene(verticalCage, 600, 450, Color.BLUE);
        primaryStage.setTitle("Szukanie sekwencji");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
