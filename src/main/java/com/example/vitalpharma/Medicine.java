package com.example.vitalpharma;

import javafx.scene.image.Image;

public class Medicine {
    private String name;
    private Image image;
    private int price;
    public Medicine(String name, Image image) {
        this.name = name;
        this.image = image;
    }
    public Medicine(String name, Image image,int price) {
        this.name = name;
        this.image = image;
        this.price=price;
    }
    public Medicine(String name) {
        this.name = name;

    }
    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
