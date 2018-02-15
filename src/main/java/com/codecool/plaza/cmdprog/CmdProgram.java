package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        System.out.flush();
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
                plaza = new PlazaImpl(ownerName, plazaName);
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
        while (true) {
            System.out.println("Welcome into the " + plaza.getName() + " owned by: " + plaza.getOwnerName() + "\n");
            System.out.println("1) to list all shops.\n" +
                "2) to add a new shop.\n" +
                "3) to remove an existing shop.\n" +
                "4) enter a shop by name.\n" +
                "5) to open the plaza.\n" +
                "6) to close the plaza.\n" +
                "7) to check if the plaza is open or not.\n" +
                "8) leave plaza.");
            String choice = reader.next();

            switch (choice) {
                case "1":
                    clearScreen();
                    try {
                        for (int i = 0; i < plaza.getShops().size(); i++) {
                            String name = plaza.getShops().get(i).getName();
                            String owner = plaza.getShops().get(i).getOwner();
                            boolean status = plaza.getShops().get(i).isOpen();
                            System.out.println((i + 1) + ". Shop name: " + name + ", Owner of the shop: " + owner+", It' opened: "+ status);
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
                    String q = reader.nextLine();
                    String ans = reader.nextLine();
                    if (ans.equalsIgnoreCase("y")) {
                        System.out.println("Enter the name of the store:");
                        String del = reader.nextLine();
                        try {
                            for (Shop sh : plaza.getShops()) {
                                if (del.equals(sh.getName())) {
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
                    reader.nextLine();
                    String shpName = reader.nextLine();

                    try {
                        Shop tmpShop = plaza.findShopByName(shpName);
                        shopMenu(tmpShop,cart);
                    } catch (NoSuchShopException | PlazaIsClosedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    clearScreen();
                    if (plaza.isOpen() == true) {
                        System.out.println("Your Mall is already open!");
                    } else {
                        plaza.open();
                        System.out.println("You opened " + plaza.getName() + "!");
                    }
                    break;
                case "6":
                    clearScreen();
                    if (plaza.isOpen() == false) {
                        System.out.println("Your Mall is already closed!");
                    } else {
                        plaza.close();
                        System.out.println("You closed " + plaza.getName() + "!");
                    }
                    break;
                case "7":
                    clearScreen();
                    String status = "";
                    if (plaza.isOpen() == true) {
                        status = "Open";
                    } else if (plaza.isOpen() == false) {
                        status = "Closed";
                    }
                    System.out.println("Status: " + status + "\n");
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

    public void shopMenu(Shop shop, List<Product> cart) {
        clearScreen();
        Map<Long, ShopImpl.ShopImplEntry> products = shop.getProductsMap();
        while (true) {
            System.out.println("Hi! This is the " + shop.getName() + " owned by" + shop.getOwner() + ", welcome!\n" +
                "Press\n" +
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

                    for (Map.Entry<Long, ShopImpl.ShopImplEntry> entry : shop.getProductsMap().entrySet()) {
                        System.out.println(entry.getValue() + " ");
                    }
                    break;
                case "2":
                    System.out.println("Type the name of the product. ");
                    String nameSearch = reader.next();
                    try {
                        System.out.println(shop.findByName(nameSearch));
                    } catch (ShopIsClosedException e) {
                        System.out.println(e.getMessage());
                    } catch (NoSuchProductException nsp) {
                        System.out.println(nsp.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("The shop's owner is: " + shop.getOwner());
                    break;
                case "4":
                    if (shop.isOpen() == true) {
                        System.out.println(shop.getName() + " is already open");
                    } else {
                        shop.open();
                        System.out.println(shop.getName() + " is opened.\n");
                    }
                    break;
                case "5":
                    if (shop.isOpen() != true) {
                        System.out.println(shop.getName() + " is already closed");
                    } else {
                        shop.open();
                        System.out.println(shop.getName() + " is closed.\n");
                    }
                    break;
                case "6":
                    System.out.println("Please enter the name of the product");
                    String l = reader.nextLine();
                    String name = reader.nextLine();

                    System.out.println("Please enter the manufacturer of the product");
                    String manufacturer = reader.nextLine();

                    System.out.println("Please enter the barcode of the product");
                    long barcode = reader.nextLong();

                    System.out.println("Please enter the quantity of the product");
                    int quantity = reader.nextInt();

                    System.out.println("Please enter the price of the product");
                    float price = reader.nextFloat();


                    System.out.println("Please enter the type of the product! (Food, Clothing,)");

                    String prodType = reader.next();
                    try {
                        switch (prodType) {
                            case "Food":
                                System.out.println("Num of Calories: ");
                                int calories = reader.nextInt();

                                System.out.println("Best before /yyyy-MM-dd/: ");

                                String bestBeforeStr = reader.next();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date bestBefore = null;
                                try {
                                    bestBefore = dateFormat.parse(bestBeforeStr);
                                } catch (ParseException ex) {
                                    System.out.println("Wrong input entered! Enter a number!");
                                    break;
                                }

                                try {
                                    shop.addNewProduct((new FoodProduct(name, barcode, manufacturer, calories, bestBefore)), quantity, price);

                                } catch (ShopIsClosedException scl) {
                                    System.out.println(scl.getMessage());
                                }
                                break;
                            case "Clothing":
                                System.out.println("Material: ");
                                reader.nextLine();
                                String mater = reader.nextLine();

                                System.out.println("Type of the garment: ");
                                String type = reader.nextLine();
                                try{
                                    shop.addNewProduct(new ClothingProduct(name, barcode, manufacturer, mater, type), quantity, price);
                                    break;
                                }catch (ShopIsClosedException shp) {
                                    System.out.println(shp.getMessage());
                                }
                                break;
                            default:
                                System.out.println("Wrong input entered! Enter a product from the given list!");
                                break;
                        }
                    } catch (ProductAlreadyExistsException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;

                case "7":
                    System.out.println("Enter the barcode of the product");
                    long bCodeInp = reader.nextLong();


                    System.out.println("Please enter the quantity of the product");
                    int addQuantity = reader.nextInt();

                    try {
                        shop.addProduct(bCodeInp, addQuantity);
                        break;
                    } catch (NoSuchProductException ex) {
                        System.out.println(ex.getMessage());
                    }catch (ShopIsClosedException shc) {
                        System.out.println(shc.getMessage());
                    }
                    break;
                case "8":
                    System.out.println("8) to buy a product by barcode.\n");
                    break;
                case "N":
                    System.out.println("Back into " + plaza.getName() + " plaza.\n");
                    mainMenu();
                    break;
                default:
                    System.out.println("Wrong input entered! Enter a proer input from the given list!");
            }
        }
    }
}
