package com.example.vitalpharma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MedicinePageController implements Initializable {

    @FXML
    private ListView<String> medicineListView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField searchtext;

    @FXML
    private ComboBox<String> comboBox = new ComboBox();

    @FXML
    private TilePane tilePane;

    @FXML
    private void goToIndexPageButtonClicked(ActionEvent event) {
        try {
            // Load the 'medicinePage' FXML file
            FXMLLoader medicinePageLoader = new FXMLLoader(getClass().getResource("index.fxml"));
            Parent medicinePageRoot = medicinePageLoader.load();

            // Create a scene with the 'medicinePage' content
            Scene medicinePageScene = new Scene(medicinePageRoot);
            medicinePageScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(medicinePageScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToInvPageButtonClicked(ActionEvent event) {
        try {
            // Load the 'medicinePage' FXML file
            FXMLLoader medicinePageLoader = new FXMLLoader(getClass().getResource("inventory.fxml"));
            Parent medicinePageRoot = medicinePageLoader.load();

            // Create a scene with the 'medicinePage' content
            Scene medicinePageScene = new Scene(medicinePageRoot);
            medicinePageScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(medicinePageScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        List<Medicine> medicineList = new ArrayList<>();

        getAllMeds(medicineList);

        for (Medicine med : medicineList) {
            VBox medBox = createMedBox(med);
            tilePane.getChildren().add(medBox);
        }


//        medicineListView.getItems().addAll(medicineList);
    }

    public void getAllMeds(List<Medicine> medicineList){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String getMedNameAndImageQuery = "SELECT med.medicine_name,med.image_url,med.excellent_review FROM vp AS med LIMIT 60";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getMedNameAndImageQuery);

            while (resultSet.next()){
                medicineList.add(new Medicine(resultSet.getString("medicine_name"), new Image(resultSet.getString("image_url")),Integer.parseInt(resultSet.getString("excellent_review"))));
            }

        }catch (Exception e){e.printStackTrace();}
    }
    public VBox createMedBox(Medicine med) {
        VBox vbox = new VBox();


        vbox.setSpacing(10);
        vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        vbox.setPadding(new Insets(30));
        BorderStroke borderStroke = new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
        vbox.setBorder(new Border(borderStroke));
        vbox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(med.getImage());
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);


        Label nameLabel = new Label(med.getName());

        vbox.getChildren().addAll(imageView, nameLabel);

        return vbox;
    }

    @FXML
    private void searchOnClick(ActionEvent event) {
//        listsearch.getItems().clear();
//        tilePane.getChildren().clear();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        System.out.println(searchtext.getText());

        String getMedNameAndImageQuery = "SELECT medicine_name,image_url FROM vp where medicine_name like '"+searchtext.getText()+"%' limit 200";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getMedNameAndImageQuery);
            ObservableList<String> items = FXCollections.observableArrayList();
            while (resultSet.next()) {
                // tilePane.getChildren().add(createMedBox(new Medicine(resultSet.getString("medicine_name"), new Image(resultSet.getString("image_url")))));
//                listsearch.getItems().addAll(resultSet.getString("medicine_name"));
                items.add(resultSet.getString("medicine_name"));
                System.out.println(resultSet.getString("medicine_name"));
            }
            comboBox.setItems(items);
            comboBox.hide();
            comboBox.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return listsearch;
    }

}
