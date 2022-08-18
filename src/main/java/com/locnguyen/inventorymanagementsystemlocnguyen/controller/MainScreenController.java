package com.locnguyen.inventorymanagementsystemlocnguyen.controller;
/**
 * MainScreenController Class
 * @author Loc Nguyen
 */
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Inventory;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Part;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Product;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Initialize the MainScreenController class and declare variables.
 */
public class MainScreenController implements Initializable {
    Stage stage;
    @FXML
    private Button addPartBtn;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button deletePartBtn;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField mainPartSearch;

    @FXML
    private TextField mainProductSearch;

    @FXML
    private Button modifyPartBtn;

    @FXML
    private Button modifyProductBtn;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, Integer> partInvLevel;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPricePerUnit;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, Integer> productInvLevel;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Double> productPricePerUnit;

    @FXML
    private TableView<Product> productTable;

    /**
     * Initializes the controller class. Populate parts table and products table with existing data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * Close the program when exit button is clicked.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }
    /**
     * Go to add part screen when add part button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToAddPartScreen(ActionEvent event) throws IOException {
        Parent addPartScreen = FXMLLoader.load(getClass().getResource("/addPartScreen.fxml"));
        Scene addPartScene = new Scene(addPartScreen);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(addPartScene);
        stage.show();
    }
    /**
     * Go to add product screen when add product button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToAddProductScreen(ActionEvent event) throws IOException {
        Parent addProductScreen = FXMLLoader.load(getClass().getResource("/addProductScreen.fxml"));
        Scene addProductScene = new Scene(addProductScreen);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(addProductScene);
        stage.show();
    }
    /**
     * RUNTIME ERROR:
     * When I was first implementing the screen switch to modify part screen, the program throws a runtime error after
     * clicking modify if there was no part selected. The solution was to implement an if-else statement to
     * check if there was a part selected before clicking modify.
     */
    /**
     * Go to modify part screen when modify part button is clicked. If there is no part selected, display an error message.
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToModifyPartScreen(ActionEvent event) throws IOException {
        if(partTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader =  new FXMLLoader();
            loader.setLocation(getClass().getResource("/modifyPartScreen.fxml"));
            loader.load();
            ModifyPartScreenController controller = loader.getController();
            controller.populateForm(partTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modify Part Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("You have to select a part to modify");
            alert.showAndWait();
        }
    }
    /**
     * Go to modify product screen when modify product button is clicked. If there is no product selected, display an error message.
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToModifyProductScreen(ActionEvent event) throws IOException {
        if(productTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader =  new FXMLLoader();
            loader.setLocation(getClass().getResource("/modifyProductScreen.fxml"));
            loader.load();
            ModifyProductScreenController controller = loader.getController();
            controller.populateForm(productTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modify Product Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("You have to select a product to modify");
            alert.showAndWait();
        }
    }
    /**
     * Delete part when delete part button is clicked. If there is no part selected, display an error message.
     */
    @FXML
    public void deletePart() {
        if(partTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you would like to delete this part?");
            alert.setContentText("This action cannot be undone.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
                partTable.setItems(Inventory.getAllParts());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Part Error");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("You have to select a part to delete");
            alert.showAndWait();
        }
    }
    /**
     * Delete product when delete product button is clicked. If there is no product selected, display an error message.
     */
    @FXML
    public void deleteProduct() {
        if(productTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you would like to delete this product?");
            alert.setContentText("This action cannot be undone.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                if(productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().size() > 0) {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Delete Product Error");
                    alert2.setHeaderText("Product has associated parts");
                    alert2.setContentText("Please remove all associated parts before deleting product");
                    alert2.showAndWait();
                } else {
                    Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    productTable.setItems(Inventory.getAllProducts());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Product Error");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("You have to select a product to delete");
            alert.showAndWait();
        }
    }
    /**
     * Search for parts after the user enters a search term in the search field and hit enter. Display an error message if no parts are found.
     */
    @FXML
    public void partSearch() {
        String searchString = mainPartSearch.getText();
        try{
            ObservableList<Part> searchResults = Inventory.lookupPart(searchString);
            if(searchResults.size() == 0){
                int searchID = Integer.parseInt(searchString);
                searchResults.add(Inventory.lookupPart(searchID));
                if (Inventory.lookupPart(searchID) == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Error");
                    alert.setHeaderText("Invalid Search");
                    alert.setContentText("No matching parts found with provided query.");
                    alert.showAndWait();
                }
            }
            partTable.setItems(searchResults);
        } catch (NumberFormatException e) {
            partTable.setItems(null);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Error");
            alert.setHeaderText("Invalid Search");
            alert.setContentText("No matching parts found with provided query.");
            alert.showAndWait();
        }
    }
    /**
     * Search for products after the user enters a search term in the search field and hit enter. Display an error message if no products are found.
     */
    @FXML
    public void productSearch() {
        String searchString = mainProductSearch.getText();
        try{
            ObservableList<Product> searchResults = Inventory.lookupProduct(searchString);
            if(searchResults.size() == 0){
                int searchID = Integer.parseInt(searchString);
                searchResults.add(Inventory.lookupProduct(searchID));
                if (Inventory.lookupProduct(searchID) == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Error");
                    alert.setHeaderText("Invalid Search");
                    alert.setContentText("No matching products found with provided query.");
                    alert.showAndWait();
                }
            }
            productTable.setItems(searchResults);
        } catch (NumberFormatException e) {
            productTable.setItems(null);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Error");
            alert.setHeaderText("Invalid Search");
            alert.setContentText("No matching products found with provided query.");
            alert.showAndWait();
        }
    }
    /**
     * Restore the default view of the part table when the user clear the search field.
     */
    @FXML
    public void restorePartSearch() {
        if(mainPartSearch.getText().isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
        }
    }
    /**
     * Restore the default view of the product table when the user clear the search field.
     */
    @FXML
    public void restoreProductSearch() {
        if(mainProductSearch.getText().isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());
        }
    }
}
