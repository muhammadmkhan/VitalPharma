package com.example.vitalpharma;

import javafx.scene.image.Image;

public class Medicine {
    private String name;
    private Image image;

    public Medicine(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}
