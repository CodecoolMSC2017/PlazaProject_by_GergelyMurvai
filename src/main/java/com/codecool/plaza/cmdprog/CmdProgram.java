package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.util.List;
import java.util.Scanner;

public class CmdProgram {

    private List<Product> cart;
    Scanner reader = new Scanner(System.in);
    PlazaImpl plaza;


    public CmdProgram(String[] args) {

    }


    public void run(){
        System.out.println("Enter your name:");
        String owner = reader.nextLine();
        while (true){
            System.out.println("There are no plaza created yet! Press\n 1) to create a new plaza.\n 2) to exit.");
            String choose = reader.nextLine();
            if (choose.equals("1")) {
                System.out.println("Enter the name of your plaza:");
                String plazaName = reader.nextLine();
                plaza = new PlazaImpl(owner,plazaName);
                break;
            }
            else if(choose.equals("2")) {
                System.out.println("OK! See you soon!");
                System.exit(0);
            }
            else {
                System.out.println("It's not a valid input!");
            }

        }
        while (true){
            System.out.println("Welcome to the " + plaza.getName() + "\n owned by : " + plaza.getOwnerName() + "!\nPress: \n1) to list all shops. \n2) to add a new shop. \n3) to remove an existing shop. \n4) enter a shop by name. \n5) to open the plaza. \n6) to close the plaza. \n7) to check if the plaza is open or not.");
            int inp = reader.nextInt();
            if (inp == 1) {
                System.out.println("It would be list all the shops soon...");
                break;
            } else if (inp == 2) {
                System.out.println("Here you can create a new shop soon...");
                break;
            } else if (inp == 3) {
                System.out.println("Remove shops soon...");
                break;
            } else if (inp == 4) {
                System.out.println("Enter into a shop by name soon...");
                break;
            } else if (inp == 5) {
                System.out.println("Opening the plaza soon...");
                break;
            } else if (inp == 6) {
                System.out.println("Closing the plaza soon...");
                break;
            } else if (inp == 7) {
                System.out.println("Checking the opening status soon...");
                break;
            } else if (inp == 8) {
                System.out.println("OK! See you soon!");
                System.exit(0);
            } else {
                System.out.println("It's not a valid input!");
            }
        }


    }


}
