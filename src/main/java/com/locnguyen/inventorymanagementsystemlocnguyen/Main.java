package com.locnguyen.inventorymanagementsystemlocnguyen;
/**
 * @author Loc Nguyen
 */
/**
 * JavaDoc Folder Location: src/main/JavaDoc
 *
 * RUNTIME ERROR notes is inside MainScreenController.java
 * LOGICAL ERROR notes is inside AddPartScreenController.java
 *
 *          FUTURE ENHANCEMENT:
 * The application right now is perfect for small scale dataset. But with large scale dataset, many features of the current application
 * is tedious and inefficient. For example, the application is not able to remove all the associated parts of a product in one click, we have to remove each
 * part one by one. The same can be said when adding large amount of associated parts to a product. A possible future enhancement to the program would be
 * to implement a select all button that would select all the associated parts of a product, or an option to select multiple parts using checkboxes.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Main method. This set the program execution start point.
 */
public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mainScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
