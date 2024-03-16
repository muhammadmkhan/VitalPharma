package com.example.vitalpharma;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CartPageController {

    @FXML
    private TilePane tilePane2;

    @FXML
    private Label totalValue;
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

        if (entries.isEmpty()){
            Label label = new Label("Empty Cart");
            tilePane2.getChildren().add(label);
        }else{
            Integer price =0;
            for (CartEntry entry:entries){
                tilePane2.getChildren().add(getMed(entry.getMedicine().getName()));
                System.out.println(entry.getMedicine().getPrice());
                price+=entry.getMedicine().getPrice();
            }
            System.out.println("t: "+price);
            totalValue = new Label(price.toString());
        }

    }

    public VBox getMed(String medName){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String getMedNameAndImageQuery = "SELECT med.medicine_name,med.image_url,med.excellent_review FROM vp AS med WHERE med.medicine_name = '"+medName+"'";
        List<Medicine> medicineList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getMedNameAndImageQuery);

            while (resultSet.next()) {
                medicineList.add(new Medicine(resultSet.getString("medicine_name"), new Image(resultSet.getString("image_url")),Integer.parseInt(resultSet.getString("excellent_review"))));
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
}
