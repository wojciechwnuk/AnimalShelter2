package animalshelter;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    static ChoiceBox<String> choiceBox;


    Scene getEmailScene() {

        VBox emailVbox = new VBox(10);
        emailVbox.setPadding(new Insets(5));
        Scene emailScene = new Scene(emailVbox, 500, 500);

        Button backToMainSceneButton = new Button("Return");
        MainScene glownastage = new MainScene();
        backToMainSceneButton.setOnAction(event -> MainScene.getPrimaryStage().setScene(glownastage.getMainScene()));

        HBox chceszEmailHbox = new HBox(50);
        Label czyChceszAutoLabel = new Label("Włącz automatyczne wiadomości email,\n gdy kończy się miejsce w schronisku");

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Nie", "Tak");
        choiceBox.getSelectionModel().selectFirst();

        chceszEmailHbox.getChildren().addAll(czyChceszAutoLabel, choiceBox);

        Label wpiszEmail = new Label("Wpisz e-mail, na jaki ma zostać wysłana wiadomość: ");
        odbiorcaTxtField = new TextField(Database.getRecipient());


        HBox labeleHbox = new HBox(100);
        Label nadawcaLabel = new Label("E-mail nadawcy:");
        Label hasloLabel = new Label("Hasło:");
        labeleHbox.getChildren().addAll(nadawcaLabel, hasloLabel);

        HBox daneHbox = new HBox(20);
        nadawcaTxtField = new TextField(Database.getSender());
        paswdField = new PasswordField();
        paswdField.setText(Database.getPassword());

        daneHbox.getChildren().addAll(nadawcaTxtField, paswdField);

        Label ileLabel = new Label("Przy ilu wolnych miejscach wysłać e-mail z powiadomieniem?");

        Button sendEmail = new Button("Wyslij mail");
        sendEmail.setOnAction(event -> {
            try {
                setEmailProperties();
            } catch (javax.mail.MessagingException e) {
                e.printStackTrace();
            }

        });

        Button saveMailButton = new Button("Zapisz");
//        if (choiceBox.getValue()=="Nie"){
//            saveMailButton.setDisable(true);
//        }
        saveMailButton.setOnAction(event -> {
            Database.addEmail(Database.tableOfEmails);
            System.out.println(nadawcaTxtField.getText());
        });


        emailVbox.getChildren().addAll(backToMainSceneButton, chceszEmailHbox, wpiszEmail, odbiorcaTxtField, labeleHbox, daneHbox, ileLabel, sendEmail, saveMailButton);
        return emailScene;
    }


    void setEmailProperties() throws javax.mail.MessagingException {
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
        String emailBody = "Kończy się miejsce w schronisku - pozostało " + freePlaces + " wolnych miejsc!";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Session configuration - ok!");


        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", nadawcaTxtField.getText(), paswdField.getText());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email send correctly.");
    }

}
