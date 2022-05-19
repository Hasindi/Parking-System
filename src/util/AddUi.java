package util;

import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public interface AddUi {
    void loadUi(String location) throws IOException;


    void CloseWindowUi(AnchorPane a) throws IOException;

}
