package com.locnguyen.inventorymanagementsystemlocnguyen.model;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
/**
 * Product class.
 * @author Loc Nguyen
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Product class constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param associatedParts
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }
    /**
     * Getter for Product's id.
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * Setter for Product's id.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter for Product's name.
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter for Product's name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter for Product's price.
     * @return price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Setter for Product's price.
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Getter for Product's stock.
     * @return stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * Setter for Product's stock.
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * Getter for Product's min.
     * @return min
     */
    public int getMin() {
        return min;
    }
    /**
     * Setter for Product's min.
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * Getter for Product's max.
     * @return max
     */
    public int getMax() {
        return max;
    }
    /**
     * Setter for Product's max.
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Return all associated parts of a product.
     * @return all associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts;}
    /**
     * Method to add an associated part to a product.
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**
     * Method to remove an associated part from a product.
     * @param part
     * @return true if part is removed, false otherwise.
     */
    public boolean deleteAssociatedPart(Part part) {
        for (int i=0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == part.getId()){
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }
}
