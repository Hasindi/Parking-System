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
import model.Deliver;
import util.AddUi;
import util.CloseUi;

import java.io.IOException;
import java.util.Optional;

public class OnDeliverController implements AddUi, CloseUi {
    public AnchorPane onDelivarContext;
    public ComboBox cmbOnDelevary;
    public TableView tblOnDelevary;
    public TableColumn colVehiNo;
    public TableColumn colVehiType;
    public TableColumn colDriverName;
    public TableColumn colLeftTime;
    public Button btnAddVehicle;
    public Button btnAddDriver;
    public Button btnLogout;

    public void initialize(){
        colVehiNo.setCellValueFactory(new PropertyValueFactory("vehicleNo"));
        colVehiType.setCellValueFactory(new PropertyValueFactory("vehicleType"));
        colDriverName.setCellValueFactory(new PropertyValueFactory("driverName"));
        colLeftTime.setCellValueFactory(new PropertyValueFactory("leftTime"));


        cmbOnDelevary.getItems().add("In Parking");
        cmbOnDelevary.getItems().add("On Delivery");

        cmbOnDelevary.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String s1 = String.valueOf(newValue);

            if(s1.equals("In Parking")){
                Stage stage = (Stage) onDelivarContext.getScene().getWindow();

                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/inParking.fxml"))));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        ObservableList obList = FXCollections.observableArrayList();
        for (Deliver d: Database.deliverTable) {

            Deliver d1= new Deliver(d.getVehicleNo(),d.getVehicleType(),d.getDriverName(),d.getLeftTime());
            obList.add(d1);

        }

        tblOnDelevary.setItems(obList);

    }

    public void AddVehidOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(onDelivarContext);
        loadUi("AddVehicle");
    }

    public void AddDriverOnAction(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(onDelivarContext);
        loadUi("AddDriver");
    }

    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING,"Are You Sure...?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            Stage stage = (Stage) onDelivarContext.getScene().getWindow();
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
        Stage stage = (Stage)a.getScene().getWindow();
        stage.close();

    }
}
