package com.example.vitalpharma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shoppingCArt {

    private static shoppingCArt INSTANCE;

    public static shoppingCArt getINSTANCE(){
        if (INSTANCE==null){
            INSTANCE= new shoppingCArt();
        }
        return INSTANCE;
    }

    private Map<String,CartEntry> entries;

    public shoppingCArt(){
        this.entries=new HashMap<>();
    }

    public void addMed(Medicine medName){
        CartEntry cartEntry =entries.get(medName.getName().toUpperCase());
    if (cartEntry!=null){
        cartEntry.addQuantity();
    }else{
        Medicine medicine= new Medicine(medName.getName(),medName.getImage());
        CartEntry entry= new CartEntry(medicine,1);
        entries.put(medName.getName().toUpperCase(),entry);
    }
    }

    public void removeMed(Medicine medicine){
        CartEntry entry=entries.get(medicine.getName().toUpperCase());
        if (entry!=null){
            entry.decQuantity();
        }
    }

    public float getQuantity(){
        float q =0;
        for(CartEntry entry:entries.values()){
            float entryCost = entry.getMedicine().getPrice();
            q=+entryCost;
        }
        return q;
    }

    public List<CartEntry> getEntries(){
        return new ArrayList<>(entries.values());
    }
}
