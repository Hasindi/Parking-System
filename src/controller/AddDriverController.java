package controller;

import db.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Driver;
import util.AddUi;
import util.CloseUi;

import java.io.IOException;

public class AddDriverController implements AddUi, CloseUi {
    public AnchorPane addDriverContext;
    public TextField txtName;
    public TextField txtNic;
    public TextField txtLicence;
    public TextField txtAddress;
    public TextField txtContact;

    public void AddDriverOnAction(ActionEvent actionEvent) {

        try{

            if(txtName.getText().equals("")) {

                Alert warning = new Alert(Alert.AlertType.WARNING, "Data Not Aded...!");
                warning.showAndWait();

            } else {
                Database.driverTable.add(new Driver(txtName.getText(), txtNic.getText(),
                    txtLicence.getText(), txtAddress.getText(), txtContact.getText()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Driver added Successfully...!");
                alert.show();

                ClearText();
            }

        }catch(Throwable t) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Something went Wrong...!");
            warning.showAndWait();
        }
    }

    private void ClearText() {
        txtName.clear();
        txtNic.clear();
        txtLicence.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    public void CancelAddDriver(ActionEvent actionEvent) throws IOException {
        CloseWindowUi(addDriverContext);

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
    public void CloseWindowUi(AnchorPane addDriverContext) throws IOException {
        Stage stage = (Stage) addDriverContext.getScene().getWindow();
        stage.close();
    }
}
