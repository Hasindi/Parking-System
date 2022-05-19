package controller;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Park;
import util.AddUi;
import util.CloseUi;

import java.io.IOException;
import java.util.Optional;

public class InParkingController implements AddUi, CloseUi {
    public AnchorPane inParkingContext;
    public ComboBox cmbInParking;
    public Button btnAddVehicle;
    public Button btnAddDriver;
    public Button btnLogout;
    public TableView tblInParking;
    public TableColumn colVehiNo;
    public TableColumn colVehiType;
    public TableColumn colParkinhgSlot;
    public TableColumn colParkingTime;

    public void initialize(){

        colVehiNo.setCellValueFactory(new PropertyValueFactory("vehicleNumber"));
        colVehiType.setCellValueFactory(new PropertyValueFactory("vehicleType"));
        colParkinhgSlot.setCellValueFactory(new PropertyValueFactory("slot"));
        colParkingTime.setCellValueFactory(new PropertyValueFactory("parkingTime"));


        cmbInParking.getItems().add("In Parking");
        cmbInParking.getItems().add("On Delivery");

        cmbInParking.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String s1 = String.valueOf(newValue);

            if(s1.equals("On Delivery")){
                Stage stage = (Stage) inParkingContext.getScene().getWindow();

                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OnDeliver.fxml"))));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        ObservableList obList = FXCollections.observableArrayList();
        for (Park p : Database.parkingTable) {

            Park p1 = new Park(p.getVehicleNumber(), p.getVehicleType(), p.getSlot(), p.getParkingTime());
            obList.add(p1);
        }

        tblInParking.setItems(obList);

    }

    public void AddVehidOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(inParkingContext);
        loadUi("AddVehicle");
    }

    public void AddDriverOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(inParkingContext);
        loadUi("AddDriver");
    }

    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure...?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            Stage stage = (Stage) inParkingContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ParkingVehicle.fxml"))));
        }

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
    public void CloseWindowUi(AnchorPane a) throws IOException {
        Stage stage= (Stage)a.getScene().getWindow();
        stage.close();
    }
}
