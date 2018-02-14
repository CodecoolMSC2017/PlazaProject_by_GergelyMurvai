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

    public boolean isStillConsumable(){
        Date date = new Date();
        String bestBeforeDate = new SimpleDateFormat("yyyy-MM-dd").format(bestBefore);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        int compareDates = bestBeforeDate.compareTo(currentDate);
        return compareDates >= 0;
    }

    public int getCalories() {
        return calories;
    }

    public String toString() {
        return "name: "+getName() + "is consumable: "+ isStillConsumable() + "calories: "+ getCalories();
    }
}
