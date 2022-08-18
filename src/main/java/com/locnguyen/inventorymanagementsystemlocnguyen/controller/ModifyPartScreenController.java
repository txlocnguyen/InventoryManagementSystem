package com.locnguyen.inventorymanagementsystemlocnguyen.controller;
/**
 * Modify Product Screen Controller Class
 * @author Loc Nguyen
 */
import com.locnguyen.inventorymanagementsystemlocnguyen.model.InHouse;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Inventory;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Outsourced;
import com.locnguyen.inventorymanagementsystemlocnguyen.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Initializes controller class ModifyPartScreenController and declares variables.
 */
public class ModifyPartScreenController implements Initializable {

    @FXML
    private TextField modPartID;

    @FXML
    private Button cancelBtn;

    @FXML
    private ToggleGroup inHouseOrOutsourced;

    @FXML
    private Label machineIDOrOutSourced;

    @FXML
    private TextField modPartInv;

    @FXML
    private TextField modPartMachineID;

    @FXML
    private TextField modPartMax;

    @FXML
    private TextField modPartMin;

    @FXML
    private TextField modPartName;

    @FXML
    private TextField modPartPriceCostPerUnit;

    @FXML
    private RadioButton partInHouse;

    @FXML
    private RadioButton partOutSourced;

    @FXML
    private Button saveBtn;
    /**
     * Populate the form's fields with selected part's existing data.
     * @param part
     */
    public void populateForm(Part part) {
        modPartID.setText(String.valueOf(part.getId()));
        modPartName.setText(part.getName());
        modPartInv.setText(String.valueOf(part.getStock()));
        modPartPriceCostPerUnit.setText(String.valueOf(part.getPrice()));
        modPartMin.setText(String.valueOf(part.getMin()));
        modPartMax.setText(String.valueOf(part.getMax()));
        if (part instanceof com.locnguyen.inventorymanagementsystemlocnguyen.model.InHouse) {
            partInHouse.setSelected(true);
            partOutSourced.setSelected(false);
            machineIDOrOutSourced.setText("Machine ID");
            modPartMachineID.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            partInHouse.setSelected(false);
            partOutSourced.setSelected(true);
            machineIDOrOutSourced.setText("Company Name");
            modPartMachineID.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     * Change form label to Machine ID if the In-House radio button is selected
     * @param event
     */
    public void inHouseSelected(ActionEvent event) {
        machineIDOrOutSourced.setText("Machine ID");
    }
    /**
     * Change form label to Company Name if the Out-Sourced radio button is selected
     * @param event
     */
    public void outSourcedSelected(ActionEvent event) {
        machineIDOrOutSourced.setText("Company Name");
    }
    /**
     * Cancel modifying part and return to main screen when cancel button is clicked.
     * @param event
     * @throws IOException
     */
    public void cancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modifying Part");
        alert.setHeaderText("Cancel Modifying This Part");
        alert.setContentText("Are you sure you want to cancel modifying this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
            stage.show();
        }
    }
    /**
     * Save newly changes to selected part when save button is clicked. Display an error if provided data is invalid.
     * @param event
     * @throws IOException
     */
    public void saveBtnClicked(ActionEvent event) throws IOException {
        try{
            int id = Integer.parseInt(modPartID.getText());
            String name = modPartName.getText();
            int inv = Integer.parseInt(modPartInv.getText());
            double price = Double.parseDouble(modPartPriceCostPerUnit.getText());
            int min = Integer.parseInt(modPartMin.getText());
            int max = Integer.parseInt(modPartMax.getText());
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
            } else if((partOutSourced.isSelected() && modPartMachineID.getText().isEmpty())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in Part Information");
                alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                        " Company Name field for the part cannot be empty.");
                alert.showAndWait();
            } else if (inv < 0 || min < 0 || max < 0 || price < 0 || (partInHouse.isSelected() && (Integer.parseInt(modPartMachineID.getText()) < 0))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error in Part Information");
                alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                        " Inv, Min, Max, Machine ID and Price cannot be negative.");
                alert.showAndWait();
            }
            else{
                if(partInHouse.isSelected()){
                    int machineID = Integer.parseInt(modPartMachineID.getText());
                    Inventory.updatePart(id, new InHouse(id, name, price, inv, min, max, machineID));
                }
                if(partOutSourced.isSelected()){
                    String companyName = modPartMachineID.getText();
                    Inventory.updatePart(id, new Outsourced(id, name, price, inv, min, max, companyName));
                }
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/mainScreen.fxml"))));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error in Part Information");
            alert.setContentText("Please check and make sure that all fields are filled out correctly." +
                    " Min, Max, Price/Cost, Inventory, and Machine ID must be numeric.");
            alert.showAndWait();
        }
    }

}
