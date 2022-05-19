package controller;

import db.Database;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Deliver;
import model.Park;
import util.AddUi;
import util.CloseUi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ParkingVehicleController implements AddUi, CloseUi {
    public AnchorPane parkingVehicleContext;
    public Button btnDelivery;
    public ComboBox cmbSelectVehicle;
    public TextField txtVehicleType;
    public TextField txtSlot;
    public ComboBox cmbDriver;
    public Button btnManagerLogin;
    public Button btnPark;
    public TextField txtDateAndTime;
    public Label lblSlot;


    boolean park = false;
    boolean deliver = false;


    public void initialize(){

        try {

            cmbSelectVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                vehicleType(newValue);
            });

        }catch(Exception e){

        }

        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Sumith Kumara");
        obList.add("Amila Pathirana");
        obList.add("Jithmal Perera");
        obList.add("Sumith Dissanayaka");
        obList.add("Sumanasiri Herath");
        obList.add("Awantha Fernando");
        obList.add("Charith Sudara");
        obList.add("Prashan Dineth");
        obList.add("Chethiya Dilan");
        obList.add("Dushantha Perera");
        obList.add("Sumith Udayanga");
        obList.add("Dinesh Udara");
        obList.add("Udana Chathuranga");
        obList.add("Mohommad Riaz");
        obList.add("Sadun Kumara");
        obList.add("Priyanga Perera");
        cmbDriver.setItems(obList);

        ObservableList<String> obList2 = FXCollections.observableArrayList();
        obList2.add("NA-3434");
        obList2.add("KA-4563");
        obList2.add("58-3567");
        obList2.add("GF-4358");
        obList2.add("CCB-3568");
        obList2.add("LM-6679");
        obList2.add("QA-3369");
        obList2.add("KB-3668");
        obList2.add("JJ-9878");
        obList2.add("GH-5772");
        obList2.add("XY-4456");
        obList2.add("YQ-3536");
        obList2.add("CBB-3566");
        obList2.add("QH-3444");
        cmbSelectVehicle.setItems(obList2);

        DateTime();
    }

    private void vehicleType(Object newValue) {
        String s1 = String.valueOf((newValue));

        for(int i=0; i<Database.vehicleTable.size(); i++){
            String no = Database.vehicleTable.get(i).getVehicleNo();
            if(s1.equals(no)){
                txtVehicleType.setText(Database.vehicleTable.get(i).getVehicleType());
            }
        }

        switch (txtVehicleType.getText()){
            case "Van": {
                setSlot("Van");
            }break;


            case "Bus": {
                setSlot("Bus");
            }break;


            case "Cargo Lorry": {
                setSlot("Cargo Lorry");
            }break;
        }

    }

    private void setSlot(String vehicleType) {
        for(int i=0; i<Database.slotTable.size(); i++){
            for (int j=0; j<Database.slotTable.size(); j++){
                if(vehicleType.equals(Database.slotTable.get(i).getVehicleType()) &&
                        Database.slotTable.get(i).getStatus().equals("notUse")) {
                    lblSlot.setText(Database.slotTable.get(i).getSlot());
                    return;
                }
            }
        }

        clearText();

    }

    private void clearText() {
        for(int i=0; i<Database.parkingTable.size(); i++){
            if(Database.parkingTable.get(i).getVehicleNumber().equals(cmbSelectVehicle.getValue())){
                lblSlot.setText("");
            }
        }
    }

    private void DateTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            LocalDate currentDate = LocalDate.now();
            txtDateAndTime.setText(currentDate.getYear()+"-"+currentDate.getMonthValue()
                    +"-"+ currentDate.getDayOfMonth()+ "    "+ currentTime.getHour() + ":" + currentTime.getMinute()
                    + ":"+ currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void OnDeliveryOnAction(ActionEvent actionEvent) {


        try {
            cmbSelectVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                String s2 = String.valueOf(newValue);
                btnDelivery.setDisable(false);

                for (int i=0; i<Database.deliverTable.size(); i++){
                    deliver = Database.deliverTable.get(i).getVehicleNo().contains(s2);
                    if(deliver==true){
                        btnDelivery.setDisable(false);
                    }
                }

            });

            if(deliver==false){
                if(cmbSelectVehicle.getValue()!=null && cmbDriver.getValue()!=null) {
                    String data = (String.valueOf(cmbSelectVehicle.getValue()));

                    for (int i=0; i<Database.parkingTable.size(); i++) {
                        if (Database.parkingTable.get(i).getVehicleNumber().contains(data)) {
                            Database.parkingTable.remove(i);
                        }
                    }

                    Deliver d1 = new Deliver(String.valueOf(cmbSelectVehicle.getValue()),
                            txtVehicleType.getText(),String.valueOf(cmbDriver.getValue()),txtDateAndTime.getText());
                    Database.deliverTable.add(d1);
                }
            }

            clearTextField();

            for (int i=0; i<Database.parkingTable.size(); i++) {
                if (cmbSelectVehicle.getValue().equals(Database.parkingTable.get(i).getVehicleNumber())) {
                    setNotUses(Database.parkingTable.get(i).getSlot());
                }
            }

        }catch(Exception e) {

        }

    }

    private void clearTextField() {
        txtVehicleType.clear();
        cmbSelectVehicle.getSelectionModel().clearSelection();
        cmbDriver.getSelectionModel().clearSelection();
    }

    private void setNotUses(String slot) {
        for (int i=0; i<Database.slotTable.size(); i++){
            if (Database.slotTable.get(i).getSlot().equals(slot)){
                Database.slotTable.get(i).setStatus("NotUses");
            }
        }
    }

    public void ManagementLogOnAction(ActionEvent actionEvent) throws IOException {
        Database.anchorPane = parkingVehicleContext;

        loadUi("LoginForm");
    }

    public void ParkVehicleOnAction(ActionEvent actionEvent) {

        for(int i=0; i<Database.slotTable.size(); i++) {
            if (lblSlot.getText().equals(Database.slotTable.get(i).getSlot())) {
              Database.slotTable.get(i).setStatus("Uses");
            }
        }

        cmbSelectVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnPark.setDisable(false);
            String s3 = String.valueOf(newValue);

            for (int i=0; i<Database.parkingTable.size(); i++){
                park = Database.parkingTable.get(i).getVehicleNumber().contains(s3);
                if(park==true){
                    btnPark.setDisable(true);
                }
            }

        });

        if(park==false){
            if(cmbSelectVehicle.getValue()!=null){
                String data = (String.valueOf(cmbSelectVehicle.getValue())) ;
                Park p = new Park(String.valueOf(cmbSelectVehicle.getValue()),txtVehicleType.getText(),lblSlot.getText(),txtDateAndTime.getText());
                Database.parkingTable.add(p);

                for(int i=0; i<Database.deliverTable.size(); i++){
                    if(Database.deliverTable.get(i).getVehicleNo().contains(data)){
                        Database.deliverTable.remove(i);
                    }
                }

            }
        }

        clearTextField();

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
    public void CloseWindowUi(AnchorPane x) throws IOException {
        Stage stage = (Stage) x.getScene().getWindow();
        stage.close();
    }
}
