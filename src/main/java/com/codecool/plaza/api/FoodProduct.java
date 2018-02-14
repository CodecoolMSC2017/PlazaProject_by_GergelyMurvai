package com.codecool.plaza.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FoodProduct extends Product {

    private int calories;
    private Date bestBefore;
    public FoodProduct(String name, long barcode, String manufacturer, int calories, Date bestBefore) {
        super(name,barcode,manufacturer);
        this.calories = calories;
        this.bestBefore = bestBefore;
    }

    public int isStillConsumable(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        return date.compareTo(bestBefore);
    }

    public int getCalories() {
        return calories;
    }

    public String toString() {
        return "name: "+getName() + "is consumable: "+ isStillConsumable() + "calories: "+ getCalories();
    }
}
