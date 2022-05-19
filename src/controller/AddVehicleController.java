package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Vehicle;
import util.AddUi;
import util.CloseUi;

import java.io.IOException;

public class AddVehicleController implements AddUi, CloseUi {
    public AnchorPane addVehicleContext;
    public TextField txtVehiNo;
    public TextField txtPsengers;
    public TextField txtWeight;
    public ComboBox cmbVehicleType;

    public void initialize(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Van");
        obList.add("Bus");
        obList.add("Cargo Lorry");

        cmbVehicleType.setItems(obList);

    }

    public void AddVehicleOnAction(ActionEvent actionEvent) {
        try {
            Database.vehicleTable.add
                    (new Vehicle(String.valueOf(txtVehiNo.getText()),
                            (String) cmbVehicleType.getValue(),
                            Double.parseDouble(txtWeight.getText()),
                            Integer.parseInt(txtPsengers.getText())));

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vehicle Added Successfully...!");
            alert.show();

            clearText();

        }catch(Throwable t) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Maximum Weight should be Double Type");
            warning.showAndWait();
            txtWeight.clear();
        }
    }

    private void clearText() {
        txtVehiNo.clear();
        txtWeight.clear();
        txtPsengers.clear();
        cmbVehicleType.getSelectionModel().clearSelection();
    }

    public void CancelAddVehicle(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(addVehicleContext);

        loadUi("InParking");
    }

    @Override
    public void loadUi(String location) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void CloseWindowUi(AnchorPane addVehicleContext) throws IOException {
        Stage stage = (Stage) addVehicleContext.getScene().getWindow();
        stage.close();
    }
}
