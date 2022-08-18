package com.locnguyen.inventorymanagementsystemlocnguyen.model;
/**
* Supplied class Part.java
 * @author Loc Nguyen
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     *  Constructor for Part class
     *  @param id
     *  @param name
     *  @param price
     *  @param stock
     *  @param min
     *  @param max
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for Part's
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Part's id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Part's name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for Part's name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for Part's price
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for Part's price
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Getter for Part's stock
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for Part's stock
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for Part's min
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for Part's min
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for Part's max
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for Part's max
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}