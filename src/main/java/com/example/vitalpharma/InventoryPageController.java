package com.example.vitalpharma;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryPageController implements Initializable {

    @FXML
    private TilePane tilePane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MedicinePageController medicinePageController = new MedicinePageController();
        List<Medicine> medicineList = new ArrayList<>();

        medicinePageController.getAllMeds(medicineList);
        for (Medicine med : medicineList) {
            VBox medBox = createMedBox(med);
            tilePane.getChildren().add(medBox);
        }
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

        Button checkBox = new Button("add to cart");
        checkBox.setUserData(med.getName());
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Node sourceComp=(Node)event.getSource();
                String medName=(String)sourceComp.getUserData();
                shoppingCArt shoppingCArt= com.example.vitalpharma.shoppingCArt.getINSTANCE();
                shoppingCArt.addMed(med);
            }
        });
        vbox.getChildren().addAll(imageView, nameLabel,checkBox);

        return vbox;
    }
    @FXML
    private void goToCartPageButtonClicked(ActionEvent event) {
        try {
            FXMLLoader medicinePageLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
            Parent medicinePageRoot = medicinePageLoader.load();
            CartPageController cartPageController =new CartPageController();
            cartPageController.setTiltePane(getSelectedVBoxes());
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

    public List<VBox> getSelectedVBoxes() {
        List<VBox> selectedVBoxes = new ArrayList<>();
        for (javafx.scene.Node node : tilePane.getChildren()) {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                for (javafx.scene.Node innerNode : vbox.getChildren()) {
                    if (innerNode instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) innerNode;
                        if (checkBox.isSelected()) {

                            selectedVBoxes.add(vbox);
                        }
                        break; // Once we found CheckBox, we don't need to check further in this VBox
                    }
                }
            }
        }
        return selectedVBoxes;
    }
}
