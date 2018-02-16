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
    private float credit = 0;
    private final Scanner reader = new Scanner(System.in);

    public CmdProgram(String[] args) {
    }


    public void run() {
        mainMenu();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void mainMenu() {

        System.out.println("Type your name !");
        ownerName = reader.next();
        System.out.println("Type the new Plaza's name!");
        //String s = reader.nextLine();
        plazaName = reader.next();
        plaza = new PlazaImpl(ownerName, plazaName);

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
                    try {
                        for (int i = 0; i < plaza.getShops().size(); i++) {
                            String name = plaza.getShops().get(i).getName();
                            String owner = plaza.getShops().get(i).getOwner();
                            boolean status = plaza.getShops().get(i).isOpen();
                            System.out.println((i + 1) + ". Shop name: " + name + ", Owner of the shop: " + owner + ", It' opened: " + status);
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
                        System.out.println(e.getMessage());
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
                            System.out.println(e.getMessage());
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
                        shop = (ShopImpl)tmpShop;
                        shopMenu(shop, cart);
                    } catch (NoSuchShopException | PlazaIsClosedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":

                    if (plaza.isOpen() == true) {
                        System.out.println("Your Mall is already open!");
                    } else {
                        plaza.open();
                        System.out.println("You opened " + plaza.getName() + "!");
                    }
                    break;
                case "6":

                    if (plaza.isOpen() == false) {
                        System.out.println("Your Mall is already closed!");
                    } else {
                        plaza.close();
                        System.out.println("You closed " + plaza.getName() + "!");
                    }
                    break;
                case "7":

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

    public void shopMenu(ShopImpl shop, List<Product> cart) {


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
                "9) to print your cart \n" +
                "N) go back to plaza.");
            String shopChoice = reader.next();

            switch (shopChoice) {
                case "1":
                    try {
                        List<Product> tempProductList = shop.getAllProducts();
                        for (Product product : tempProductList) {
                            System.out.println(product.toString() + ", price: " + shop.getPrice(product.getBarcode()));
                        }
                    } catch (ShopIsClosedException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    System.out.println("\n");
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
                    Product newProduct = null;
                    reader.nextLine();
                    System.out.println("Enter the name of the product :");
                    String name = reader.nextLine();

                    System.out.println("Please enter the manufacturer of the product");
                    String manufacturer = reader.nextLine();

                    System.out.println("Please enter the barcode of the product");
                    String barcodeString = reader.nextLine();
                    long barcode;
                    try {
                        barcode = Long.parseLong(barcodeString);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong input entered! Enter a number!");
                        break;
                    }


                    System.out.println("Please enter the type of the product! (Food, Cloth)");
                    String cases = reader.nextLine();

                    switch (cases) {
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
                            newProduct = new FoodProduct(name, barcode, manufacturer, calories, bestBefore);
                            break;
                        case "Cloth":
                            System.out.println("Material: ");
                            String mater = reader.nextLine();

                            System.out.println("Type of the garment: ");
                            String type = reader.nextLine();
                            newProduct = new ClothingProduct(name, barcode, manufacturer, mater, type);
                            break;

                        default:
                            System.out.println("Wrong input entered! Enter a product from the given list!");
                            break;
                    }

                    System.out.println("Please enter the quantity of the product");
                    reader.nextLine();
                    String quantityString = reader.nextLine();
                    int quantity;

                    try {
                        quantity = Integer.parseInt(quantityString);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong input entered! Enter a number!");
                        break;
                    }

                    System.out.println("Please enter the price of the product");
                    String priceString = reader.nextLine();
                    float price;

                    try {
                        price = Float.parseFloat(priceString);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong input entered! Enter a number!");
                        break;
                    }

                    try {
                        shop.addNewProduct(newProduct, quantity, price);
                    } catch (ProductAlreadyExistsException | ShopIsClosedException ex) {
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
                    } catch (NoSuchProductException | ShopIsClosedException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "8":
                    int rounds = 0;
                    System.out.println("Enter the item's barcode  what you want to buy:");
                    long code = reader.nextLong();

                    System.out.println("Enter how many you want to buy:");
                    int amount = reader.nextInt();
                    while (rounds < amount) {
                        try {
                            cart.add(shop.buyProduct(code));
                            credit += (shop.getPrice(code));
                            rounds++;

                        } catch (NoSuchProductException | ShopIsClosedException | OutOfStockException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                    break;
                case "9":
                    System.out.println("Your shopping cart list : ");
                    for (Product prod : cart) {
                        System.out.println(" - " + prod.getName());
                    }
                    System.out.println(credit);
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

