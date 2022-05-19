package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.AddUi;
import util.CloseUi;
import java.io.IOException;
import java.util.Optional;

import static db.Database.anchorPane;

public class LoginFormController implements AddUi, CloseUi {
    public AnchorPane loginFormContext;
    public TextField txtName;
    public TextField pwdPassword;
    public Button LoginBtn;
    public Button cancelBtn;


    int attemptsLogin = 0;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        CloseWindowUi(loginFormContext);

        attemptsLogin++;
        if (attemptsLogin < 3) {
            if (txtName.getText().equals("admin") & pwdPassword.getText().equals("1234")) {

                try{
                    Stage stage = (Stage) anchorPane.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/inParking.fxml"))));

                }catch (Exception e){
                }

            }else{
                new Alert(Alert.AlertType.WARNING, "Wrong Password... Try Again...!").showAndWait();

                loadUi( "loginForm");

            }

        }else{
            txtName.setVisible(false);
            pwdPassword.setVisible(false);
        }

    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure...?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            Stage stage = (Stage) loginFormContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        }
    }

    @Override
    public void loadUi(String location) throws IOException {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void CloseWindowUi(AnchorPane x) throws IOException {
        Stage stage = (Stage) x.getScene().getWindow();
        stage.close();
    }
}
