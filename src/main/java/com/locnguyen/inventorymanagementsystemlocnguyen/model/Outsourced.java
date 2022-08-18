package com.locnguyen.inventorymanagementsystemlocnguyen.model;
/**
 * Outsourced class
 * @author Loc Nguyen
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructors of Outsourced class
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /**
     * Setter for companyName.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * Getter for companyName.
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}
