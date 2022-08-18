package com.locnguyen.inventorymanagementsystemlocnguyen.model;
/**
 * InHouse Class
 * @author Loc Nguyen
 */
public class InHouse extends Part{
    private int machineId;
    /**
     * Constructor for InHouse class.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse (int id, String name, double price, int stock, int min, int max, int machineId) {
        super (id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * Getter for machineId.
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * Setter for machineId.
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


}
