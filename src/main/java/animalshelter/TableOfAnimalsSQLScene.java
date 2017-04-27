package animalshelter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.*;


class TableOfAnimalsSQLScene {
    private TableView tableView;
    private ObservableList<ObservableList> listaDoTabeli = FXCollections.observableArrayList();


    Scene getTableOfAnimalsSQLScene() {

        tableView = new TableView<>();
        VBox textTablevbox = new VBox(20);
        textTablevbox.setPadding(new Insets(5));
        Scene textTableScene = new Scene(textTablevbox, 600, 450);

        Connection connection;

        try {
            connection = Database.connectDatabase(Database.tableOfAnimals);
            String SQL = "SELECT * from " + Database.tableOfAnimals;
            assert connection != null;
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {

                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }

            while (resultSet.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {

                    row.add(resultSet.getString(i));
                }
                listaDoTabeli.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setEditable(true);
        tableView.setItems(listaDoTabeli);

        Button deleteButton = new Button("Usuń zaznaczone zwierzę");
        deleteButton.setOnAction(event -> deleteAnimal());

        Button backToMainSceneButton = new Button("Return");
        MainScene glownastage = new MainScene();
        backToMainSceneButton.setOnAction(event -> MainScene.getPrimaryStage().setScene(glownastage.getMainScene()));
        textTablevbox.getChildren().addAll(backToMainSceneButton, deleteButton, tableView);

        return textTableScene;
    }

    private void deleteAnimal() {
        String row = tableView.getSelectionModel().getSelectedItem().toString();
        String name = row.substring(row.indexOf("[") + 1, row.indexOf(','));
        System.out.println(name);

        tableView.getItems().removeAll(
                tableView.getSelectionModel().getSelectedItems()
        );

        String sqlDelete = "DELETE FROM " + Database.tableOfAnimals + " WHERE NAME = " + "'" + name + "'";
        try {
            PreparedStatement preparedStatement = Database.connectDatabase(Database.tableOfAnimals).prepareStatement(sqlDelete);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
