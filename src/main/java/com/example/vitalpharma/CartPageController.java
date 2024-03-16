package com.example.vitalpharma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class CartPageController {

    @FXML
    private TilePane tilePane2;

    @FXML
    private Label totalValue;

    @FXML
    private Label op;

 /*   public void setTiltePane(List<VBox> medicineList){

        System.out.println(medicineList.size());
        for (VBox med : medicineList) {

            tilePane2.getChildren().add(med);
        }
    }
*/


    @FXML
    public void initialize() {
        List<CartEntry> entries = shoppingCArt.getINSTANCE().getEntries();
        tilePane2.getChildren().clear();
        op.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        op.setText("");

        if (entries.isEmpty()) {
            Label label = new Label("Empty Cart");
            tilePane2.getChildren().add(label);
        } else {
            Integer price = 0;
            for (CartEntry entry : entries) {
                tilePane2.getChildren().add(getMed(entry.getMedicine().getName()));
                price += entry.getMedicine().getPrice();
            }

            totalValue.setText("$ " + price);
        }

    }

    public VBox getMed(String medName) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String getMedNameAndImageQuery = "SELECT med.medicine_name,med.image_url,med.excellent_review FROM vp AS med WHERE med.medicine_name = '" + medName + "'";
        List<Medicine> medicineList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getMedNameAndImageQuery);

            while (resultSet.next()) {
                medicineList.add(new Medicine(resultSet.getString("medicine_name"), new Image(resultSet.getString("image_url")), Integer.parseInt(resultSet.getString("excellent_review"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return createMedBox(medicineList.get(0));
    }

    private VBox createMedBox(Medicine med) {
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
    private void saveOrder() {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        List<CartEntry> entries = shoppingCArt.getINSTANCE().getEntries();

        String meds = "";
        for (CartEntry entry : entries) {
            if (meds.equals("")) {
                meds = entry.getMedicine().getName();
            } else {
                meds = meds + "," + entry.getMedicine().getName();
            }
        }
        Random random = new Random();
        String getMedNameAndImageQuery = "INSERT INTO `order`(orderID,medicines) VALUES ('" + random.nextInt(Integer.MAX_VALUE) + "','" + meds + "');";
        try {
            PreparedStatement statement = connection.prepareStatement(getMedNameAndImageQuery);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        op.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        op.setText("Order Placed!");
        op.setTextFill(Color.WHITE);
        op.setTextAlignment(TextAlignment.CENTER);

        shoppingCArt.getINSTANCE().emptyCart();
    }

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
    private void goToMedicinePageButtonClicked(ActionEvent event) {
        try {
            // Load the 'medicinePage' FXML file
            FXMLLoader medicinePageLoader = new FXMLLoader(getClass().getResource("medicinePage.fxml"));
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
}
