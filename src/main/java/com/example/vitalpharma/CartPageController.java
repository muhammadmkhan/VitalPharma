package com.example.vitalpharma;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CartPageController  {

    @FXML
    private TilePane tilePane2;

    public void setTiltePane(List<VBox> medicineList){

        System.out.println(medicineList.size());
        for (VBox med : medicineList) {

            tilePane2.getChildren().add(med);
        }
    }


}
