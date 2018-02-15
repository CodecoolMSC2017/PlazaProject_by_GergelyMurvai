package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {

    private List<Shop> shops;
    private String owner;
    private String name;
    private boolean open;


    public PlazaImpl(String owner,String name) {
        this.owner = owner;
        this.name = name;
        shops = new ArrayList<>();
        open = false;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return owner;
    }


    public boolean shopIsExist(Shop shop) {
        for (Shop tempShop : shops) {
            if (tempShop.getName().equals(shop.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {


        if (isOpen()) {
            if (!shops.contains(shop)) {
                shops.add(shop);
            } else {
                throw new ShopAlreadyExistsException("Shop "+ shop.getName()+ " is already exist\n");
            }
        } else {
            throw new PlazaIsClosedException("Plaza " + name + " is closed!\n");
        }
    }

    public List<Shop> getShops() throws PlazaIsClosedException {
        if(isOpen()) {
            return shops;
        }
        throw new PlazaIsClosedException("Plaza is closed");
    }

    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if (isOpen()) {
            if (shops.contains(shop)) {
                shops.remove(shop);
            } else {
                throw new NoSuchShopException("There's no shop found in this plaza!");
            }
        }else{
            throw new PlazaIsClosedException("The plaza is closed!");
        }
    }

    public Shop findShopByName(String name) throws NoSuchShopException, PlazaIsClosedException {

        if (isOpen()) {
            for (Shop shop : shops) {
                if (shop.getName().equals(name)) {
                    return shop;
                } else {
                    throw new NoSuchShopException("There's no shop found in this plaza!");
                }
            }
        } else {
            throw new PlazaIsClosedException("The plaza is closed!");
        }
        return null;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }

    public String toString() {
        return "Owner's name: " + owner + "Shop name: " + name;
    }


}
