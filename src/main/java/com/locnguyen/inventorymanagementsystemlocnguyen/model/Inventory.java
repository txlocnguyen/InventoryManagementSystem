package com.locnguyen.inventorymanagementsystemlocnguyen.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Inventory class.
 * @author Loc Nguyen
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Method to add new Part to the allParts list.
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * Add new Product to the allProducts list.
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * Search the allParts list using partId.
     * @param partId
     */
    public static Part lookupPart(int partId) {
        for(int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId)
                return allParts.get(i);
        }
        return null;
    }
    /**
     * Search the allProducts list using productId.
     * @param productId
     */
    public static Product lookupProduct(int productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productId)
                return allProducts.get(i);
        }
        return null;
    }
    /**
     * Search the allParts list using partName, can be a full match or partial match.
     * @param partName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().contains(partName))
                searchResults.add(allParts.get(i));
        }
        return searchResults;
    }
    /**
     * Search the allProducts list using productName, can be a full match or partial match.
     * @param productName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().contains(productName))
                searchResults.add(allProducts.get(i));
        }
        return searchResults;
    }
    /**
     * Update the part in the allParts list.
     * @param index
     * @param newPart
     */
    public static void updatePart(int index, Part newPart) {
        int i = 0;
        for (Part part : allParts) {
            if (part.getId() == index) {
                allParts.set(i, newPart);
            }
            i++;
        }
    }
    /**
     * Update the product in the allProducts list.
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        int i = 0;
        for (Product product : allProducts) {
            if (product.getId() == index) {
                allProducts.set(i, newProduct);
            }
            i++;
        }
    }
    /**
     * Delete a part from the allParts list.
     * @param part
     * @return true if successful, false otherwise.
     */
    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
    }
    /**
     * Delete a part from the allProducts list.
     * @param product
     * @return true if successful, false otherwise.
     */
    public static boolean deleteProduct(Product product) {
        allProducts.remove(product);
        return true;
    }
    /**
     * Return the allParts list.
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Return the allProducts list.
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
