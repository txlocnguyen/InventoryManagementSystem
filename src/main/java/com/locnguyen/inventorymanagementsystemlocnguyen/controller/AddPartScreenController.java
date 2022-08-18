package com.locnguyen.inventorymanagementsystemlocnguyen.controller;
/**
 * Add Part Controller Class
 * @author Loc Nguyen
 */
import com.locnguyen.inventorymanagementsystemlocnguyen.model.InHouse;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Inventory;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Initializes controller class AddPartScreenController and declares variables.
 */
public class AddPartScreenController implements Initializable {
    static int id;
    @FXML
    private TextField addPartID;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartMachineID;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartPriceCost;

    @FXML
    private Button cancelBtn;

    @FXML
    private RadioButton partInHouse;

    @FXML
    private RadioButton partOutSourced;

    @FXML
    private Button saveBtn;

    @FXML
    private ToggleGroup inHouseOrOutSourced;

    @FXML
    private Label machineIDOrCompanyName;
    /**
     * Initializes the controller class.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /**
     * Cancel adding part and go back to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancelAddPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Adding Part");
        alert.setHeaderText("Cancel Adding New Part");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
            stage.show();
        }
    }
    /**
     * Change the form label to machine ID if In-House radio button is selected.
     * @param event
     */
    @FXML
    public void inHouseSelected(ActionEvent event) {
        machineIDOrCompanyName.setText("Machine ID");
    }
    /**
     * Change the form label to company name if Out-Sourced radio button is selected.
     * @param event
     */
    @FXML
    public void outSourcedSelected(ActionEvent event) {
        machineIDOrCompanyName.setText("Company Name");
    }
    /**
     * LOGICAL ERROR:
     * Upon first attempting to save a new part to the inventory, I was trying to figure out a way to implement the
     * auto-increment ID for the new part. I did this by declaring a static int id variable within the class.
     * However, I encountered a logical error when using id++ while saving the new part to the inventory.
     * The issue here was that by using id++, it returns the value before id is incremented, thus resulting in a
     * logical error whereas the first id does not start with 1.
     * My solution was switching id++ with ++id, which make the id variable incremented first before return the value.
     */
    /**
     * Save the new part with user-provided information then switch to the main screen. Display error message if user-provided information is invalid.
     * @param event
     * @throws IOException
     */

    @FXML
    public void saveAddPart(ActionEvent event) throws IOException {
        try{
        String name = addPartName.getText();
        int inv = Integer.parseInt(addPartInv.getText());
        int min = Integer.parseInt(addPartMin.getText());
        int max = Integer.parseInt(addPartMax.getText());
        double price = Double.parseDouble(addPartPriceCost.getText());
        if(min > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " Min must be less than Max.");
            alert.showAndWait();
        } else if(inv < min || inv > max){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " Inv must be between Min and Max.");
            alert.showAndWait();
        } else if(name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " The name field for the part cannot be empty.");
            alert.showAndWait();
        } else if((partOutSourced.isSelected() && addPartMachineID.getText().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " Company Name field for the part cannot be empty.");
            alert.showAndWait();
        } else if (inv < 0 || min < 0 || max < 0 || price < 0 || (partInHouse.isSelected() && (Integer.parseInt(addPartMachineID.getText()) < 0))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " Inv, Min, Max, Machine ID and Price cannot be negative.");
            alert.showAndWait();
        }
        else{
            if(partInHouse.isSelected()){
                int machineID = Integer.parseInt(addPartMachineID.getText());
                InHouse newInHousePart = new InHouse(++id, name, price, inv, min, max, machineID);
                Inventory.addPart(newInHousePart);
            }
            if(partOutSourced.isSelected()){
                String companyName = addPartMachineID.getText();
                Outsourced newOutSourcedPart = new Outsourced(++id, name, price, inv, min, max, companyName);
                Inventory.addPart(newOutSourcedPart);
            }
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
            stage.show();
        }
        } catch(NumberFormatException error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    "Min, Max, Price/Cost, Inventory, and Machine ID must be numeric.");
            alert.showAndWait();
        }
    }
}
