package com.locnguyen.inventorymanagementsystemlocnguyen.controller;
/**
 * Add Product Controller Class
 * @author Loc Nguyen
 */
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Inventory;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Part;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Initializes controller class AddProductScreenController and declares variables.
 */
public class AddProductScreenController implements Initializable {
    static int productID = 1000;
    ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    @FXML
    private Button addProductBtn;

    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductMax;

    @FXML
    private TextField addProductMin;

    @FXML
    private TextField addProductName;

    @FXML
    private TextField addProductPrice;

    @FXML
    private TableColumn<Part, Integer> associatedPartID;

    @FXML
    private TableColumn<Part, Integer> associatedPartInvLevel;

    @FXML
    private TableColumn<Part, String> associatedPartName;

    @FXML
    private TableColumn<Part, Double> associatedPartPricePerUnit;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private Button cancelBtn;

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
    private TextField productPartSearch;

    @FXML
    private Button removeProductBtn;

    @FXML
    private Button saveProductBtn;
    /**
     *  Initializes the controller class. Populate the associated part and part table with data from the part inventory.
     *  @param u
     *  @param rb
     */
    @Override
    public void initialize(URL u, ResourceBundle rb){
        partTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    /**
     * Cancel adding new product. Go back to the main screen when cancel button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Product");
        alert.setHeaderText("Cancel Adding New Product");
        alert.setContentText("Are you sure you want to cancel adding a new product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
            stage.show();
        }
    }
    /**
     * Search through the part list and display the result in the part table. Display an error otherwise if there was no matching result.
     * @param event
     */
    @FXML
    public void searchPartTable(ActionEvent event) {
        String searchString = productPartSearch.getText();
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
     * Restore the part table when the search field is cleared.
     */
    @FXML
    public void restorePartSearch() {
        if(productPartSearch.getText().isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
        }
    }
    /**
     * Add selected part to the associated part table.
     * @param event
     */
    @FXML
    public void addToAssociatedTable(ActionEvent event) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        associatedPartsList.add(part);
        associatedPartTable.setItems(associatedPartsList);
    }
    /**
     * Delete an associated part from the associated part table.
     * @param event
     */
    @FXML
    public void removeFromAssociatedTable(ActionEvent event) {
        Part part = associatedPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Confirmation");
        alert.setHeaderText("Remove Associated Part");
        alert.setContentText("Are you sure you want to remove this part from the associated parts list?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK && result.isPresent()){
            associatedPartsList.remove(part);
            associatedPartTable.setItems(associatedPartsList);
        }
    }
    /**
     * Add new product to the product list with user-provided information, then go back to the main screen.
     * Display an error otherwise if the information provided was invalid.
     * @param event
     * @throws IOException
     */
    public void addingProduct(ActionEvent event) throws IOException {
        try {
            String name = addProductName.getText();
            int inv = Integer.parseInt(addProductInv.getText());
            int min = Integer.parseInt(addProductMin.getText());
            int max = Integer.parseInt(addProductMax.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in Product Information");
                alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                        " Min must be less than Max.");
                alert.showAndWait();
            } else if(inv < min || inv > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in Product Information");
                alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                        " Inv must be between Min and Max.");
                alert.showAndWait();
            } else if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in Product Information");
                alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                        " The name field for the part cannot be empty.");
                alert.showAndWait();
            } else if (inv < 0 || min < 0 || max < 0 || price < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in Product Information");
                alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                        " Inv, Min, Max, and Price cannot be negative.");
                alert.showAndWait();
            } else {
                Product product = new Product(++productID, name, price, inv, min, max, associatedPartsList);
                Inventory.addProduct(product);
                Stage stage = (Stage) saveProductBtn.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Product Error");
            alert.setHeaderText("Error in Product Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    "Min, Max, Price/Cost, Inventory must be numeric.");
            alert.showAndWait();
        }
    }
}
