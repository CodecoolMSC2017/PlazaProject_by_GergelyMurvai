package com.codecool.plaza.api;

import java.util.List;
import java.util.Map;

public interface Shop {
    public String getName();

    public String getOwner();

    public boolean isOpen();

    public void open();

    public void close();

    public List<Product> getAllProducts() throws ShopIsClosedException ;

    public float getPrice(long barcode);

    public Product findByName(String name) throws ShopIsClosedException, NoSuchProductException;

    public boolean hasProduct(long barcode) throws ShopIsClosedException;

    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException;

    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException;

    public Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException, OutOfStockException;

    public String toString();

}
