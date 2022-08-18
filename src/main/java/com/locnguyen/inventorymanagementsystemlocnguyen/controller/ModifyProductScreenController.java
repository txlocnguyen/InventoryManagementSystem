package com.locnguyen.inventorymanagementsystemlocnguyen.controller;
/**
 * Modify Product Screen Controller Class
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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Initializes controller class ModifyProductScreenController and declares variables.
 */
public class ModifyProductScreenController implements Initializable {
    public ObservableList<Part> associatedPartList = FXCollections.observableArrayList();
    @FXML
    private Button addProductBtn;

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
    private TextField modProductID;

    @FXML
    private TextField modProductInv;

    @FXML
    private TextField modProductMax;

    @FXML
    private TextField modProductMin;

    @FXML
    private TextField modProductName;

    @FXML
    private TextField modProductPrice;

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
     * Initializes the controller class. Populate the part table and associated part table using selected product's existing data.
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
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * Populate the main form with selected product's existing data.
     */
    public void populateForm(Product product) {
        modProductID.setText(String.valueOf(product.getId()));
        modProductName.setText(product.getName());
        modProductInv.setText(String.valueOf(product.getStock()));
        modProductPrice.setText(String.valueOf(product.getPrice()));
        modProductMax.setText(String.valueOf(product.getMax()));
        modProductMin.setText(String.valueOf(product.getMin()));
        associatedPartList = product.getAllAssociatedParts();
        associatedPartTable.setItems(associatedPartList);
    }
    /**
     * Cancel modifying product and return to main screen when cancel button is clicked.
     * @param event
     * @throws IOException
     */
    public void cancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modifying Product");
        alert.setHeaderText("Cancel Modifying This Product");
        alert.setContentText("Are you sure you want to cancel modifying this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
            stage.show();
        }
    }
    /**
     * Search through part list and display the result in part table. Display an error otherwise if no matching part is found.
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
    public void addPartToProduct(ActionEvent event) {
        Part part = partTable.getSelectionModel().getSelectedItem();
        associatedPartList.add(part);
        associatedPartTable.setItems(associatedPartList);
    }
    /**
     * Delete an associated part from the associated part table.
     * @param event
     */
    @FXML
    public void removePartFromProduct(ActionEvent event) {
        Part part = associatedPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Confirmation");
        alert.setHeaderText("Remove Associated Part");
        alert.setContentText("Are you sure you want to remove this part from the associated parts list?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK && result.isPresent()){
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.show();
            associatedPartList.remove(part);
            associatedPartTable.setItems(associatedPartList);
        }
    }
    /**
     * Save modified product data to the inventory. Display an error if provided data is invalid.
     * @param event
     * @throws IOException
     */
    @FXML
    public void saveProduct(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modProductID.getText());
            String name = modProductName.getText();
            int inv = Integer.parseInt(modProductInv.getText());
            int min = Integer.parseInt(modProductMin.getText());
            int max = Integer.parseInt(modProductMax.getText());
            double price = Double.parseDouble(modProductPrice.getText());
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
                Product product = new Product(id, name, price, inv, min, max, associatedPartList);
                Inventory.updateProduct(product.getId(), product);
                Stage stage = (Stage) saveProductBtn.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Product Error");
            alert.setHeaderText("Error in Product Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " Min, Max, Price/Cost, Inventory must be numeric.");
            alert.showAndWait();
        }
    }

}
