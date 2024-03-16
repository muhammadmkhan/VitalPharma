package com.example.vitalpharma;

public class CartEntry {

    private Medicine medicine;
    private int quantity;


    public CartEntry(Medicine medicine,int quantity){

        this.medicine=medicine;
        this.quantity=quantity;
    }

    public Medicine getMedicine(){
        return this.medicine;
    }
    public int getQuantity(){
        return this.quantity;
    }

    public void addQuantity(){
        this.quantity++;
    }
    public void decQuantity(){
        if (this.quantity>0){
        this.quantity--;}
    }
}
