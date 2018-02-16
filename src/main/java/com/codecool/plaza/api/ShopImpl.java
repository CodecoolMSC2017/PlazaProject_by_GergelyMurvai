package com.codecool.plaza.api;

import java.util.*;

public class ShopImpl implements Shop {

    private String name;
    private String owner;
    private boolean open;
    private Map<Long, ShopImplEntry> products;

    public ShopImpl(String name, String owner) {
        this.name = name;
        this.owner = owner;
        open = false;
        products = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public void open() {
        open = true;
    }

    @Override
    public void close() {
        open = false;
    }

    @Override
    public List<Product> getAllProducts() throws ShopIsClosedException {
        List<Product> productList = new ArrayList<>();
        if (isOpen()) {
            for (ShopImplEntry temp : products.values()) {
                productList.add(temp.getProduct());

            }
        } else {
            throw new ShopIsClosedException("This shop is closed!\n");
        }
        return productList;

    }

    @Override
    public float getPrice(long barcode) throws NoSuchProductException, ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
                if (entry.getKey() == barcode) {
                    return entry.getValue().getPrice();
                }
            }
            throw new NoSuchProductException("No product with this barcode!\n");
        }
        throw new ShopIsClosedException("This shop is closed!\n");
    }


    @Override
    public boolean isOpen() {
        return open;
    }

    public Map<Long, ShopImplEntry> getProducts() {
        return products;
    }

    public Product findByName(String name) throws ShopIsClosedException, NoSuchProductException {
        Product product = null;
        if (isOpen()) {
            for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
                ShopImplEntry temp = entry.getValue();
                if (temp.getProduct().getName().equals(name)) {
                    product = temp.getProduct();
                }
            }
            if (product == null) {
                throw new NoSuchProductException("No such product in the shop!");
            } else {
                return product;
            }
        }
        throw new ShopIsClosedException("This shop is closed!");
    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
                long code = entry.getKey();
                if (code == barcode) {
                    return true;
                }
            }
            return false;
        }
        throw new ShopIsClosedException("This hop is closed!\n");
    }

    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        if (isOpen()) {
            for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
                if (entry.getValue().getProduct().getBarcode() == product.getBarcode()) {
                    throw new ProductAlreadyExistsException("Product with this barcode is already exist!\n");
                }
            }
            products.put(product.getBarcode(), new ShopImplEntry(product, quantity, price));

        } else {
            throw new ShopIsClosedException("This shop is closed!\n");
        }
    }


    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        if (isOpen()) {
            if (hasProduct(barcode)) {
                for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
                    if (barcode == entry.getKey()) {
                        entry.getValue().increaseQuantity(quantity);
                    }
                }

            }
            throw new NoSuchProductException("No such product in the shop!");
        }
        throw new ShopIsClosedException("This shop is closed!");
    }

    public Product buyProduct(long barcode) throws NoSuchProductException, ShopIsClosedException, OutOfStockException {
        if (isOpen()) {
            if (hasProduct(barcode)) {
                for (Map.Entry<Long, ShopImplEntry> entry : products.entrySet()) {
                    if (barcode == entry.getKey()) {
                        if (entry.getValue().getQuantity() > 0) {
                            entry.getValue().decreaseQuantity(1);
                            return entry.getValue().getProduct();

                        } else {
                            throw new OutOfStockException("This product is out of stock!");
                        }

                    }
                }
            }
            throw new NoSuchProductException("No such product in the shop!");
        }
        throw new ShopIsClosedException("This shop is closed!");
    }

    class ShopImplEntry {
        private Product product;
        private int quantity;
        private float price;

        public ShopImplEntry(Product product, int quantity, float price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void increaseQuantity(int amount) {
            quantity += amount;
        }

        public void decreaseQuantity(int amount) {
            quantity -= amount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String toString() {
            return getProduct().toString() + ", quantity: " + getQuantity() + ", price: " + getPrice();
        }

    }


}



