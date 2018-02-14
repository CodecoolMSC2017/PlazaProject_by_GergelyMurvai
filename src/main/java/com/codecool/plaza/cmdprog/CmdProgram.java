package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Scanner;

public class CmdProgram {
    Product product;

    private List<Product> cart = new ArrayList<>();
    private String plazaName;
    private String ownerName;
    private PlazaImpl plaza;
    private ShopImpl shop;

    private final Scanner reader = new Scanner(System.in);

    public CmdProgram(String[] args) {
    }


    public void run() {
        init();
        mainMenu();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public void init() {
        clearScreen();
        System.out.println("There are no plaza created yet! Press\n" +
            "1) to create a new plaza.\n" +
            "2) to exit.\n");
        String initChoose = reader.next();

        switch (initChoose) {

            case "1":
                System.out.println("Type your name !");
                ownerName = reader.next();
                System.out.println("Add a name for your fancy new Plaza! ");
                //String s = reader.nextLine();
                plazaName = reader.next();
                plaza = new PlazaImpl(ownerName,plazaName);
                break;
            case "2":
                System.out.println("Ok! See you soon! ");
                System.exit(0);
                break;
            default:
                System.out.println("Wrong input entered");
                break;
        }
    }

    public void mainMenu() {
        clearScreen();
        while(true) {
            System.out.println("Welcome into the "+ plaza.getName()+" owned by: "+ plaza.getOwnerName()+"\n");
            System.out.println("1) to list all shops.\n" +
                "2) to add a new shop.\n" +
                "3) to remove an existing shop.\n" +
                "4) enter a shop by name.\n" +
                "5) to open the plaza.\n" +
                "6) to close the plaza.\n" +
                "7) to check if the plaza is open or not.\n" +
                "8) leave plaza.");
            String choice = reader.next();

            switch(choice) {
                case "1":
                    clearScreen();
                    try {
                        for (int i = 0; i < plaza.getShops().size(); i++) {
                            String name = plaza.getShops().get(i).getName();
                            String owner = plaza.getShops().get(i).getOwner();
                            System.out.println((i + 1) + ". Shop name: " + name + ", Owner of the shop: " + owner);
                        }
                    } catch (PlazaIsClosedException | NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    System.out.println("Type the name of the new shop: ");
                    String s = reader.nextLine();
                    String shopName = reader.nextLine();
                    System.out.println("Enter the owner's name:");
                    String shopOwner = reader.nextLine();
                    try {
                        plaza.addShop(new ShopImpl(shopName, shopOwner));
                    } catch (ShopAlreadyExistsException | PlazaIsClosedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    System.out.println("Do you really want to remove a store? (y/n)");
                    String ans = reader.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        System.out.println("Enter the name of the store:");
                        String delete = reader.nextLine();
                        try {
                            for (Shop sh : plaza.getShops()) {
                                if (delete.equalsIgnoreCase(sh.getName())) {
                                    plaza.removeShop(sh);
                                    System.out.println(sh.getName() + " deleted.");
                                }
                            }
                        } catch (NoSuchShopException | ConcurrentModificationException | PlazaIsClosedException | NullPointerException e) {
                            e.printStackTrace();
                        }
                    } else if (ans.equalsIgnoreCase("n")) {
                        continue;
                    }
                    break;
                case "4":
                    System.out.println("Enter shop's name:");
                    String sname = reader.nextLine();

                    try {
                        plaza.findShopByName(sname);
                    } catch (NoSuchShopException | PlazaIsClosedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    clearScreen();
                    if(plaza.isOpen() == true) {
                        System.out.println("Your Mall is already open!");
                    }else {
                        plaza.open();
                        System.out.println("You opened "+ plaza.getName()+"!");
                    }
                    break;
                case "6":
                    clearScreen();
                    if(plaza.isOpen() == false) {
                        System.out.println("Your Mall is already closed!");
                    }else {
                        plaza.close();
                        System.out.println("You closed "+ plaza.getName()+"!");
                    }
                    break;
                case "7":
                    clearScreen();
                    String status = "";
                    if(plaza.isOpen() == true) {
                        status = "Open";
                    }else if(plaza.isOpen() == false){
                        status = "Closed";
                    }
                    System.out.println("Status: " + status +"\n");
                    break;
                case "8":
                    System.out.println("Ok! See you soon! ");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong input entered! Enter a product from the given list!");
            }

        }
    }

    public void shopMenu() {
        clearScreen();
        while(true) {
            System.out.println("Hi! This is the Supercharged Cotton Chicken Deluxe Grocery Store, welcome! Press\n" +
                "1) to list available products.\n" +
                "2) to find products by name.\n" +
                "3) to display the shop's owner.\n" +
                "4) to open the shop.\n" +
                "5) to close the shop.\n" +
                "6) to add new product to the shop.\n" +
                "7) to add existing products to the shop.\n" +
                "8) to buy a product by barcode.\n" +
                "N) go back to plaza.");
            String shopChoice = reader.next();

            switch (shopChoice) {
                case "1":
                    System.out.println("1) to list available products.\n");
                case "2":
                    System.out.println("2) to find products by name.\n");
                case "3":
                    System.out.println("3) to display the shop's owner.\n");
                case "4":
                    System.out.println("4) to open the shop.\n");
                case "5":
                    System.out.println("5) to close the shop.\n");
                case "6":
                    System.out.println("6) to add new product to the shop.\n");
                case "7":
                    System.out.println("7) to add existing products to the shop.\n");
                case "8":
                    System.out.println("8) to buy a product by barcode.\n");
                case "N":
                    System.out.println("N) go back to plaza.\n");
            }
        }
    }
}

