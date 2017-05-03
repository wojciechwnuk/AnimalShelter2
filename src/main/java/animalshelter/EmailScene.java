package animalshelter;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class EmailScene {


    static PasswordField paswdField;
    static TextField nadawcaTxtField;
    static TextField odbiorcaTxtField;
    static String autoEmailButtonOn = "Włącz";
    static String autoEmailButtonOff = "Wyłącz";
    static int alertPlaces = 3;


    static Button switchButton = new Button(Database.getOnOffButton());



    Scene getEmailScene() {

        VBox emailVbox = new VBox(10);
        emailVbox.setPadding(new Insets(5));
        Scene emailScene = new Scene(emailVbox, 500, 500);

        Button backToMainSceneButton = new Button("Save and return");


        MainScene glownastage = new MainScene();
        backToMainSceneButton.setOnAction(event -> {
            Database.addEmail(Database.tableOfEmails);
            System.out.println("Zapisano wartości");
            MainScene.getPrimaryStage().setScene(glownastage.getMainScene());
        });


        Label wpiszEmail = new Label("Wpisz e-mail, na jaki ma zostać wysłana wiadomość: ");
        odbiorcaTxtField = new TextField(Database.getRecipient());
        if (switchButton.getText().equals(autoEmailButtonOn)) {
            odbiorcaTxtField.setDisable(true);
        }else odbiorcaTxtField.setDisable(false);


        HBox labeleHbox = new HBox(100);
        Label nadawcaLabel = new Label("E-mail nadawcy:");
        Label hasloLabel = new Label("Hasło:");
        labeleHbox.getChildren().addAll(nadawcaLabel, hasloLabel);

        HBox daneHbox = new HBox(20);
        nadawcaTxtField = new TextField(Database.getSender());
        if (switchButton.getText().equals(autoEmailButtonOn)) {
            nadawcaTxtField.setDisable(true);
        }else nadawcaTxtField.setDisable(false);

        paswdField = new PasswordField();
        paswdField.setText(Database.getPassword());
        if (switchButton.getText().equals(autoEmailButtonOn)) {
            paswdField.setDisable(true);
        }else paswdField.setDisable(false);

        switchButton.setText(Database.getOnOffButton());


        daneHbox.getChildren().addAll(nadawcaTxtField, paswdField);
        Label howMuchLabel = new Label("Przy ilu pozostałych miejsach wysłać email?");
        HBox buttonsHbox = new HBox();
        buttonsHbox.setSpacing(10);
        Button plusBtn = new Button("+");
        Button minusBtn = new Button("-");
        Label alertPlacesLabel = new Label();
        alertPlacesLabel.setText(String.valueOf(alertPlaces));

        plusBtn.setOnAction(event -> {
            if (alertPlaces > 0 && alertPlaces < 5) {
                alertPlaces++;
            }
            alertPlacesLabel.setText(String.valueOf(alertPlaces));
        });

        minusBtn.setOnAction(event -> {
            if (alertPlaces > 1 && alertPlaces <= 5) {
                alertPlaces--;
            }
            alertPlacesLabel.setText(String.valueOf(alertPlaces));
        });

        buttonsHbox.getChildren().addAll(plusBtn, alertPlacesLabel, minusBtn);


        Label onOffLabel = new Label();
        if (switchButton.getText().equals(autoEmailButtonOn)) {
            switchButton.setText(autoEmailButtonOn);
            onOffLabel.setText("Auto-mail jest wyłączony");
            onOffLabel.setTextFill(Color.RED);
        } else {
            switchButton.setText(autoEmailButtonOff);
            onOffLabel.setText("Auto-mail jest włączony");
            onOffLabel.setTextFill(Color.GREEN);
        }

        switchButton.setOnAction(event -> {

            if (switchButton.getText().equals(autoEmailButtonOn)) {
                switchButton.setText(autoEmailButtonOff);
                onOffLabel.setText("Auto-mail jest włączony");
                onOffLabel.setTextFill(Color.GREEN);
                paswdField.setDisable(false);
                nadawcaTxtField.setDisable(false);
                odbiorcaTxtField.setDisable(false);

            } else {
                switchButton.setText(autoEmailButtonOn);
                onOffLabel.setText("Auto-mail jest wyłączony");
                onOffLabel.setTextFill(Color.RED);
                paswdField.setDisable(true);
                nadawcaTxtField.setDisable(true);
                odbiorcaTxtField.setDisable(true);
            }

        });

        emailVbox.getChildren().addAll(backToMainSceneButton, wpiszEmail, odbiorcaTxtField, labeleHbox, daneHbox, howMuchLabel, buttonsHbox, onOffLabel, switchButton);
        return emailScene;
    }


    void sendEmail() throws javax.mail.MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;


        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Email server configuration - ok!");


        int freePlaces = EditFreeSpaceScene.places - Database.databazeSize();


        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(odbiorcaTxtField.getText()));

        generateMailMessage.setSubject("Uwaga!");
        String emailBody = "Konczy sie miejsce w schronisku - pozostalo " + freePlaces + " wolnych miejsc!";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Session configuration - ok!");


        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", nadawcaTxtField.getText(), paswdField.getText());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email send correctly.");
    }

}
